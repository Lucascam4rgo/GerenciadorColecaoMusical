package app;

import model.dao.AlbumDAO;
import model.dao.ArtistDAO;
import model.dao.DaoFactory;
import model.dao.MusicDAO;
import model.dao.impl.ArtistDAOJDBC;
import model.entities.Album;
import model.entities.Artist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Program {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat dfmt = new SimpleDateFormat("dd/MM/yyyy");

        ArtistDAO artistDao = DaoFactory.createArtistDao();
        AlbumDAO albumDAO = DaoFactory.createAlbumDao();

        // Teste 1 - Insert de Artista
        //Artist artist = new Artist(null, "Paramore", "Rock", "United States");
        //artistDao.save(artist);

        // Teste 2 - Update de Artista
//        Artist artist = new Artist(10, "Clairo", "Bedroom Pop, Indie Pop, Lo-fi", null);
//        artistDao.update(artist);

        // Teste 3 - Deletando Artista
        //artistDao.removeByID(9);

        // Teste 4 - Achando pelo ID
        //System.out.println(artistDao.searchByID(8));

        // Teste 5 - Listando todos os artistas
        /*List<Artist> artists = artistDao.listAll();

        for (Artist artist: artists) {
            System.out.println(artist);
        }*/

        // Teste 6 - Insert em album
//        Album album = new Album(null, "Riot!", dfmt.parse("12/06/2007"), 15, null);
//        Album album2 = new Album(null, "Brand New Eyes", dfmt.parse("25/09/2009"), 15, null);
//        Album album3 = new Album(null, "This Is Why",
//                dfmt.parse("10/02/2023"), 15, null);
//
//        albumDAO.save(album);
//        albumDAO.save(album2);
//        albumDAO.save(album3);

        // Teste 7 - Albuns de um artista
//        List<Album> albums = albumDAO.findByArtistId(6);
//
//        for (Album album: albums) {
//            System.out.println("Álbum: ID = " + album.getId() + ", Title = " + album.getTitle() + ", Release Year = " +
//                    album.getRelease_year() + ", Artist ID = " + album.getArtist().getId() + ", Artist Name = " +
//                    album.getArtist().getName());
//        }

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
        Album album = new Album(null, "this", null,
                new Artist(null, "more", null, null));

        System.out.println(albumDAO.searchByArtistAndTitle(album));


    }

}
