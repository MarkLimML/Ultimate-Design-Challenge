package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromUserPlaylist implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromUserPlaylist";

    public ResultSet getSongs(int playlist_id){
        return dbc.getPlaylistSongs(playlist_id);

    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}

