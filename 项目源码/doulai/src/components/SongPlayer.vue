<template>
  <div tabindex="-1" ref="SongPlayer" id="SongPlayer" :style="{bottom: bottom}" @blur="blurConductList">
    <div ref="SongPlayer_box" id="SongPlayer_box">
      <!--隐藏的系统音频标签-->
      <audio v-show="false" v-if="isLoad" ref="audio" :src="store.state.nowPlaySong.url" controls="controls"></audio>
      <!--旋转图片盒子-->
      <div id="cover">
        <div id="coverImg">
          <img alt="" src="../image/cover.jpeg">
        </div>
        <div id="coverCircle">
          <van-circle id="circle" :speed="100" :current-rate="playbackProgress" size="55px"></van-circle>
        </div>
      </div>
      <!--歌名-->
      <div id="SongName">
        <a v-if="isLoad" class="font_black">{{store.state.nowPlaySong.song_name}}</a>
      </div>
      <!--当前播放列表-->
      <div id="songPlayList" @click="showList">
        <svg t="1664020007934" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2229" width="200" height="200"><path d="M927.1552 244.7872H289.8176a35.39456 35.39456 0 1 1 0-70.79424h637.3376a35.39456 35.39456 0 1 1 0 70.79424zM146.432 209.408a42.496 42.496 0 1 0-42.496 42.496 42.496 42.496 0 0 0 42.496-42.496z m780.73856 341.5808H289.8176a35.39968 35.39968 0 1 1 0-70.79424h637.3376a35.39968 35.39968 0 1 1 0 70.77888zM146.432 515.584a42.496 42.496 0 1 0-42.496 42.496 42.496 42.496 0 0 0 42.496-42.496z m780.73856 332.73344H289.8176a35.39968 35.39968 0 1 1 0-70.79424h637.3376a35.39968 35.39968 0 1 1 0 70.784zM146.432 812.90752a42.496 42.496 0 1 0-42.496 42.496 42.496 42.496 0 0 0 42.496-42.496z" fill="#595BB3" p-id="2230"></path></svg>
      </div>
      <!--播放暂停按钮-->
      <div id="PlayerButton">
        <svg @click="onPlayer" v-show="!store.state.PlayerPause" t="1664020289219" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2378" width="200" height="200"><path d="M514.56 952.32a437.76 437.76 0 1 1 437.76-437.76 438.272 438.272 0 0 1-437.76 437.76z m0-804.91008a367.1552 367.1552 0 1 0 367.1552 367.15008 367.56992 367.56992 0 0 0-367.1552-367.15008zM445.37344 685.37344a75.61216 75.61216 0 0 1-75.33056-75.45344V417.96096a75.33056 75.33056 0 0 1 113.00864-65.23392l166.24128 95.9744a75.34592 75.34592 0 0 1 0 130.4832l-166.25152 95.96928a75.09504 75.09504 0 0 1-37.66784 10.21952z m0.03072-272.18944a4.6592 4.6592 0 0 0-2.38592 0.6912 4.38784 4.38784 0 0 0-2.36544 4.096v191.95904a4.72576 4.72576 0 0 0 7.08096 4.096l166.2464-95.9744a4.73088 4.73088 0 0 0 0-8.192L447.73888 413.87008a4.53632 4.53632 0 0 0-2.33472-0.68608z" fill="#595BB3" p-id="2379"></path></svg>
        <svg @click="onPause" v-show="store.state.PlayerPause" t="1664020467960" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2527" width="200" height="200"><path d="M512 957.44a445.44 445.44 0 1 1 445.44-445.44 445.952 445.952 0 0 1-445.44 445.44z m0-819.03104A373.59616 373.59616 0 1 0 885.59616 512 374.016 374.016 0 0 0 512 138.40896z m-102.62016 576.512a35.92192 35.92192 0 0 1-35.92192-35.92192V372.46976a35.92192 35.92192 0 1 1 71.84384 0v306.5344a35.92192 35.92192 0 0 1-35.92192 35.92192z m205.952 0a35.92192 35.92192 0 0 1-35.92192-35.92192V372.46976a35.92192 35.92192 0 1 1 71.84384 0v306.5344a35.92192 35.92192 0 0 1-35.92192 35.92192z" fill="#595BB3" p-id="2528"></path></svg>
      </div>
    </div>

    <!--播放列表-->
    <div id="conductList">
      <ConductList v-if="showConductList_two"></ConductList>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import store from "@/store";
import ConductList from "@/components/ConductList.vue";
import Search from "@/Interface/Search";
import { Toast } from 'vant';
import 'vant/es/toast/style';

export default defineComponent({
  name: "SongPlayer",
  components: {ConductList},
  props: ['bottom'],
  setup() {
    //绑定元素
    let audio: Ref<HTMLAudioElement | null> = ref(null);
    let playbackProgress: Ref<number> = ref(0);

    //设置播放器距离底部
    let bottom: Ref<string> = ref('80px');
    watch(
        () => store.state.PlayerBottom,
        (value) => {
          bottom.value = value;
        },
        {deep: true, immediate: true}
    )

    //检查是否有播放内容
    let isLoad: Ref<boolean> = ref(false);
    watch(
        () => store.state.nowPlaySong,
        (value) => {
          if (value === undefined) {
            isLoad.value = false;
          } else {
            isLoad.value = true;
          }
        },
        {deep: true, immediate: true}
    );

    //播放按钮控
    const onPlayer = ():  void => {
      if (!isLoad.value) return;
      store.state.PlayerPause = true;
    }
    const onPause = ():  void => {
      if (!isLoad.value) return;
      store.state.PlayerPause = false;
    }

    //监听播放
    watch(
        () => store.state.PlayerPause,
        (value) => {
          if (value) {  //  播放
            console.log("播放");
            //绑定进度条事件
            audio.value!.ontimeupdate = () => {
              playbackProgress.value = Math.round(Math.round(audio.value!.currentTime)/Math.round(audio.value!.duration)*100);
            }
            //绑定结束事件
            audio.value!.onended = () => {
              //关闭
              store.state.PlayerPause = false;
              //是否开启列表循环
              nextSong();
            }
            audio.value!.play();
          } else {  //  暂停
            console.log("暂停");
            audio.value!.pause();
          }
        },
        {deep: true}
    )

    //列表循环
    const nextSong = ():  void => {
      let nowID: number = store.state.nowPlaySong.id;
      let nextSongIndex: number = 0;
      store.state.nowPlaySongList.forEach((i: Search, index: number) => {
          if (nowID === i.id) {
            if (store.state.nowPlaySongList.length === (index+1)) return;
            nextSongIndex = index + 1;
          }
      });
      store.state.nowPlaySong = {...store.state.nowPlaySongList[nextSongIndex]};
      setTimeout(() => store.state.PlayerPause = true,100);
    }

    //点击list后需要设置的样式
    let SongPlayer: Ref<HTMLElement | null> = ref(null);
    let SongPlayer_box: Ref<HTMLElement | null> = ref(null);
    let showConductList: Ref<boolean> = ref(false);
    let showConductList_two: Ref<boolean> = ref(false);
    const showList = (): void => {
      showConductList.value = !showConductList.value;
    }
    const blurConductList = (): void => {
      console.log("离开");
      showConductList.value = false;
    }
    watch(
        () => showConductList.value,
        (value) => {
          if (value) {
            console.log("打开");
            SongPlayer.value!.style.height = "360px";
            setTimeout(() => {
              SongPlayer.value!.style.borderRadius = "24px";
              SongPlayer_box.value!.style.borderRadius = "24px";
              showConductList_two.value = showConductList.value;
            },500)
          } else {
            console.log("关闭");
            SongPlayer.value!.style.height = "60px";
            showConductList_two.value = showConductList.value;
            setTimeout(() => {
              SongPlayer.value!.style.borderRadius = "50px";
              SongPlayer_box.value!.style.borderRadius = "50px";
            },500)
          }
        },
        {deep: true}
    )

    //监听播放列表(去重)
    watch(
        () => store.state.nowPlaySongList,
        (value, oldValue, onCleanup) => {
          //列表里有东西
          if (store.state.nowPlaySong === undefined && store.state.nowPlaySongList.length > 0) {
            store.state.nowPlaySong = {...store.state.nowPlaySongList[0]}
            isLoad.value = true;
          }
          value.forEach((i: Search, index: number) => {
            value.forEach((j: Search, jndex: number) => {
              if (index !== jndex && i.id === j.id) {
                value.splice(jndex,1);
                Toast('列表已存在');
              }
            })
          })
        },
        {deep: true}
    )

    return {
      store,isLoad,bottom,audio,playbackProgress,SongPlayer,SongPlayer_box,showConductList,showConductList_two,
      onPlayer,onPause,blurConductList,showList
    }
  }
})
</script>

<style lang="scss" scoped>
#conductList {
  //background-color: pink;
  width: 100%;
  height: 300px;
  overflow: hidden;
}

#SongPlayer {
  //子元素排列
  display: flex;
  flex-direction: column-reverse;
  justify-content: center;
  align-content: center;

  background-color: #e3e3e3;
  width: 90%;
  height: 60px;
  border-radius: 50px;
  position: fixed;
  bottom: 80px;
  left: 5%;
  overflow: hidden;
  transition: 500ms;

  //盒子
  #SongPlayer_box {
    transition: 500ms;
    width: 100%;
    height: 60px;
    position: relative;
    display: flex;

    //audio
    &>audio {
      position: absolute;
      top: 0;
    }

    //播放暂停按钮
    #PlayerButton {
      width: 70px;
      height: 60px;
      display: flex;
      justify-content: center;
      align-items: center;

      &>svg {
        width: 30px;
        height: 30px;
      }
    }

    //当前播放列表
    #songPlayList {
      width: 70px;
      height: 60px;
      display: flex;
      justify-content: center;
      align-items: center;

      &>svg {
        width: 30px;
        height: 30px;
      }
    }

    //歌名
    #SongName {
      width: 150px;
      height: 60px;
      display: flex;
      justify-content: center;
      align-items: center;
      overflow: hidden;
    }

    //旋转图片盒子
    #cover {
      width: 60px;
      height: 60px;
      margin: 0 5px;
      position: relative;

      #coverImg {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;

        &>img {
          width: 50px;
          border-radius: 50%;
        }
      }

      #coverCircle {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }
}
</style>