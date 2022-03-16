package users;

import codes.SystemClass;

public class Root extends Person {

    private final SystemClass systemClass = new SystemClass();

    public Root(String schoolNumber, String userPass, String userName,
            String userSurname, String userMail, int authority) {
        super(schoolNumber, userPass, userName, userSurname, userMail, authority);

        systemClass.connect();
    }

    public String displayUserInfo(String schoolNumber) {
        if (systemClass.getUser(schoolNumber) != null) {
            User user = systemClass.getUser(schoolNumber);
            return user.toString();
        } else if (systemClass.getMod(schoolNumber) != null) {
            Moderator mod = systemClass.getMod(schoolNumber);
            return mod.toString();
        }
        return null;
    }

    public void deleteUser(String schoolNumber) {
        systemClass.delUser(schoolNumber);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
