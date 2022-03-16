package users;

public class Unroot extends Person {

    private String userFac;

    public Unroot(String userFac, String schoolNumber, String userPass,
            String userName, String userSurname, String userMail, int authority) {
        super(schoolNumber, userPass, userName, userSurname, userMail, authority);
        this.userFac = userFac;
    }

    public String getUserFac() {
        return userFac;
    }

    public void setUserFac(String userFac) {
        this.userFac = userFac;
    }

    @Override
    public String toString() {
        return super.toString() + "\n\n  Fakülte : " + userFac;
    }

}
