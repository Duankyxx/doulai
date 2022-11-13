import requests, {baseURL} from "@/api/request";
import User from "@/Interface/User";
import Search from "@/Interface/Search";
import SoundQuality from "@/Interface/SoundQuality";
import MySongList from "@/Interface/MySongList";
import axios from "axios";

export const reqTest = ()=>{
    //由于前面配置了基础路径，所以不用加/api
    return requests({url:'/hello',method:'get'});
}

//搜获请求
export const reqSearch = (content: string): Promise<any> => {
    return requests({url:'/search',method:'post',data: {content: content}});
}
//精确搜获
export const reqAccurateSearch = (content: string): Promise<any> => {
    return requests({url:'/accurateSearch',method:'post',data: {content: content}});
}
//请求单曲
export const reqRequestSong = (id: number): Promise<any> => {
    return requests({url:'/requestSong',method:'post',data: {id: id}});
}
//今日推荐
export const reqRecommendedToday = (): Promise<any> => {
    return requests({url:'/recommendedToday',method:'post'});
}
//注册请求
export const reqRegistration = (user: User): Promise<any> => {
    return requests({url:'/registration',method:'post',data: user});
}
//登陆请求
export const reqLogin = async (user: User): Promise<any> => {
    return requests({url:'/login',method:'post',data: user});
}
//请求我已发布歌曲
export const reqGetMySongs = (mysongList: MySongList): Promise<any> => {
    return requests({url:'/getMySongs',method:'post',data: mysongList});
}
//上传song_list
export const reqUpLoadSongList = (song_list: Search): Promise<any> => {
    return requests({url:'/upLoadSongList',method:'post',data: song_list});
}
//上传song_list_detailed
export const reqUpLoadSongListDetailed = (song_list_detailed: SoundQuality): Promise<any> => {
    return requests({url:'/upLoadSongListDetailed',method:'post',data: song_list_detailed});
}
//获取STS令牌
export const reqGetSTS = async (user: User): Promise<any> => {
    return requests({url:'/getSTS',method:'post',data: user});
}

