package Profile;

import AddSong.Song;
import Model.ModelAbstract;
import Model.User;
import dashboard.PlaylistBox;
import makePlaylist.Playlist;
import makePlaylist.PlaylistModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileModel extends ModelAbstract {
    ProfileController controller;
  //  private User displayedUser;


    public ArrayList<User> getArtistsFollowed(){
        System.out.println("getArtistsFollowed()");
        ArrayList<User> artists = new ArrayList<>();

        User u;
        ResultSet rs = getDbc().getUserFollowed(ModelAbstract.getUser().getUser_id(), 1);
        try {
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString("username"));
                artists.add(u);
                System.out.println(u.getUsername());
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return artists;
    }

    public ArrayList<User> getListenersFollowed(){
        System.out.println("getListenersFollowed()");
        ArrayList<User> listeners = new ArrayList<>();

        User u;
        ResultSet rs = getDbc().getUserFollowed(ModelAbstract.getUser().getUser_id(), 0);
        try {
            while (rs.next()) {
                u = new User();
                u.setUsername(rs.getString("username"));
                listeners.add(u);
                System.out.println(u.getUsername());
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return listeners;
    }

    public ArrayList<PlaylistModel> getFavoritePlaylists() {
        System.out.println("getFavoritePlaylists()");
        ArrayList<PlaylistModel> favplaylists = new ArrayList<>();
        PlaylistModel p;
        ResultSet rs = getDbc().getFavoritePlaylist(ModelAbstract.getUser().getUser_id());
        try {
            while (rs.next()) {
                p = new PlaylistModel();
                p.setPlaylistID(rs.getInt("playlist_id"));
                p.setAlbumTitle(rs.getString("title"));
                favplaylists.add(p);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return favplaylists;
    }

    public ArrayList<Song> getFavoriteSongs() {
        System.out.println("getFavoriteSongs()");
        ArrayList<Song> songs = new ArrayList<>();
        Song s;
        ResultSet rs = getDbc().getFavoriteSongs(ModelAbstract.getUser().getUser_id());
        try {
            while (rs.next()) {
                s = new Song();
                s.setName(rs.getString("song_name"));
                songs.add(s);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return songs;
    }

    public void setCurrentProfile(User user) {
        setUser(user);
    }

    public void setController(ProfileController controller) {
        System.out.println("setController()");
        this.controller = controller;
    }

/*    public void setDisplayedUser(User user) {
        this.displayedUser = user;
    }*/
}
