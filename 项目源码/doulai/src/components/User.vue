<template>
  <div id="User_box" class="flexCentered">
    <div id="User" class="DarkWhite borderRadius CardShadow">

      <!--上半部分-->
      <div id="top">
        <!--头像-->
        <div id="avatar"></div>
        <!--其他-->
        <div id="other">
          <p id="uName" class="font_20">用户名</p>
        </div>
      </div>

      <!--下半部分-->
      <div id="bottom">

      </div>

    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "User",
  setup() {

    return {

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


      &>div {height: 100%;}
      //头像
      #avatar {
        border-radius: 50%;
        width: 65px;
        outline: 1px solid red;
      }

      //其他
      #other {
        padding: 0 10px;
        width: calc(100% - 85px);

        //用户名
        #uName {
          //outline: 1px solid red;
          overflow: hidden;
        }
      }
    }

    #bottom {

    }
  }
}
</style>