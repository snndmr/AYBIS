package users;

import codes.SystemClass;

public class Moderator extends Unroot {

    private SystemClass systemClass = new SystemClass();

    public Moderator(String userFac, String schoolNumber, String userPass,
            String userName, String userSurname, String userMail, int authority) {
        super(userFac, schoolNumber, userPass, userName, userSurname, userMail, authority);
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

    @Override
    public String toString() {
        return super.toString();
    }

}
