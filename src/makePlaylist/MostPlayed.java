package makePlaylist;

import Model.DatabaseConnector;

import java.sql.ResultSet;

public class MostPlayed implements Playlist {
    private DatabaseConnector dbc;
    private static final String type = "MostPlayed";

    public MostPlayed() {
    }

    @Override
    public ResultSet getSongs(int id) {
        return dbc.getMostPlayedSongs(id);
    }

    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }

    public String getType() {
        return type;
    }

}
