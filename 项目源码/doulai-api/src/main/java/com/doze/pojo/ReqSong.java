package com.doze.pojo;

public class ReqSong {
    int id;
    String song_name;
    String duration;
    String sound_quality;
    String url;
    int uid;

    public ReqSong() {
    }

    public ReqSong(int id, String song_name, String duration, String sound_quality, String url, int uid) {
        this.id = id;
        this.song_name = song_name;
        this.duration = duration;
        this.sound_quality = sound_quality;
        this.url = url;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSound_quality() {
        return sound_quality;
    }

    public void setSound_quality(String sound_quality) {
        this.sound_quality = sound_quality;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ReqSong{" +
                "id=" + id +
                ", song_name='" + song_name + '\'' +
                ", duration='" + duration + '\'' +
                ", sound_quality='" + sound_quality + '\'' +
                ", url='" + url + '\'' +
                ", uid=" + uid +
                '}';
    }
}
