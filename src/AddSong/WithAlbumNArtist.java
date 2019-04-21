package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithAlbumNArtist implements Metadata
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
    private int user_id;

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    @Override
    public void saveToDatabase(Song song) {
        int album_id;

        user_id = song.getUser_id();
        name = song.getName();
        album = song.getAlbum();
        artist = song.getArtist();
        year = song.getYear();
        imgPath = song.getImgPath();
        songPath = song.getSongPath();

//        artist_id = dbc.getArtistIdFromName(artist);
        if (dbc.albumExists(song.getAlbumId()))
            album_id = song.getAlbumId();
        else
            album_id = dbc.getAlbumIdFromNameYearUser(album, year, user_id);
//
//        if (artist_id == -1) {
//            dbc.createArtist(artist);
//            artist_id = dbc.getArtistIdFromName(artist);
 //       }
        if (album_id == -1) {
            dbc.createAlbum(album, imgPath, year);
            album_id = dbc.getAlbumIdFromNameYear(album, year);
        }

        dbc.createSongWArtistAlbum(name, songPath, album_id, user_id);
    }
}