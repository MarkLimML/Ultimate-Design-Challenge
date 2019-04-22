package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistPlaylist implements Playlist {
	private DatabaseConnector dbc;
	private static final String type = "ArtistPlaylist";

		public ResultSet getSongs(int artist_id){
            System.out.println("ArtistSongs.getSongs(id)");
		    return dbc.getArtistSongs(artist_id);
		}


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    public String getType(){
        return type;
    }


}

