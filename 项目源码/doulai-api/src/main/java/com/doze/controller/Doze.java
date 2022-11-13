package com.doze.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.doze.lib.MyLog;
import com.doze.mapper.DozeMapper;
import com.doze.pojo.*;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Doze {
    private MyLog myLog = new MyLog();

    //测试
    @RequestMapping(value = "/hello")
    public String hello() {
        myLog.info("来自/hello的测试", 2);
        return "Hello World!";
    }

    //请求搜索列表
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<Search> search(@RequestBody Search_content search_content) throws IOException {
        myLog.info("模糊搜索请求  -->  搜索内容:  " + search_content.getContent(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.search(search_content.getContent());
    }

    //精确搜索accurate
    @RequestMapping(value = "/accurateSearch", method = RequestMethod.POST)
    public Search accurateSearch(@RequestBody Search_content search_content) throws IOException {
        myLog.info("精确搜索请求  -->  搜索内容:  " + search_content.getContent(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.accurateSearch(search_content.getContent());
    }

    //请求单曲播放
    @RequestMapping(value = "/requestSong", method = RequestMethod.POST)
    public List<ReqSong> requestSong(@RequestBody RequestSong requestSong) throws IOException {
        myLog.info("请求单曲播放  -->  请求歌曲id:  " + requestSong.getId(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        return mapper.requestSong(requestSong.getId());
    }

    //请求今日推荐
    @RequestMapping(value = "/recommendedToday", method = RequestMethod.POST)
    public List<Search> recommendedToday() throws IOException {
        myLog.info("获取今日推荐", 2);
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
        myLog.info("账号注册请求  -->  注册用户名:  " + user.getUsername(), 2);
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
        User u = mapper.login(user);
        myLog.info("账号登录请求  -->  用户id:  " + u.getId(), 2);
        return u;
    }

    //请求该账户发布的歌曲
    @RequestMapping(value = "/getMySongs", method = RequestMethod.POST)
    public MySongList getMySongs(@RequestBody MySongList user) throws IOException {
        myLog.info("请求该账户发布的歌曲  -->  用户id:  " + user.getUser_id(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        Integer mySongsNum = mapper.getMySongsNum(user.getUser_id());    //总数
        List<Search> mySongs = mapper.getMySongs(user.getUser_id(), user.getCurrentPage()*20);    //歌曲
        return new MySongList(user.getUser_id(), mySongsNum, 0, mySongs);
    }

    //上传song_list
    @RequestMapping(value = "/upLoadSongList", method = RequestMethod.POST)
    public List<Search> upLoad(@RequestBody Search search) throws IOException {
        myLog.info("上传到歌曲列表  -->  用户id:  " + search.getUid() + "  --  歌曲名:  " + search.getSong_name(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        mapper.upLoadSongList(search);
        return  mapper.search(search.getSong_name());
    }

    //上传song_list_detailed
    @RequestMapping(value = "/upLoadSongListDetailed", method = RequestMethod.POST)
    public boolean upLoadSongListDetailed(@RequestBody ReqSong reqSong) throws IOException {
        myLog.info("上传到歌曲详细  -->  歌曲列表id:  " + reqSong.getUid(), 2);
        DozeMapper mapper = GetMapper.getMapper();
        mapper.upLoadSongListDetailed(reqSong);
        return true;
    }

    //获取阿里云上传STS
    @RequestMapping(value = "/getSTS", method = RequestMethod.POST)
    public String getSTS(@RequestBody User user) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tNsugLXceXPri8mDsU2", "cFiSVxuCk1bvyEiRPKX1wCqYFE8Mvj");
        IAcsClient client = new DefaultAcsClient(profile);

        //构造请求，设置参数。
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRegionId("cn-hangzhou");
        request.setRoleArn("acs:ram::1052147859501565:role/ramosstest");
        request.setRoleSessionName("alice");

        //发起请求，并得到响应。
        String json = "";
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            json = new Gson().toJson(response);
        } catch (ClientException e) {
            myLog.info("----------------------------------------------------------->>>>>>", 0);
            myLog.info("获取STS错误  -->  :  ErrCode:" + e.getErrCode(), 0);
            myLog.info("获取STS错误  -->  ErrMsg:" + e.getErrMsg(), 0);
            myLog.info("获取STS错误  -->  RequestId:" + e.getRequestId(), 0);
            myLog.info("<<<<<<-----------------------------------------------------------", 0);
        }
        myLog.info("请求STSToken  -->  用户Id:" + user.getId(), 2);
        return json;
    }
}
