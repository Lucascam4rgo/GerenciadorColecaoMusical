package model.dao.impl;

import db.DB;
import db.dbException;
import model.dao.AlbumDAO;
import model.entities.Album;
import model.entities.Artist;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumDAOJDBC implements AlbumDAO {

    private Connection conn;

    public AlbumDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Album searchByArtistAndTitle(Album album) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement("select album.*, artist.name as \"name_artist\"\n" +
                    "from album inner join artist\n" +
                    "on album.artist_id = artist.id\n" +
                    "where LOWER(album.title) LIKE LOWER(?) \n" +
                    "and LOWER(artist.name) LIKE LOWER(?);");

            ps.setString(1, "%" + album.getTitle() + "%");
            ps.setString(2, "%" + album.getArtist().getName() + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                return instantializeAlbum(rs);
            }

            return null;

        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
    }

    @Override
    public List<Album> findByArtistId(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Album> albums = new ArrayList<>();

        try{

            ps = conn.prepareStatement("SELECT album.*, artist.name as \"name_artist\" \n" +
                    "FROM album INNER JOIN artist\n" +
                    "ON album.artist_id = artist.id\n" +
                    "WHERE artist_id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                Album album = instantializeAlbum(rs);
                albums.add(album);
            }

            return albums;


        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
            DB.closeRS(rs);
        }
    }

    @Override
    public void save(Album album) {
        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("INSERT INTO album (title, release_year, artist_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, album.getTitle());
            ps.setDate(2, new Date(album.getRelease_year().getTime()));
            ps.setInt(3, album.getArtist().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int idgenerated = rs.getInt(1);
                    album.setId(idgenerated);

                    System.out.println("Album created with ID = " + idgenerated);
                }
            }
        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
        }
    }

    @Override
    public void update(Album album) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE album SET artist_id = ? where id = ?");

            ps.setInt(1, album.getArtist().getId());
            ps.setInt(2, album.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Updated!");
            }
            else {
                System.out.println("Something went wrong. No rows updated.");
            }

        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
        }

    }

    @Override
    public void removeByID(Integer id) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("DELETE FROM album WHERE id = ?");

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Deleted ID = " + id);

        } catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }

    }

    @Override
    public Album searchByID(Integer id) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = conn.prepareStatement("SELECT album.title, album.release_year, artist.name as \"name_artist\"\n" +
                    "FROM album inner join artist on\n" +
                    "album.artist_id = artist.id\n" +
                    "where album.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                Artist artist = new Artist();

                artist.setName(rs.getString("name_artist"));

                Album album = new Album();

                album.setTitle(rs.getString("title"));
                album.setRelease_year(rs.getDate("release_year"));
                album.setArtist(artist);

                return album;
            }

            return null;
        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }

    }

    @Override
    public List<Album> listAll() {
        return List.of();
    }

    protected Album instantializeAlbum(ResultSet rs) throws SQLException {

        Artist artist = new Artist();

        artist.setId(rs.getInt("artist_id"));
        artist.setName(rs.getString("name_artist"));

        Album album = new Album();

        album.setId(rs.getInt("id"));
        album.setTitle(rs.getString("title"));
        album.setRelease_year(rs.getDate("release_year"));
        album.setArtist(artist);

        return album;

    }
}
