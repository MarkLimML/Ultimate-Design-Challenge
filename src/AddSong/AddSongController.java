package AddSong;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import AddFromLib.AddFromLibView;
import dashboard.ControllerAbstract;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AddSongController extends ControllerAbstract
{   
	/*
	 * concrete controller
	 * factory packagey later
	 *
	 * NOTES:
	 * 
	 * SongFactory - SongFactoryMethod()
	 * SongFactoryMethod(){
	 * 	//
	 * 	return Song.CreateSong()
	 * }
	 * 
	 * song scrubber and player of the same heirarchy
	 * [<<interface>>] : Song - +CreateSong() static
	 * 	SongwGnre - +CreateSong() static
	 * 	SongwArtst - +CreateSong() static
	 * 
	 * factory packagey later
	 */
	
	@FXML
    Button backButton2;
    @FXML
    Button saveButton2;
    @FXML
    Button chooseSongButton;
    @FXML
    Button chooseImageButton;
    @FXML
    Label songNameLabel;
    @FXML
    Label songFileLabel;
    @FXML
    Label songGenreLabel;
    @FXML
    Label artistLabel;
    @FXML
    Label albumNameLabel;
    @FXML
    Label albumYearLabel;
    @FXML
    Label albumArtLabel;
    @FXML
    TextField songNameInput;
    @FXML
    ComboBox<String> songGenreInput;
    @FXML
    TextField artistNameInput;
    @FXML
    TextField albumNameInput;
    @FXML
    TextField albumYearInput;
    @FXML
    TextField imageChosenPath;
    @FXML
    TextField songChosenPath;
    @FXML
    AnchorPane newSongPane;

    private AddSongModel model;

    /*
        0 - name
        1 - imagePath
        2 - songPath
        3 - album
        4 - albumYear
        5 - artist
        6 - genre
     */

    public AddSongController() {
        model = new AddSongModel();
    }

    public AddSongModel getModel() {
        System.out.println("getModel()");
        return model;
    }

    public void setModel(AddSongModel model) {
        System.out.println("AddFromLibController.setModel(model)");
        this.model = model;
    }

    @FXML
    public void initialize() 
    {
    	songGenreInput.getItems().removeAll(songGenreInput.getItems());
    	songGenreInput.getItems().addAll("", "Pop", "Rock", "Kpop", "Metal", "Jazz", "Country", "Instrumental", "Classic");
    	songGenreInput.getSelectionModel().select("");

        songChosenPath.setEditable(false);
        imageChosenPath.setEditable(false);
    }


	public void saveToDatabase(MouseEvent e)
    {
        String album = albumNameInput.getText();
        String artist = artistNameInput.getText();
        String genre = songGenreInput.getValue();

        System.out.println(album);
        model.getSong().setAlbum(album);

        System.out.println(artist);
        model.getSong().setArtist(artist);
        model.getSong().setGenre(genre);
        model.getSong().setName(songNameInput.getText());
        model.getSong().setYear(Integer.parseInt(albumYearInput.getText()));
        model.getSong().setUser_id(model.getUser_id());

        Metadata metadata = model.returnSongMetadata(album, artist, genre);
        metadata.saveToDatabase(model.getSong());
    }
    
    public void callPreviousScreen(MouseEvent e)  throws IOException
    {
        this.setScene(newSongPane.getScene());
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

    public void getImage(MouseEvent e)
    {
        FileFactory fileFactory = new FileFactory();
        FileF thing = fileFactory.fileCreate("image");

        thing.fileCreate();

        File file = thing.chooser.showOpenDialog(getStage());

        if(file != null)
        {
            imageChosenPath.setText(file.getAbsolutePath());

            try
            {
                File file2 = new File(file.getAbsolutePath());

                String imgPath = "src/mediaPlayer/image/" + file2.getName();

               if(file2.renameTo(new File(imgPath)))
                {
                    System.out.println("File is moved successfully!");
                    System.out.println(imgPath);
                    model.getSong().setImgPath(imgPath);
                }

                else
                    System.out.println("File failed to move!");

            }

            catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void getSong(MouseEvent e)
    {
        FileFactory fileFactory = new FileFactory();
        FileF thing = fileFactory.fileCreate("song");

        thing.fileCreate();

        File file = thing.chooser.showOpenDialog(getStage());
        if(file != null)
        {
            songChosenPath.setText(file.getAbsolutePath());

            try
            {
                File file2 = new File(file.getAbsolutePath());


                String songPath = "src/mediaPlayer/song/" + file2.getName();

                if(file2.renameTo(new File(songPath)))
                {
                    System.out.println("File is moved successfully!");
                    System.out.println(songPath);
                    model.getSong().setSongPath(songPath);
                }

                else
                    System.out.println("File failed to move!");

            }

            catch(Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }
}
