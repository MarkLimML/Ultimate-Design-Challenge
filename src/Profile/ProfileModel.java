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
        ArrayList<User> artists;

        //Missing query in DB
        //Could be userisFollowing(), but the method is not returning a resultset
        //artists = ModelAbstract.getDbc().getFollowedArtists();

        return null;
    }

    public ArrayList<User> getListenersFollowed(){
        System.out.println("getListenersFollowed()");
        ArrayList<User> listeners = new ArrayList<>();

        //Same as the one above

        return null;
    }

    public ArrayList<PlaylistModel> getFavoritePlaylists() {
        System.out.println("getFavoritePlaylists()");
        ArrayList<PlaylistModel> favplaylists = new ArrayList<>();
        PlaylistModel p = new PlaylistModel();
        ResultSet rs = getDbc().getFavoritePlaylist(ModelAbstract.getUser().getUser_id());
        try {
            while (rs.next()) {
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
        Song s = new Song();
        ResultSet rs = getDbc().getFavoriteSongs(ModelAbstract.getUser().getUser_id());
        try {
            while (rs.next()) {
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
