package AddSong;

import Model.DatabaseConnector;

import java.util.ArrayList;

public interface Metadata
{
    void attachDB(DatabaseConnector dbc);

    void saveToDatabase(Song song);
}