package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithAlbum implements Metadata
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
        year = song.getYear();
        imgPath = song.getImgPath();
        songPath = song.getSongPath();

        album_id = dbc.getAlbumIdFromNameYear(album, year);
        if (album_id == -1) {
            dbc.createAlbum(album, imgPath, year);
            album_id = dbc.getAlbumIdFromNameYear(album, year);
        }
        dbc.createSongWAlbum(name, songPath, album_id, user_id);
    }
}
