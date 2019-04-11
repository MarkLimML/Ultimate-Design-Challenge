package makePlaylist;

import Model.DatabaseConnector;

import java.sql.ResultSet;

public class YearPlaylist implements Playlist {

    private DatabaseConnector dbc;
    private static final String type = "YearPlaylist";

    public ResultSet getSongs(int year){
        return dbc.getSongsFromYear(year);
    }

    public String getType(){
        return type;
    }


    @Override
    public void attachDB(DatabaseConnector dbc) {
        this.dbc = dbc;
    }
}