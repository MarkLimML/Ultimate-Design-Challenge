package PlaylistView;

import AddFromLib.AddFromLibToCurPlaylist;
import AddSong.Song;
import Search.SearchController;
import dashboard.ControllerAbstract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import makePlaylist.PlaylistModel;
import mediaPlayer.MediaPlayerController;
import mediaPlayer.MediaPlayerStage;

import java.io.IOException;
import java.util.ArrayList;

public class PlaylistViewController extends ControllerAbstract {

    public Button searchButton;
    public Button deleteSongs;
    public Button sortByUpload;
    public Button sortByYear;
    public Button publicButton;
    public Button addFavorite;
    public Button deleteSongMode;
    public Button btnEndDeleteMode;

    private PlaylistViewModel model;

    @FXML
    public Pane playlistViewPane;
    @FXML
    public Label playlistTitle;
    @FXML
    public Button btnBack;
    @FXML
    public Button btnAddSong;
    @FXML
    public ScrollPane tableScroll;
    @FXML
    public AnchorPane tablePane;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<Song, Void> colBtn;
    @FXML
    public TableColumn<Song, String> colTitle;
    @FXML
    public TableColumn<Song, String> colArtist;
    @FXML
    public TableColumn<Song, String> colAlbum;

    private TableColumn<Song, Boolean> colCheckBox;
    private ArrayList<BooleanProperty> selectedRowList;
    private Callback<Integer,ObservableValue<Boolean>> colCbxState;



    private ArrayList<Song> songArrayList;
    private ObservableList<Song> songList;
    private MediaPlayerStage mediaPlayerStage;
    private MediaPlayerController mediaPlayerController;
    private Song currentSong;

    public PlaylistViewController () {
        model = new PlaylistViewModel();
    }

    public void initialize() {

        System.out.println("PlaylistViewController.initialize()");
        deleteSongs.setVisible(false);
        btnEndDeleteMode.setVisible(false);
    }

    public void setObservableValues () {
        System.out.println("setObservableValues()");

        if (!model.getPlaylistModel().isPlaylistNull()) {
            btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    backToPlaylist(me);
                }
            });
        }


        if (model.getPlaylistType().equals("UserPlaylist")) {
            btnAddSong.setVisible(true);
            //btnAddSong.setVisible(false);
        } else {
            btnAddSong.setVisible(false);
        }

        tableView = new TableView<Song>();
        tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        System.out.println(model.getPlaylistTitle());
        playlistTitle.setText(model.getPlaylistTitle());
        this.setTableViewColumns();
        tablePane.getChildren().add(tableView);
    }

    private void setTableViewColumns() {
        System.out.println("setTableViewColumns()");

        colTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));

        colTitle.setSortType(TableColumn.SortType.DESCENDING);
        colAlbum.setSortable(false);

        tableView.setFixedCellSize(40.0);

        tableView.getColumns().clear();

        if (deleteSongs.isVisible()) {
            this.addTableCheckboxColumn();
            tableView.getColumns().add(colCheckBox);
        } else {
            this.addTableButton();
            tableView.getColumns().add(colBtn);
        }

        tableView.getColumns().addAll(colTitle, colArtist, colAlbum);
        songList = getSongList();
        tableView.setItems(songList);
    }

    public void addTableCheckboxColumn () {
        selectedRowList = new ArrayList<BooleanProperty>();
        for(Song s : songList) {
            selectedRowList.add( new SimpleBooleanProperty() );
        }
        colCbxState = new Callback<Integer,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer index) {
                System.out.println("return selectedRowList.get(index);");

                System.out.println(selectedRowList.get(index));
                return selectedRowList.get(index);
            }
        };

        colCheckBox = new TableColumn<>("");
        colCheckBox.setMinWidth(50);
        colCheckBox.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<Song, Boolean>, ObservableValue<Boolean>>()  {
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Song, Boolean> cdf) {
                TableView<Song> tblView = cdf.getTableView();
                Song rowData = cdf.getValue();
                int rowIndex = tblView.getItems().indexOf(rowData);
                return selectedRowList.get(rowIndex);
            }
        });

        colCheckBox.setCellFactory(CheckBoxTableCell.forTableColumn(colCbxState));
        colCheckBox.setEditable(true);
    }

    public void addTableButton() {
        System.out.println("addTableButton()");
        Callback<TableColumn<Song, Void>, TableCell<Song, Void>> cellFactory = new Callback<TableColumn<Song, Void>, TableCell<Song, Void>>() {
            @Override
            public TableCell<Song, Void> call(final TableColumn<Song, Void> param) {
                final TableCell<Song, Void> cell = new TableCell<Song, Void>() {

                    private final Button btn = new Button("PLAY");
                    {
                        btn.setStyle("-fx-background-color: #FF6E53;\n" +
                                "    -fx-text-fill: #84cc5a;\n" +
                                "    -fx-font-size: 5px;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-font-family: 'Futura' ;\n" +
                                "    -fx-background-radius: 5em;\n" +
                                "    -fx-min-width: 30px;\n" +
                                "    -fx-min-height: 30px;\n" +
                                "    -fx-max-width: 30px;\n" +
                                "    -fx-max-height: 30px;\n" +
                                "    -fx-border-width: 4px;\n" +
                                "    -fx-border-radius: 30px;\n" +
                                "    -fx-border-color: #2489CC ;\n" +
                                "    -fx-effect: dropshadow(three-pass-box, #274152, 4, 0.10, 0, 3);");
                        btn.setAlignment(Pos.CENTER);
                        btn.setOnMouseClicked((MouseEvent event) -> {
                            currentSong = getTableView().getItems().get(getIndex());
                            play(event);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
    }

    private ObservableList<Song> getSongList() {
        System.out.println("getSongList()");
        if (songArrayList == null) {
            if (model.getPlaylistModel() != null)
                songArrayList = model.getPlaylistSongs();
            return null;
        }
        return FXCollections.observableArrayList(songArrayList);
    }

    public void setSongListFromAL (ArrayList<Song> songList)  {
        songArrayList = songList;
    }

    public void play(MouseEvent e)
    {
        if (mediaPlayerStage == null) {
            mediaPlayerStage = new MediaPlayerStage();
            mediaPlayerController = mediaPlayerStage.getController();
            mediaPlayerController.setSongList(songArrayList);
            mediaPlayerController.setCurrentIndex(songArrayList.indexOf(currentSong));
        } else {

        }
    }
    
    public void back(MouseEvent e)
    {
        this.setScene(playlistViewPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);

    }

    public PlaylistViewModel getModel() {
        return model;
    }

    public void setModel(PlaylistViewModel model) {
        this.model = model;
    }

    public void addFromUserLibrary(MouseEvent mouseEvent) {
        System.out.println("switchToAddPlaylist()");
        this.setScene(playlistViewPane.getScene());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[5]));
            this.setRoot(loader.load());
            loader.setController(new AddFromLibToCurPlaylist());
            AddFromLibToCurPlaylist addFromLibController = loader.getController();
            model.getPlaylistModel().setPlaylistType("UserPlaylist");
            addFromLibController.getModel().setController(addFromLibController);
            addFromLibController.getModel().attachPlaylistModel(model.getPlaylistModel());
            addFromLibController.attachCurPlaylistModel(model.getPlaylistModel());
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        /*
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
         */
    }

    public void setPlaylistTitle (String title) {
        System.out.println("setPlaylistTitle");
        playlistTitle.setText(title);
        playlistTitle.setVisible(true);
    }

    public void returnToDashboard(MouseEvent mouseEvent) {
        this.setScene(playlistViewPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }

    public void setSearchPlaylist(ArrayList<Song> playlist) {
        model.getPlaylistModel().setPlaylist(playlist);
        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                backToPlaylist(me);
            }
        });
    }

    public void backToPlaylist(MouseEvent mouseEvent) {
        btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                returnToDashboard(me);
            }
        });
        model.getPlaylistModel().setEmptyPlaylist();
        setObservableValues();
    }

    public void switchToSearch(MouseEvent mouseEvent) {
        this.setScene(playlistViewPane.getScene());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[7]));
            this.setRoot(loader.load());
            SearchController searchController = loader.getController();
            searchController.getModel().setPlaylistModel(model.getPlaylistModel());
            searchController.getModel().setController(searchController);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void deleteCheckedSongs(MouseEvent mouseEvent) {

    }

    public void sortUpload(MouseEvent mouseEvent) {

    }

    public void setToPublicOrNot(MouseEvent mouseEvent) {
        if (!(model.getDbc().isPlaylistPublic(model.getDbc().getPlaylistIdFromTitleUser(model.getPlaylistTitle(), model.getUser().getUser_id()))))
        { model.getDbc().setPlaylistPublic(model.getDbc().getPlaylistIdFromTitleUser(model.getPlaylistTitle(), model.getUser().getUser_id()));}
        else
            model.getDbc().removePlaylistPublic(model.getDbc().getPlaylistIdFromTitleUser(model.getPlaylistTitle(), model.getUser().getUser_id()));
    }

    public void addToFavorites(MouseEvent mouseEvent) {
        if (!(model.getDbc().PlaylistIsSetFavorite(
                model.getUser().getUser_id(),
                model.getDbc().getPlaylistIdFromTitleUser(
                        model.getPlaylistTitle(), model.getUser().getUser_id()))))
        {  System.out.println("Functionworks");
            model.getDbc().playlistIsFavorite(model.getUser().getUser_id(), model.getDbc().getPlaylistIdFromTitleUser(model.getPlaylistTitle(), model.getUser().getUser_id()));}
        else
            model.getDbc().removeIsFavoritePlaylist(model.getUser().getUser_id(), model.getDbc().getPlaylistIdFromTitleUser(model.getPlaylistTitle(), model.getUser().getUser_id()));
    }

    public void enterDeleteSongMode(MouseEvent mouseEvent) {
        searchButton.setVisible(false);
        deleteSongMode.setVisible(false);
        btnAddSong.setVisible(false);
        btnBack.setVisible(false);
        publicButton.setVisible(false);
        addFavorite.setVisible(false);

        deleteSongs.setVisible(true);
        btnEndDeleteMode.setVisible(true);
        setObservableValues();
    }

    public void exitDeleteSongMode(MouseEvent mouseEvent) {
        searchButton.setVisible(true);
        deleteSongMode.setVisible(true);
        btnAddSong.setVisible(true);
        btnBack.setVisible(true);
        publicButton.setVisible(true);
        addFavorite.setVisible(true);

        deleteSongs.setVisible(false);
        btnEndDeleteMode.setVisible(false);
        setObservableValues();
    }

    public void deleteSelectedList(MouseEvent mouseEvent) {
        System.out.println("saveUserList(MouseEvent mouseEvent)");
        ArrayList<Song> selectedSongs = new ArrayList<>();

        for (BooleanProperty p: selectedRowList) {
            int index;
            if (p.getValue()) {
                index = selectedRowList.indexOf(p);
                selectedSongs.add(songList.get(index));
            }
        }
        model.deleteSongsInPlaylist(selectedSongs);
    }

}
