<template>
  <div id="MySongs" class="White">
    <!--头-->
    <Title title="已发布的歌曲"></Title>

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

export default defineComponent({
  name: "MySongs",
  components: {Loading, SongList, Title},
  setup() {
    //请求我发布的歌曲
    let songList: Ref<Search|null> = ref(null);
    let songListBoolean: Ref<boolean> = ref(false);
    if (store.state.isLogin) {
      store.state.isShowOverlay = true;
      reqGetMySongs(store.state.User).then((res: Search) => {
        songList.value = res;
        songListBoolean.value = true;
        store.state.isShowOverlay = false;
      });
    }

    return {
      songList,songListBoolean
    }
  }
})
</script>

<style lang="scss" scoped>
#MySongs {
  min-height: 100vh;
}
</style>