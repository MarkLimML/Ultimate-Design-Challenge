package Search;

import AddSong.Song;
import Model.ModelAbstract;
import makePlaylist.PlaylistModel;

import java.util.ArrayList;

public class SearchModel extends ModelAbstract {
    private ArrayList<Song> searchSongs;
    private String searchSong;
    private String playlistType;
    private PlaylistModel playlistModel;
    private SearchController searchController;
    private int searchSongID;

    public SearchModel () {
        searchSongs = getPlaylistSongs();
    }

    public PlaylistModel getPlaylistModel() {
        return playlistModel;
    }

    public void setPlaylistModel(PlaylistModel playlistModel) {
        this.playlistModel = playlistModel;
    }

    public String getPlaylistType() {
        return playlistModel.getPlaylistType();
    }

    public void setPlaylistType(String playlistType) {

    }

    public ArrayList<Song> getPlaylistSongs() {
        System.out.println("getPlaylistSongs()");
        System.out.println("playlist id=" + playlistModel.getPlaylistID());
        searchSongs = playlistModel.getPlaylist();

        return searchSongs;
    }

    public ArrayList<Song> searchSongs () {
        ArrayList<Song> searchedSongs = new ArrayList<>();

        for (Song s: searchSongs) {
            if (s.getName().contains(searchSong)) {
                searchedSongs.add(s);
            }
        }
        return searchedSongs;
    }


    public int getSearchSongID() {
        return searchSongID;
    }

    public String getSearchSong(){
        return searchSong;
    }

    public void setSearchSongID(int searchSongID){
        this.searchSongID = searchSongID;
    }

    public void setSearchSong(String searchSong) {
        this.searchSong = searchSong;
    }

    public void setController(SearchController searchController) {
        this.searchController = searchController;
    }

}
