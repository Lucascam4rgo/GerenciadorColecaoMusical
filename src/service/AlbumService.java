package service;

import db.dbException;
import model.dao.AlbumDAO;
import model.dao.ArtistDAO;
import model.entities.Album;
import model.entities.Artist;

import java.util.Date;
import java.util.List;

public class AlbumService {

    private AlbumDAO albumDAO;
    private ArtistDAO artistDAO;

    public AlbumService(AlbumDAO albumDAO, ArtistDAO artistDAO) {
        this.albumDAO = albumDAO;
        this.artistDAO = artistDAO;
    }

    public Album albumByArtistAndTitle(Album album) {

        if (album.getTitle() == null || album.getArtist() == null || album.getArtist().getName() == null) {
            throw new IllegalArgumentException("The album must have a title and an artist name.");
        }

        return albumDAO.searchByArtistAndTitle(album);

    }

    public List<Album> findByArtistID(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        Artist artist = artistDAO.searchByID(id);

        if (artist == null) {
            throw new IllegalArgumentException("Artist doesn't exist");
        }

        return albumDAO.findByArtistId(id);

    }

    public void createAlbum(Album album) {

        if (album.getTitle() == null || album.getTitle().isBlank() ||
                album.getRelease_year() == null || album.getArtist() == null
                || album.getArtist().getId() == null) {

            throw new IllegalArgumentException("Album must have a title, a release year and an artist ID.");

        }

        if (artistDAO.searchByID(album.getArtist().getId()) == null) {
            throw new dbException("This ID doesn't exist");
        }

        albumDAO.save(album);

    }

    public void updateAlbum(Album album) {

        if (album.getId() == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        Album currentAlbum = albumDAO.searchByID(album.getId());

        if (currentAlbum == null) {
            throw new dbException("Album doesn't exist.");
        }

        if (album.getTitle() != null && !album.getTitle().equals(currentAlbum.getTitle())) {
            currentAlbum.setTitle(album.getTitle());
        }

        if(album.getRelease_year() != null && !album.getRelease_year().equals(currentAlbum.getRelease_year())) {
            currentAlbum.setRelease_year(new Date(album.getRelease_year().getTime()));
        }

        if (album.getArtist() != null && album.getArtist().getId() != null &&
                !album.getArtist().getId().equals(currentAlbum.getArtist().getId())) {

            Artist newArtist = artistDAO.searchByID(album.getArtist().getId());

            if (newArtist == null) {
                throw new dbException("This artist doesn't exist");
            }

            currentAlbum.setArtist(newArtist);

        }

        albumDAO.update(currentAlbum);


    }

    public void removeByID(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        Album album = albumDAO.searchByID(id);

        if (album == null) {
            throw new dbException("ID doesn't exist.");
        }

        albumDAO.removeByID(id);

    }

    public Album searchByID(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null.");
        }

        return albumDAO.searchByID(id);

    }

    public List<Album> listAllAlbums() {

        return albumDAO.listAll();

    }


}
