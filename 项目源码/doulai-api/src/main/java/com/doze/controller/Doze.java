package com.doze.controller;

import com.doze.mapper.DozeMapper;
import com.doze.pojo.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Doze {

    //测试
    @RequestMapping(value = "/hello")
    public String hello() {
        return "Hello World!";
    }

    //请求搜索列表
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Search> search(@RequestBody Search_content search_content) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.search(search_content.getContent());
    }

    //请求单曲播放
    @RequestMapping(value = "/requestSong", method = RequestMethod.POST)
    public List<ReqSong> requestSong(@RequestBody RequestSong requestSong) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.requestSong(requestSong.getId());
    }

    //请求今日推荐
    @RequestMapping(value = "/recommendedToday", method = RequestMethod.POST)
    public List<Search> recommendedToday() throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        int total = mapper.recommendedToday();
        LinkedHashSet<Integer> randomValue = new LinkedHashSet<>();
        List<Search> searchList = new ArrayList<>();

        for (int i=0; i<20; i++) {
            randomValue.add(((int)(Math.random()*(total-1))+1));
        }
        randomValue.forEach(i -> {
            searchList.add(mapper.idSearch(i));
        });
        return searchList;
    }

    //账号注册
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public boolean registration(@RequestBody User user) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        System.out.println(user);
        try {
            mapper.addUser(user);
            return true;
        } catch (Exception e) { //异常
            return false;
        }
    }

    //登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.login(user);
    }

    //请求该账户发布的歌曲
    @RequestMapping(value = "/getMySongs", method = RequestMethod.POST)
    public List<Search> getMySongs(@RequestBody User user) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.getMySongs(user.getId());
    }

    //上传song_list
    @RequestMapping(value = "/upLoadSongList", method = RequestMethod.POST)
    public List<Search> upLoad(@RequestBody Search search) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        mapper.upLoadSongList(search);
        return  mapper.search(search.getSong_name());
    }

    //上传song_list_detailed
    @RequestMapping(value = "/upLoadSongListDetailed", method = RequestMethod.POST)
    public boolean upLoadSongListDetailed(@RequestBody ReqSong reqSong) throws IOException {
        DozeMapper mapper = GetMapper.getMapper();
        mapper.upLoadSongListDetailed(reqSong);
        return true;
    }
}
