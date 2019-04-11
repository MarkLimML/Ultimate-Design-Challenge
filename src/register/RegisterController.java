package register;

import dashboard.ControllerAbstract;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Model.*;

public class RegisterController extends ControllerAbstract {

    public Button btnRegister;
    public Button btnGuestLogIn;
    public VBox mainPane;
    public TextField txtUsername;
    public TextField txtPassword;
    public Label lblUserMin;
    public Label lblPassMin;
    public CheckBox checkArtist;

    private boolean invalidInput;
    private RegisterModel model;


    public RegisterController() {
        model = new RegisterModel();
        RegisterView view = new RegisterView(this, model);
        model.attach(view);
    }

    public void initialize () {
        lblPassMin.setVisible(false);
        lblUserMin.setVisible(false);
    }

    public void registerUser(MouseEvent mouseEvent) {
        String username, password;
        Boolean isArtist = checkArtist.isSelected();

        username = txtUsername.getText().trim();
        password = txtPassword.getText();

        if (username.length() < 3) {
            invalidInput = true;
            lblUserMin.setVisible(true);
        }
        if (password.length() < 8) {
            invalidInput = true;
            lblPassMin.setVisible(true);
        }

        if (!invalidInput) {

            model.setUsername(username);
            model.setPassword(password);
            model.setArtist(isArtist);

            if (model.registerUser()) {
                model.logUser();
                this.setScene(mainPane.getScene());
                this.switchScene(this.getScreenUrls()[0]);
            } else {
                lblUserMin.setText("*Username is already taken");
                lblUserMin.setVisible(true);
            }
        }
    }

    public void logInAsGuest(MouseEvent mouseEvent) {
        this.setScene(mainPane.getScene());
        ModelAbstract.setUser(null);
        this.switchScene(this.getScreenUrls()[0]);
    }

}
