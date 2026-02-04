package model.dao;

import db.DB;
import model.dao.impl.AlbumDAOJDBC;
import model.dao.impl.ArtistDAOJDBC;

public class DaoFactory {

    public static ArtistDAO createArtistDao() {
        return new ArtistDAOJDBC(DB.getConn());
    }

    public static AlbumDAO createAlbumDao() {
        return new AlbumDAOJDBC(DB.getConn());
    }

}
