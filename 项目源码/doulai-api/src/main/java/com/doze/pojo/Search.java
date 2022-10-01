package com.doze.pojo;

public class Search {
    int id;
    String song_name;
    String creator;
    int song_type;
    int uid;

    public Search() {
    }

    public Search(int id, String song_name, String creator, int song_type, int uid) {
        this.id = id;
        this.song_name = song_name;
        this.creator = creator;
        this.song_type = song_type;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getSong_type() {
        return song_type;
    }

    public void setSong_type(int song_type) {
        this.song_type = song_type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", song_name='" + song_name + '\'' +
                ", creator='" + creator + '\'' +
                ", song_type=" + song_type +
                ", uid=" + uid +
                '}';
    }
}
