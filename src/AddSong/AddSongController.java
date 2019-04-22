package AddSong;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import AddFromLib.AddFromLibView;
import Model.ModelAbstract;
import dashboard.ControllerAbstract;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AddSongController extends ControllerAbstract
{
    public RadioButton rbNewAlbum;
    public RadioButton rbYourAlbums;
    public ComboBox cmbAlbumName;
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
    Label AlbumLabel;
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

    ///////////////////
    private boolean editing;
    private String song_Name;
    private int song_id;
//////////////////////////////////

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

    ////////////////////////////////////////////////
    public void setEditingMode(boolean editing, int song_id) {
        this.editing = editing;
        if(editing)
        {
            this.song_id = song_id;

            songFileLabel.setVisible(false);
            chooseSongButton.setVisible(false);
            songChosenPath.setVisible(false);
            artistNameInput.setVisible(false);
            artistLabel.setVisible(false);
            imageChosenPath.setVisible(false);
            chooseImageButton.setVisible(false);
            albumArtLabel.setVisible(false);
            albumNameLabel.setVisible(false);
            albumNameInput.setVisible(false);
            AlbumLabel.setVisible(false);
            rbNewAlbum.setVisible(false);
            rbYourAlbums.setVisible(false);
            cmbAlbumName.setVisible(false);
            albumNameLabel.setVisible(false);


            songGenreInput.getSelectionModel().select(ModelAbstract.getDbc().GetSongGenreFromSongID(song_id));
            songNameInput.setText(ModelAbstract.getDbc().GetSongNameFromSongID(song_id));
            albumYearInput.setText(String.valueOf(ModelAbstract.getDbc().GetSongYearFromSongID(song_id)));
            System.out.println(ModelAbstract.getUser().getUsername());
        }
    }
    ////////////////////////////////////

    public void setModel(AddSongModel model) {
        System.out.println("AddFromLibController.setModel(model)");
        this.model = model;
    }

    @FXML
    public void initialize() 
    {
        artistNameInput.setVisible(false);
        artistLabel.setVisible(false);

    	songGenreInput.getItems().removeAll(songGenreInput.getItems());
    	songGenreInput.getItems().addAll("", "Pop", "Rock", "Kpop", "Metal", "Jazz", "Country", "Instrumental", "Classic");
    	songGenreInput.getSelectionModel().select("");

    	model.setUserAlbumsFromDb();
    	rbNewAlbum.setSelected(true);
    	ArrayList<Album> albums = model.getUserAlbums();

    	if (albums.size() == 0) {
    	    rbYourAlbums.setDisable(true);
        } else {
            for (Album a : albums) {
                cmbAlbumName.getItems().add(a.getAlbum_name() + " (" + a.getYear() + ")");
            }
        }
        newAlbumMode();

        cmbAlbumName.setEditable(false);
        songChosenPath.setEditable(false);
        imageChosenPath.setEditable(false);
    }


	public void saveToDatabase(MouseEvent e) {
        String album;
//        String artist = artistNameInput.getText();
        String genre = songGenreInput.getValue();

        Boolean validInputs = true;

//        System.out.println(artist);
//        model.getSong().setArtist(artist);
/////////////////////////////////////////////////////
        if(!editing){
            if (songNameInput.getText().trim().length() == 0) {
                validInputs = false;
            } if (songChosenPath.getText().trim().length() == 0) {
                validInputs = false;
            } if (rbNewAlbum.isSelected()) {
                try {
                    int num = Integer.parseInt(albumYearInput.getText().trim());
                    if (num < 1)
                        validInputs = false;
                } catch (NumberFormatException ne) {
                    validInputs = false;
                }
                if (imageChosenPath.getText().trim().length() == 0) {
                    validInputs = false;
                }
            }

            if (validInputs) {
                model.getSong().setGenre(genre);
                model.getSong().setName(songNameInput.getText());
                model.getSong().setUser_id(model.getUser_id());


                if (rbNewAlbum.isSelected()) {
                    album = albumNameInput.getText();
                    model.getSong().setAlbum(album);
                    model.getSong().setYear(Integer.parseInt(albumYearInput.getText()));
                } else {
                    int index = cmbAlbumName.getItems().indexOf(cmbAlbumName.getValue());
                    model.getSong().setYear(model.getUserAlbums().get(index).getYear());
                    model.getSong().setAlbumId(model.getUserAlbums().get(index).getAlbum_id());
                    album = model.getUserAlbums().get(index).getAlbum_name();
                }

//        Metadata metadata = model.returnSongMetadata(album, artist, genre);
                Metadata metadata = model.returnSongMetadata(album, genre);
                metadata.saveToDatabase(model.getSong());
                callPreviousScreen(e);
            } else {
                System.out.println("Invalid inputs.");
            }
        }


        if (editing)
        {
            String songName = songNameInput.getText();
            int genreID = songGenreInput.getItems().indexOf(songGenreInput.getValue())+1;
            int albumYear = Integer.parseInt(albumYearInput.getText());

            ModelAbstract.getDbc().UpdateSongName(song_id,songName);
            ModelAbstract.getDbc().UpdateSongYear(song_id,albumYear);
            ModelAbstract.getDbc().UpdateGenre(song_id,genreID);
        }
////////////////////////////////////////////////////////
    }
    
    public void callPreviousScreen(MouseEvent e)
    {
        this.setScene(newSongPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }


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

    public void newAlbumMode() {
        if (rbYourAlbums.isSelected()) {
            rbYourAlbums.setSelected(false);
        }
        rbYourAlbums.setSelected(false);
        cmbAlbumName.setVisible(false);

        albumNameInput.setDisable(false);
        albumYearInput.setDisable(false);
        chooseImageButton.setDisable(true);
        imageChosenPath.setDisable(false);

    }

    public void selectAlbumMode() {
        if (rbNewAlbum.isSelected()) {
            rbNewAlbum.setSelected(false);
        }

        rbYourAlbums.setSelected(true);
        cmbAlbumName.setVisible(true);

        albumNameInput.setDisable(true);
        albumYearInput.setDisable(true);
        chooseImageButton.setDisable(false);
        imageChosenPath.setDisable(true);

    }
}
