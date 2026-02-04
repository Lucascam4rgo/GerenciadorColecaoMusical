package model.dao;

import model.entities.Track;

import java.util.List;

public interface TrackDAO extends MusicDAO<Track> {

    List<Track> tracks();

}
