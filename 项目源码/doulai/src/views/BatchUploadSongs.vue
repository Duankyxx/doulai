<template>
  <div id="BatchUploadSongs" class="White">
    <!--title-->
    <Title title="批量上传"></Title>

    <!--上传列表-->
    <div id="uploadList">
      <!--单个元素  v-for="(i, index) in data"-->
      <div v-for="(i, index) in dataList" id="item">
        <UploadSongs :data="i" :index="index" ref="UploadSongs"></UploadSongs>
      </div>
    </div>

    <!--选择文件-->
    <div id="button">
      <van-uploader
          accept="audio/*"
          :max-count="100"
          :multiple="true"
          :after-read="afterRead"
          v-show="isShowUploaderButton">
        <van-button icon="points" type="primary">批量选择文件</van-button>
      </van-uploader>
      <van-button icon="guide-o" plain hairline type="success" @click="upload" v-show="!isShowUploaderButton">提交</van-button>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref, Ref} from "vue";
import Title from "@/components/Title.vue";
import UploadSongs from "@/components/UploadSongs.vue";
import Tools from "@/Tools/Tools";

export default defineComponent({
  name: "BatchUploadSongs",
  components: {UploadSongs, Title},
  setup() {
    let dataList: Ref<Array<any>> = ref([]);

    //列表ref
    let UploadSongs: Ref<any> = ref();

    //显示
    let isShowUploaderButton: Ref<boolean> = ref(true);

    const afterRead = (file: any): void => {
      if (file.length === undefined) file = [file];
      dataList.value = [...file];
      isShowUploaderButton.value = false;
      file.forEach((i: any) => {
        //检查是否为音频文件
        if (!Tools.fileType(i)) {
          dataList.value = [];
          isShowUploaderButton.value = true;
        }
      })
    }

    //调用子组件上传
    const upload = (): void => {
      UploadSongs.value.forEach((i: any) => {
        i.upLoad();
      });
    }

    return {
      dataList,UploadSongs,isShowUploaderButton,
      afterRead,upload,
    }
  }
})
</script>

<style lang="scss" scoped>
#BatchUploadSongs {
  min-height: 100vh;

  #button {
    padding: 10px;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  #uploadList {
    //outline: 1px solid red;
    padding: 10px 5px;

    #item {
      //outline: 1px solid blue;
      margin: 10px 0;
    }
  }
}
</style>