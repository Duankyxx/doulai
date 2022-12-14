<template>
  <div id="CUploadSongs">
    <van-cell-group inset>
      <!--歌名-->
      <van-field v-model="serialNumber" label="序号" readonly></van-field>
      <van-field v-model="songName" label="歌名"></van-field>
      <van-field label="时长" :model-value="duration" readonly />
      <!--选择品质-->
      <van-field
          v-model="pickerValue"
          is-link
          readonly
          label="音质"
          placeholder="选择音质"
          @click="isShowOverlay = true"
      />
      <van-popup :show="isShowOverlay" round position="bottom">
        <van-picker
            :columns="columns"
            :default-index="0"
            @cancel="isShowOverlay = false"
            @confirm="onConfirm"
        />
      </van-popup>
    </van-cell-group>

    <!--加载  isShowLoading-->
    <div id="Loading" class="flexCentered" v-if="isShowLoading">
      <van-loading v-if="!isShowHint" vertical size="50px" color="#1989fa" text-color="#0094ff" text-size="20px">生成任务中...</van-loading>
      <div class="hint anim" v-if="isShowHint">
        库重复
      </div>
    </div>
    <!--加载  isShow-->
    <div id="Circle" class="flexCentered" v-if="isShowCircle">
      <div class="hint anim2" v-if="!isShowUploadMsg">
        <van-circle
            :current-rate="currentRate"
            :rate="100"
            :color="gradientColor"
            :text="currentRateMsg"
            :stroke-width="60"
        />
      </div>
      <div class="hint anim" v-if="isShowUploadMsg">
        {{uploadMsg}}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import store from "@/store";
import {reqAccurateSearch, reqGetSTS, reqUpLoadSongList, reqUpLoadSongListDetailed} from "@/api";
import Search from "@/Interface/Search";
import SoundQuality from "@/Interface/SoundQuality";
import Tools from "@/Tools/Tools";

export default defineComponent({
  name: "UploadSongs",
  props: ["data","index"],
  setup(props, content) {
    const data: Ref<any> = ref(props.data);

    //获取音频长度
    const getAudioTime = (file: any): void => {
      let url = URL.createObjectURL(file);
      let audioElement = new Audio(url);
      audioElement.addEventListener("loadedmetadata", function () {
        let dur;
        dur = (audioElement.duration/60); //3.6
        let time = Math.round((dur - Math.floor(dur)) * 60);
        duration.value = Tools.addZero(Math.floor(dur)) + ":" + Tools.addZero(time);
      });
    }

    //单曲信息
    let isShowOverlay: Ref<boolean> = ref(false);
    const columns = ["标准","高品质"];
    let songName: Ref<string> = ref(Tools.checkUpKeyWork(data.value.file.name));
    let fileName: Ref<string> = ref(`song_newList/${data.value.file.name}`);
    let duration: Ref<string> = ref("");
    let pickerValue: Ref<string> = ref("标准");
    let pickerIndex: Ref<number> = ref(0);
    let serialNumber: Ref<number> = ref((props.index+1));

    //生成并检查是否重名
    let isShowLoading: Ref<boolean> = ref(false);
    let isShowHint: Ref<boolean> = ref(false);
    let isShowCircle: Ref<boolean> = ref(false);

    let isShowUploadMsg: Ref<boolean> = ref(false);
    let uploadMsg: Ref<string> = ref("");

    //进度条
    let currentRate: Ref<number> = ref(1);
    let currentRateMsg: Ref<string> = ref("0%");
    const gradientColor = {
      '0%': '#3fecff',
      '50%': '#6149f6',
      '100%': '#ff5a5a'
    };

    watch(
        () => currentRate.value,
        (value: number) => {
          currentRateMsg.value = value + "%";
        },
        {deep: true}
    )


    const onConfirm = (value: string, index: number): void => {
      pickerValue.value = value;
      pickerIndex.value = index;
    }

    //上传....(向父组件暴露)
    const upLoad = async (): Promise<void> => {
      //检查是否有重复
      isShowLoading.value = true;
      const res = await reqAccurateSearch(songName.value);
      if (res !== "") {
        isShowHint.value = true;
        return;
      }
      isShowLoading.value = false;
      isShowCircle.value = true;

      //song_list对象
      const song_list: Search = {
        id: 0,
        song_name: songName.value,
        duration: "",
        creator: store.state.User.username,
        song_type: pickerIndex.value,
        uid: store.state.User.id,
      }
      //song_list_detailed对象
      const song_list_detailed: SoundQuality = {
        id: 0,
        song_name: songName.value,
        duration: duration.value,
        url: "",
        uid: 0,
        sound_quality: "高品质",
      }
      //发送...
      await aliOss(fileName.value, data.value.file, song_list, song_list_detailed);
    }

    //设置分片上传参数
    const options = {
      // 获取分片上传进度、断点和返回值。
      progress: (p: number) => {
        currentRate.value = Math.round((p * 100));
      },
      // 设置并发上传的分片数量。
      parallel: 1,
      // 设置分片大小。默认值为1 MB，最小值为100 KB。
      partSize: 1024 * 1024 * 1,
    };

    //连接阿里云
    const aliOss = async (fileName: string, data: object, song_list: Search, song_list_detailed: SoundQuality): Promise<void> => {
      let STSToken = await reqGetSTS(store.state.User);
      const OSS = require('ali-oss');
      const client = new OSS({
        // yourRegion填写Bucket所在地域。以华东1（杭州）为例，yourRegion填写为oss-cn-hangzhou。
        region: 'oss-cn-hangzhou',
        // 从STS服务获取的临时访问密钥（AccessKey ID和AccessKey Secret）
        accessKeyId: STSToken.credentials.accessKeyId,
        accessKeySecret: STSToken.credentials.accessKeySecret,
        //STSToken
        stsToken: STSToken.credentials.securityToken,
        // 填写Bucket名称。
        bucket: 'udnsunusn'
      });

      try {
        let result = await client.multipartUpload(fileName, data, {...options});          //AJAX
        let url = result.res.requestUrls[0];
        let index = url.indexOf('?uploadId=');
        url = url.slice(0, index);
        try {
          //上传song_list
          let id = (await reqUpLoadSongList(song_list))[0].id;    //AJAX
          //上传song_list_detailed
          song_list_detailed.url = url;
          song_list_detailed.uid= id;
          await reqUpLoadSongListDetailed(song_list_detailed);    //AJAX
          //上传成功
          isShowUploadMsg.value = true;
          uploadMsg.value = "上传成功";
        } catch (e) {
          //加载层
          store.state.isShowSchedule = false;
          store.state.Schedule = 0;
          console.log(e);
          //有重复歌名
          isShowUploadMsg.value = true;
          uploadMsg.value = "有重复歌名";
          return;
        }
      } catch (e) {
        console.log(e);
        //
        isShowUploadMsg.value = true;
        uploadMsg.value = "网络错误";
      }
      //加载层
      currentRate.value = 0;
    }


    getAudioTime(data.value.file);
    return {
      currentRateMsg,isShowUploadMsg,uploadMsg,fileName,songName,duration,pickerValue,pickerIndex,isShowOverlay,columns,serialNumber,isShowLoading,isShowHint,isShowCircle,currentRate,gradientColor,
      onConfirm,upLoad
    }
  }
})
</script>

<style lang="scss" scoped>
#CUploadSongs {
  //outline: 1px solid blue;
  position: relative;

  #Circle {
    position: absolute;
    top: 0;
    left: 0;
    width: calc(100% - 28px);
    height: 175px;
    background-color: rgba(31, 31, 31, 0.7);
    margin: 0 14px;
    border-radius: 12px;

    .hint {
      width: 80px;
      height: 80px;
      padding: 10px;
      background-color: #f3f3f3;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-radius: 14px;
    }
  }

  #Loading {
    position: absolute;
    top: 0;
    left: 0;
    width: calc(100% - 28px);
    height: 175px;
    background-color: rgba(31, 31, 31, 0.7);
    margin: 0 14px;
    border-radius: 12px;

    .hint {
      width: 80px;
      height: 80px;
      background-color: #f3f3f3;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-radius: 14px;
    }
  }
}

.anim {
  animation: Anim 1s ease-in-out 1s forwards;
}
@keyframes Anim {
  0% {

  }
  80% {
    height: 90%;
    width: 90px;
    background-color: rgba(255, 255, 255, 0.2);
  }
  100% {
    height: 80%;
    width: 80px;
    margin-left: calc(100% - 120px);
    background-color: rgba(255, 255, 255, 0.6);
    border-radius: 10px;
  }
}
.anim2 {
  animation: Anim2 3s ease-in-out forwards infinite;
}
@keyframes Anim2 {
  0% {
    width: 80px;
    height: 80px;
  }
  50% {
    width: 100px;
    height: 100px;
  }
  100% {
    width: 80px;
    height: 80px;
  }
}
</style>