package AddFromLib;

import dashboard.ViewAbstract;

public class AddFromLibView extends ViewAbstract {

    private AddFromLibController controller;
    private AddFromLibModel model;

    public AddFromLibView (AddFromLibController controller, AddFromLibModel model) {
        System.out.println("AddFromLibView()");
        System.out.println("this.controller = controller;");
        this.controller = controller;
        System.out.println("this.model = model;");
        this.model = model;
        System.out.println("this.setFxmlFile(\"/AddFromLib/AddFromLib.fxml\");");
        this.setFxmlFile("/AddFromLib/AddFromLib.fxml");
    }

    @Override
    public void update() {

    }
}
