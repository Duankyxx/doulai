<template>
  <div id="NoLogin_box" class="flexCentered">
    <div id="NoLogin" class="DarkWhite borderRadius" @click="toLogin">
      <!--头像-->
      <div id="avatar">
        <img alt="" src="../image/NoLogin.jpeg">
      </div>
      <!--text-->
      <div id="cont">
        <p class="font_black">未登录</p>
        <div id="login">
          <a>点击登录</a>
          <svg t="1664434073268" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2080" width="200" height="200"><path d="M957.44 514.56a442.88 442.88 0 1 0-442.88 442.88 443.392 443.392 0 0 0 442.88-442.88z m-814.32576 0a371.44576 371.44576 0 1 1 371.44576 371.44576A371.8656 371.8656 0 0 1 143.11424 514.56z m605.99808 39.424a51.41504 51.41504 0 0 0-0.19456-72.55552l-159.232-158.63808a35.712 35.712 0 0 0-50.41152 50.60096L646.51264 480.256H290.34496a35.71712 35.71712 0 1 0 0 71.43424h360.36608L535.552 667.56608a35.71712 35.71712 0 1 0 50.688 50.35008z" fill="#595BB3" p-id="2081"></path></svg>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import router from "@/router";

export default defineComponent({
  name: "NoLogin",
  setup() {
    //前往登陆页
    const toLogin = (): void => {
      router.push('/login');
    }


    return {
      toLogin
    }
  }
})
</script>

<style lang="scss" scoped>
#NoLogin_box {


  #NoLogin {
    margin-top: 10px;
    padding: 5px;
    width: 90%;
    height: 60px;
    display: flex;

    #cont {
      width: calc(100% - 70px);
      height: 100%;
      //outline: 1px solid blue;
      position: relative;

      #login {
        //outline: 1px solid red;
        position: absolute;
        right: 10px;
        top: 15px;
        display: flex;
        justify-content: center;
        align-items: center;

        svg {
          width: 30px;
          height: 30px;
        }
      }

      &>p {
        //outline: 1px solid red;
        text-align: center;
        font-size: 20px;
        width: 80px;
      }
    }

    #avatar {
      //outline: 1px solid red;
      width: 60px;
      height: 60px;
      padding: 0 5px;

      &>img {
        //outline: 1px solid blue;
        width: 60px;
        height: 60px;
        border-radius: 50%;
        overflow: hidden;
      }
    }
  }
}
</style>