<template>
  <div id="Loading">
    <van-overlay :show="store.state.isShowOverlay" style="display: flex;justify-content: center;align-items: center">
      <van-loading size="50px" text-size="16px" vertical text-color="#0094ff" color="#0094ff">加载中...</van-loading>
    </van-overlay>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import store from "@/store";

export default defineComponent({
  name: "Loading",
  setup() {

    return {
      store
    }
  }
})
</script>

<style scoped>

</style>