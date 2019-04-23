package PlaylistView;

import AddSong.Song;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Model.ModelAbstract;
import dashboard.PlaylistBox;
import makePlaylist.PlaylistModel;

import java.util.ArrayList;

public class PlaylistViewModel extends ModelAbstract{
	/*
	private int framecount;
	private ListView<String> titleList;
    private ListView<String> artistList;
    private ListView<String> albumList;
    private PlaylistBox playlistBox;
    private int playFrom;
    
    /**/

	private ArrayList<Song> playlistSongs;

	private PlaylistModel playlistModel;
	private PlaylistViewController controller;

	public ArrayList<Song> getPlaylistSongs() {
		System.out.println("getPlaylistSongs()");
		System.out.println("playlist id=" + playlistModel.getPlaylistID());
		playlistSongs = playlistModel.getPlaylist();

		return playlistSongs;
	}

	public String getPlaylistType() {
		return playlistModel.getPlaylistType();
	}

	public void saveSongsToPlaylist(ArrayList<Song> songs) {
		for (Song s : songs) {
			if (!ModelAbstract.getDbc().isSongInPlaylist(
					s.getSongId(), playlistModel.getPlaylistID())) {
				ModelAbstract.getDbc().addSongToPlaylist(s.getSongId(), playlistModel.getPlaylistID());
			}
		}
	}

	public void deleteSongsInPlaylist(ArrayList<Song> songs) {
		for (Song s : songs) {
			if (ModelAbstract.getDbc().isSongInPlaylist(
					s.getSongId(), playlistModel.getPlaylistID())) {
				ModelAbstract.getDbc().deleteSongInPlaylist(s.getSongId(), playlistModel.getPlaylistID());
			}
		}
	}

	public void setController(PlaylistViewController controller) {
		System.out.println("setController()");
		this.controller = controller;
	}

	public void attachPlaylistModel (PlaylistModel playlistModel) {
		System.out.println("attachPlaylistModel()");
		this.playlistModel = playlistModel;
		controller.setObservableValues();
		System.out.println("aksdfjalksasdfa");
	}

    public String getPlaylistTitle() {
		return playlistModel.getTitle();
	}

	public PlaylistModel getPlaylistModel() {
		return playlistModel;
	}

	public boolean deleteSongsInAlbum (ArrayList<Song> songs) {
		for (Song s : songs) {
			if (ModelAbstract.getDbc().isSongInPlaylist(
					s.getSongId(), playlistModel.getPlaylistID())) {
				ModelAbstract.getDbc().deleteSong(s.getSongId());
			}

		}
		if (ModelAbstract.getDbc().isAlbumEmpty(playlistModel.getPlaylistID())) {
			ModelAbstract.getDbc().deleteAlbum(playlistModel.getPlaylistID());
			return true;
		}
		return false;
	}
}
