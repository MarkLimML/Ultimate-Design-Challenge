package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumPlaylist implements Playlist {

	private DatabaseConnector dbc;
	private static final String type = "AlbumPlaylist";

		public ResultSet getSongs(int album_id){
			return dbc.getAlbumSongs(album_id);
		}

    public String getType(){
        return type;
    }


	@Override
	public void attachDB(DatabaseConnector dbc) {
		this.dbc = dbc;
	}
}

