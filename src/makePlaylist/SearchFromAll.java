package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromAll implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromAll";

    public ResultSet getSongs(int user_id){

        return dbc.getPlaylistSongs(user_id);

    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}

