<template>
  <div id="ConductList">
    <div id="title">
      <p  id="tit">歌曲列表</p>
    </div>
    <div id="cont">
      <div id="item" ref="item" v-for="(i, index) in store.state.nowPlaySongList" :key="i.id" :class="`item${i.id}`">
        <!--图片-->
        <div id="img">
          <img alt="" src="../image/cover.jpeg">
        </div>
        <!--文字-->
        <div id="text">
          <div id="text_font">
            <div id="song_name">
              <SongName v-model:songname="i.song_name" width="100%" height="21px"></SongName>
            </div>
            <div id="duration">{{i.duration}}</div>
          </div>

          <!--按钮-->
          <div id="button_box">
            <svg @click="removeItem(index)" t="1664167278503" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2080" width="200" height="200"><path d="M517.12 962.56a445.44 445.44 0 1 1 445.44-445.44 445.952 445.952 0 0 1-445.44 445.44z m0-819.03104A373.59616 373.59616 0 1 0 890.71616 517.12 374.016 374.016 0 0 0 517.12 143.52896z m190.3872 409.51808h-380.7744a35.92192 35.92192 0 1 1 0-71.84384h380.7744a35.92192 35.92192 0 1 1 0 71.83872z" fill="#595BB3" p-id="2081"></path></svg>
            <svg @click="player(i)" t="1664167427892" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2229" width="200" height="200"><path d="M514.56 952.32a437.76 437.76 0 1 1 437.76-437.76 438.272 438.272 0 0 1-437.76 437.76z m0-804.91008a367.1552 367.1552 0 1 0 367.1552 367.15008 367.56992 367.56992 0 0 0-367.1552-367.15008zM445.37344 685.37344a75.61216 75.61216 0 0 1-75.33056-75.45344V417.96096a75.33056 75.33056 0 0 1 113.00864-65.23392l166.24128 95.9744a75.34592 75.34592 0 0 1 0 130.4832l-166.25152 95.96928a75.09504 75.09504 0 0 1-37.66784 10.21952z m0.03072-272.18944a4.6592 4.6592 0 0 0-2.38592 0.6912 4.38784 4.38784 0 0 0-2.36544 4.096v191.95904a4.72576 4.72576 0 0 0 7.08096 4.096l166.2464-95.9744a4.73088 4.73088 0 0 0 0-8.192L447.73888 413.87008a4.53632 4.53632 0 0 0-2.33472-0.68608z" fill="#595BB3" p-id="2230"></path></svg>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import store from "@/store";
import Search from "@/Interface/Search";
import SongName from "@/components/SongName.vue";

export default defineComponent({
  name: "ConductList",
  components: {SongName},
  setup() {

    //删除事件
    const removeItem = (index: number): void => {
      console.log("删除");
      store.state.nowPlaySongList.splice(index, 1);
      console.log(store.state.nowPlaySongList);
    }

    //播放事件
    const player = (i: Search): void => {
      //添加到正在播放
      store.state.nowPlaySong = {...i};
      //开始播放

      setTimeout(() => {
        store.state.PlayerPause = false;
        store.state.PlayerPause = true;
      },1000);
    }


    //监听当前播放到歌曲并渲染页面
    let item: any = ref(null);
    watch(
        () => store.state.nowPlaySong,
        (value) => {
          if (value === undefined) return;
          setTimeout(() => {
            let id: number = value.id;
            store.state.nowPlaySongList.forEach((i, index: number) => {
              let itemID: number = Math.round((item.value![index].className).slice(4));
              if (id === itemID) {
                item.value![index].style.backgroundColor = "rgba(255, 192, 203, 0.4)";
              } else {
                item.value![index].style.backgroundColor = "";
              }
            })
          },100);
        },
        {deep: true, immediate: true}
    )

    return {
      store,item,
      removeItem,player
    }
  }
})
</script>

<style lang="scss" scoped>
#ConductList {
  //background-color: aquamarine;
  //outline: 1px solid red;
  width: calc(100% - 20px);
  height: calc(100% - 20px);
  padding: 10px;
  border-top-left-radius: 24px;
  border-top-right-radius: 24px;

  #title {

    #tit {
      width: 100%;
      height: 22px;
      text-align: center;
    }
  }
  
  #cont {
    width: 100%;
    height: calc(100% - 22px);
    //outline: 1px solid red;
    border-radius: 30px;
    overflow: auto;

    //列表
    #item {
      width: calc(100% - 20px);
      height: 50px;
      //outline: 1px solid red;
      padding: 5px 10px;
      display: flex;

      #text {
        width: calc(100% - 60px);
        height: 100%;
        //outline: 1px solid green;
        padding: 0 0 0 10px;
        display: flex;

        #button_box {
          //outline: 1px solid red;
          width: calc(100% - 170px);
          height: 100%;
          display: flex;
          justify-content: space-between;
          align-items: center;

          &>svg {
            width: 30px;
            height: 30px;
          }
        }

        #text_font {
          position: relative;
          //outline: 1px solid blue;
          height: 100%;
          width: 170px;

          #song_name {
            width: 100%;
            height: 22px;
            overflow: hidden;
          }

          #duration {
            font-size: 12px;
            color: #3f3f3f;
            position: absolute;
            bottom: 5px;
          }
        }
      }

      #img {
        width: 50px;
        height: 50px;

        &>img {
          width: 100%;
          height: 100%;
          object-fit: cover;
          border-radius: 16px;
        }
      }
    }
  }
}
</style>