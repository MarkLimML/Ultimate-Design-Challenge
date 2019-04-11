package Model;

import java.sql.*;
import java.util.ArrayList;
import AddSong.Song;

public class DatabaseConnector {

    private static DatabaseConnector instance = null;
    private Connection connection;

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
    private PreparedStatement selectFavoriteSongs;
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

    private DatabaseConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/musicplayerudc?autoReconnect=true&useSSL=false",
                    "despa2", "#str3ssp4");
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
                    "NATURAL LEFT JOIN Artists\n";
            selectAllSongs = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE song_id = ?";
            selectSongFromId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE album_id = ?";
            selectSongsWithAlbumId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE artist_id = ?";
            selectSongsWithArtistId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE genre_id = ?";
            selectSongsWithGenreId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Playlists\n" +
                    "NATURAL LEFT JOIN Playlist_Songs\n" +
                    "NATURAL LEFT JOIN Songs\n" +
                    "LEFT JOIN Albums using(album_id)\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "WHERE playlist_id = ? AND user_id = ?";
            selectSongsWithPlaylistId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "WHERE user_id = ?";
            selectSongsWithUserId = connection.prepareStatement(stmt);

            stmt = "SELECT * FROM Songs \n" +
                    "NATURAL LEFT JOIN Albums \n" +
                    "NATURAL LEFT JOIN Genres \n" +
                    "NATURAL LEFT JOIN Artists \n" +
                    "WHERE year = ?";
            selectSongsWithYear = connection.prepareStatement(stmt);

            stmt = "SELECT distinct year FROM Albums\n" +
                    "ORDER BY year desc";
            selectDistinctYears = connection.prepareStatement(stmt);

            stmt = "SELECT DISTINCT * FROM Songs\n" +
                    "NATURAL LEFT JOIN Genres\n" +
                    "NATURAL LEFT JOIN Artists\n" +
                    "NATURAL LEFT JOIN Albums\n" +
                    "NATURAL LEFT JOIN Times_played\n" +
                    "WHERE user_id = ?\n" +
                    "ORDER BY Times_played\n" +
                    "DESC LIMIT 10";
            selectFavoriteSongs = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, song_path, user_id) VALUES (?, ?, ?)";
            insertSong = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, album_id, song_path, user_id) VALUES (?, ?, ?, ?)";
            insertSongWAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, artist_id, song_path, user_id) VALUES (?, ?, ?, ?)";
            insertSongWArtist = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, song_path, user_id) VALUES (?, ?, ?, ?)";
            insertSongWGenre = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, artist_id, song_path, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWGenreArtist = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, album_id, song_path, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWGenreAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, artist_id, album_id, song_path, user_id) VALUES (?, ?, ?, ?, ?)";
            insertSongWArtistAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Songs (song_name, genre_id, artist_id, album_id, song_path, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            insertSongWGenreArtistAlbum = connection.prepareStatement(stmt);

            stmt = "INSERT INTO Playlists (title, user_id) VALUES (?, ?)";
            insertPlaylist = connection.prepareStatement(stmt);


            stmt = "SELECT times_played FROM Times_played\n" +
                    "WHERE song_id = ? AND user_id = ?";
            timesPlayedFromID = connection.prepareStatement(stmt);

            stmt = "UPDATE Times_played\n" +
                    "SET times_played = ?\n" +
                    "WHERE song_id = ?\n" +
                    "AND user_id = ?\n" ;

            updateTimesPlayed = connection.prepareStatement(stmt);

            stmt =  "INSERT INTO Times_played (song_id, user_id) VALUES (?, ?)";

            insertSongToTimesPlayed = connection.prepareStatement(stmt);

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
                song.setAlbumId(rs.getInt("artist_id"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("artist_name"));
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
                song.setAlbumId(rs.getInt("artist_id"));
                song.setAlbumId(rs.getInt("album_id"));
                song.setGenreId(rs.getInt("genre_id"));
                song.setYear(rs.getInt("year"));
                song.setAlbum(rs.getString("album_name"));
                song.setGenre(rs.getString("genre_name"));
                song.setArtist(rs.getString("artist_name"));
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

    public void createArtist (String artist_name) {
        System.out.println("createArtist(" + artist_name +")");
        String stmt = "INSERT INTO Artists (artist_name) VALUES (?);";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            ps.setString(1, artist_name);
            boolean success = ps.execute();
            System.out.println(success);
        } catch (SQLException se) {
            System.out.println("fail");
            se.printStackTrace();
        }
    }

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

    public void createSong (String song_name, String song_path, int user_id) {
        try {
            insertSong.setString(1, song_name);
            insertSong.setString(2, song_path);
            insertSong.setInt(3, user_id);
            insertSong.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenre (String song_name, String song_path, int genre_id, int user_id) {
        try {
            insertSongWGenre.setString(1, song_name);
            insertSongWGenre.setString(2, song_path);
            insertSongWGenre.setInt(3, genre_id);
            insertSongWGenre.setInt(4, user_id);
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
            insertSongWGenreArtist.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWArtistAlbum (String song_name, String song_path, int artist_id, int album_id, int user_id) {
        try {
            insertSongWArtistAlbum.setString(1, song_name);
            insertSongWArtistAlbum.setString(2, song_path);
            insertSongWArtistAlbum.setInt(3, artist_id);
            insertSongWArtistAlbum.setInt(4, album_id);
            insertSongWArtistAlbum.setInt(5, user_id);
            insertSongWArtistAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void createSongWGenreArtistAlbum (String song_name, String song_path, int genre_id, int artist_id, int album_id, int user_id) {
        try { //INSERT INTO Songs (song_name, genre_id, artist_id, album_id, song_path, user_id) VALUES (?, ?, ?, ?, ?, ?)
            insertSongWGenreArtistAlbum.setString(1, song_name);
            insertSongWGenreArtistAlbum.setInt(2, genre_id);
            insertSongWGenreArtistAlbum.setInt(3, artist_id);
            insertSongWGenreArtistAlbum.setInt(4, album_id);
            insertSongWGenreArtistAlbum.setString(5, song_path);
            insertSongWGenreArtistAlbum.setInt(6, user_id);
            insertSongWGenreArtistAlbum.execute();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    public void createPlaylist (String title, int user_id) {
        System.out.println("createPlaylist()");
        try { //             stmt = "INSERT INTO Playlists (title, user_id, img_path)";
            System.out.println("Title="+title);
            System.out.println("user_id="+user_id);
            insertPlaylist.setString(1, title);
            insertPlaylist.setInt(2, user_id);
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


    public int getArtistIdFromName (String name) {
        String stmt = "SELECT artist_id FROM Artists WHERE artist_name LIKE ?";
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

    public ResultSet getAlbumSongs (int album_id) {
        try {
            selectSongsWithAlbumId.setInt(1, album_id);
            return selectSongsWithAlbumId.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ResultSet getArtistInfo () {
        String stmt = "SELECT * FROM Artists";
        try {
            PreparedStatement ps = connection.prepareStatement(stmt);
            return ps.executeQuery();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

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


    public ResultSet getUserSongs (int user_id) {
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

    public ResultSet getFavoriteSongs (int user_id) {
        try {
            selectFavoriteSongs.setInt(1, user_id);
            ResultSet rs = selectFavoriteSongs.executeQuery();
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

}
