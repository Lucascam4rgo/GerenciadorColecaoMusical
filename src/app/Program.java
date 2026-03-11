package app;

import model.dao.*;
import model.dao.impl.ArtistDAOJDBC;
import model.entities.Album;
import model.entities.Artist;
import model.entities.Track;
import service.AlbumService;
import service.ArtistService;
import service.TrackService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Program {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");

        ArtistDAO artistDao = DaoFactory.createArtistDao();
        AlbumDAO albumDAO = DaoFactory.createAlbumDao();
        TrackDAO trackDAO = DaoFactory.createTrackDao();

        ArtistService artistService = new ArtistService(artistDao);
        AlbumService albumService = new AlbumService(albumDAO, artistDao);
        TrackService trackService = new TrackService(trackDAO, artistDao, albumDAO);

        // Teste 1 - Insert de Artista
//        Artist artist = new Artist(null, "Mac Miller", "Hip Hop", "United States");
//        artistService.createArtist(artist);
//        System.out.println("Artist created successfully!");

        // Teste 2 - Update de Artista
//        Artist artist = new Artist(16, "Mac Miller", "Hip Hop, Pop Rap", null);
//        artistService.updateArtist(artist);
//        System.out.println("Artist updated successfully!");

        // Teste 3 - Deletando Artista
//        artistService.removeArtistByID(16);
//        System.out.println("Artist removed successfully");


        // Teste 4 - Achando pelo ID
        //System.out.println(artistService.getArtist(10));

        // Teste 5 - Listando todos os artistas
//        List<Artist> artists = artistService.listAllArtists();
//
//        for (Artist artist: artists) {
//            System.out.println(artist);
//        }

        // Teste 6 - Insert em album
//        Album album = new Album(null, "CHROMAKOPIA", dfmt.parse("28/10/2024"),
//                new Artist(1, null, null, null));
//
//        albumService.createAlbum(album);
//        System.out.println("Album created successfully!");

        // Teste 7 - Albuns de um artista
        List<Album> albums = albumService.findByArtistID(6);

        for (Album album: albums) {
            System.out.println(album);
        }

        // Teste 8 - Update em album - Mudar artist_id
//        Album album = new Album(36, null, null,
//                new Artist(13, null, null, null));
//
//        albumDAO.update(album);

        // Teste 9 - Removendo pelo ID
        //albumDAO.removeByID(42);

        // Teste 10 - Achando álbum pelo ID
//        Album album = albumDAO.searchByID(25);
//
//        System.out.println("Álbum: " + album.getTitle() + ", Data de Lançamento: " +
//                dfmt.format(album.getRelease_year()) + ", " +
//                "Nome do artista: " + album.getArtist().getName());

        // Teste 11 - Achando o Álbum pelo artista e titulo
//        Album album = new Album(null, "this", null,
//                new Artist(null, "more", null, null));
//
//        System.out.println(albumDAO.searchByArtistAndTitle(album));

        // Teste 12 - Listando todos os albuns e seus artistas

//        albumDAO.listAll().forEach(System.out::println);

        // Teste 13 - Inserindo tracks
//        trackDAO.save(new Track(null, "Foreword", 199, new Album(1, null, null, null)));
//        trackDAO.save(new Track(null, "Where This Flower Blooms", 194, new Album(1, null, null, null)));
//        trackDAO.save(new Track(null, "See You Again", 180, new Album(1, null, null, null)));
//        trackDAO.save(new Track(null, "Who Dat Boy", 206, new Album(1, null, null, null)));
//        trackDAO.save(new Track(null, "Garden Shed", 223, new Album(1, null, null, null)));

        // Teste 14 - Achando track pelo ID

        //System.out.println(trackDAO.searchByID(5));

        // Teste 15 - List All dos tracks

//        trackDAO.listAll().forEach(System.out::println);





    }

}
