package users;

public class User extends Unroot {

    private String userDep;
    private String userClass;

    public User(String userDep, String userClass, String userFac,
            String schoolNumber, String userPass, String userName,
            String userSurname, String userMail, int authority) {
        super(userFac, schoolNumber, userPass, userName, userSurname, userMail, authority);
        this.userDep = userDep;
        this.userClass = userClass;
    }

    public String getUserDep() {
        return userDep;
    }

    public void setUserDep(String userDep) {
        this.userDep = userDep;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    @Override
    public String toString() {
        return super.toString() + "\n  Departman : " + userDep + "\n  Sınıf : " + userClass;
    }

}
