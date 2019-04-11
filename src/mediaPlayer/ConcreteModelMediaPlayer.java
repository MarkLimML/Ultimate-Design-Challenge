package mediaPlayer;

import AddSong.Song;
import Model.ModelAbstract;
import PlaylistView.PlaylistViewController;
import PlaylistView.PlaylistViewModel;
import makePlaylist.Playlist;

import java.util.ArrayList;

public class ConcreteModelMediaPlayer extends ModelAbstract {

    private MediaPlayerController controller;

    private int songIndex;
    private String imgPath;
    private String songPath;
    private ArrayList<Song> songList;


    public void setSongIndex(int songIndex) {
        System.out.println("SongIndex="+songIndex);
        this.songIndex = songIndex;
    }

    /*

    public void setImgPath(String imgPath){
        imgPath = currPlaylist.get(SongIndex).getImagePath();
    }

    public void setSongPath(String songPath) {
        songPath = currPlaylist.get(SongIndex).getSongPath();
    }
    */
    public int getSongIndex() {
        return songIndex;
    }


   public String getImgPath(){
        return this.imgPath;
    }

    public String getSongPath(){
        return this.songPath;
    }

    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public ArrayList<Song> getSongList() {
        return songList;
    }

    public void setController(MediaPlayerController controller) {
        System.out.println("setController()");
        this.controller = controller;
    }

    public void incrementTimesPlayed() {
        int times;
        if (songList != null && songIndex < songList.size()) {
            times = getDbc().getTimesPlayedFromID(songList.get(songIndex).getSongId(), getUser().getUser_id());
            System.out.println("times = " + times);
            if (times == -1) {
                getDbc().insertSongToTimesPlayed(songList.get(songIndex).getSongId(), getUser().getUser_id());
            } else {
                System.out.println("= " + (times+1));
                getDbc().updateTimesPlayed(times + 1, songList.get(songIndex).getSongId(), getUser().getUser_id());
            }
        }

    }

}
