<template>
  <div id="SearchSongList">
    <!--渲染列表-->
    <div id="song_list" v-for="(i, index) in store.state.search_list" :key="i.id">
      <div id="list_cont" @click="requestSong(i.id, i.song_name)">
        <!--歌名-->
        <p>
          <SongName v-model:songname="i.song_name" width="100%" height="21px"></SongName>
        </p>
        <!--时长-->
        <div id="duc">
          时长:{{i.duration}}
        </div>
      </div>
      <van-divider style="color: #c3c3c3;background-color: #c3c3c3;margin: 5px 0"></van-divider>
    </div>

    <!--音质弹出层-->
    <PopUpSoundQuality ref="popUpSoundQuality"></PopUpSoundQuality>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import store from "@/store";
import SongPlayer from "@/components/SongPlayer.vue";
import PopUpSoundQuality from "@/components/PopUpSoundQuality.vue";
import SongName from "@/components/SongName.vue";

export default defineComponent({
  name: "SearchSongList",
  components: {SongName, PopUpSoundQuality, SongPlayer},
  setup() {
    let popUpSoundQuality: Ref<any> = ref();

    const requestSong = (id: number, song_name: string): void => {
      popUpSoundQuality.value.requestSong(id, song_name);
    }

    return {
      store,popUpSoundQuality,requestSong
    }
  }
})
</script>

<style lang="scss" scoped>
#SearchSongList {
  width: 100%;
  //border: 1px solid red;
  box-sizing: border-box;

  //列表
  #song_list {
    //background-color: pink;
    height: 60px;
    padding: 5px 10px;

    //内容
    #list_cont {
      width: 100%;
      height: calc(100% - 11px);

      &>p {
        //outline: 1px solid red;
        width: 100%;
        height: 21px;
        overflow: hidden;
      }

      &>#duc {
        height: calc(100% - 21px);
        display: flex;
        align-items: center;
        padding: 0 10px;
        font-size: 14px;
      }
    }
  }
}
</style>