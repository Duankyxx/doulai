package com.doze.pojo;

public class Search_content {
    String content;

    public Search_content() {
    }

    public Search_content(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Search_content{" +
                "content='" + content + '\'' +
                '}';
    }
}
