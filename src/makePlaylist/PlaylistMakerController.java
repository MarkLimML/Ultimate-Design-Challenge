package makePlaylist;

import AddFromLib.AddFromLibController;
import AddFromLib.AddFromLibModel;
import dashboard.ControllerAbstract;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class PlaylistMakerController extends ControllerAbstract {

	    @FXML
	    Button songLibraryButton;
	    @FXML
	    Button newSongButton;
	    @FXML
	    Button backButton;
	    @FXML
	    Button savePlaylistButton;
	    @FXML
	    Label playlistLabel;
	    @FXML
	    TextField nameInput; //TEXTFIELD for Playlist name
	    @FXML
		Pane playlistMakerPanel;


	    PlaylistModel model;


		public PlaylistMakerController() {
            model = new PlaylistModel();

		}

		public void savePlaylist(MouseEvent e) {
			//save to DataBase
			/*
			if name input exists call factory (userplaylist)
			pass data through song repository ,  song repository w settters
			 */

			//;ConcretePlaylistFactory factory = new ConcretePlaylistFactory();
			///Playlist playlist = factory.getPlaylist("UserPlaylist");

            model.setTitle(nameInput.getText().trim());
            model.createPlaylist();
		}
	    
	    public void callUserLibrary(MouseEvent e) {
			System.out.println("callUserLibrary()");
		    String playlistName = nameInput.getText().trim();


		    if (playlistName.length() > 0) {
                this.setScene(playlistMakerPanel.getScene());
                model.setPlaylistType("UserPlaylist");
                model.setTitle(playlistName);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[5]));
                    this.setRoot(loader.load());
                    AddFromLibController addFromLibController = loader.getController();
                    addFromLibController.getModel().setController(addFromLibController);
                    addFromLibController.getModel().attachPlaylistModel(model);

                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
	    }

	    //opens newSong maker
	    public void callNewSongMaker(MouseEvent e) {
			this.setScene(playlistMakerPanel.getScene());
			this.switchScene(this.getScreenUrls()[3]);
	    }
	    
	    public void returnToDashboard(MouseEvent e) {
			this.setScene(playlistMakerPanel.getScene());
			this.switchScene(this.getScreenUrls()[0]);
		}

    /*
            0 = "/dashboard/dashboard.fxml",
            1 = "/logIn/Login.fxml",
            2 = "/register/Register.fxml",
            3 = "/AddSong/NewSongView.fxml",
            4 = "/makePlaylist/makePlaylist.fxml"

            this.setScene(mainPane.getScene());
            this.switchScene(this.getScreenUrls()[3]);
    */
}
