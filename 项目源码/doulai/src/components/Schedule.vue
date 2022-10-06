<template>
  <van-overlay :show="store.state.isShowSchedule">
    <div class="wrapper">
      <div class="block">
        <van-circle
            :speed="0.5"
            :current-rate="store.state.Schedule"
            :size="70"
            :rate="100"
            :color="gradientColor"
            :text="schedule"
            :stroke-width="60"
            stroke-linecap="square"
        />
      </div>
    </div>
  </van-overlay>
</template>

<script lang="ts">
import {defineComponent, ref, Ref, watch} from "vue";
import store from "@/store";

export default defineComponent({
  name: "Schedule",
  setup() {

    //渐变色对象
    const gradientColor = {
      '0%': '#3fecff',
      '50%': '#6149f6',
      '100%': '#ff0000'
    };

    //监听进度条
    let schedule: Ref<string> = ref("0%");
    watch(
        () => store.state.Schedule,
        (value, oldValue) => {
          schedule.value = value + "%";
        },
        {deep: true}
    )

    return {
      store,gradientColor,schedule
    }
  }
})
</script>

<style lang="scss" scoped>
.wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.block {
  width: 120px;
  height: 120px;
  border-radius: 12px;
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>