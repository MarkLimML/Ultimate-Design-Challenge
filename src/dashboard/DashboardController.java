package dashboard;

import Model.ModelAbstract;
import PlaylistView.PlaylistViewController;
import PlaylistView.PlaylistViewModel;
import Profile.ProfileController;
import Profile.ProfileModel;
import Search.SearchController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardController extends dashboard.ControllerAbstract {


    public HBox hbxUserAlbums;
    public AnchorPane anchorPane;
    public TextField artistInput;
    public TextField albumInput;
    public TextField listenerInput;
    public TextField playlistInput;
    public Button searchArtist;
    public Button searchListener;
    public Button searchAlbum;
    public Button searchSong;
    private DashboardModel model;

    @FXML
    private Label recentlyPlayedLabel;
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
        searchSong = new Button();
        searchSong.setVisible(false);
        playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        playlistPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        playlistScroll.setFitToWidth(true);
//        recentlyPlayedLabel.setText("Recently Played Song: " + model.getDbc().showRecentlyPlayed(model.getUser().getUser_id()));
        ResultSet rs = model.getDbc().showRecentlyPlayed(model.getUser().getUser_id());
        String title = "";
        try
        {
            if(rs.next()) {
                title = rs.getString("song_name");
            }
            else
                title = " None";
            recentlyPlayedLabel.setText("Recently Played Song: " + title);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
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
        if (imgpath == "Artist")
            imgpath = "/dashboard/Artist.png";
        else if (imgpath == "Listener")
            imgpath = "/dashboard/Listener.png";

        //if(imgpath!="Artist" && imgpath!="Listener") {

            Image img = new Image(imgpath);
            imageView.setImage(img);
        //}


        box.getChildren().add(imageView);

        Label label = new Label(box.getPlaylistName());
        label.setTextFill(Color.web("#ffffff"));
        box.getChildren().add(label);
        box.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("handle()");
                if(box.getImgPath()=="Artist" || box.getImgPath()=="Listener")
                    switchToUserInfoWithId(box.getPlaylistId());
                else
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
        System.out.println("switchToUserInfo()");
        this.setScene(mainPane.getScene());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[8]));
            this.setRoot(loader.load());
            ProfileController profileController = loader.getController();
            profileController.getModel().setCurrentProfile(ModelAbstract.getUser().getUser_id());
            profileController.setInfo();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void switchToUserInfoWithId(int user_id) {
        System.out.println("switchToUserInfoWithId()");
        this.setScene(mainPane.getScene());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[8]));
            this.setRoot(loader.load());
            ProfileController profileController = loader.getController();
            profileController.getModel().setCurrentProfile(user_id);
            profileController.setInfo();
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public void loggingOut() {
        ModelAbstract.setUser(null);
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[1]);
    }

    public void searchArtistButt(MouseEvent mouseEvent) {
        String searchArtist = artistInput.getText();

        playlistPane.getChildren().clear();
        ArrayList<PlaylistBox> boxes;
        boxes = model.getUserList();
        for (PlaylistBox box : boxes) {
            if(box.getPlaylistName().contains(searchArtist) && box.getImgPath().equals("Artist"))
                this.addPlaylistBox(box);
        }
        artistInput.setText("");
    }

    public void searchListenerButt(MouseEvent mouseEvent) {
        String searchListener = listenerInput.getText();

        playlistPane.getChildren().clear();
        ArrayList<PlaylistBox> boxes;
        boxes = model.getUserList();
        for (PlaylistBox box : boxes) {
            if(box.getPlaylistName().contains(searchListener) && box.getImgPath().equals("Listener"))
                this.addPlaylistBox(box);
        }
        listenerInput.setText("");
    }

    public void searchAlbumButt(MouseEvent mouseEvent) {
       String searchAlbums = albumInput.getText().trim();

        playlistPane.getChildren().clear();
        currentPlaylistsType = "AlbumPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            if(box.getPlaylistName().equalsIgnoreCase(searchAlbums)) {
                System.out.println("Searched: " + searchAlbums);
                System.out.println("Compared: " + box.getPlaylistName());
                this.addPlaylistBox(box);
            }
        }
        albumInput.setText("");
    }

    public void searchSongButt(MouseEvent mouseEvent) {
        this.setScene(playlistPane.getScene());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[7]));
            this.setRoot(loader.load());
            SearchController searchController = loader.getController();
            PlaylistModel pm = new PlaylistModel();
            pm.setPlaylistType("AllUserSongs");
            pm.setPlaylistID();
            searchController.getModel().setPlaylistModel(pm);
            searchController.getModel().setSongs();
            searchController.getModel().setController(searchController);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
    
    public void viewUserAlbumPlaylists(MouseEvent mouseEvent) {
        playlistPane.getChildren().clear();
        currentPlaylistsType = "UserAlbumPlaylist";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            this.addPlaylistBox(box);
        }
        currentPlaylistsType = "AlbumPlaylist";
    }

    public void searchPlaylistButt(MouseEvent mouseEvent) {
        String searchPlaylists = playlistInput.getText().trim();

        playlistPane.getChildren().clear();
        currentPlaylistsType = "AllPlaylists";
        ArrayList<PlaylistBox> boxes;
        boxes = model.getListOfPlaylist(currentPlaylistsType);
        for (PlaylistBox box : boxes) {
            if(box.getPlaylistName().equalsIgnoreCase(searchPlaylists))
                this.addPlaylistBox(box);
        }
        playlistInput.setText("");
    }
}
