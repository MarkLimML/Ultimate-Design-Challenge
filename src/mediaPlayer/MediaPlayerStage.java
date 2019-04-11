package mediaPlayer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MediaPlayerStage extends Stage {

    private FXMLLoader loader;

    public MediaPlayerStage(){
        try
        {
            loader = new FXMLLoader(getClass().getResource("/mediaPlayer/mediaPlayer.fxml"));

            Parent root = loader.load();
            this.setTitle("MP3Player");
            this.setScene(new Scene(root, 600, 500));
            this.show();
        }
        catch(Exception D)
        {
            D.printStackTrace();
        }
    }

    public MediaPlayerController getController() {
        return loader.getController();
    }
}
