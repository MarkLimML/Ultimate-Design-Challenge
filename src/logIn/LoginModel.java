package logIn;

import Model.ModelAbstract;
import Model.User;

public class LoginModel extends ModelAbstract {

    private String username, password;
    private int userId;
    private boolean isArtist;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserIdFromUserPass() {
        userId = getDbc().validUser(username,password);
    }

    public void setArtistFromId() {
        isArtist = getDbc().userIsArtist(userId);
    }

    public boolean isArtist() {
        return isArtist;
    }

    public void setArtist(boolean artist) {
        isArtist = artist;
    }

    public void logUser() {
        setUser(new User());
        getUser().setUser_id(userId);
        getUser().setUsername(username);
        getUser().setUsername(password);
        getUser().setArtist(isArtist);
    }

    public boolean isValidId() {
        if (userId > 0)
            return true;
        return false;
    }

}
