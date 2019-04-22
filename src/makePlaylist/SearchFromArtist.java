package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromArtist implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromArtist";

    public ResultSet getSongs(int artist_id){
        return dbc.getPlaylistSongs(artist_id, ModelAbstract.getUser().getUser_id());

    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}

