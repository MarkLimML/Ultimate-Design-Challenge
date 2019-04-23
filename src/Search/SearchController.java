package Search;

import AddSong.Song;
import PlaylistView.PlaylistViewController;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import makePlaylist.PlaylistModel;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController extends ControllerAbstract {
    @FXML
    public Button searchButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField searchField;
    public Pane mainPane;

    private ArrayList<Song> songArrayList;
    private Song currentSong;

    private SearchModel model;

    public SearchController(){
      this.model = new SearchModel();
    }

    private ArrayList<Song> currentPlaylist;

    private void setSongList() {
        System.out.println("getSongList()");
        currentPlaylist = model.getPlaylistSongs();
    }

    public void startSearch(MouseEvent mouseEvent){

        model.setSearchSong(searchField.getText());

        System.out.println("switchToPlaylistView()\n\n");
        this.setScene(mainPane.getScene());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[6]));
            this.setRoot(loader.load());
            PlaylistViewController playlistViewController = loader.getController();
            model.getPlaylistModel().setPlaylist(model.searchSongs());
            playlistViewController.getModel().setController(playlistViewController);
            playlistViewController.getModel().attachPlaylistModel(model.getPlaylistModel());
            playlistViewController.setSearchPlaylist(model.searchSongs());
            playlistViewController.setPlaylistTitle("Search: " + model.getSearchSong());
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public SearchModel getModel() {
        return model;
    }

    public void goBack(){
    //switch screen
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }
}
