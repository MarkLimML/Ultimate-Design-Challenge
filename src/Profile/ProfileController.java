package Profile;

import Model.ModelAbstract;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import javax.swing.text.TableView;
import java.awt.event.MouseEvent;

public class ProfileController {

    private ProfileModel model;


    @FXML
    public Label username;
    @FXML
    public Label usertype; //listener or artist
    @FXML
    public Label name;
    @FXML
    public Label type;
    @FXML
    public CheckBox isFollowed;
    @FXML
    public TableView followlist;
    @FXML
    public TableView songlist;
    @FXML
    public Button back;

    public ProfileController() {
        model = new ProfileModel();
        ProfileView view = new ProfileView(this, model);
        model.attach(view);
    }

    public void initialize() {
        username.setText(ModelAbstract.getUser().getUsername());
        if(ModelAbstract.getUser().isArtist())
            usertype.setText("Artist");
        else
            usertype.setText("Listener");
        name.setText("Username");
        type.setText("User Type");

    }

    public void getfollowing() {

    }

    public void setIsFollowed(MouseEvent e) {

    }

}
