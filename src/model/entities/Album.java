package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Album {

    private Integer id;
    private String title;
    private Date release_year;

    private Artist artist;

    public Album() {

    }

    public Album(Integer id, String title, Date release_year, Artist artist) {
        this.release_year = release_year;
        this.title = title;
        this.id = id;
        this.artist = artist;
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

    public Date getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Date release_year) {
        this.release_year = release_year;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release_year=" + release_year +
                ", artistID=" + artist.getId() +
                ", artistName=" + artist.getName() +
                '}';
    }
}