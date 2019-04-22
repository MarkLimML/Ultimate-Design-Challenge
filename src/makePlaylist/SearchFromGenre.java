package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromGenre implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromGenre";

    public ResultSet getSongs(int genre_id){

        return dbc.getPlaylistSongs(genre_id);

    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}

