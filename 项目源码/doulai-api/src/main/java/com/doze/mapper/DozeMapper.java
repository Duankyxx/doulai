package com.doze.mapper;

import com.doze.pojo.ReqSong;
import com.doze.pojo.Search;

import java.util.List;

public interface DozeMapper {
    //搜索歌曲列表
    List<Search> search(String content);

    //单曲详细
    List<ReqSong> requestSong(int id);

    //今日推荐
    int recommendedToday();
    Search idSearch(int id);
}
