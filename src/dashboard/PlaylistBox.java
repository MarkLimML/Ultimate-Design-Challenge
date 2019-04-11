package dashboard;

import javafx.scene.layout.VBox;

public class PlaylistBox extends VBox {
    private int playlistId;
    private String playlistName;
    private String imgPath;

    public PlaylistBox() {
        imgPath = "/dashboard/smileyFace.png";
        this.setPrefWidth(200);
        this.setPrefHeight(230);
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
