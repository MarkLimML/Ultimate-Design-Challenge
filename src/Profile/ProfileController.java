package Profile;

import AddSong.Song;
import Model.ModelAbstract;
import Model.User;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.util.Callback;
import makePlaylist.Playlist;
import javafx.scene.input.MouseEvent;
import makePlaylist.PlaylistModel;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileController extends ControllerAbstract {

    public Button backButt;
    public Button songsButt;
    public Button favsButt;
    public Button artistButt;
    public Button listenerButt;
    public ScrollPane scrollPane;
    public AnchorPane tablePane;
    public Label userNameLabel;
    public Label userTypeLabel;
    public Button followButt;
    public Pane mainPane;
    private ProfileModel model;

    private TableView<Song> SongTable;
    private TableColumn<Song, String> colSong;
    private TableView<PlaylistModel> PlaylistTable;
    private TableColumn<PlaylistModel, String> colPlaylist;
    private TableView<User> ListenerTable;
    private TableView<User> ArtistTable;
    private TableColumn<User, String> colUser;


    private ArrayList<User> listeners;
    private ObservableList<User> listenerList;
    private ArrayList<User> artists;
    private ObservableList<User> artistList;
    private ArrayList<Song> songs;
    private ObservableList<Song> songList;
    private ArrayList<PlaylistModel> playlists;
    private ObservableList<PlaylistModel> favPlaylists;

    private User displayedUser;

    public ProfileController() {
        model = new ProfileModel();
    }

    public void initialize() {
        userNameLabel = new Label();
        userTypeLabel = new Label();
        userNameLabel.setText(ModelAbstract.getUser().getUsername());
        if(ModelAbstract.getUser().isArtist())
            userTypeLabel.setText("Artist");
        else
            userTypeLabel.setText("Listener");
    }

    private void setSongTableColumns() {
        colSong = new TableColumn<Song, String>("Favorite Song");
        colSong.setMinWidth(350);
        colSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSong.setEditable(false);

        SongTable.setFixedCellSize(60.0);
        SongTable.setItems(songList);
        SongTable.getColumns().addAll(colSong);
    }

    private void setPlaylistTableColumns() {
        colPlaylist = new TableColumn<PlaylistModel, String>("Playlists");
        colPlaylist.setMinWidth(350);
        colPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));
        colPlaylist.setEditable(false);

        PlaylistTable.setFixedCellSize(60.0);
        PlaylistTable.setItems(favPlaylists);
        PlaylistTable.getColumns().addAll(colPlaylist);
    }

    private void setListenerTableColumns() {
        colUser = new TableColumn<User, String>("Name");
        colUser.setMinWidth(50);
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUser.setEditable(false);

        ListenerTable.setFixedCellSize(60.0);
        ListenerTable.setItems(listenerList);
        ListenerTable.getColumns().addAll(colUser);
    }

    private void setArtistTableColumns() {
        colUser = new TableColumn<User, String>("Name");
        colUser.setMinWidth(50);
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUser.setEditable(false);

        ArtistTable.setFixedCellSize(60.0);
        ArtistTable.setItems(artistList);
        ArtistTable.getColumns().addAll(colUser);
    }

    public void goBack(MouseEvent mouseEvent) {
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }

    public void showSongs(MouseEvent mouseEvent) {
        //tableview should contain fav songs
        tablePane.getChildren().removeAll();
        System.out.println("createSongTable()");
        SongTable = new TableView<Song>();
        SongTable.setMinHeight(399.0);
        SongTable.setMinWidth(1000.0);
        SongTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setSongList();
        setSongTableColumns();
        tablePane.getChildren().add(SongTable);
    }

    public void showPlaylists(MouseEvent mouseEvent) {
        /*
        favPlaylists = getFavPlaylists();
        tableView.setItems(favPlaylists)

        //Should be in model but model is empty and i dont get what you said you were trying to do cause there's nothing to use as basis
        public Arraylist<Playlist> getFavPlaylists(){
        ModelAbstract.getDbc().getFavoritePlaylist(userId);
        }
        */
        tablePane.getChildren().removeAll();
        System.out.println("createPlaylistTable()");
        PlaylistTable = new TableView<PlaylistModel>();
        PlaylistTable.setMinHeight(399.0);
        PlaylistTable.setMinWidth(1000.0);
        PlaylistTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setFavPlaylists();
        setPlaylistTableColumns();
        tablePane.getChildren().add(PlaylistTable);
    }

    public void showArtists(MouseEvent mouseEvent) {
        //tableview should contain followed artists
        tablePane.getChildren().removeAll();
        System.out.println("createArtistTable()");
        ArtistTable = new TableView<User>();
        ArtistTable.setMinHeight(399.0);
        ArtistTable.setMinWidth(1000.0);
        ArtistTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setArtistList();
        setArtistTableColumns();
        tablePane.getChildren().add(ArtistTable);
    }

    public void showListeners(MouseEvent mouseEvent) {
        //tableview should contain followed listeners
        tablePane.getChildren().removeAll();
        System.out.println("createListenerTable()");
        ListenerTable = new TableView<User>();
        ListenerTable.setMinHeight(399.0);
        ListenerTable.setMinWidth(1000.0);
        ListenerTable.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        setListenerList();
        setListenerTableColumns();
        tablePane.getChildren().add(ListenerTable);
    }

    public void followUser(MouseEvent mouseEvent) {
        if (!(model.getDbc().userIsFollowing(model.getUser().getUser_id(), displayedUser.getUser_id()))){
            model.getDbc().followUser(model.getUser().getUser_id(), displayedUser.getUser_id());
    }
        else
            model.getDbc().unfollowUser(model.getUser().getUser_id(), displayedUser.getUser_id());
    }

    private void setSongList() {
        System.out.println("getSongList()");
        songs = model.getFavoriteSongs();
        songList = FXCollections.observableArrayList(songs);
    }

    private void setFavPlaylists() {
        System.out.println("getFavPlaylist()");
        playlists = model.getFavoritePlaylists();
        favPlaylists = FXCollections.observableArrayList(playlists);
    }

    private void setListenerList() {
        System.out.println("getListenerList()");
        listeners = model.getListenersFollowed();
        System.out.println(listeners.get(0).getUsername());
        listenerList = FXCollections.observableArrayList(listeners);
    }

    private void setArtistList() {
        System.out.println("getArtistList()");
        artists = model.getArtistsFollowed();
        artistList = FXCollections.observableArrayList(artists);
    }
}
