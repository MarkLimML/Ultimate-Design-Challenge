package AddFromLib;

import AddSong.Song;
import PlaylistView.PlaylistViewController;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import makePlaylist.PlaylistModel;

import java.io.IOException;
import java.util.ArrayList;

public class AddFromLibToCurPlaylist extends AddFromLibController {
    private PlaylistModel curPlaylistModel;

    public void backToLastScene(MouseEvent mouseEvent) {
        this.setScene(addFromLibPane.getScene());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getScreenUrls()[6]));
            this.setRoot(loader.load());
            PlaylistViewController playlistViewController = loader.getController();
            playlistViewController.getModel().setController(playlistViewController);
            playlistViewController.setPlaylistTitle(curPlaylistModel.getTitle());

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    public void saveUserList(MouseEvent mouseEvent) {
        System.out.println("saveUserList(MouseEvent mouseEvent)");
        ArrayList<Song> selectedSongs = new ArrayList<>();

        for (BooleanProperty p: getSelectedRowList()) {
            int index;
            if (p.getValue()) {
                index = getSelectedRowList().indexOf(p);
                selectedSongs.add(getSongListObservable().get(index));
            }
        }

        getModel().saveSongsToPlaylist(selectedSongs);
        backToLastScene(mouseEvent);
    }

    public void attachCurPlaylistModel (PlaylistModel curPlaylistModel) {
        this.curPlaylistModel = curPlaylistModel;
    }
}
