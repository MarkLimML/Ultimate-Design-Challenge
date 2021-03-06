package dashboard;

import Model.ModelAbstract;
import PlaylistView.PlaylistViewController;
import PlaylistView.PlaylistViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import makePlaylist.Playlist;
import makePlaylist.PlaylistModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardController extends dashboard.ControllerAbstract {


    public HBox hbxUserAlbums;
    private DashboardModel model;

    @FXML
    public FlowPane playlistPane;
    @FXML
    public ScrollPane playlistScroll;
    @FXML
    public Button profileIcon;
    public Button logOut;
    public ScrollPane side_scroll;
    public VBox side_nav;
    public HBox hbxGenres;
    public HBox hbxArists;
    public HBox hbxUserLib;
    public HBox hbxFavorites;
    public HBox hbxAddSong;
    public HBox hbxAddPlist;
    public HBox hbxUserPlaylists;
    public HBox hbxYearSongs;

    public HBox mainPane;

    public String currentPlaylistsType;


    public DashboardController() {
        model = new DashboardModel();
        DashboardView view = new DashboardView(this, model);
        model.attach(view);
    }

    public void initialize() {

        playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        playlistPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playlistScroll.setFitToWidth(true);

        if (!model.isLogged()) {
            hbxAddSong.setDisable(true);
            hbxAddPlist.setDisable(true);
            hbxUserLib.setDisable(true);
            System.out.println(ModelAbstract.getUser().getUser_id());
        } else if (!model.isArtist()) {
            System.out.println(model.isArtist());
            side_nav.getChildren().remove(hbxUserAlbums);
            side_nav.getChildren().remove(hbxAddSong);
        }
    }

    public void addPlaylistBox(PlaylistBox box) {
        System.out.println("addPlaylistBox()");
        box.setVisible(true);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        String imgpath = box.getImgPath().replace("src", "");
        System.out.println(imgpath);
        Image img = new Image(imgpath);
        imageView.setImage(img);

        box.getChildren().add(imageView);

        Label label = new Label(box.getPlaylistName());
        label.setTextFill(Color.web("#ffffff"));
        box.getChildren().add(label);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("handle()");
                switchToPlaylistView(me);
            }
        });

        playlistPane.getChildren().add(box);
    }

    public void switchToPlaylistView (MouseEvent mouseEvent) {
        System.out.println("switchToPlaylistView()\n\n");
        this.setScene(mainPane.getScene());
        PlaylistModel pm = new PlaylistModel();
        pm.setPlaylistType(currentPlaylistsType);
        if (currentPlaylistsType.equals("AllUserSongs")) {
            pm.setTitle("All User Songs");
            pm.setPlaylistID(ModelAbstract.getUser().getUser_id());
        } else if (currentPlaylistsType.equals("MostPlayed")) {
            pm.setTitle("Most Played Songs");
            pm.setPlaylistID(ModelAbstract.getUser().getUser_id());
        } else {
            PlaylistBox box = (PlaylistBox) mouseEvent.getSource();
            System.out.println("boxname="+box.getPlaylistName());
            pm.setTitle(box.getPlaylistName());
            pm.setPlaylistID(box.getPlaylistId());
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[6]));
            this.setRoot(loader.load());
            PlaylistViewController playlistViewController = loader.getController();
            playlistViewController.getModel().setController(playlistViewController);
            //playlistViewController.setPlaylistTitle(pm.getTitle());
            playlistViewController.getModel().attachPlaylistModel(pm);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void viewGenrePlaylists(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "GenrePlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
    }

    public void viewUserPlaylists(MouseEvent mouseEvent) {
        System.out.println("viewUserPlaylists()");
        playlistPane.getChildren().clear();
        currentPlaylistsType = "UserPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
    }

    public void viewArtistPlaylists(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "ArtistPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
    }

    public void viewAlbumPlaylists(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "AlbumPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
    }

    public void viewYearPlaylists(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "YearPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
    }

    public void viewAllUserSongs(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "AllUserSongs";
        switchToPlaylistView(mouseEvent);
    }

    public void viewFavorites(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "MostPlayed";
        switchToPlaylistView(mouseEvent);
    }

    public void switchToAddSong(MouseEvent mouseEvent) {
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[3]);
    }

    public void switchToAddPlaylist(MouseEvent mouseEvent) {
        System.out.println("switchToAddPlaylist()");
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[4]);
    }

    public void switchToUserInfo(MouseEvent mouseEvent) {

    }

    public void loggingOut() {
        ModelAbstract.setUser(null);
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[1]);
    }

}
