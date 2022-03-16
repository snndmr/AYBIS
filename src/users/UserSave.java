package users;

import java.io.Serializable;

public class UserSave implements Serializable {

    private String schoolNumber;
    private String password;

    public UserSave(String schoolNumber, String password) {
        this.schoolNumber = schoolNumber;
        this.password = password;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
