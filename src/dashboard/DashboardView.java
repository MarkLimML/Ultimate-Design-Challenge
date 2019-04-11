package dashboard;

public class DashboardView extends ViewAbstract {

    private DashboardController controller;
    private DashboardModel model;

    public DashboardView (DashboardController controller, DashboardModel model) {
        this.controller = controller;
        this.model = model;
        this.setFxmlFile("/dashboard/dashboard.fxml");
    }

    @Override
    public void update() {

    }
}
