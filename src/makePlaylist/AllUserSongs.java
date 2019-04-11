package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AllUserSongs implements Playlist {
	private DatabaseConnector dbc;
	private static final String type = "AllUserSongs";

		public ResultSet getSongs(int user_id){
			return dbc.getUserSongs(user_id);

		}
    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}
