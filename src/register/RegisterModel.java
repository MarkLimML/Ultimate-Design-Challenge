package register;

import Model.ModelAbstract;
import Model.User;
import logIn.LoginModel;

public class RegisterModel extends LoginModel {

    public boolean registerUser() {
        int id;
        System.out.println("p");
        if (this.getUsername() != null && this.getPassword() != null) {
            System.out.println("pumasok");
            id = getDbc().validUser(this.getUsername(), this.getPassword());
            System.out.println(id);
            if (id == -1) {
                getDbc().createUser(this.getUsername(), this.getPassword(), this.isArtist());
                setUserIdFromUserPass();
                System.out.println("isArtist" + isArtist());
                return true;
            }
        }
        return false;
    }
}
