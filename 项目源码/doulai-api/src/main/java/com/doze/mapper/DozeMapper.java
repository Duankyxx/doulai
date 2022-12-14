package com.doze.mapper;

import com.doze.pojo.MySongList;
import com.doze.pojo.ReqSong;
import com.doze.pojo.Search;
import com.doze.pojo.User;

import java.util.List;

public interface DozeMapper {
    //搜索歌曲列表
    List<Search> search(String content);
    //精确搜索
    Search accurateSearch(String content);

    //单曲详细
    List<ReqSong> requestSong(int id);

    //今日推荐
    int recommendedToday();
    Search idSearch(int id);

    void addUser(User user);

    User login(User user);

    //查询当前用户已发布的歌曲(分页)
    Integer getMySongsNum(int id);
    List<Search> getMySongs(int id, int page);

    //上传song_list
    void upLoadSongList(Search search);

    //上传song_list_detailed
    void upLoadSongListDetailed(ReqSong reqSong);
}
