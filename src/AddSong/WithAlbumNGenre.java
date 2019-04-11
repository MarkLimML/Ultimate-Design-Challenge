package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithAlbumNGenre implements Metadata
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
    private String genre;
    private int user_id;

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    @Override
    public void saveToDatabase(Song song) {
        int album_id, genre_id;

        user_id = song.getUser_id();
        name = song.getName();
        album = song.getAlbum();
        year = song.getYear();
        imgPath = song.getImgPath();
        songPath = song.getSongPath();
        genre = song.getGenre();

        album_id = dbc.getAlbumIdFromNameYear(album, year);
        genre_id = dbc.getGenreIdFromName(genre);
        if (album_id == -1) {
            dbc.createAlbum(album, imgPath, year);

            album_id = dbc.getAlbumIdFromNameYear(album, year);
        }

        dbc.createSongWGenreAlbum(name, songPath, genre_id, album_id, user_id);
    }
}