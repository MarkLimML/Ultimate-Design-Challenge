package dashboard;

import Model.ModelAbstract;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

//repair this abstract controller with logic from the example we found
public class ControllerAbstract {
    private static User user;
    private static Stage stage;
    private Scene scene;
    private String[] screenUrls = {
            "/dashboard/dashboard.fxml",
            "/logIn/Login.fxml",
            "/register/Register.fxml",
            "/AddSong/NewSongView.fxml",
            "/makePlaylist/makePlaylist.fxml",
            "/AddFromLib/AddFromLib.fxml",
            "/PlaylistView/PlaylistView.fxml",
            "/Search/SearchView.fxml",
            "/Profile/Profile.fxml"};

    /*
            0 = "/dashboard/dashboard.fxml",
            1 = "/logIn/Login.fxml",
            2 = "/register/Register.fxml",
            3 = "/AddSong/NewSongView.fxml",
            4 = "/makePlaylist/makePlaylist.fxml"
            5 = "/AddFromLib/AddFromLib.fxml"
            6 = "/PlaylistView/PlaylistView.fxml"
            7 = "/Search/SearchView.fxml"
            8 = "/Profile/Profile.fxml"
     */

    public Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ControllerAbstract.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public String[] getScreenUrls() {
        return screenUrls;
    }

    public void switchScene(String sceneName) {
        this.activate(sceneName);
    }

    public void setRoot(Parent root) {
        try {
            scene.setRoot(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }

    public void activate(String url){
        try {
            scene.setRoot(FXMLLoader.load(getClass().getResource(url)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
    }

}
