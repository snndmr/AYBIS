package users;

public abstract class Person {

    private String schoolNumber;
    private String userPass;
    private String userName;
    private String userSurname;
    private String userMail;

    private int authority;

    public Person(String schoolNumber, String userPass, String userName,
            String userSurname, String userMail, int authority) {
        this.schoolNumber = schoolNumber;
        this.userPass = userPass;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userMail = userMail;
        this.authority = authority;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "\n\tKişi\n\t----\n\n  Okul Numarası : " + schoolNumber + "\n  Şifre : "
                + userPass + "\n\n  Ad : " + userName + "\n  Soyad : "
                + userSurname + "\n  Mail Adresi : " + userMail;
    }

}
