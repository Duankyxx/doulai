<template>
  <div id="Home" class="White">
    <!--导航栏及其搜索-->
    <Search></Search>
    <!--今日推荐-->
    <RecommendedToday></RecommendedToday>
    <!--导航栏-->
    <Navigation></Navigation>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Search from "@/components/Search.vue";
import Navigation from "@/components/Navigation.vue";
import store from "@/store";
import RecommendedToday from "@/components/RecommendedToday.vue";

export default defineComponent({
  name: "Home",
  components: {RecommendedToday, Navigation, Search},
  setup() {
    //加载播放器
    store.state.isShowPlayer = true;
    store.state.PlayerBottom = "70px";
  }
});
</script>

<style lang="scss" scoped>
#Home {
  min-height: 100vh;
}
</style>