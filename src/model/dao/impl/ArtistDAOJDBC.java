package model.dao.impl;

import db.DB;
import db.dbException;
import model.dao.ArtistDAO;
import model.entities.Album;
import model.entities.Artist;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistDAOJDBC implements ArtistDAO {

    private Connection conn;

    public ArtistDAOJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Artist artist) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO artist (name, genre, country) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, artist.getName());
            ps.setString(2, artist.getGenre());
            ps.setString(3, artist.getCountry());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet IDs = ps.getGeneratedKeys();

                if (IDs.next()) {
                    int id = IDs.getInt(1);
                    System.out.println("Artist inserted with ID = " + id + "!");
                    artist.setId(id);
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
    public void update(Artist artist) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE artist SET name = ?, genre = ? WHERE id = ?");

            ps.setString(1, artist.getName());
            ps.setString(2, artist.getGenre());
            ps.setInt(3, artist.getId());

            ps.executeUpdate();

            System.out.println("Updated!");

        } catch (SQLException sql) {
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

            ps = conn.prepareStatement("DELETE FROM artist WHERE id = ?");

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Deleted ID " + id);
            }

        } catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
        }

    }

    @Override
    public Artist searchByID(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM artist WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                return instantializeArtist(rs);
            }

            return null;

        } catch (Exception sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
            DB.closeRS(rs);
        }
    }

    @Override
    public List<Artist> listAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Artist> artists = new ArrayList<>();

        try {

            ps = conn.prepareStatement("SELECT * FROM artist ORDER BY id");

            rs = ps.executeQuery();

            while (rs.next()) {
                Artist artist = instantializeArtist(rs);

                artists.add(artist);
            }

            return artists;

        }
        catch (SQLException sql) {
            throw new dbException(sql.getMessage());
        }
        finally {
            DB.closePS(ps);
            DB.closeRS(rs);
        }

    }

    protected Artist instantializeArtist(ResultSet rs) throws SQLException {
        Artist artist = new Artist();

        artist.setId(rs.getInt("id"));
        artist.setName(rs.getString("name"));
        artist.setGenre(rs.getString("genre"));
        artist.setCountry(rs.getString("country"));

        return artist;
    }
}
