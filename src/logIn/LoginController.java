package logIn;

import dashboard.ControllerAbstract;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import Model.*;

public class LoginController extends ControllerAbstract {

    public Button btnRegister;
    public Button btnGuestLogIn;
    public Button btnLogIn;
    public Text lblForgot;
    public VBox mainPane;
    public TextField txtUsername;
    public PasswordField txtPassword;

    private LoginModel model;

    public LoginController () {
        model = new LoginModel();
    }

    public void initialize () {
        this.setScene(mainPane.getScene());
    }

    public void registerUser(MouseEvent mouseEvent) {
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[2]);
    }

    public void logInAsGuest(MouseEvent mouseEvent) {
        ModelAbstract.setUser(null);
        switchToDashboard();
    }

    public void logInUser(MouseEvent mouseEvent) {
        String username, password;
        username = txtUsername.getText().trim();
        password = txtPassword.getText();
        boolean invalidInput = false;
        if (username.length() < 1)
            invalidInput = true;
        if (password.length() < 1)
            invalidInput = true;

        if (!invalidInput) {
            System.out.println("entered");
            model.setUsername(username);
            model.setPassword(password);
            model.setUserIdFromUserPass();

            if (model.isValidId()) {
                model.setArtistFromId();
                model.logUser();
                switchToDashboard();
            } else {

            }
        } else {
            System.out.println("Invalid input :(");
        }

    }

    public void switchToDashboard() {
        this.setScene(mainPane.getScene());
        this.switchScene(this.getScreenUrls()[0]);
    }

    public void forgotPassword(MouseEvent mouseEvent) {

    }
}
