<template>
  <div id="MySongs" class="White" v-if="store.state.isLogin">
    <!--头-->
    <Title title="已发布的歌曲"></Title>

    <!--信息-->
    <div id="information" v-if="isShowInformation">
      <div id="information_box" class="flexCentered">
        总贡献歌曲:{{mysongList.pageCount}}
      </div>
    </div>

    <!--页码-->
    <div id="page">
      <div id="page_box">
        <van-pagination :page-count="getCountPage()" show-page-size="6" v-model="currentPage" force-ellipses mode="simple"></van-pagination>
        <div id="button" @click="() => {isShowPage = true}"></div>
      </div>
    </div>

    <!--弹出自定义页面-->
    <van-dialog v-model:show="isShowPage" title="跳转到" show-cancel-button :before-close="toPage">
      <div id="stepper">
        <van-stepper v-model="dialogNum" min="1" :max="getCountPage()" input-width="40px" button-size="32px" />
      </div>
    </van-dialog>

    <!--歌曲列表-->
    <SongList v-if="songListBoolean" :song-list="songList"></SongList>



    <!--加载动画-->
    <Loading></Loading>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import {reqGetMySongs} from "@/api";
import store from "@/store";
import Title from "@/components/Title.vue";
import SongList from "@/components/SongList.vue";
import Search from "@/Interface/Search";
import Loading from "@/components/Loading.vue";
import router from "@/router";
import {Notify} from "vant";
import MySongList from "@/Interface/MySongList";

export default defineComponent({
  name: "MySongs",
  components: {Loading, SongList, Title},
  setup() {
    //检查登录状态
    if (!store.state.isLogin) {
      Notify({ type: 'primary', message: '请先登录!' });
      router.push("/login");
      return {store};
    }
    //分页
    const getCountPage = (): number => {
      return Math.floor(mysongList.value.pageCount/20+1)
    }
    let dialogNum: Ref<number> = ref(0);
    let isShowPage: Ref<boolean> = ref(false);
    let currentPage: Ref<number> = ref(0);  //当前页数
    let mysongList: Ref<MySongList> = ref({
      user_id: store.state.User.id,
      pageCount: 0,
      currentPage: 0,
      searchList: [],
    });
    //自由跳转
    const toPage = (e: string): boolean => {
      if (e === 'confirm') {
        mysongList.value.searchList = [];
        currentPage.value = dialogNum.value;
        mysongList.value.currentPage = dialogNum.value-1;
        getMySongList();
      }
      return true;
    }

    //请求
    const getMySongList = (): void => {
      store.state.isShowOverlay = true;
      songListBoolean.value = false;
      reqGetMySongs(mysongList.value).then((mySongList: MySongList) => {
        mysongList.value = {...mySongList};
        songList.value = mySongList.searchList;
        songListBoolean.value = true;
        store.state.isShowOverlay = false;
        isShowInformation.value = true;
      });
    }

    let isShowInformation: Ref<boolean> = ref(false);

    //请求我发布的歌曲
    let songList: Ref<Array<Search>|null> = ref(null);
    let songListBoolean: Ref<boolean> = ref(false);
    if (store.state.isLogin) {
      store.state.isShowOverlay = true;
      getMySongList();
    }

    //监听页数变化
    watch(
        () => currentPage.value,
        (value) => {
          mysongList.value.searchList = [];
          mysongList.value.currentPage = value-1;
          getMySongList();
        }
    )
    return {
      store,songList,songListBoolean,isShowInformation,mysongList,currentPage,getMySongList,isShowPage,dialogNum,getCountPage,toPage
    }
  }
})
</script>

<style lang="scss" scoped>
#MySongs {
  min-height: 100vh;
}

//页码
#page {
  padding: 10px 10px;
  position: sticky;
  top: 50px;
  background-color: #f3f3f3;
  z-index: 9;

  #page_box {
    background-color: #fff;
    width: 100%;
    border-radius: 12px;
    overflow: hidden;
  }

  #button {
    width: calc(33.33333% - 10px);
    height: calc(100% - 20px);
    margin: 10px 0;
    position: absolute;
    top: 0;
    left: 50%;
    z-index: 9;
    transform: translate(-50%);
  }
}

#information {
  padding: 10px 10px;

  #information_box {
    width: 100%;
    height: 60px;
    background-color: white;
    border-radius: 16px;
    font-size: 22px;
  }
}

#stepper {
  display: flex;
  justify-content: center;
}
</style>