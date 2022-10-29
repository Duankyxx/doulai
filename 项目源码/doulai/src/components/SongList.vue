<template>
  <div id="SongList">
    <!--渲染列表-->
    <div v-for="(i, index) in songList" id="item">

      <!--左-->
      <div id="left">
        <p>
          <SongName v-model:songname="i.song_name" width="100%" height="21px"></SongName>
        </p>
        <div id="bottom">
          时长:{{i.duration}}
        </div>
      </div>

      <!--右-->
      <div id="right">
        <svg @click="requestSong(i.id, i.song_name)" t="1664634241483" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7742" width="200" height="200"><path d="M514.56 952.32a437.76 437.76 0 1 1 437.76-437.76 438.272 438.272 0 0 1-437.76 437.76z m0-804.91008a367.1552 367.1552 0 1 0 367.1552 367.15008 367.56992 367.56992 0 0 0-367.1552-367.15008zM445.37344 685.37344a75.61216 75.61216 0 0 1-75.33056-75.45344V417.96096a75.33056 75.33056 0 0 1 113.00864-65.23392l166.24128 95.9744a75.34592 75.34592 0 0 1 0 130.4832l-166.25152 95.96928a75.09504 75.09504 0 0 1-37.66784 10.21952z m0.03072-272.18944a4.6592 4.6592 0 0 0-2.38592 0.6912 4.38784 4.38784 0 0 0-2.36544 4.096v191.95904a4.72576 4.72576 0 0 0 7.08096 4.096l166.2464-95.9744a4.73088 4.73088 0 0 0 0-8.192L447.73888 413.87008a4.53632 4.53632 0 0 0-2.33472-0.68608z" fill="#707070" p-id="7743"></path></svg>
      </div>
    </div>

    <!--音质弹出层-->
    <PopUpSoundQuality ref="popUpSoundQuality"></PopUpSoundQuality>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import Search from "@/Interface/Search";
import PopUpSoundQuality from "@/components/PopUpSoundQuality.vue";
import Loading from "@/components/Loading.vue";
import SongName from "@/components/SongName.vue";

export default defineComponent({
  name: "SongList",
  components: {SongName, Loading, PopUpSoundQuality},
  props: ["songList"],
  setup(props, content) {
    let songList: Array<Search> = props.songList;

    //弹出层
    let popUpSoundQuality: Ref<any> = ref(null);
    const requestSong = (id: number,song_name: string): void => {
      popUpSoundQuality.value.requestSong(id, song_name);
    }

    return {
      songList,popUpSoundQuality,
      requestSong
    }
  }
})
</script>

<style lang="scss" scoped>
#SongList {
  //outline: 1px solid red;
  padding: 10px 0;
  width: 100%;
  z-index: 1;

  #item {
    //outline: 1px solid red;
    background-color: #fff;
    border-radius: 12px;
    margin-bottom: 10px;
    width: calc(100% - 20px);
    height: 50px;
    padding: 5px 10px;
    display: flex;

    #left {
      //outline: 1px solid red;
      width: 60%;
      height: 100%;

      &>p {
        //outline: 1px solid blueviolet;
        width: calc(100% - 20px);
        padding: 0 10px;
        height: 40%;
        //overflow: hidden;
      }

      &>#bottom {
        //background-color: pink;
        //outline: 1px solid blue;
        border-top: 1px solid rgba(128, 128, 128, 0.3);
        padding: 0 10px;
        height: 60%;
        display: flex;
        align-items: center;
        font-size: 14px;
      }
    }
    #right {
      //outline: 1px solid blue;
      width: 40%;
      height: 100%;
      display: flex;
      flex-direction: row-reverse;
      align-items: center;

      &>svg {
        width: 30px;
        height: 30px;
      }
    }
  }
}
</style>