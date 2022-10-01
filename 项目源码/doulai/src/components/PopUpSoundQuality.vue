<template>
  <!--音质弹出层-->
  <van-action-sheet v-model:show="actionShow" :closeable="false" title="选择音质">
    <div id="soundQuality">
      <p id="title">{{song_name}}</p>
      <div v-for="(i, index) in soundQuality" :key="i.id" class="font_black font_24" id="p">
        {{i.sound_quality}}<p @click="onSelect(i)">标准音质</p><a id="a" @click="addSongList(i)">加入播放列表</a>
      </div>

      <!--加载层-->
      <div id="Loading" v-show="store.state.isLoading.PopUpSoundQuality">
        <van-loading size="50" color="#1989fa" />
      </div>
    </div>
  </van-action-sheet>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import SoundQuality from "@/Interface/SoundQuality";
import store from "@/store";
import { reqRequestSong } from "@/api";
import Search from "@/Interface/Search";
import { Toast } from 'vant';
import 'vant/es/toast/style';

export default defineComponent({
  name: "PopUpSoundQuality",
  setup() {
    //加载层
    store.state.isLoading.PopUpSoundQuality = false;
    //当前选中的歌曲的音质选项
    let soundQuality: Ref<Array<SoundQuality>> = ref([]);
    //歌名
    let song_name: Ref<string> = ref('');

    //弹出层控制
    let actionShow: Ref<boolean> = ref(false);
    //向父元素暴露方法
    const requestSong = (id: number,songName: string): void => {
      song_name.value = songName;
      soundQuality.value = [];
      //弹出
      actionShow.value = true;
      //加载层
      store.state.isLoading.PopUpSoundQuality = true;
      reqRequestSong(id).then((res: Array<SoundQuality>) => {
        soundQuality.value = [...res];
        //加载层
        store.state.isLoading.PopUpSoundQuality = false;
      });
    }

    //选中音质并且播放歌曲
    const onSelect = (i: Ref<SoundQuality>): void => {
      store.state.nowPlaySong = {...i};
      actionShow.value = false;

      //加入歌单
      store.state.nowPlaySongList.push(i);
      //播放
      setTimeout(() => {
        store.state.PlayerPause = true;
      },1000);
    }

    //加入播放列表
    const addSongList = (i :Search): void => {
      store.state.nowPlaySongList.push(i);
      actionShow.value = false;
      Toast({
        message: "添加到歌单",
        icon: 'passed',
      });
    }

    return {
      soundQuality,actionShow,song_name,store,
      onSelect,requestSong,addSongList
    }
  }
})
</script>

<style lang="scss" scoped>
//音质弹出层
#soundQuality {
  padding: 10px 10px 20px;
  min-height: 100px;
//子元素设定

  &>div {
    width: 100%;
    text-align: center;
    border-bottom: 1px solid #c3c3c3;
  }

  #title {
    width: 100%;
    text-align: center;
    overflow: hidden;
  }

  #p {
    position: relative;

    #a {
      position: absolute;
      right: 10px;
      top: 10px;
      font-size: 12px;
    }
  }
}
</style>