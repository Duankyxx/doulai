package com.doze.pojo;

import java.util.List;

public class MySongList {
    private int user_id;    //用户id
    private int PageCount;  //总页数
    private int CurrentPage;    //当前页数
    private List<Search> searchList;

    public MySongList() {
    }

    public MySongList(int user_id, int pageCount, int currentPage, List<Search> searchList) {
        this.user_id = user_id;
        PageCount = pageCount;
        CurrentPage = currentPage;
        this.searchList = searchList;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int pageCount) {
        PageCount = pageCount;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public List<Search> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Search> searchList) {
        this.searchList = searchList;
    }

    @Override
    public String toString() {
        return "MySongList{" +
                "user_id=" + user_id +
                ", PageCount=" + PageCount +
                ", CurrentPage=" + CurrentPage +
                ", searchList=" + searchList +
                '}';
    }
}
