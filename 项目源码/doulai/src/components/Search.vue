<template>
  <div id="Search_box">
    <div :tabindex="1" id="Search" class="DarkWhite" :style="{height: SearchElementHeight}" @blur="blurSearch">
      <div id="Search_input" class="flexCentered">
        <svg id="to_home" v-show="showSVG" @click="toHome" x="1663917422723" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1229" width="200" height="200"><path d="M625.615238 769.462857L368.152381 512l257.462857-257.462857 34.499048 34.499047L437.150476 512l222.96381 222.96381-34.499048 34.499047z" p-id="1230"></path></svg>
        <input @blur="blurSearch" @keyup.enter="onSearch" v-model="search_content" type="text" :placeholder="store.state.search_content" class="font_black font_14"/>
        <button type="button" class="font_16 font_black" @click="onSearch">搜索</button>
      </div>

      <div id="Search_cont" class="flexCentered">
        <div>
          <!--渲染搜索-->
          <div v-for="(i, index) in search_list" id="item">
            <p @click="SachChange(i)">
              <SongName :songname="i.song_name" width="100%" height="30px"></SongName>
            </p>
            <van-divider id="item_divider" :style="{ color: '#1989fa', borderColor: '#1989fa'}"/>
          </div>

          <!--空状态-->
          <van-empty v-show="noContList" image="error" description="找不到这首歌,快去上传吧!" />
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onUnmounted, ref, Ref, watch} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import store from "@/store";
import {reqSearch} from '@/api';
import Search from "@/Interface/Search";
import * as _ from 'lodash';
import SongName from "@/components/SongName.vue";

export default defineComponent({
  name: "Search",
  components: {SongName},
  setup() {


    //DOM
    const SearchElementHeight: Ref<string> = ref("60px");
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

    //输入框内容改变时
    let search_list: Ref<Array<Search> | null> = ref(null);
    let sachFalg: Ref<boolean> = ref(false);
    let noContList: Ref<boolean> = ref(false);
    const sach = (): void => {
      search_list.value = null;
      if (search_content.value === "") return;
      reqSearch(search_content.value).then((res: Array<Search>) => {
        search_list.value = [...res];
      });
    }
    watch(
        () => search_list.value,
        (value) => {
          noContList.value = value?.length === 0;
          if (value === null) {
            sachFalg.value = false;
            setTimeout(() => {
              SearchElementHeight.value = "60px";
            },200);
          } else {
            sachFalg.value = true;
            setTimeout(() => {
              SearchElementHeight.value = "300px";
            },200);
          }
        },
        {deep: true, immediate: true}
    )
    watch(
        () => search_content.value,
        () => {
          fangdou();
        },
    );
    const fangdou = _.debounce(sach, 700, {
      leading: false,  // 延长开始后调用
      trailing: true  // 延长结束前调用
    });
    //移除组件时，取消防抖
    onUnmounted(()=>{
      fangdou.cancel();
    })
    //确认搜索
    const SachChange = (i: Search): void => {
      search_content.value = i.song_name;
      onSearch();
    }
    //离开列表时收起
    const blurSearch = (): void => {
      setTimeout(() => {
        search_list.value = null;
      },200)
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
      search_content,showSVG,store,sachFalg,search_list,SearchElementHeight,noContList,
      onSearch,toHome,SachChange,blurSearch
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
  z-index: 9999;

  #Search {
    transition: 500ms;
    width: 100%;
    height: 60px;
    position: fixed;
    //子元素排列
    display: flex;
    flex-direction: column;

    #Search_input {
      width: 100%;
      height: 60px;
      //outline: 1px solid red;
    }

    #Search_cont {
      //outline: 1px solid blue;
      width: 100%;
      height: calc(100% - 70px);
      overflow: hidden;

      &>div {
        background-color: #e8f1ec;
        width: calc(100% - 30px);
        height: calc(100% - 10px);
        border-radius: 16px;
        padding: 5px;
        overflow: auto;

        #item {
          padding: 0 15px;
          width: calc(100% - 30px);
          line-height: 30px;

          &>p {
            //outline: 1px solid red;
            width: 100%;
            height: 30px;
            overflow: hidden;
          }

          #item_divider {
            margin: 5px 0;
          }
        }
      }
    }

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