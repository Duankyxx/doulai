<template>
  <div id="Login_box" class="White">
    <div id="Login">
      <div id="close" class="flexCentered">
        <svg @click="toMy" t="1664437202157" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8537" width="200" height="200"><path d="M512 71.68a440.32 440.32 0 1 0 440.32 440.32 440.832 440.832 0 0 0-440.32-440.32z m0 809.62048A369.30048 369.30048 0 1 1 881.30048 512 369.72032 369.72032 0 0 1 512 881.30048zM670.208 353.792a35.5072 35.5072 0 0 0-50.21184 0L512 461.78304 404.02944 353.792a35.5072 35.5072 0 1 0-50.23744 50.23744L461.78816 512 353.792 619.97056a35.5072 35.5072 0 0 0 50.21184 50.21696L512 562.21696l107.9808 107.99104a35.5072 35.5072 0 0 0 50.21184-50.21696L562.22208 512 670.208 404.02944a35.5072 35.5072 0 0 0 0-50.23744z" fill="#8a8a8a" p-id="8538"></path></svg>
      </div>
      <!--表单-->
      <div id="fm">
        <input v-model="user.username" type="text" placeholder="账号">
        <input v-model="user.password" type="text" placeholder="密码">
        <div id="btn">
          <div @click="registration">注册<svg t="1664436669354" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8055" width="200" height="200"><path d="M514.56 71.68a442.88 442.88 0 1 0 442.88 442.88A443.392 443.392 0 0 0 514.56 71.68z m0 814.32576a371.44576 371.44576 0 1 1 371.44576-371.44576 371.8656 371.8656 0 0 1-371.44576 371.44576z m0-657.17248a178.78016 178.78016 0 0 0-178.58048 178.58048 35.71712 35.71712 0 1 0 71.43424 0A107.14624 107.14624 0 1 1 514.56 514.56a35.72224 35.72224 0 0 0-35.71712 35.71712c0 1.024 0.21504 1.97632 0.30208 2.97472-0.08192 0.9984-0.30208 1.96096-0.30208 2.97984v95.83616a35.71712 35.71712 0 1 0 71.43424 0V582.4a178.59072 178.59072 0 0 0-35.71712-353.56672z m1.78688 496.45056a41.67168 41.67168 0 1 0 41.66656 41.67168 41.67168 41.67168 0 0 0-41.66656-41.67168z" fill="#e6e6e6" p-id="8056"></path></svg></div>
          <div @click="login">登陆<svg t="1664436844441" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8323" width="200" height="200"><path d="M488.40192 655.24736a77.96224 77.96224 0 0 1-50.53952-18.432l-104.27904-88.35584A35.328 35.328 0 0 1 379.22816 494.592l104.27392 88.3456a7.74144 7.74144 0 0 0 10.80832-0.81408l179.93216-206.21312a35.328 35.328 0 0 1 53.20704 46.41792l-179.92192 206.208a78.25408 78.25408 0 0 1-59.12576 26.71104zM514.56 952.32a437.76 437.76 0 1 1 437.76-437.76 438.272 438.272 0 0 1-437.76 437.76z m0-804.91008a367.1552 367.1552 0 1 0 367.1552 367.15008 367.56992 367.56992 0 0 0-367.1552-367.15008z" fill="#e6e6e6" p-id="8324"></path></svg></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import router from "@/router";
import User from "@/Interface/User";
import { reqLogin } from "@/api";
import store from "@/store";
import {Toast} from "vant";

export default defineComponent({
  name: "Login",
  setup() {

    //用户
    let user: Ref<User> = ref({
      id: 0,
      username: '',
      password: '',
      sex: 0,
    });

    //登陆
    const login = (): void => {
      reqLogin(user.value).then((res: User | "") => {
        if (res === "") {
          Toast.fail('账号或密码错误!');
          return;
        }
        store.state.isLogin = true;
        store.state.User = {...res};
        Toast.success('登陆成功!');
        //跳转
        toMy();
      })
    }

    //路由跳转
    const toMy = (): void => {
      router.push('/my');
    }
    const registration = (): void => {
      router.push('/registration');
    }

    return {
      user,
      toMy,registration,login
    }
  }
})
</script>

<style lang="scss" scoped>
#Login_box {
  width: 100%;
  height: 100vh;

  #Login {
    padding: 10px;
    width: calc(100% - 20px);
    height: calc(100% - 20px);
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;

    #close {
      position: absolute;
      top: 10px;
      left: 10px;
      width: 40px;
      height: 40px;

      &>svg {
        width: 35px;
        height: 35px;
      }
    }

    //表单
    #fm {
      //outline: 1px solid red;
      padding: 10px;
      width: 280px;
      margin-bottom: 200px;

      &>input {
        text-align: center;
      }

      #btn {
        //outline: 1px solid blue;
        height: 50px;
        display: flex;
        justify-content: space-around;
        align-items: center;
        margin-top: 10px;

        &>div {
          width: 40%;
          height: 30px;
          background-color: #5f5f5f;
          border-radius: 50px;
          //outline: 1px solid purple;
          display: flex;
          justify-content: space-around;
          align-items: center;
          color: #f3f3f3;
        }
        svg {
          width: 20px;
          height: 20px;
        }
      }

      &>input {
        width: calc(100% - 10px);
        outline: none;
        height: 30px;
        border: 1px solid #5f5f5f;
        padding: 5px;
        color: #5f5f5f;
      }
      &>input:nth-child(1) {
        border-top-left-radius: 14px;
        border-top-right-radius: 14px;
      }
      &>input:nth-child(2) {
        border-bottom-left-radius: 14px;
        border-bottom-right-radius: 14px;
      }
    }
  }
}
</style>