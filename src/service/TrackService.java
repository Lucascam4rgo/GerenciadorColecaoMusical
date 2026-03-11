package service;

import db.dbException;
import model.dao.AlbumDAO;
import model.dao.ArtistDAO;
import model.dao.TrackDAO;
import model.entities.Album;
import model.entities.Track;

import java.util.List;

public class TrackService {

    private TrackDAO trackDAO;

    private ArtistDAO artistDAO;
    private AlbumDAO albumDAO;

    public TrackService(TrackDAO trackDAO, ArtistDAO artistDAO, AlbumDAO albumDAO) {
        this.trackDAO = trackDAO;
        this.artistDAO = artistDAO;
        this.albumDAO = albumDAO;
    }

    public void createTrack(Track track) {

        if (track.getTitle() == null || track.getDuration() == null ||
                track.getAlbum() == null || track.getAlbum().getId() == null) {
            throw new IllegalArgumentException("Track must have a title, a duration and an album");
        }

        Album album = albumDAO.searchByID(track.getAlbum().getId());

        if (album == null) {
            throw new dbException("Album doesn't exist");
        }

        trackDAO.save(track);

    }

    public void updateTrack(Track track) {

        if (track.getId() == null) {
            throw new IllegalArgumentException("Track must have an ID");
        }

        Track currentTrack = trackDAO.searchByID(track.getId());

        if (track.getTitle() != null && !track.getTitle().equals(currentTrack.getTitle())) {
            currentTrack.setTitle(track.getTitle());
        }

        if (track.getDuration() != null && !track.getDuration().equals(currentTrack.getDuration())) {
            currentTrack.setDuration(track.getDuration());
        }

        if (track.getAlbum() != null && track.getAlbum().getId() != null &&
                !track.getAlbum().getId().equals(currentTrack.getAlbum().getId())) {

            Album newAlbum = albumDAO.searchByID(track.getAlbum().getId());

            if (newAlbum == null) {
                throw new dbException("This album doesn't exist.");
            }

            currentTrack.setAlbum(newAlbum);

        }
    }

    public void removeTrackByID(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }

        Track track = trackDAO.searchByID(id);

        if (track == null) {
            throw new dbException("Track doesn't exist");
        }

        trackDAO.removeByID(id);

    }

    public Track searchByTrackID(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }

        return trackDAO.searchByID(id);

    }

    public List<Track> listAllTracks() {

        return trackDAO.listAll();

    }

}
