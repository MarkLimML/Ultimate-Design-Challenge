package Profile;


import dashboard.ViewAbstract;

public class ProfileView extends ViewAbstract{

    private ProfileController controller;
    private ProfileModel model;

    public ProfileView(ProfileController controller, ProfileModel model) {
        this.controller = controller;
        this.model = model;
        this.setFxmlFile("/dashboard/dashboard.fxml");
    }

    @Override
    public void update() {

    }
}
