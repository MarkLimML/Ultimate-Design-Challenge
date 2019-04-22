package AddFromLib;

import AddSong.Song;
import PlaylistView.PlaylistViewController;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import makePlaylist.PlaylistModel;

import java.io.IOException;
import java.util.ArrayList;

public class AddFromLibController extends ControllerAbstract {

    @FXML
    public Button btnCancel;
    @FXML
    public Button btnSave;
    @FXML
    public Label playlistTitle;
    @FXML
    public TextField txtSongsChosen;
    @FXML
    public Pane addFromLibPane;
    @FXML
    public ScrollPane tableScroll;
    @FXML
    public Pane tablePane;

    private TableView<Song> tableView;
    private TableColumn<Song, Boolean> colCheckBox;
    private TableColumn<Song, String> colSong;
    private TableColumn<Song, String> colArtist;
    private TableColumn<Song, String> colAlbum;
    public TableColumn<Song, String> colUploadDate;
    public TableColumn<Song, Integer> colYear;
    private ArrayList<BooleanProperty> selectedRowList;
    private Callback<Integer,ObservableValue<Boolean>> colCbxState;

    private ArrayList<Song> userLibrary;
    private ObservableList<Song> songList;

    private boolean addMode;

    private AddFromLibModel model;
    private PlaylistModel curPlaylistModel;

    public AddFromLibController() {
        addMode = false;
        System.out.println();
        model = new AddFromLibModel();
        AddFromLibView view = new AddFromLibView(this, model);
        model.attach(view);
        System.out.println();
    }

    public void setAddMode(boolean mode) {
        addMode = mode;
    }



    public AddFromLibModel getModel() {
        System.out.println("getModel()");
        return model;
    }

    public void setModel(AddFromLibModel model) {
        System.out.println("AddFromLibController.setModel(model)");
        this.model = model;
    }

    public void initialize() {
        System.out.println("AddFromLibController.initialize()");
    }

    public void setObservableValues () {
        System.out.println("createTable()");
        tableView = new TableView<Song>();
        tableView.setMinHeight(300.0);
        tableView.setMinWidth(950.0);
        tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);


        colCbxState = new Callback<Integer,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer index) {
                System.out.println("return selectedRowList.get(index);");

                System.out.println(selectedRowList.get(index));
                return selectedRowList.get(index);
            }
        };

        setSongList();

        selectedRowList = new ArrayList<BooleanProperty>();
        for(Song s : songList) {
            selectedRowList.add( new SimpleBooleanProperty() );
        }

        setTableViewColumns();
        System.out.println(tablePane == null);
        System.out.println(tableView == null);
        tablePane.getChildren().add(tableView);
    }

    private void setTableViewColumns() {
        colCheckBox = new TableColumn<>("");
        colSong = new TableColumn<Song, String>("Name");
        colAlbum = new TableColumn<Song, String>("Artist");
        colArtist = new TableColumn<Song, String>("Album");

        colUploadDate = new TableColumn<Song, String>("Upload Date");
        colUploadDate.setCellValueFactory(new PropertyValueFactory<>("created"));
        colYear = new TableColumn<Song, Integer>(" Upload Year");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        colCheckBox.setMinWidth(50);
        colSong.setMinWidth(350);
        colArtist.setMinWidth(225);
        colAlbum.setMinWidth(225);

        colCheckBox.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Song, Boolean>, ObservableValue<Boolean>>()  {
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Song, Boolean> cdf) {
                TableView<Song> tblView = cdf.getTableView();
                Song rowData = cdf.getValue();
                int rowIndex = tblView.getItems().indexOf(rowData);
                return selectedRowList.get(rowIndex);
            }
        });


        colCheckBox.setCellFactory(
                CheckBoxTableCell.forTableColumn(colCbxState));
        // colCheckBox.setCellFactory( tc -> new CheckBoxTableCell<>());
        colSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        tableView.setEditable(true);
        colCheckBox.setEditable(true);
        colSong.setEditable(false);
        colAlbum.setEditable(false);
        colArtist.setEditable(false);

        colSong.setSortType(TableColumn.SortType.DESCENDING);
        colAlbum.setSortable(false);

        tableView.setFixedCellSize(60.0);

        tableView.setItems(songList);

        tableView.getColumns().addAll(colCheckBox, colSong, colArtist, colAlbum);
    }

    private void setSongList() {
        System.out.println("getSongList()");
        userLibrary = model.getUserLibrary();
        songList = FXCollections.observableArrayList(userLibrary);
    }

    public void backToLastScene(MouseEvent mouseEvent) {

        if (addMode) {
            this.setScene(addFromLibPane.getScene());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[6]));
                this.setRoot(loader.load());
                PlaylistViewController playlistViewController = loader.getController();
                playlistViewController.getModel().setController(playlistViewController);
                playlistViewController.getModel().attachPlaylistModel(curPlaylistModel);
                playlistViewController.setPlaylistTitle(curPlaylistModel.getTitle());

            } catch (IOException ie) {
                ie.printStackTrace();
            }
        } else {
            this.setScene(addFromLibPane.getScene());
            this.switchScene(this.getScreenUrls()[0]);
        }
    }

    public void saveUserList(MouseEvent mouseEvent) {
        System.out.println("saveUserList(MouseEvent mouseEvent)");
        ArrayList<Song> selectedSongs = new ArrayList<>();

        if (addMode) {
            for (BooleanProperty p: getSelectedRowList()) {
                int index;
                if (p.getValue()) {
                    index = getSelectedRowList().indexOf(p);
                    selectedSongs.add(getSongListObservable().get(index));
                }
            }
        } else {
            for (BooleanProperty p: selectedRowList) {
                int index;
                if (p.getValue()) {
                    index = selectedRowList.indexOf(p);
                    selectedSongs.add(songList.get(index));
                }
            }
        }

        model.saveSongsToPlaylist(selectedSongs);
        backToLastScene(mouseEvent);

    }

    public ObservableList<Song> getSongListObservable () {
        return songList;
    }

    public ArrayList<BooleanProperty> getSelectedRowList() {
        return selectedRowList;
    }

    public void attachCurPlaylistModel (PlaylistModel curPlaylistModel) {
        this.curPlaylistModel = curPlaylistModel;
    }

}
