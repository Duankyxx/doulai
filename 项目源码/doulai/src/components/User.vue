<template>
  <div id="User_box" class="flexCentered">
    <div id="User" class="DarkWhite borderRadius CardShadow">

      <!--上半部分-->
      <div id="top">
        <!--头像-->
        <div id="avatar">
          <!--用户有自己的头像-->

          <!--用户没有自己的头像-->
          <img class="avatar" alt="" src="../image/NoLogin.jpeg">
        </div>

        <!--其他-->
        <div id="other">
          <p id="uName" class="font_20">{{store.state.User.username}}</p>
          <p id="uid" class="font_black">id: {{store.state.User.id}}</p>
          <!--性别-->
          <div id="bottom_box">
            <div id="sex_box">
              <svg v-if="userSex" t="1664607075412" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7114" width="200" height="200"><path d="M884.62336 71.68h-293.04832a34.816 34.816 0 0 0 0 69.632h242.432l-210.73408 211.6096A340.66944 340.66944 0 0 0 404.5312 273.60768a344.42752 344.42752 0 1 0 267.38176 128.97792l211.03104-211.88608v243.2a34.688 34.688 0 1 0 69.376 0V139.648A67.90656 67.90656 0 0 0 884.62336 71.68zM404.5312 892.928a274.82624 274.82624 0 1 1 273.7152-274.82112A274.58048 274.58048 0 0 1 404.5312 892.928z" fill="#1296db" p-id="7115"></path></svg>
              <svg v-if="!userSex" t="1664607100579" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="7684" width="200" height="200"><path d="M614.28736 76.8a342.22592 342.22592 0 0 0-267.43296 556.92288L252.16 728.2688 136.03328 612.352a34.66752 34.66752 0 0 0-49.06496 48.98304l116.12672 115.93728-116.12672 115.92192a34.6624 34.6624 0 0 0 49.06496 48.97792l116.12672-115.93728 116.1216 115.93728a34.6624 34.6624 0 1 0 49.06496-48.97792l-116.12672-115.9424 94.2848-94.1312A342.76864 342.76864 0 1 0 614.28736 76.8z m0 615.936a273.32096 273.32096 0 1 1 273.7664-273.32096 273.85344 273.85344 0 0 1-273.7664 273.32096z" fill="#d4237a" p-id="7685"></path></svg>
            </div>
          </div>
        </div>
      </div>

      <!--下半部分-->
      <div id="bottom">

      </div>

    </div>
  </div>
</template>

<script lang="ts">
import User from "@/Interface/User";
import store from "@/store";
import {defineComponent, ref, Ref, watch} from "vue";

export default defineComponent({
  name: "User",
  setup() {

    //用户拥有自己的头像或者没有

    //用户性别
    let userSex: Ref<boolean> = ref(true);
    watch(
        () => store.state.User,
        (value: User) => {
          value.sex === 0 ? userSex.value=true : userSex.value=false;
        },
        {deep: true, immediate: true}
    )

    return {
      store,userSex
    }
  }
})
</script>

<style lang="scss" scoped>
#User_box {
  //background-color: aqua;
  width: 100%;
  height: 200px;

  #User {
    padding: 5px;
    width: 90%;
    height: 150px;

    &>div {width: calc(100% - 10px);height: calc(50% - 10px);padding: 5px}

    #top {
      display: flex;
      //outline: 1px solid red;


      &>div {height: 100%;}
      //头像
      #avatar {
        border-radius: 50%;
        width: 65px;
        //outline: 1px solid red;

        .avatar {
          width: 100%;
          height: 100%;
          object-fit: cover;
          border-radius: 50%;
        }
      }

      //其他
      #other {
        //outline: 1px solid purple;
        padding: 0 10px;
        width: calc(100% - 85px);
        display: flex;
        flex-direction: column;

        //用户名
        #uName {
          //outline: 1px solid red;
          overflow: hidden;
        }

        //用户ID
        #uid {
          //outline: 1px solid red;
          overflow: hidden;
          font-size: 12px;
        }

        //用户性别
        #bottom_box {
          //outline: 1px solid red;
          width: 100%;
          height: 30px;

          #sex_box {
            //outline: 1px solid blue;
            width: 30px;
            height: 30px;
            display: flex;
            justify-content: center;
            align-items: center;

            &>svg {
              width: 20px;
              height: 20px;
            }
          }
        }
      }
    }

    #bottom {
      //outline: 1px solid blue;
    }
  }
}
</style>