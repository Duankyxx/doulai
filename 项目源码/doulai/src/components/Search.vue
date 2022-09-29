<template>
  <div id="Search_box">
    <div id="Search" class="DarkWhite">
      <svg id="to_home" v-show="showSVG" @click="toHome" x="1663917422723" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1229" width="200" height="200"><path d="M625.615238 769.462857L368.152381 512l257.462857-257.462857 34.499048 34.499047L437.150476 512l222.96381 222.96381-34.499048 34.499047z" p-id="1230"></path></svg>
      <input v-model="search_content" type="text" :placeholder="store.state.search_content" class="font_black font_14"/>
      <button type="button" class="font_16 font_black" @click="onSearch">搜索</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, Ref } from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import store from "@/store";
import {reqSearch} from '@/api';
import Search from "@/Interface/Search";

export default defineComponent({
  name: "Search",
  setup() {

    //搜索内容
    let search_content: Ref<string> = ref("");
    //搜索
    const onSearch = (): void => {
      if (search_content.value === "") return;
      store.state.search_content = search_content.value;
      router.push({name: 'Search_home',query: {"search_content": search_content.value}});
      //发送搜索请求
      store.state.isShowOverlay = true; // 显示遮照层
      reqSearch(search_content.value).then((res: Array<Search>) => {
        //内容交给全局
        store.state.search_list = res;
        store.state.isShowOverlay = false; // 关闭遮照层
      }).catch(() => store.state.isShowOverlay = false);
    }
    //返回主页
    const toHome = (): void => {
      router.push("/home");
    }

    //是否加载返回主页图标
    let showSVG: Ref<boolean> = ref(false);
    const route = useRoute();
    if (route.path !== '/home') showSVG.value = true;

    //加载搜索框的值
    if (store.state.search_content === "") store.state.search_content = "搜索歌曲";

    return {
      search_content,showSVG,store,
      onSearch,toHome
    }
  }
})
</script>

<style lang="scss" scoped>

#Search_box {
  background-color: #f3f3f3;
  width: 100%;
  height: 60px;
  position: relative;
  #Search {
    width: 100%;
    height: 60px;
    position: fixed;
    //子元素排列
    display: flex;
    justify-content: center;
    align-items: center;

    //搜索框
    input {
      border: 0;
      width: 250px;
      height: 20px;
      padding: 5px;
      border-radius: 50px;
      outline: none;
    }
    //按钮
    button {
      width: 50px;
      height: 30px;
      border-radius: 50px;
      border: 0;
    }
    //返回图标
    #to_home {
      width: 30px;
      height: 30px;
      padding-right: 20px;
    }
  }
}
</style>