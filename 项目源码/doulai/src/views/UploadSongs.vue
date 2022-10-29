<template>
  <div id="UploadSongs" class="White">
    <!--头-->
    <Title title="上传歌曲">
      <template #default>
        <van-button id="right" to="batchUploadSongs" plain type="success" size="small" round>批量</van-button>
      </template>
    </Title>

    <!--表单-->
    <div id="fm">
      <van-cell-group inset>
        <!--歌名-->
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

      <!--文件-->
      <div id="file">
        <van-uploader accept="audio/*" :after-read="afterRead">
          <van-button icon="plus" type="primary">选择文件</van-button>
        </van-uploader>
        <van-button type="success" @click="updata" :disabled="isDisabled">确认上传</van-button>
      </div>
    </div>

    <!--加载层-->
    <Schedule></Schedule>
    <!--Loading-->
    <Loading></Loading>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import {uploadSongs} from "@/ali-oss/request";
import Title from "@/components/Title.vue";
import Search from "@/Interface/Search";
import store from "@/store";
import SoundQuality from "@/Interface/SoundQuality";
import Loading from "@/components/Loading.vue";
import {Notify, Toast} from "vant";
import router from "@/router";
import Schedule from "@/components/Schedule.vue";
import {reqAccurateSearch} from "@/api";
import Tools from "@/Tools/Tools";

export default defineComponent({
  name: "UploadSongs",
  components: {Schedule, Loading, Title},
  setup() {
    //检查登录状态
    if (!store.state.isLogin) {
      Notify({ type: 'primary', message: '请先登录!' });
      router.push("/login");
    }

    //音质选择
    const columns = ["标准","高品质"];
    let isShowOverlay: Ref<boolean> = ref(false);
    let pickerValue: Ref<string> = ref("标准");
    let pickerIndex: Ref<number> = ref(0);
    const onConfirm = (value: string, index: number): void => {
      pickerValue.value = value;
      pickerIndex.value = index;
    }

     //文件读取完成后
     let songName: Ref<string> = ref("");
     let fileName: Ref<string> = ref("");
     let data: Ref<any> = ref(null);
     let duration: Ref<string> = ref("");
     let isDisabled: Ref<boolean> = ref(false);

     const clr = (): void => {
       songName.value = "";
       fileName.value = "";
       data.value = null;
       duration.value = "";
       isDisabled.value = true;
     }

     const afterRead = (file: any): void => {
       //检查是否为音频文件
       if (!Tools.fileType(file)) {
        clr();
        return;
       }
       console.log(file);
       data.value = file.file;
       songName.value = Tools.checkUpKeyWork(data.value.name);  //  去掉特殊关键字
       fileName.value = `song_newList/${data.value.name}`;
       getAudioTime(data.value);
     }

    //监听
     watch(
         () => data.value,
         (value) => {
           value === null ? isDisabled.value=true : isDisabled.value = false;
         },
         {deep: true, immediate: true}
     )

     //获取音频文件的长度
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


     //上传
     const updata = async (): Promise<void> => {
       //检查是否有重复
       store.state.isShowOverlay = true;
       const res = await reqAccurateSearch(songName.value);
       store.state.isShowOverlay = false;
       if (res !== "") {
         Toast.fail('有重复歌名');
         return;
       }

       //song_list对象
       const song_list: Search = {
         id: 0,
         song_name: songName.value,
         duration: '',
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
       //加载层
       store.state.isShowSchedule = true;
       //向阿里云发送及其同步
       await uploadSongs(fileName.value, data.value, song_list, song_list_detailed);
       //清空
       clr();
     }
    return {
      fileName,duration,songName,data,isDisabled,columns,isShowOverlay,pickerValue,
      afterRead,updata,onConfirm
    }
   }
})
</script>

<style lang="scss" scoped>
#UploadSongs {
  min-height: 100vh;

  #file {
    //outline: 1px solid blue;
    padding: 20px 0;
    width: 100%;
    height: 50px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
}

#fm {
  //outline: 1px solid red;
  padding: 20px 0;
}

#right {
  z-index: 999;
  position: absolute;
  top: calc(50% - 16px);
  right: 10px;
}
</style>