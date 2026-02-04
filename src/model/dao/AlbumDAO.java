package model.dao;

import model.entities.Album;
import model.entities.Artist;

import java.util.List;

public interface AlbumDAO extends MusicDAO<Album> {

    Album searchByArtistAndTitle(Album album);
    List<Album> findByArtistId(Integer id);

}
