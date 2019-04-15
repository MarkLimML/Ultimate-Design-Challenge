package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithAll implements Metadata
{
    /*
        0 - name
        1 - imagePath
        2 - songPath
        3 - album
        4 - albumYear
        5 - artist
        6 - genre
     */
    private DatabaseConnector dbc;
    private String name;
    private String imgPath;
    private String songPath;

    private String album;
    private int year;
    private String artist;
    private String genre;

    private int user_id;

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    @Override
    public void saveToDatabase(Song song) {
        int album_id, artist_id, genre_id;

        user_id = song.getUser_id();
        name = song.getName();
        album = song.getAlbum();
        artist = song.getArtist();
        year = song.getYear();
        imgPath = song.getImgPath();
        songPath = song.getSongPath();
        genre = song.getGenre();

        System.out.println(artist);
//        artist_id = dbc.getArtistIdFromName(artist);
//        System.out.println(artist_id);
        album_id = dbc.getAlbumIdFromNameYear(album, user_id);
        genre_id = dbc.getGenreIdFromName(genre);
        System.out.println("if (artist_id == -1) {");
//        if (artist_id == -1) {
//            dbc.createArtist(artist);
//            artist_id = dbc.getArtistIdFromName(artist);
//        }
        System.out.println("if (album_id == -1) {");
        if (album_id == -1) {
            dbc.createAlbum(album, imgPath, year);
            album_id = dbc.getAlbumIdFromNameYear(album, year);
        }
        System.out.println("dbc.createSongWGenreArtistAlbum(name, songPath, genre_id, artist_id, album_id);");

        dbc.createSongWGenreArtistAlbum(name, songPath, genre_id, album_id, user_id);
    }

}