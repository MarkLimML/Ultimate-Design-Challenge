package Model;

import dashboard.ViewAbstract;

public class ModelAbstract {
    private static DatabaseConnector dbc = DatabaseConnector.getInstance();
    private static User user;
    private ViewAbstract view;

    public ModelAbstract() {
    }

    public static DatabaseConnector getDbc() {
        return dbc;
    }

    public void attach (ViewAbstract view) {
        this.view = view;
    }

    public static User getUser () {
        return user;
    }

    public static void setUser (User user) {
        ModelAbstract.user = user;
    }

    public boolean isLogged () {
        if (user == null)
            return false;
        return true;
    }


}
