package makePlaylist;

import AddSong.Song;
import Model.ModelAbstract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class PlaylistModel extends ModelAbstract {
    private String title;
    private int playlistID;
    private String img_path;
    private int albumYear;
    private String genre;
    private String playlistType;
    private ArrayList<Song> playlist;
    private ConcretePlaylistFactory playlistFactory;
    private Date created;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    private String albumTitle;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    private String artistName;

    public int getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(int albumYear) {
        this.albumYear = albumYear;
    }

    public PlaylistModel() {
        playlistFactory = new ConcretePlaylistFactory();
    }

    public String getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(String playlistType) {
        this.playlistType = playlistType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {/*
        if(playlistType.equals("UserPlaylist"))
            this.title = title;
        else if(playlistType.equals("AlbumPlaylist"))
            title = albumTitle ;
        else if(playlistType.equals("AllUserSongs"))
            title = "All user songs" ;
        else if(playlistType.equals("ArtistPlaylist"))
            title = artistName;
        else if(playlistType.equals("GenrePlaylist"))
            title = genre;
        else if(playlistType.equals("UserPlaylist"))
            title = Integer.toString(albumYear);*/
        this.title = title;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID() {
        if(playlistType.equals("UserPlaylist") || playlistType.equals("MostPlayed"))
             playlistID = getDbc().getPlaylistIdFromTitleUser(title, this.getUser_id());
        else if(playlistType.equals("AlbumPlaylist"))
            playlistID = getDbc().getAlbumIdFromNameYear(title, albumYear);
        else if(playlistType.equals("AllUserSongs"))
            playlistID = this.getUser_id();
//        else if(playlistType.equals("ArtistPlaylist"))
//            playlistID = getDbc().getArtistIdFromName(artistName);
        else if(playlistType.equals("GenrePlaylist"))
            playlistID = getDbc().getGenreIdFromName(genre);
        else if(playlistType.equals("YearPlaylist"))
            playlistID = albumYear;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public int getUser_id() {
        return getUser().getUser_id(); // returns user.getUser_id() from the ModelAbstract
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        if(playlistType=="AlbumPlaylist")
        this.img_path = img_path;
        else
            this.img_path = null;
    }

    public ArrayList<Song> resultSetToSong(ResultSet rs){
        System.out.println("resultSetToSong");
        ArrayList<Song> songs = null;
        try {
            if (rs != null && rs.next()) {
                if (rs.getInt("song_id") > 0) {
                    songs = new ArrayList<>();
                    do {
                        Song song = new Song();

                        System.out.println(rs.getInt("song_id"));
                        System.out.println(rs.getString("song_name"));
                        System.out.println(rs.getInt("album_id"));
                        System.out.println(rs.getString("img_path"));


                        song.setSongId(rs.getInt("song_id"));
                        song.setGenreId(rs.getInt("genre_id"));
                        song.setAlbumId(rs.getInt("album_id"));
                        song.setArtist(rs.getString("artist_name"));
                        song.setName(rs.getString("song_name"));
                        song.setGenre(rs.getString("genre_name"));
                        song.setAlbum(rs.getString("album_name"));
                        song.setYear(rs.getInt("year"));
                        song.setSongPath(rs.getString("song_path"));
                        song.setImgPath(rs.getString("img_path"));

                        System.out.println();

                        songs.add(song);

                    } while (rs.next());
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public void createPlaylist() {
        if (title != null) {
            getDbc().createPlaylist(title, this.getUser_id());
            playlistType = "UserPlaylist";
            setPlaylistID();
        }
    }

    public ArrayList<Song> getPlaylist() {
        if (playlist == null) {
            System.out.println("getPlaylist()");
            Playlist playlist = playlistFactory.getPlaylist(playlistType);
            System.out.println(playlist.getType());
            playlist.attachDB(getDbc());
            System.out.println("playlist.getSongs(" + playlistID + ")");
            ResultSet rs = playlist.getSongs(playlistID);
            return resultSetToSong(rs);
        }
        return playlist;
    }

    public void setPlaylist(ArrayList<Song> playlist) {
        this.playlist = playlist;
    }

    public void setEmptyPlaylist () {
        playlist = null;
    }

    public boolean isPlaylistNull () {
        return playlist == null;
    }
}