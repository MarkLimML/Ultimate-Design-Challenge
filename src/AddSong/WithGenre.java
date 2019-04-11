package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithGenre implements Metadata
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
    private int user_id;
    private String genre;

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    @Override
    public void saveToDatabase(Song song) {
        int genre_id;
        user_id = song.getUser_id();
        name = song.getName();
        imgPath = song.getImgPath();
        songPath = song.getSongPath();
        genre = song.getGenre();

        genre_id = dbc.getGenreIdFromName(genre);
        dbc.createSongWGenre(name, songPath, genre_id, user_id);
    }

}
