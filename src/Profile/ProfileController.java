package Profile;

import AddSong.Song;
import Model.ModelAbstract;
import Model.User;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import makePlaylist.Playlist;

import javax.swing.text.TableView;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ProfileController extends ControllerAbstract {

    private ProfileModel model;


    @FXML
    public Label username;
    @FXML
    public Label usertype; //listener or artist
    @FXML
    public Label name;
    @FXML
    public Label type;
    @FXML
    public CheckBox isFollowed;
    @FXML
    public Button back;
    @FXML
    public AnchorPane newProfilePane;
    @FXML
    public Pane usertablepane;
    @FXML
    public Pane songtablepane;
    @FXML
    public Pane playlisttablepane;

    private javafx.scene.control.TableView<User> UserTable;
    private TableColumn<User, String> colUsers;
    private javafx.scene.control.TableView<User> SongTable;
    private TableColumn<User, String> colArtist;
    private TableColumn<User, String> colListener;

    private ArrayList<User> users;
    private ArrayList<Playlist> playlists;

    private User displayedUser;

    public ProfileController() {
        model = new ProfileModel();
        ProfileView view = new ProfileView(this, model);
        model.attach(view);
    }

    public void initialize() {
        isFollowed.setOpacity(0);
        username.setText(displayedUser.getUsername());
        if(displayedUser.getUser().isArtist())
            usertype.setText("Artist");
        else
            usertype.setText("Listener");
        name.setText("Username");
        type.setText("User Type");
        if(!username.getText().equals(ModelAbstract.getUser().getUsername()))
            isFollowed.setOpacity(1);

    }

    public void getfollowing() {

    }

    public void setIsFollowed(MouseEvent e) {

    }

    public void callPreviousScreen(javafx.scene.input.MouseEvent e)  throws IOException
    {
        this.setScene(newProfilePane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }

    public void setDisplayedUser(User user) {
        this.displayedUser = user;
    }
}
