<template>
  <div id="my" class="White">
    <!--未登陆-->
    <NoLogin v-if="!store.state.isLogin"></NoLogin>
    <!--个人信息-->
    <User v-if="store.state.isLogin"></User>

    <!--功能列表-->
    <FunList></FunList>

    <!--导航栏-->
    <Navigation></Navigation>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import Navigation from "@/components/Navigation.vue";
import store from "@/store";
import User from "@/components/User.vue";
import NoLogin from "@/components/NoLogin.vue";
import FunList from "@/components/FunList.vue";

export default defineComponent({
  name: "My",
  components: {FunList, NoLogin, User, Navigation},
  setup() {
    //加载播放器
    store.state.isShowPlayer = false;
    store.state.PlayerBottom = "70px";
    //检查是否登陆
    if (store.state.User === undefined) {
      store.state.isLogin = false;
    } else {
      store.state.isLogin = true;
    }

    return {
      store
    }
  }
})
</script>

<style lang="scss" scoped>
#my {
  min-height: 100vh;
}
</style>