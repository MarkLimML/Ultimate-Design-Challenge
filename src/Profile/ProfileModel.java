package Profile;

import AddSong.Song;
import Model.ModelAbstract;
import Model.User;
import dashboard.PlaylistBox;
import makePlaylist.Playlist;

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
        ArrayList<User> listeners;

        //Same as the one above

        return null;
    }

    public ArrayList<Playlist> getFavoritePlaylists() {
        System.out.println("getFavoritePlaylists()");
        ArrayList<User> favplaylists;

        ResultSet rs = getDbc().getFavoritePlaylist(ModelAbstract.getUser().getUser_id(),ModelAbstract.getUser().getUser_id());
        try {
            while (rs.next()) {

            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    public ArrayList<Song> getFavoriteSongs() {

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
