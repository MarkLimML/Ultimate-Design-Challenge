package register;

import dashboard.ViewAbstract;

public class RegisterView extends ViewAbstract {

    RegisterController controller;
    RegisterModel model;

    public RegisterView (RegisterController controller, RegisterModel model) {
        this.controller = controller;
        this.model = model;
        this.setFxmlFile("/dashboard/dashboard.fxml");
    }

    @Override
    public void update() {

    }
}
