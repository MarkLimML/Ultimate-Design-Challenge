package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public class WithNone implements Metadata
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

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    @Override
    public void saveToDatabase(Song song) {
        imgPath = song.getImgPath();
        songPath = song.getSongPath();
        name = song.getName();
        user_id = song.getUser_id();

        dbc.createSong(name, songPath, user_id);
    }
}
