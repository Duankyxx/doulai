<template>
  <div id="RecommendedToday_box" class="flexCentered">
    <div id="RecommendedToday" class="DarkWhite borderRadius font_20 CardShadow">
      <!--title-->
      <div id="title">今日好歌推荐</div>
      <!--列表-->
      <div id="SongList">
        <!--卡片-->
        <div v-if="isShowList" class="card" id="card" v-for="(i, index) in recommendedList" :key="i.id" @click="requestSong(i.id, i.song_name)">
          <!--图片-->
          <div id="cardImg" class="flexCentered"><img alt="" src="../image/cover.jpeg"></div>
          <!--歌名-->
          <div id="cardSongName" class="font_black">{{i.song_name}}</div>
        </div>

        <!--加载层-->
        <div id="Loading" v-show="!store.state.isLoading.RecommendedToday">
          <van-loading size="50" color="#1989fa" />
        </div>
      </div>
    </div>

    <!--音质弹出层-->
    <PopUpSoundQuality ref="popUpSoundQuality"></PopUpSoundQuality>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import {reqRecommendedToday} from "@/api";
import Search from "@/Interface/Search";
import PopUpSoundQuality from "@/components/PopUpSoundQuality.vue";
import Loading from "@/components/Loading.vue";
import store from "@/store";

export default defineComponent({
  name: "RecommendedToday",
  components: {Loading, PopUpSoundQuality},
  setup() {
    //加载层
    store.state.isLoading.RecommendedToday = false;
    //是否渲染列表
    let isShowList: Ref<boolean> = ref(false);
    //好歌列表
    let recommendedList: Ref<Array<Search>> = ref([]);
    //弹出层
    let popUpSoundQuality: Ref<any> = ref(null);
    const requestSong = (id: number,song_name: string): void => {
      popUpSoundQuality.value.requestSong(id, song_name);
    }


    //加载
    reqRecommendedToday().then((res: Array<Search>) => {
      let index: number = 0;
      res.forEach((i: Search) => {
        if (i === null) return;
        recommendedList.value[index] = i;
        index++;
      });
      //加载层
      store.state.isLoading.RecommendedToday = true;
    });
    watch(
        () => recommendedList.value,
        (value) => {
          if (value.length > 0) {
            isShowList.value = true;
          } else {
            isShowList.value = false;
          }
        },
        {deep: true, immediate: true}
    )

    return {
      recommendedList,isShowList,popUpSoundQuality,store,
      requestSong
    }
  }
})
</script>

<style lang="scss" scoped>
#Loading {
  //outline: 1px solid blue;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

#RecommendedToday_box {
  //background-color: aqua;
  width: 100%;
  height: 170px;

  #RecommendedToday {
    padding: 5px;
    width: 90%;
    height: 140px;
    display: flex;
    flex-direction: column;

    //title
    #title {
      width: 100%;
      height: 30px;
      text-align: center;
    }
    #SongList {
      //outline: 1px solid red;
      display: flex;
      padding: 5px;
      width: calc(100% - 10px);
      height: calc(100% - 40px);
      overflow: auto;
      border-radius: 16px;

      .card:nth-child(1) {
        margin-left: 0;
      }
      .card {
        margin-left: 10px;
      }

      #card {
        //outline: 1px solid blue;
        border-radius: 16px;
        background-color: rgba(195, 195, 195, 0.7);
        padding: 5px;
        width: 70px;
        height: calc(100% - 10px);
        display: flex;
        flex-direction: column;
        justify-content: space-between;

        #cardSongName {
          //outline: 1px solid red;
          font-size: 12px;
          text-align: center;
          overflow: hidden;
          //overflow-x: ;
          height: 15px;
        }

        #cardImg {
          //outline: 1px solid green;
          width: 100%;
          height: 60px;

          img {
            border-radius: 14px;
            width: 60px;
            height: 60px;
            object-fit: cover;
          }
        }
      }
    }
  }
}
</style>