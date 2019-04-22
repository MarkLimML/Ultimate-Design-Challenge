package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromYear implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromYear";

    public ResultSet getSongs(int year){
        return dbc.getPlaylistSongs(year, ModelAbstract.getUser().getUser_id());
    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}
