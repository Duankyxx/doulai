import requests from "@/api/request";

export const reqTest = ()=>{
    //由于前面配置了基础路径，所以不用加/api
    return requests({url:'/hello',method:'get'});
}

//搜获请求
export const reqSearch = (content: string): Promise<any> => {
    return requests({url:'/search',method:'post',data: {content: content}});
}
//请求单曲
export const reqRequestSong = (id: number): Promise<any> => {
    return requests({url:'/requestSong',method:'post',data: {id: id}});
}
//今日推荐
export const reqRecommendedToday = (): Promise<any> => {
    return requests({url:'/recommendedToday',method:'post'});
}