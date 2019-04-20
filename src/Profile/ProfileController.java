package Profile;

import AddSong.Song;
import Model.ModelAbstract;
import Model.User;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;
import makePlaylist.Playlist;

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

    private TableView<Song> SongTable;
    private TableColumn<Song, String> colSong;
    private TableView<Playlist> PlaylistTable;
    private TableColumn<Playlist, String> colPlaylist;
    private TableView<User> UserTable;
    private TableColumn<User, String> colUser;
    private TableColumn<User, String> colType;
    private ArrayList<BooleanProperty> selectedRowList;
    Callback<Integer,ObservableValue<Boolean>> colCbxState;

    private ArrayList<User> users;
    private ObservableList<Song> songList;
    private ObservableList<Playlist> playlists;

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

    private void setTableViewColumns() {
        colUser = new TableColumn<User, String>("Name");
        colType = new TableColumn<User, String>("Type");
        colSong = new TableColumn<Song, String>("Favorite Song");
        colPlaylist = new TableColumn<Playlist, String>("Playlists");

        colUser.setMinWidth(50);
        colType.setMinWidth(50);
        colSong.setMinWidth(350);
        colPlaylist.setMinWidth(350);


        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colType.setCellValueFactory(new PropertyValueFactory<>("isartist"));
        colSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));

        colUser.setEditable(true);
        colType.setEditable(true);
        colSong.setEditable(false);
        colPlaylist.setEditable(false);



        SongTable.setFixedCellSize(60.0);
        SongTable.setItems(songList);
        SongTable.getColumns().addAll(colSong);
    }

    public void setIsFollowed(MouseEvent e) {

    }

    public void callPreviousScreen(MouseEvent e)  throws IOException
    {
        this.setScene(newProfilePane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }

    public void setDisplayedUser(User user) {
        this.displayedUser = user;
    }
}
