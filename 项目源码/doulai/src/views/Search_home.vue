<template>
  <div id="Search_home" class="White">
    <!--导航栏及其搜索-->
    <Search></Search>
    <!--遮照层-->
    <van-overlay :show="store.state.isShowOverlay" class="flexCentered"><van-loading color="#1989fa" size="70px" vertical text-size="20px">加载中</van-loading></van-overlay>
    <!--列表-->
    <SearchSongList></SearchSongList>
  </div>
</template>

<script lang="ts">
import Search from "@/components/Search.vue";
import {useRoute} from "vue-router";
import store from "@/store";
import SearchSongList from "@/components/SearchSongList.vue";
export default {
  name: "Search_home",
  components: {SearchSongList, Search},
  setup() {
    //加载播放器
    store.state.isShowPlayer = true;
    store.state.PlayerBottom = "10px";
    //路由内容
    const route = useRoute();
    //搜索内容
    const { search_content } = route.query;
    
    return {
      search_content,store
    }
  }
}
</script>

<style lang="scss" scoped>
#Search_home {
  min-height: 100vh;
}
</style>