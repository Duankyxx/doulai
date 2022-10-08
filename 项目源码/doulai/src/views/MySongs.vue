<template>
  <div id="MySongs" class="White">
    <!--头-->
    <Title title="已发布的歌曲"></Title>

    <!--信息-->
    <div id="information" v-if="isShowInformation">
      <div id="information_box" class="flexCentered">
        总贡献歌曲:{{songList.length}}
      </div>
    </div>

    <!--歌曲列表-->
    <SongList v-if="songListBoolean" :song-list="songList"></SongList>

    <!--加载动画-->
    <Loading></Loading>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import {reqGetMySongs} from "@/api";
import store from "@/store";
import Title from "@/components/Title.vue";
import SongList from "@/components/SongList.vue";
import Search from "@/Interface/Search";
import Loading from "@/components/Loading.vue";
import router from "@/router";
import {Notify} from "vant";

export default defineComponent({
  name: "MySongs",
  components: {Loading, SongList, Title},
  setup() {
    let isShowInformation: Ref<boolean> = ref(false);
    //检查登录状态
    if (store.state.User === undefined) {
      Notify({ type: 'primary', message: '请先登录!' });
      router.push("/login");
    }
    //请求我发布的歌曲
    let songList: Ref<Search|null> = ref(null);
    let songListBoolean: Ref<boolean> = ref(false);
    if (store.state.isLogin) {
      store.state.isShowOverlay = true;
      reqGetMySongs(store.state.User).then((res: Search) => {
        songList.value = res;
        songListBoolean.value = true;
        store.state.isShowOverlay = false;
        isShowInformation.value = true;
      });
    }

    return {
      songList,songListBoolean,isShowInformation
    }
  }
})
</script>

<style lang="scss" scoped>
#MySongs {
  min-height: 100vh;
}

#information {
  outline: 1px solid red;
  padding: 10px 10px;

  #information_box {
    width: 100%;
    height: 60px;
    background-color: white;
    border-radius: 16px;
    font-size: 22px;
  }
}
</style>