package model.dao.impl;

import db.DB;
import db.dbException;
import db.dbIntegrityException;
import model.dao.MusicDAO;
import model.dao.TrackDAO;
import model.entities.Album;
import model.entities.Artist;
import model.entities.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackDAOJDBC implements TrackDAO {

    private Connection conn;

    public TrackDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Track track) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("INSERT INTO track(title, duration, album_id)" +
                    " VALUES(?, ? * INTERVAL '1 second', ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, track.getTitle());
            ps.setInt(2, track.getDuration());
            ps.setInt(3, track.getAlbum().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()) {
                    track.setId(rs.getInt(1));
                }
            }

        }
        catch (SQLException e) {
            throw new dbException(e.getMessage());
        }
        finally {
            DB.closePS(ps);
        }

    }

    @Override
    public void update(Track track) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE track SET title=? where id=?");

            ps.setString(1, track.getTitle());

            ps.setInt(2, track.getId());

            ps.executeUpdate();

        }
        catch (SQLException e) {
            throw new dbException(e.getMessage());
        }
        finally {
            DB.closePS(ps);
        }

    }

    @Override
    public void removeByID(Integer id) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("DELETE FROM track WHERE id=?");

            ps.setInt(1, id);

            ps.executeUpdate();

        }
        catch (SQLException sql) {
            throw new dbIntegrityException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
        }

    }

    @Override
    public Track searchByID(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("select track.id, track.title," +
                    " EXTRACT(EPOCH FROM track.duration) as duration_seconds," +
                    " album.title as \"albumTitle\"," +
                    " artist.name as \"artistName\"\n" +
                    "from track INNER JOIN album\n" +
                    "on track.album_id = album.id\n" +
                    "INNER JOIN artist\n" +
                    "on album.artist_id = artist.id\n" +
                    "where track.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                Artist artist = instantiateArtist(rs);
                Album album = instantiateAlbum(rs, artist);
                return instantiateTrack(rs, album);

            }

            return null;
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
    public List<Track> listAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            List<Track> tracks = new ArrayList<>();
            Map<String, Album> albums = new HashMap<>();
            Map<String, Artist> artists = new HashMap<>();

            ps = conn.prepareStatement("select track.id, track.title," +
                    " EXTRACT(EPOCH from track.duration) as duration_seconds, album.title as \"albumTitle\"," +
                    " artist.name as \"artistName\"\n" +
                    "from track INNER JOIN album\n" +
                    "on track.album_id = album.id\n" +
                    "INNER JOIN artist\n" +
                    "on album.artist_id = artist.id\n" +
                    "order by track.id");

            rs = ps.executeQuery();

            while (rs.next()) {

                Artist artist = artists.get(rs.getString("artistName"));

                if (artist == null) {
                    artists.put(rs.getString("artistName"), artist = instantiateArtist(rs));
                }

                Album album = albums.get(rs.getString("albumTitle"));

                if(album == null) {
                    albums.put(rs.getString("albumTitle"), album = instantiateAlbum(rs, artist));
                }

                Track track = instantiateTrack(rs, album);

                tracks.add(track);

            }

            return tracks;

        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
            DB.closeRS(rs);
        }
    }

    private static Artist instantiateArtist(ResultSet rs) throws SQLException {
        Artist artist = new Artist();

        artist.setName(rs.getString("artistName"));

        return artist;
    }

    private static Album instantiateAlbum(ResultSet rs, Artist artist) {
        try {
            Album album = new Album();

            album.setTitle(rs.getString("albumTitle"));
            album.setArtist(artist);

            return album;

        } catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }

    }

    private static Track instantiateTrack(ResultSet rs, Album album) {
        try {
            Track track = new Track();

            track.setId(rs.getInt("id"));
            track.setTitle(rs.getString("title"));
            track.setDuration(rs.getInt("duration_seconds"));
            track.setAlbum(album);

            return track;
        }
        catch (SQLException sql) {
            throw new RuntimeException(sql.getMessage());
        }
    }


}
