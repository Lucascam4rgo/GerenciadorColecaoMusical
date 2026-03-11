package service;

import db.dbException;
import model.dao.AlbumDAO;
import model.dao.ArtistDAO;
import model.entities.Artist;

import java.util.List;

public class ArtistService {

    private ArtistDAO artistDAO;

    public ArtistService(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public void createArtist(Artist artist) {

        if (artist.getName() == null || artist.getName().isBlank() || artist.getGenre() == null ||
                artist.getGenre().isBlank() || artist.getCountry() == null || artist.getCountry().isBlank()) {

            throw new IllegalArgumentException("The artist must have a name, a genre and a country.");

        }

        artistDAO.save(artist);

    }

    public void updateArtist(Artist artist) {

        if (artist.getId() == null) {
            throw new IllegalArgumentException("Artist must have an ID");
        }

        Artist currentArtist = artistDAO.searchByID(artist.getId());

        if (currentArtist == null) {
            throw new IllegalArgumentException("No artist found.");
        }

        if (artist.getName() != null && !artist.getName().equals(currentArtist.getName())) {
            currentArtist.setName(artist.getName());
        }

        if (artist.getGenre() != null && !artist.getGenre().equals(currentArtist.getGenre())) {
            currentArtist.setGenre(artist.getGenre());
        }

        if (artist.getCountry() != null && !artist.getCountry().equals(currentArtist.getCountry())) {
            currentArtist.setCountry(artist.getCountry());
        }

        artistDAO.update(currentArtist);

    }

    public void removeArtistByID(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Artist artist = artistDAO.searchByID(id);

        if (artist == null) {
            throw new IllegalArgumentException("Artist not found");
        }

        artistDAO.removeByID(id);

    }

    public Artist getArtist(Integer id) {

        if (id == null) {
            throw new IllegalArgumentException("ID can't be null");
        }

        Artist artistFound = artistDAO.searchByID(id);

        if (artistFound == null) {
            throw new IllegalArgumentException("No artist found.");
        }

        return artistFound;

    }

    public List<Artist> listAllArtists() {

        return artistDAO.listAll();

    }






}
