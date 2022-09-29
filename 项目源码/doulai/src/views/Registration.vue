<template>
  <div id="Registration_box" class="White">
    <div id="Registration" class="flexCentered">
      <!--关闭按钮-->
      <div id="close" class="flexCentered">
        <svg @click="toLogin" t="1664440741272" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9039" width="200" height="200"><path d="M71.68 514.56a442.88 442.88 0 1 1 442.88 442.88A443.392 443.392 0 0 1 71.68 514.56z m814.32576 0a371.44576 371.44576 0 1 0-371.44576 371.44576 371.8656 371.8656 0 0 0 371.44576-371.44576z m-605.99808 39.424a51.41504 51.41504 0 0 1 0.19456-72.55552l159.232-158.63808a35.71712 35.71712 0 1 1 50.41152 50.60096L382.60736 480.256h356.16768a35.71712 35.71712 0 1 1 0 71.43424H378.40896L493.568 667.56608a35.71712 35.71712 0 0 1-50.688 50.35008z" fill="#8a8a8a" p-id="9040"></path></svg>
      </div>

      <!--表单-->
      <div id="fm">
        <input v-model="user.username" type="text" placeholder="用户名">
        <input v-model="user.password" type="text" placeholder="密码">
        <div id="sex" class="flexCentered">
          <van-radio-group v-model="user.sex" direction="horizontal">
            <van-radio :name="0">男</van-radio>
            <van-radio :name="1">女</van-radio>
          </van-radio-group>
        </div>
        <div id="btn">
          <div @click="sub">提交<svg t="1664436844441" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8323" width="200" height="200"><path d="M488.40192 655.24736a77.96224 77.96224 0 0 1-50.53952-18.432l-104.27904-88.35584A35.328 35.328 0 0 1 379.22816 494.592l104.27392 88.3456a7.74144 7.74144 0 0 0 10.80832-0.81408l179.93216-206.21312a35.328 35.328 0 0 1 53.20704 46.41792l-179.92192 206.208a78.25408 78.25408 0 0 1-59.12576 26.71104zM514.56 952.32a437.76 437.76 0 1 1 437.76-437.76 438.272 438.272 0 0 1-437.76 437.76z m0-804.91008a367.1552 367.1552 0 1 0 367.1552 367.15008 367.56992 367.56992 0 0 0-367.1552-367.15008z" fill="#e6e6e6" p-id="8324"></path></svg></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import router from "@/router";
import User from "@/Interface/User";
import { Toast } from "vant";


export default defineComponent({
  name: "Registration",
  setup() {
    let user: Ref<User> = ref({
      id: 0,
      username: '',
      password: '',
      sex: 0,
    });

    //注册检查及其发送ajax   \u2E80-\u9FFF
    let reg = {username: /^[A-Za-z0-9\u2E80-\u9FFF]{3,16}$/, password: /^[A-Za-z0-9]{6,18}$/}
    const sub = (): void => {
      if (!reg.username.test(user.value.username)) {
        if (user.value.username === '') {
          Toast.fail('用户名不能为空!');
        } else {
          Toast.fail('用户名填写不合规!');
        }
      }
      if (!reg.password.test(user.value.password)) {
        if (user.value.password === '') {
          Toast.fail('密码不能为空');
        } else {
          Toast.fail('密码填写不合规');
        }
      }
      console.log(user.value);
    }

    //路由跳转
    const toLogin = (): void => {
      router.push('/login');
    }

    return {
      user,sub,
      toLogin,
    }
  }
})
</script>

<style lang="scss" scoped>
#Registration_box {
  width: 100%;
  height: 100vh;

  #Registration {
    padding: 10px;
    width: calc(100% - 20px);
    height: calc(100% - 20px);

    //表单
    #fm {
      //outline: 1px solid red;
      padding: 10px;
      width: 280px;
      margin-bottom: 200px;

      #sex {
        //outline: 1px solid red;
        width: 100%;
        margin-top: 10px;
      }

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
  }
}
</style>