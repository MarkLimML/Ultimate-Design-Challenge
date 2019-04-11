package AddFromLib;

import AddSong.Song;
import Model.ModelAbstract;
import dashboard.ViewAbstract;
import makePlaylist.PlaylistModel;

import java.util.ArrayList;

public class AddFromLibModel extends ModelAbstract {
    PlaylistModel playlistModel;
    AddFromLibController controller;

    public AddFromLibModel() {
        System.out.println("[Constructor] AddFromLibModel()");
    }

    public ArrayList<Song> getUserLibrary() {
        System.out.println("getUserLibrary()");
        ArrayList<Song> userLibrary;
        System.out.println(playlistModel == null);

        playlistModel.setPlaylistType("AllUserSongs");
        playlistModel.setPlaylistID();
        System.out.println("playlist id" + playlistModel.getPlaylistID());
        userLibrary = playlistModel.getPlaylist();

        playlistModel.createPlaylist();

        return userLibrary;
    }

    public void saveSongsToPlaylist(ArrayList<Song> songs) {
        for (Song s : songs) {
            if (!ModelAbstract.getDbc().isSongInPlaylist(
                    s.getSongId(), playlistModel.getPlaylistID())) {
                ModelAbstract.getDbc().addSongToPlaylist(s.getSongId(), playlistModel.getPlaylistID());
            }
        }
    }

    public void setController(AddFromLibController controller) {
        System.out.println("setController()");
        this.controller = controller;
    }

    public PlaylistModel getPlaylistModel() {
        return playlistModel;
    }

    public void attachPlaylistModel (PlaylistModel playlistModel) {
        System.out.println("attachPlaylistModel()");
        this.playlistModel = playlistModel;
        controller.setObservableValues();
        System.out.println("aksdfjalksasdfa");
    }


}
