<template>
  <div>
    <div ref="boxElement" id="box" :style="{width: width,height: height,justifyContent: isCenter}" style="display: flex">
      <div ref="songnameElemnet" id="songname">
        {{songname}}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, Ref} from "vue";

export default defineComponent({
  name: "SongName",
  props: ['songname','width','height','isCenter'],
  setup(props) {
    let songname: string = props.songname;
    let width: string = props.width;
    let height: string = props.height;
    let isCenter: Ref<undefined | string> = ref(props.isCenter);
    if (isCenter.value === undefined) {
      isCenter.value = "left";
    }
    //元素
    let boxElement: Ref<HTMLElement | null> = ref(null);
    let songnameElemnet: Ref<HTMLElement | null> = ref(null);

    onMounted(() => {
      if (boxElement.value!.offsetWidth < songnameElemnet.value!.offsetWidth) {
        //符合添加动画条件animation: zom 10s cubic-bezier(0.69, -0.15, 0.43, 1.13) 1s infinite alternate;
        isCenter.value = "left";
        songnameElemnet.value!.className = "zom";
        let width = boxElement.value!.offsetWidth + "px";
        songnameElemnet.value!.style.setProperty('--width', width);
      }
    })
    return {
      songname,width,height,boxElement,songnameElemnet,isCenter
    }
  }
})
</script>

<style lang="scss" scoped>
@import "../index.scss";

#box {
  //outline: 1px solid red;
  white-space: nowrap;
  position: relative;
  overflow: hidden;
  width: $BoxWidth;

  #songname {
    position: absolute;
    //background-color: pink;
  }
}

.zom {
  animation: zom 10s cubic-bezier(0.69, -0.15, 0.43, 1.13) 1s infinite alternate;
}

@keyframes zom {
  0% {
    left: 0;
  }
  100% {
    transform: translateX(calc(-100% + $BoxWidth));
    left: 0;
  }
}
</style>