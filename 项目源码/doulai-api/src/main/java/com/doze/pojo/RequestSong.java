package com.doze.pojo;

public class RequestSong {
    int id;

    public RequestSong() {
    }

    public RequestSong(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "requestSong{" +
                "id=" + id +
                '}';
    }
}
