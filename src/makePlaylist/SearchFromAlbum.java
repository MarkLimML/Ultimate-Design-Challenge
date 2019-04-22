package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchFromAlbum implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "SearchFromAlbum";

    public ResultSet getSongs(int album_id){

        return dbc.getPlaylistSongs(album_id);

    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}

