package makePlaylist;

import AddSong.Song;
import Model.DatabaseConnector;

import java.sql.ResultSet;

public interface Playlist {
    //public void Playlist();

    public String getType();

    public void attachDB(DatabaseConnector dbc);

    public ResultSet getSongs(int id);

}
