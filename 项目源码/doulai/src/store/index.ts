import { createStore } from 'vuex'


let any: any;
export default createStore({
  state: {
    //是否显示遮照层
    isShowOverlay: false,
    isLoading: {
      RecommendedToday: false,
      PopUpSoundQuality: false,
    },
    //导航栏状态
    navigation: {
      Home: false,
      My: false,
    },

    //当前正在播放的歌曲
    nowPlaySong: any,
    nowPlaySongList: [any], //当前播放列表
    //播放暂停控制
    PlayerPause: false,
    //是否显示播放器
    isShowPlayer: false,
    //播放器高度
    PlayerBottom: "70px",

    //登陆信息
    isLogin: false,
    User: any,

    search_content: "",
    search_list: any,
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
