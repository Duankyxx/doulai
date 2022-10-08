<template>
  <div id="Title_box">
    <div id="Title">
      <svg @click="toMy" t="1664632034387" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7114" width="200" height="200"><path d="M71.68 514.56a442.88 442.88 0 1 1 442.88 442.88A443.392 443.392 0 0 1 71.68 514.56z m814.32576 0a371.44576 371.44576 0 1 0-371.44576 371.44576 371.8656 371.8656 0 0 0 371.44576-371.44576z m-605.99808 39.424a51.41504 51.41504 0 0 1 0.19456-72.55552l159.232-158.63808a35.71712 35.71712 0 1 1 50.41152 50.60096L382.60736 480.256h356.16768a35.71712 35.71712 0 1 1 0 71.43424H378.40896L493.568 667.56608a35.71712 35.71712 0 0 1-50.688 50.35008z" fill="#707070" p-id="7115"></path></svg>
      {{props.title}}
    </div>
    <slot></slot>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import router from "@/router";

export default defineComponent({
  name: "Title",
  props: ["title"],
  setup(props, content) {

    //跳转到个人页面
    const toMy = (): void => {
      router.push("/my");
    }

    return {
      props,toMy
    }
  }
})
</script>

<style lang="scss" scoped>
#Title_box {
  position: relative;
  //outline: 1px solid red;
  overflow: hidden;
  width: 100%;
  height: 50px;

  #Title {
    z-index: 999;
    position: fixed;
    top: 0;
    left: 0;
    width: calc(100% - 10px);
    height: 40px;
    padding: 5px;
    background-color: #fff;
    font-size: 24px;
    display: flex;
    justify-content: center;
    align-items: center;
    //position: relative;

    &>svg {
      width: 30px;
      height: 30px;
      //outline: 1px solid red;
      position: absolute;
      top: 10px;
      left: 20px;
    }
  }
}
</style>