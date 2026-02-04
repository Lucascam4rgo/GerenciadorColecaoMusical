package model.entities;

import java.time.Duration;

public class Track {

    private Integer id;
    private String title;
    private Duration duration;
    private Integer album_id;

    public Track() {

    }

    public Track(Integer id, String title, Duration duration, Integer album_id) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.album_id = album_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Integer getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(Integer album_id) {
        this.album_id = album_id;
    }
}
