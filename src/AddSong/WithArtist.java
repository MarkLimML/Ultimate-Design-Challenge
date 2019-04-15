//package AddSong;
//
//import Model.DatabaseConnector;
//
//import java.util.ArrayList;
//
//public class WithArtist implements Metadata
//{
//    /*
//        0 - name
//        1 - imagePath
//        2 - songPath
//        3 - album
//        4 - albumYear
//        5 - artist
//        6 - genre
//     */
//    private DatabaseConnector dbc;
//    private String name;
//    private String imgPath;
//    private String songPath;
//    private int user_id;
//    private String artist;
//
//    @Override
//    public void attachDB(DatabaseConnector dbc) {
//        this.dbc = dbc;
//    }
//
//    @Override
//    public void saveToDatabase(Song song) {
//        int artist_id;
//        name = song.getName();
//        artist = song.getArtist();
//        imgPath = song.getImgPath();
//        songPath = song.getSongPath();
//        user_id = song.getUser_id();
//
//        artist_id = dbc.getArtistIdFromName(artist);
//
//        if (artist_id == -1) {
//            dbc.createArtist(artist);
//            artist_id = dbc.getArtistIdFromName(artist);
//        }
//
//        dbc.createSongWArtist(name, songPath, artist_id, user_id);
//    }
//}
