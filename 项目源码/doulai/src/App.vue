<template>
  <router-view></router-view>
  <!--播放器-->
  <SongPlayer v-show="store.state.isShowPlayer" :bottom="store.state.PlayerBottom"></SongPlayer>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import SongPlayer from "@/components/SongPlayer.vue";
import store from "@/store";

export default defineComponent({
  name: "App",
  components: {SongPlayer},
  setup() {
    store.state.nowPlaySongList.splice(0, 1);
    return {
      store
    }
  }
});
</script>

<style lang="scss">

</style>
