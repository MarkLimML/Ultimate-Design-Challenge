package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenrePlaylist implements Playlist {

    private DatabaseConnector dbc;
	private static final String type = "GenrePlaylist";

	public ResultSet getSongs(int genre_id){
	    return dbc.getGenreSongs(genre_id);
	}

    public String getType(){
        return type;
    }

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

}

