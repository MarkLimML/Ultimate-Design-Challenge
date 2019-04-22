package Model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import AddSong.Song;

public class DatabaseConnector {

    private static DatabaseConnector instance = null;
    private Connection connection;
    private LocalDate localdate = LocalDate.now();
    private java.util.Date date = new java.util.Date();
    private java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String create=df.format(date);

    private PreparedStatement selectUserFromId;
    private PreparedStatement selectSongFromId;
    private PreparedStatement selectSongsWithGenreId;
    private PreparedStatement selectSongsWithArtistId;
    private PreparedStatement selectSongsWithAlbumId;
    private PreparedStatement selectSongsWithPlaylistId;
    private PreparedStatement selectSongsWithUserId;
    private PreparedStatement selectSongsWithYear;
    private PreparedStatement selectDistinctYears;
    private PreparedStatement selectAllSongs;
    private PreparedStatement selectMostPlayed;
    private PreparedStatement timesPlayedFromID;

    private PreparedStatement insertSong;
    private PreparedStatement insertSongWAlbum;
    private PreparedStatement insertSongWArtist;
    private PreparedStatement insertSongWGenre;
    private PreparedStatement insertSongWGenreArtist;
    private PreparedStatement insertSongWGenreAlbum;
    private PreparedStatement insertSongWArtistAlbum;
    private PreparedStatement insertSongWGenreArtistAlbum;

    private PreparedStatement insertPlaylist;
    private PreparedStatement updateTimesPlayed;
    private PreparedStatement insertSongToTimesPlayed;


    //UPDATE
    private PreparedStatement insertBlob;
    private PreparedStatement insertSongWBlob;
    private PreparedStatement insertSongWAlbumBlob;
    private PreparedStatement insertSongWArtistBlob;
    private PreparedStatement insertSongWGenreBlob;
    private PreparedStatement insertSongWGenreArtistBlob;
    private PreparedStatement insertSongWGenreAlbumBlob;
    private PreparedStatement insertSongWArtistAlbumBlob;
    private PreparedStatement insertSongWGenreArtistAlbumBlob;
    //    private PreparedStatement selectSongBlob;
    private PreparedStatement insertBlobToSong;
    private PreparedStatement selectAllIDFromSongName;
    private PreparedStatement deleteSongWID;
    private PreparedStatement deleteAlbumWID;
    private PreparedStatement removeSongInPlaylist;
    private PreparedStatement removeSongInAlbum;
    private PreparedStatement deletePlaylistWID;
    private PreparedStatement updatePublish;
    private PreparedStatement isSongPublic;
    private PreparedStatement selectFavoriteSongs;
    private PreparedStatement selectFavoritePlaylist;
    private PreparedStatement selectAllSongsWSongYearOrder;
    private PreparedStatement selectAllSongsWSongUploadOrder;
    private PreparedStatement selectAllSongsWAlbumYearOrder;
    private PreparedStatement selectAllSongsWAlbumUploadOrder;
    private PreparedStatement selectAllSongsWPlaylistUploadOrder;


    private PreparedStatement selectSongsWithGenreIdByUpload;
    private PreparedStatement selectSongsWithArtistIdByUpload;
    private PreparedStatement selectSongsWithAlbumIdByUpload;
    private PreparedStatement selectSongsWithPlaylistIdByUpload;
    private PreparedStatement selectSongsWithUserIdByUpload;
    private PreparedStatement selectSongsWithYearByUpload;

    private PreparedStatement selectSongsWithGenreIdByAlbumYear;
    private PreparedStatement selectSongsWithArtistIdByAlbumYear;
    private PreparedStatement selectSongsWithAlbumIdByAlbumYear;
    private PreparedStatement selectSongsWithPlaylistIdByAlbumYear;
    private PreparedStatement selectSongsWithUserIdByAlbumYear;
    private PreparedStatement selectSongsWithYearByAlbumYear;

    private PreparedStatement sortSongInPlaylistByUpload;
    private PreparedStatement sortSongInPlaylistByYear;




    public DatabaseConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/musicplayerudc?autoReconnect=true&useSSL=false",
                    "root", "jude842");
            prepareStatements();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }


    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public void prepareStatements() {
        try {
            String stmt = "SELECT * FROM Accounts WHERE user_id = ?";
            selectUserFromId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n";
//                    "NATURAL LEFT JOIN Artists\n";
            selectAllSongs = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE song_id = ?";
            selectSongFromId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE album_id = ?";
            selectSongsWithAlbumId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?";
            selectSongsWithArtistId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE genre_id = ?";
            selectSongsWithGenreId = connection.prepareStatement(stmt);

            stmt = "SELECT songs.song_id, songs.genre_id, songs.album_id, accounts.username, songs.song_name, genres.genre_name, albums.album_name, songs.song_year AS 'year', songs.song_path, playlists.img_path \n" +
                    "FROM albums\n" +
                    "INNER JOIN accounts\n" +
                    "ON albums.user_id = accounts.user_id\n" +
                    "INNER JOIN playlists\n" +
                    "ON accounts.user_id = playlists.user_id\n" +
                    "INNER JOIN playlist_songs\n" +
                    "ON playlists.playlist_id = playlist_songs.playlist_id\n" +
                    "INNER JOIN songs\n" +
                    "ON playlist_songs.song_id = songs.song_id\n" +
                    "INNER JOIN genres\n" +
                    "ON songs.genre_id = genres.genre_id\n" +
                    "WHERE playlists.playlist_id = ? AND accounts.user_id = ?;";
            selectSongsWithPlaylistId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?";
            selectSongsWithUserId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums \n" +
                    "NATURAL LEFT JOIN Genres \n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists \n" +
                    "WHERE year = ?";
            selectSongsWithYear = connection.prepareStatement(stmt);

            stmt = "SELECT distinct year FROM Albums\n" +
                    "ORDER BY year desc";
            selectDistinctYears = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM songs \n" +
                    "INNER join albums\n" +
                    "on songs.album_id = albums.album_id\n" +
                    "WHERE albums.publish =1";
            isSongPublic = connection.prepareStatement(stmt);

            stmt = "SELECT DISTINCT * FROM Songs\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Times_played\n" +
                    "WHERE user_id = ?\n" +
                    "ORDER BY Times_played\n" +
                    "DESC LIMIT 10";
            selectMostPlayed = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, song_path, user_id,created) VALUES (?, ?, ?, ?)";
            insertSong = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, album_id, song_path, user_id,created) VALUES (?, ?, ?, ?, ?)";
            insertSongWAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, artist_id, song_path, user_id,created) VALUES (?, ?, ?, ?,?)";
            insertSongWArtist = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, song_path, user_id,created) VALUES (?, ?, ?, ?,?)";
            insertSongWGenre = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, artist_id, song_path, user_id,created) VALUES (?, ?, ?, ?, ?,?)";
            insertSongWGenreArtist = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, album_id, song_path, user_id, created) VALUES (?, ?, ?, ?, ?,?)";
            insertSongWGenreAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name,  album_id, song_path, user_id, created) VALUES (?, ?, ?, ?,?)";
            insertSongWArtistAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, album_id, song_path, user_id,created) VALUES (?, ?, ?, ?, ?,?)";
            insertSongWGenreArtistAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Playlists (title, user_id,created) VALUES (?, ?,?)";
            insertPlaylist = connection.prepareStatement(stmt);


            stmt = "SELECT times_played FROM Times_played\n" +
                    "WHERE song_id = ? AND user_id = ?";
            timesPlayedFromID = connection.prepareStatement(stmt);

            stmt = "UPDATE Times_played\n" +
                    "SET times_played = ?\n" +
                    "WHERE song_id = ?\n" +
                    "AND user_id = ?\n";

            updateTimesPlayed = connection.prepareStatement(stmt);

            stmt = "INSERT INTO songs (song_id, user_id) VALUES (?, ?)";

            insertSongToTimesPlayed = connection.prepareStatement(stmt);


            //Update

            stmt = "INSERT INTO Songs (song_name, song_path, song_blob, user_id) VALUES (?, ?, ?, ?)";
            insertSongWBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWAlbumBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, artist_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWArtistBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWGenreBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, artist_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            insertSongWGenreArtistBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            insertSongWGenreAlbumBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWArtistAlbumBlob = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            insertSongWGenreArtistAlbumBlob = connection.prepareStatement(stmt);


            stmt = "INSERT INTO Songs (song_name, song_blob, user_id) VALUES (?, ?, ?)";
            insertBlob = connection.prepareStatement(stmt);

            stmt = "SELECT song_id FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE song_name = %?%";
            selectAllIDFromSongName = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Times_played (song_id, user_id) VALUES (?, ?)";
            insertSongToTimesPlayed = connection.prepareStatement(stmt);

            stmt = "UPDATE Songs\n" +
                    "SET song_blob = ?\n" +
                    "WHERE song_id = ?\n";
            insertBlobToSong = connection.prepareStatement(stmt);


//DELETE/REMOVE


            stmt = "DELETE FROM Songs WHERE songs_id=?";
            deleteSongWID = connection.prepareStatement(stmt);

            stmt = "DELETE FROM albums WHERE album_id=?";
            deleteAlbumWID = connection.prepareStatement(stmt);

            stmt ="DELETE FROM palylists WHERE song_id = ? AND user_id = ?";
            removeSongInPlaylist = connection.prepareStatement(stmt);

            stmt = "DELETE FROM song WHERE album_id =?";
            removeSongInAlbum = connection.prepareStatement(stmt);

            stmt = "DELETE FROM playlists WHERE playlist_id=?";
            deletePlaylistWID = connection.prepareStatement(stmt);


//   for publish
            stmt = "UPDATE albums\n" +
                    "SET  publish = ?\n" +
                    "WHERE album_id = ?\n";

            updatePublish = connection.prepareStatement(stmt);

            stmt = "SELECT * \n" +
                    "FROM songs\n" +
                    "INNER JOIN favorite\n" +
                    "ON songs.song_id = favorite.fave_id\n" +
                    "INNER JOIN accounts\n" +
                    "ON favorite.fave_userid = accounts.user_id\n" +
                    "WHERE accounts.user_id = ? AND favorite.fave_type = ?";
            selectFavoriteSongs = connection.prepareStatement(stmt);

            stmt = "SELECT * \n" +
                    "FROM playlists\n" +
                    "INNER JOIN favorite\n" +
                    "ON playlists.playlist_id = favorite.fave_id\n" +
                    "INNER JOIN accounts\n" +
                    "ON favorite.fave_userid = accounts.user_id\n" +
                    "WHERE accounts.user_id = ? AND favorite.fave_type = ?";
            selectFavoritePlaylist = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    " ORDER by songs.song_year";

            selectAllSongsWSongYearOrder = connection.prepareStatement(stmt);


            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    " ORDER by songs.created";

            selectAllSongsWSongUploadOrder = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "ORDER by albums.year";

            selectAllSongsWAlbumYearOrder = connection.prepareStatement(stmt);


            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "ORDER by albums.created";

            selectAllSongsWAlbumUploadOrder = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Playlists\n" +
                    "NATURAL LEFT JOIN Playlist_Songs\n" +
                    "NATURAL LEFT JOIN Songs\n" +
                    "LEFT JOIN Albums using(user_id)\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE playlist_id = ? AND user_id = ?\n" +
                    "ORDER by playlists.created";

            selectAllSongsWPlaylistUploadOrder=connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE genre_id = ?\n" +
                    "ORDER by songs.created";
            selectSongsWithGenreIdByUpload = connection.prepareStatement(stmt);


            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                   "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?\n"+
                    "ORDER by songs.created";
            selectSongsWithArtistIdByUpload = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE album_id = ?\n"+
                    "ORDER by songs.created";
            selectSongsWithAlbumIdByUpload = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Playlists\n" +
                    "NATURAL LEFT JOIN Playlist_Songs\n" +
                    "NATURAL LEFT JOIN Songs\n" +
                    "LEFT JOIN Albums using(user_id)\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE playlist_id = ? AND user_id = ? \n"+
                    "ORDER by songs.created";
            selectSongsWithPlaylistIdByUpload = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?\n"+
                    "ORDER by songs.created";
            selectSongsWithUserIdByUpload = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums \n" +
                    "NATURAL LEFT JOIN Genres \n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists \n" +
                    "WHERE year = ?\n"+
                    "ORDER by songs.created";
            selectSongsWithYearByUpload = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE genre_id = ?\n" +
                    "ORDER by albums.year";
            selectSongsWithGenreIdByAlbumYear = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                   "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?\n"+
                    "ORDER by albums.year";
            selectSongsWithArtistIdByAlbumYear = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE album_id = ?\n"+
                    "ORDER by albums.year";
            selectSongsWithAlbumIdByAlbumYear = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Playlists\n" +
                    "NATURAL LEFT JOIN Playlist_Songs\n" +
                    "NATURAL LEFT JOIN Songs\n" +
                    "LEFT JOIN Albums using(user_id)\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
                    "WHERE playlist_id = ? AND user_id = ? \n"+
                    "ORDER by albums.year";
            selectSongsWithPlaylistIdByAlbumYear = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?\n"+
                    "ORDER by albums.year";
            selectSongsWithUserIdByAlbumYear = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums \n" +
                    "NATURAL LEFT JOIN Genres \n" +
                    "NATURAL LEFT JOIN Accounts\n" +
//                    "NATURAL LEFT JOIN Artists \n" +
                    "WHERE year = ?\n"+
                    "ORDER by albums.year";
            selectSongsWithYearByAlbumYear = connection.prepareStatement(stmt);


            stmt= "Select * \n" +
                    "from playlist INNER JOIN playlist_songs \n" +
                    "on playlists.playlist_id = playlist_songs.playlist_id \n"+
                    "INNER JOIN songs on playlist_songs.song_id = songs.song_id \n"+
                    "WHERE playlists.user_id =? AND playlists.playlist_id=? \n"+
                    "ORDER BY songs.created";

            sortSongInPlaylistByUpload=connection.prepareStatement(stmt);

            stmt= "Select * \n" +
                    "from playlist INNER JOIN playlist_songs \n" +
                    "on playlists.playlist_id = playlist_songs.playlist_id \n"+
                    "INNER JOIN songs on playlist_songs.song_id = songs.song_id \n"+
                    "WHERE playlists.user_id =? AND playlists.playlist_id=? \n"+
                    "ORDER BY songs.song_year";
            sortSongInPlaylistByYear=connection.prepareStatement(stmt);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Song getSongFromSongID(int song_id) {
        Song song = null;
        try {
            selectSongFromId.setInt(1, song_id);
            ResultSet rs = selectSongFromId.executeQuery();
            if (rs.next()) {
                song = new Song();
                song.setSongId(song_id);
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return song;
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongs.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public User getUserFromID (int user_id) {
        try {
            selectUserFromId.setInt(1, user_id);
            //return selectUserFromId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();

        }
        return null;
    }

    public int validUser(String username, String password) {
        ResultSet rs = userExists(username);
        try {
            if (rs.next()) {
                if (rs.getString("password").equals(password))
                    return rs.getInt("user_id");
                else
                    return 0;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }

    public ResultSet userExists(String username) {
        String stmt = "SELECT * FROM Accounts WHERE username LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, username);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public void createUser(String username, String password, Boolean isArtist) {
        String stmt = "INSERT INTO Accounts (username, password, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setBoolean(3, isArtist);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

//    public void createArtist (String artist_name) {
//        System.out.println("createArtist(" + artist_name +")");
//        String stmt = "INSERT INTO Artists (artist_name) VALUES (?);";
//        try {
//            PreparedStatement ps = connection.prepareStatement(stmt);
//            ps.setString(1, artist_name);
//            boolean success = ps.execute();
//            System.out.println(success);
//        } catch (SQLException se) {
//            System.out.println("fail");
//            se.printStackTrace();
//        }
//    }

    public void createAlbum (String album_name, String img_path, int year) {
        String stmt = "INSERT INTO Albums (album_name, img_path, year) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, album_name);
            ps.setString(2, img_path);
            ps.setInt(3, year);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSong (String song_name, String song_path, int user_id ) {
        try {
            insertSong.setString(1, song_name);
            insertSong.setString(2, song_path);
            insertSong.setInt(3, user_id);
            insertSong.setString(4,create);
            insertSong.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenre (String song_name, String song_path, int genre_id, int user_id){
        try {
            insertSongWGenre.setString(1, song_name);
            insertSongWGenre.setString(2, song_path);
            insertSongWGenre.setInt(3, genre_id);
            insertSongWGenre.setInt(4, user_id);
            insertSongWGenre.setString(5, create);
            insertSongWGenre.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWArtist (String song_name, String song_path, int artist_id, int user_id) {
        try {
            insertSongWArtist.setString(1, song_name);
            insertSongWArtist.setString(2, song_path);
            insertSongWArtist.setInt(3, artist_id);
            insertSongWArtist.setInt(4, user_id);
            insertSongWArtist.setString(5, create);
            insertSongWArtist.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWAlbum (String song_name, String song_path, int album_id, int user_id) {
        try {
            insertSongWAlbum.setString(1, song_name);
            insertSongWAlbum.setString(2, song_path);
            insertSongWAlbum.setInt(3, album_id);
            insertSongWAlbum.setInt(4, user_id);
            insertSongWAlbum.setString(5,create);
            insertSongWAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreAlbum (String song_name, String song_path, int genre_id, int album_id, int user_id) {
        try {
            insertSongWGenreAlbum.setString(1, song_name);
            insertSongWGenreAlbum.setString(2, song_path);
            insertSongWGenreAlbum.setInt(3, genre_id);
            insertSongWGenreAlbum.setInt(4, album_id);
            insertSongWGenreAlbum.setInt(5, user_id);
            insertSongWGenreAlbum.setString(6,create);
            insertSongWGenreAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreArtist (String song_name, String song_path, int genre_id, int artist_id, int user_id) {
        try {
            insertSongWGenreArtist.setString(1, song_name);
            insertSongWGenreArtist.setString(2, song_path);
            insertSongWGenreArtist.setInt(3, genre_id);
            insertSongWGenreArtist.setInt(4, artist_id);
            insertSongWGenreArtist.setInt(5, user_id);
            insertSongWGenreArtist.setString(6,create);
            insertSongWGenreArtist.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWArtistAlbum (String song_name, String song_path, int album_id, int user_id) {
        try {
            insertSongWArtistAlbum.setString(1, song_name);
            insertSongWArtistAlbum.setString(2, song_path);
            insertSongWArtistAlbum.setInt(3, album_id);
            insertSongWArtistAlbum.setInt(4, user_id);
            insertSongWArtistAlbum.setString(5, create);
            insertSongWArtistAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreArtistAlbum (String song_name, String song_path, int genre_id, int album_id, int user_id) {
        try { //INSERT INTO Songs (song_name, genre_id, artist_id, album_id, song_path, user_id) VALUES (?, ?, ?, ?, ?, ?)

            insertSongWGenreArtistAlbum.setString(1, song_name);
            insertSongWGenreArtistAlbum.setInt(2, genre_id);

            insertSongWGenreArtistAlbum.setInt(3, album_id);
            insertSongWGenreArtistAlbum.setString(4, song_path);
            insertSongWGenreArtistAlbum.setInt(5, user_id);
            insertSongWGenreArtistAlbum.setString(6,create);
            insertSongWGenreArtistAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    public void createPlaylist (String title, int user_id) {
        System.out.println("createPlaylist()");
        try { //             stmt = "INSERT INTO Playlists (title, user_id, img_path)";
            System.out.println("Title= " + title);
            System.out.println("user_id= " + user_id);
            insertPlaylist.setString(1, title);
            insertPlaylist.setInt(2, user_id);
            insertPlaylist.setString(3, create);
            System.out.println();
            insertPlaylist.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public int getPlaylistIdFromTitleUser (String title, int user_id) {
        String stmt = "SELECT playlist_id FROM Playlists WHERE title LIKE ? AND user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, title);
            ps.setInt(2, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }

    public void addSongToPlaylist (int song_id, int playlist_id) {
        String stmt = "INSERT INTO Playlist_Songs (song_id, playlist_id) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, song_id);
            ps.setInt(2, playlist_id);
            ps.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


//    public int getArtistIdFromName (String name) {
//        String stmt = "SELECT artist_id FROM Artists WHERE artist_name LIKE ?";
//        try {
//            PreparedStatement ps = connection.prepareStatement(stmt);
//            ps.setString(1, name);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        return -1;
//    }

    public int getAlbumIdFromNameYear (String name, int year) {
        String stmt = "SELECT album_id FROM Albums WHERE album_name LIKE ? AND year = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, name);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }

    public int getAlbumIdFromNameYearUser (String name, int year, int user_id) {
        String stmt = "SELECT album_id FROM Albums WHERE album_name LIKE ? AND year = ? AND user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, name);
            ps.setInt(2, year);
            ps.setInt(3, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }

    public int getGenreIdFromName (String name) {

        String stmt = "SELECT genre_id FROM Genres WHERE genre_name LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return -1;
    }

    /*
    private int playlistId;
    private int playlistName;
    private String imgPath;
     */
    public ResultSet getGenreInfo () {
        String stmt = "SELECT * FROM Genres";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getGenreSongs (int genre_id) {
        try {
            selectSongsWithGenreId.setInt(1, genre_id);
            return selectSongsWithGenreId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getAlbumInfo () {
        String stmt = "SELECT * FROM Albums";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getAlbumsOfArtist (int user_id) {
        String stmt = "SELECT * FROM Albums WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public boolean albumExists (int album_id) {
        String stmt = "SELECT * FROM Albums WHERE album_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, album_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    public ResultSet getAlbumSongs (int album_id) {
        try {
            selectSongsWithAlbumId.setInt(1, album_id);
            return selectSongsWithAlbumId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

//    public ResultSet getArtistInfo () {
//        String stmt = "SELECT * FROM Artists";
//        try {
//            PreparedStatement ps = connection.prepareStatement(stmt);
//            return ps.executeQuery();
//        } catch (SQLException se) {
//            se.printStackTrace();
//        }
//        return null;
//    }

    public ResultSet getArtistSongs (int artist_id) {
        try {
            selectSongsWithArtistId.setInt(1, artist_id);
            return selectSongsWithArtistId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getPlaylistInfo (int user_id) {
        String stmt = "SELECT * FROM Playlists WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getPlaylistSongs (int playlist_id, int user_id) {
        try {
            selectSongsWithPlaylistId.setInt(1, playlist_id);
            selectSongsWithPlaylistId.setInt(2, user_id);
            return selectSongsWithPlaylistId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public boolean isSongInPlaylist (int song_id, int playlist_id) {
        String stmt = "SELECT * FROM Playlist_Songs WHERE song_id = ? AND playlist_id = ?";
        ResultSet rs;
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, song_id);
            ps.setInt(2, playlist_id);

            rs = ps.executeQuery();

            if (rs.next())
                return true;


        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }


    public ResultSet getUserSongs (int user_id) { ///artists
        try {
            selectSongsWithUserId.setInt(1, user_id);
            ResultSet rs = selectSongsWithUserId.executeQuery();
            System.out.println("rs == null is " + (rs == null));
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getMostPlayedSongs (int user_id) {
        try {
            selectMostPlayed.setInt(1, user_id);
            ResultSet rs = selectMostPlayed.executeQuery();
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getYearInfo () {
        try {
            return selectDistinctYears.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getSongsFromYear (int year) {
        try {
            selectSongsWithYear.setInt(1, year);
            return selectSongsWithYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public int getTimesPlayedFromID (int song_id, int user_id) {
        try {
            timesPlayedFromID.setInt(1, song_id);
            timesPlayedFromID.setInt(2, user_id);
            ResultSet rs = timesPlayedFromID.executeQuery();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }


    public void updateTimesPlayed (int timesPlayed, int song_id, int user_id) {
        try {
            updateTimesPlayed.setInt(1, timesPlayed);
            updateTimesPlayed.setInt(2, song_id);
            updateTimesPlayed.setInt(3, user_id);
            updateTimesPlayed.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void insertSongToTimesPlayed (int song_id, int user_id) {
        try {
            insertSongToTimesPlayed.setInt(1, song_id);
            insertSongToTimesPlayed.setInt(2, user_id);
            insertSongToTimesPlayed.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void addBlobToSong (Blob song_blob, int song_id) {
        try {
            insertBlobToSong.setBlob(1, song_blob);
            insertBlobToSong.setInt(2, song_id);
            insertBlobToSong.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createBlob (String song_name, Blob song_blob, int user_id) {
        try {// "INSERT INTO Songs (song_name, song_blob, user_id) VALUES (?, ?, ?)

            insertBlob.setString(1, song_name);
            insertBlob.setBlob(2, song_blob);
            insertBlob.setInt(3, user_id);
            insertBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWBlob (String song_name, String song_path,Blob song_blob, int user_id) {
        try {//INSERT INTO Songs (song_name, song_path, song_blob, user_id) VALUES (?, ?, ?, ?)
            insertSongWBlob.setString(1, song_name);
            insertSongWBlob.setString(2, song_path);
            insertSongWBlob.setBlob(3, song_blob);
            insertSongWBlob.setInt(4, user_id);
            insertSongWBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreBlob (String song_name, String song_path, int genre_id, Blob song_blob, int user_id) {
        try {//INSERT INTO Songs (song_name, genre_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)";


            insertSongWGenreBlob.setString(1, song_name);
            insertSongWGenreBlob.setString(3, song_path);
            insertSongWGenreBlob.setInt(2, genre_id);
            insertSongWGenreBlob.setBlob(4, song_blob);
            insertSongWGenreBlob.setInt(5, user_id);
            insertSongWGenreBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWArtist (String song_name, String song_path, int artist_id, Blob song_blob,int user_id) {
        try {
            // INSERT INTO Songs (song_name, artist_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)

            insertSongWArtistBlob.setString(3, song_path);
            insertSongWArtistBlob.setString(1, song_name);
            insertSongWArtistBlob.setInt(3, artist_id);
            insertSongWArtistBlob.setBlob(4, song_blob);
            insertSongWArtistBlob.setInt(5, user_id);
            insertSongWArtistBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWAlbumBlob (String song_name, String song_path, int album_id,  Blob song_blob, int user_id) {
        try {// INSERT INTO Songs (song_name, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?)


            insertSongWAlbumBlob.setString(1, song_name);
            insertSongWAlbumBlob.setString(3, song_path);
            insertSongWAlbumBlob.setInt(2, album_id);
            insertSongWAlbumBlob.setBlob(4, song_blob);
            insertSongWAlbumBlob.setInt(5, user_id);
            insertSongWAlbumBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreAlbumBlob (String song_name, String song_path, int genre_id, int album_id,Blob song_blob, int user_id) {
        try { //INSERT INTO Songs (song_name, genre_id, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)


            insertSongWGenreAlbumBlob.setString(1, song_name);
            insertSongWGenreAlbumBlob.setString(4, song_path);
            insertSongWGenreAlbumBlob.setInt(2, genre_id);
            insertSongWGenreAlbumBlob.setInt(3, album_id);
            insertSongWGenreAlbumBlob.setBlob(5, song_blob);
            insertSongWGenreAlbumBlob.setInt(6, user_id);
            insertSongWGenreAlbumBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreArtistBlob (String song_name, String song_path, int genre_id, int artist_id, Blob song_blob, int user_id) {
        try { //INSERT INTO Songs (song_name, genre_id, artist_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)

            insertSongWGenreArtistBlob.setString(1, song_name);
            insertSongWGenreArtistBlob.setString(4, song_path);
            insertSongWGenreArtistBlob.setInt(2, genre_id);
            insertSongWGenreArtistBlob.setInt(3, artist_id);
            insertSongWGenreArtistBlob.setBlob(5, song_blob);
            insertSongWGenreArtistBlob.setInt(6, user_id);
            insertSongWGenreArtistBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    public void createSongWArtistAlbumBlob (String song_name, String song_path, int album_id,Blob song_blob, int user_id) {
        try {//INSERT INTO Songs (song_name, artist_id, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?)

            insertSongWArtistAlbumBlob.setString(1, song_name);
            insertSongWArtistAlbumBlob.setString(3, song_path);
            insertSongWArtistAlbumBlob.setInt(2, album_id);
            insertSongWArtistAlbumBlob.setBlob(4, song_blob);
            insertSongWArtistAlbumBlob.setInt(5, user_id);
            insertSongWArtistAlbumBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreArtistAlbumBlob (String song_name, String song_path, int genre_id, int album_id, Blob song_blob, int user_id) {
        try {// INSERT INTO Songs (song_name, genre_id, artist_id, album_id, song_path, song_blob, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)

            insertSongWGenreArtistAlbumBlob.setString(1, song_name);
            insertSongWGenreArtistAlbumBlob.setInt(2, genre_id);

            insertSongWGenreArtistAlbumBlob.setInt(3, album_id);
            insertSongWGenreArtistAlbumBlob.setString(4, song_path);
            insertSongWGenreArtistAlbumBlob.setBlob(5, song_blob);
            insertSongWGenreArtistAlbumBlob.setInt(6, user_id);
            insertSongWGenreArtistAlbumBlob.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Blob getSongBlob (Blob song_blob) {

        String stmt = "SELECT song_blob FROM songs WHERE song_id LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setBlob(1, song_blob);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBlob(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public ResultSet getAllIDFromSongname () {
        try {
            return selectAllIDFromSongName.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public int getIDFromSongName (String name) {
        String stmt = "SELECT song_id FROM Songs WHERE song_name = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return -1;
    }


    // FOR FAVORITE
    public boolean SongIsSetFavorite(int user_id, int fave_id) {
        String stmt = "SELECT * FROM favorite WHERE fave_userid = ? AND fave_id = ? AND fave_type = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            ps.setInt(2, fave_id);
            ps.setInt(3, 0);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean PlaylistIsSetFavorite(int user_id, int fave_id) {
        String stmt = "SELECT * FROM favorite WHERE fave_userid = ? AND fave_id = ? AND fave_type = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            ps.setInt(2, fave_id);
            ps.setInt(3, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void songIsFavorite (int user_id, int fave_id){
        String stmt =  "INSERT INTO favorite(fave_userid, fave_id,fave_type) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,user_id);
            ps.setInt(2,fave_id);
            ps.setInt(3,0);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playlistIsFavorite (int user_id, int fave_id){
        String stmt =  "INSERT INTO favorite(fave_userid, fave_id,fave_type) VALUES (?, ?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,user_id);
            ps.setInt(2,fave_id);
            ps.setInt(3,1);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeIsFavoriteSong (int user_id, int fave_id){
        String stmt =  "DELETE FROM favorite where user_id=? AND fave_id=? AND fave_type=?";;
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,user_id);
            ps.setInt(2,fave_id);
            ps.setInt(3,0);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeIsFavoritePlaylist (int user_id, int fave_id){
        String stmt =  "DELETE FROM favorite where user_id=? AND fave_id=? AND fave_type=?";;
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,user_id);
            ps.setInt(2,fave_id);
            ps.setInt(3,1);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

// END OF FAVORITE


    public void DeleteSong (int song_id) {
        try {
            deleteSongWID.setInt(1, song_id);
            deleteSongWID.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void DeleteAlbum ( int album_id) {
        try {
            deleteAlbumWID.setInt(1, album_id);
            deleteAlbumWID.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void DeletePlaylist(int playlist_id){
        try{//"DELETE FROM playlists WHERE playlist_id=?"
            deletePlaylistWID.setInt(1,playlist_id);
            deletePlaylistWID.execute();
        }catch(SQLException se){
            se.printStackTrace();
        }

    }

    public void deleteSongInPlaylist (int song_id, int user_id) {
        try {// DELETE FROM palylists WHERE song_id = ? AND user_id = ?"
            removeSongInPlaylist.setInt(1, song_id);
            removeSongInPlaylist.setInt(2, user_id);
            removeSongInPlaylist.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void deleteSongInAlbum (int album_id) {
        try {// "DELETE FROM song WHERE album_id =?";
            removeSongInAlbum.setInt(1, album_id);
            removeSongInAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }





//pa check this part, not sure if tama or not

    //    check if album is empty if true we can delete it otherwise we can not
    public boolean isAlbumEmpty(int album_id) {
        String stmt = "SELECT COUNT(*) FROM songs WHERE album_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, album_id);
            ResultSet rs = ps.executeQuery();
            if (rs.equals("0"))
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    Publish



    public void setUpdatePublish (Boolean publish, int album_id) {
        try {
            updatePublish.setBoolean(1, publish);
            updatePublish.setInt(2, album_id);
            updatePublish.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

//    public boolean isSongPublic(){
//        String stmt = "SELECT * \n" +
//                "FROM songs INNER join albums\n" +
//                "on songs.album_id = albums.album_id\n" +
//                "WHERE albums.publish =1";
//        try {
//            PreparedStatement ps = connection.prepareStatement(stmt);
//            ResultSet rs = ps.executeQuery();
//            if (rs.equals("0"))
//                return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public ResultSet getPublicSongs () {
        try {
            return isSongPublic.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public boolean userIsArtist (int user_id) {
        String stmt = "SELECT type FROM Accounts WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    public ResultSet getFavoriteSongs (int user_id) {
        try {
            selectFavoriteSongs.setInt(1, user_id);
            selectFavoriteSongs.setInt(2, 0);
            ResultSet rs = selectFavoriteSongs.executeQuery();
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getFavoritePlaylist (int user_id) {
        try {
            selectFavoritePlaylist.setInt(1, user_id);
            selectFavoritePlaylist.setInt(2, 1);
            ResultSet rs = selectFavoritePlaylist.executeQuery();
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserFollowed (int follower, int type)
    {
        String stmt = "SELECT username \n" +
                "FROM follow \n" +
                "INNER JOIN accounts\n" +
                "ON follow.following = accounts.user_id\n" +
                "WHERE follow.follower = ? AND accounts.type = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, follower);
            ps.setInt(2, type);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    //Following

    public boolean userIsFollowing (int follower, int following)
    {
        String stmt = "SELECT * FROM follow WHERE follower = ? AND following = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, follower);
            ps.setInt(2, following);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void followUser (int follower, int following){
        String stmt =  "INSERT INTO follow(follower, following) VALUES (?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,follower);
            ps.setInt(2,following);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unfollowUser (int follower, int following){
        String stmt =  "DELETE FROM follow where follower=? AND following=?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,follower);
            ps.setInt(2,following);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAlbumSongsByUpload (int album_id) {
        try {
            selectSongsWithAlbumIdByUpload .setInt(1, album_id);
            return selectSongsWithAlbumIdByUpload .executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getGenreSongsByUpload (int genre_id) {
        try {
            selectSongsWithGenreIdByUpload.setInt(1, genre_id);
            return selectSongsWithGenreIdByUpload.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getArtistSongsByUpload (int artist_id) {
        try {
            selectSongsWithArtistIdByUpload.setInt(1, artist_id);
            return selectSongsWithArtistIdByUpload.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getPlaylistSongsByUpload (int playlist_id, int user_id) {
        try {
            selectSongsWithPlaylistIdByUpload.setInt(1, playlist_id);
            selectSongsWithPlaylistIdByUpload.setInt(2, user_id);
            return selectSongsWithPlaylistIdByUpload.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserSongsByUpload (int user_id) { ///artists
        try {
            selectSongsWithUserIdByUpload.setInt(1, user_id);
            ResultSet rs = selectSongsWithUserIdByUpload.executeQuery();
            System.out.println("rs == null is " + (rs == null));
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getSongsFromYearByUpload (int year) {
        try {
            selectSongsWithYearByUpload.setInt(1, year);
            return selectSongsWithYearByUpload.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }





    public ResultSet getAlbumSongsByAlbumYear (int album_id) {
        try {
            selectSongsWithAlbumIdByAlbumYear .setInt(1, album_id);
            return  selectSongsWithAlbumIdByAlbumYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getGenreSongsByAlbumYear (int genre_id) {
        try {
            selectSongsWithGenreIdByAlbumYear.setInt(1, genre_id);
            return selectSongsWithGenreIdByAlbumYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getArtistSongsByAlbumYear (int artist_id) {
        try {
            selectSongsWithArtistIdByAlbumYear.setInt(1, artist_id);
            return selectSongsWithArtistIdByAlbumYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getPlaylistSongsByAlbumYear (int playlist_id, int user_id) {
        try {
            selectSongsWithPlaylistIdByAlbumYear.setInt(1, playlist_id);
            selectSongsWithPlaylistIdByAlbumYear.setInt(2, user_id);
            return selectSongsWithPlaylistIdByAlbumYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getUserSongsByAlbumYear (int user_id) { ///artists
        try {
            selectSongsWithUserIdByAlbumYear.setInt(1, user_id);
            ResultSet rs = selectSongsWithUserIdByAlbumYear.executeQuery();
            System.out.println("rs == null is " + (rs == null));
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getSongsFromYearByAlbumYear (int year) {
        try {
            selectSongsWithYearByAlbumYear.setInt(1, year);
            return selectSongsWithYearByAlbumYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ArrayList<Song> getAllSongsWSongYearOrder() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongsWSongYearOrder.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                song.setCreated(rs.getString("created"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> getAllSongsWSongUploadOrder() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongsWSongUploadOrder.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                song.setCreated(rs.getString("created"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> getAllSongsWAlbumYearOrder() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongsWAlbumYearOrder.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                song.setCreated(rs.getString("created"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> getAllSongsWAlbumUploadOrder() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongsWAlbumUploadOrder.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                song.setCreated(rs.getString("created"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> getAllSongsWPlaylistUploadOrder() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = null;
        try {
            ResultSet rs = selectAllSongsWPlaylistUploadOrder.executeQuery();
            while (rs.next()) {
                song = new Song();
                song.setSongId(rs.getInt("song_id"));
                song.setName(rs.getString("name"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("username"));
                song.setImgPath(rs.getString("img_path"));
                song.setSongPath(rs.getString("song_path"));
                song.setCreated(rs.getString("created"));
                songs.add(song);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;

    }

    public ResultSet sortSonginPlaylistByPublish (int user_id , int playlist_id) {
        try {
            sortSongInPlaylistByUpload.setInt(1, user_id);
            sortSongInPlaylistByUpload.setInt(2, playlist_id);
            return sortSongInPlaylistByUpload.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet sortSonginPlaylistByYear (int user_id , int playlist_id) {
        try {
            sortSongInPlaylistByYear.setInt(1, user_id);
            sortSongInPlaylistByYear.setInt(2, playlist_id);
            return sortSongInPlaylistByYear.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public boolean isPlaylistPublic(int playlist_id){
        String stmt = "SELECT * FROM playlists WHERE playlist_id = ? AND publish = 1";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, playlist_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPlaylistPublic (int playlist_id){
        String stmt =  "UPDATE playlists SET publish = 1 WHERE playlist_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,playlist_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlaylistPublic (int playlist_id)
    {
        String stmt =  "UPDATE playlists SET publish = 0 WHERE playlist_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1,playlist_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRecentlyPlayed (int song_id, int user_id)
    {
        String stmt = "UPDATE accounts SET recently_played_id = ? WHERE user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, song_id);
            ps.setInt(2, user_id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet showRecentlyPlayed (int user_id)
    {
        String stmt = "SELECT song_name\n" +
                "FROM songs\n" +
                "INNER JOIN accounts\n" +
                "ON songs.song_id = accounts.recently_played_id\n" +
                "WHERE accounts.user_id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}