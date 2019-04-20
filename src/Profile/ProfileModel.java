package Profile;

import Model.ModelAbstract;
import Model.User;
import dashboard.PlaylistBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileModel extends ModelAbstract {
    ProfileModel controller;

    public void getUserPlaylists() {

    }

    public void getFavoriteSongs() {

    }

    public void setCurrentProfile(User user) {
        setUser(user);
    }

    public void setController(ProfileModel controller) {
        System.out.println("setController()");
        this.controller = controller;
    }
}
