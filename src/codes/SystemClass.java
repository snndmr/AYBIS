package codes;

import interfaces.LogIn;
import interfaces.SignUp;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import users.Moderator;
import users.Root;
import users.User;

public class SystemClass implements SignUp, LogIn {

    private String query;
    private Statement statement = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    // <editor-fold defaultstate="collapsed" desc=" CONNECTION OPTIONS ">
    /*// <editor-fold defaultstate="collapsed" desc=" LOCAL DATABASE SETTINGS ">
    private final String databaseUserName = "root";
    private final String databasePassword = "";
    private final String databaseName = "aybupanel";
    private final String host = "localhost";
    private final int port = 3306;*/
// </editor-fold>   //FAST but, offline
    // <editor-fold defaultstate="collapsed" desc=" ONLINE DATABASE SETTINGS ">
    private final String databaseUserName = "aybupanel";
    private final String databasePassword = "12345678";
    private final String databaseName = "aybupanel";
    private final String host = "db4free.net";
    private final int port = 3306;
    // </editor-fold>   //SLOW but, online

    public boolean connect() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName
                + "?useUnicode=true&characterEncoding=UTF-8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager
                    .getConnection(url, databaseUserName, databasePassword);
            System.out.println("Connection successful .. !");
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver cannot find .. !");
            System.exit(-1);
        } catch (SQLException ex) {
            System.out.println("Connection failed .. !");
        }
        return false;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" USER OPTIONS ">
    public int addUser(User user) {
        if (checkSchoolNumber(user.getSchoolNumber())) {
            return -1;
        } else if (checkUserMail(user.getUserMail())) {
            return 0;
        }

        query = "INSERT INTO user (schoolNumber, userPass, userName,"
                + " userSurname, userMail, userFac, userDep, userClass, authority) "
                + "VALUES('"
                + user.getSchoolNumber() + "','"
                + user.getUserPass() + "','"
                + user.getUserName() + "','"
                + user.getUserSurname() + "','"
                + user.getUserMail() + "','"
                + user.getUserFac() + "','"
                + user.getUserDep() + "','"
                + user.getUserClass() + "','"
                + +user.getAuthority() + "')";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Adding successful .. !");
        return 1;
    }

    public boolean delUser(String schoolNumber) {
        query = "DELETE FROM user WHERE schoolNumber='" + schoolNumber + "'";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public User getUser(String schoolNumber) {
        query = "Select * From user where schoolNumber like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, schoolNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            User user = new User(resultSet.getString("UserDep"),
                    resultSet.getString("userClass"),
                    resultSet.getString("userFac"),
                    resultSet.getString("schoolNumber"),
                    resultSet.getString("userPass"),
                    resultSet.getString("userName"),
                    resultSet.getString("userSurname"),
                    resultSet.getString("userMail"),
                    Integer.parseInt(resultSet.getString("authority")));
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Moderator getMod(String schoolNumber) {
        query = "Select * From user where schoolNumber like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, schoolNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            Moderator moderator = new Moderator(resultSet.getString("userFac"),
                    resultSet.getString("schoolNumber"),
                    resultSet.getString("userPass"),
                    resultSet.getString("userName"),
                    resultSet.getString("userSurname"),
                    resultSet.getString("userMail"),
                    Integer.parseInt(resultSet.getString("authority")));
            return moderator;
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Root getRoot(String schoolNumber) {
        query = "Select * From user where schoolNumber like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, schoolNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            Root root = new Root(resultSet.getString("schoolNumber"),
                    resultSet.getString("userPass"),
                    resultSet.getString("userName"),
                    resultSet.getString("userSurname"),
                    resultSet.getString("userMail"),
                    Integer.parseInt(resultSet.getString("authority")));
            return root;
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" LOG IN / SIGN UP CHECK ">
    @Override
    public boolean checkSchoolNumber(String schoolNumber) {
        query = "Select * From user where schoolNumber like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, schoolNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String number = resultSet.getString("schoolNumber");
                return number.equals(schoolNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean checkUserMail(String userMail) {
        query = "Select * From user where userMail like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userMail + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String mail = resultSet.getString("userMail");
                return mail.equals(userMail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean checkAccuracy(String schoolNumber, String userPass) {
        query = "Select * From user where schoolNumber like ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, schoolNumber + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String number = resultSet.getString("schoolNumber");
                if (number.equals(schoolNumber)) {
                    return resultSet.getString("userPass").equals(userPass);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" CHAT ">
    public int getId() {
        query = "SELECT max(id) FROM chat";

        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public String getMessages() {
        query = "Select * From chat";
        String temp = "";
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("userName");
                String time = resultSet.getString("time");
                String message = resultSet.getString("message");
                int id = resultSet.getInt("id");
                temp += "\n(" + time + ") \n~" + name + " : " + message + "\n"
                        + "\n----------------------------------------------------\n";
            }
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return temp;
    }

    public void addMessage(String userName, String message) {
        Date date = new Date();

        SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");

        query = "INSERT INTO chat (userName, time, message) "
                + "VALUES('" + userName + "','" + clockFormat.format(date)
                + "','" + message + "')";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Adding successful .. !");
        } catch (SQLException ex) {
            Logger.getLogger(SystemClass.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" WEB PARSER WITH JSOUP ">
    ArrayList<String> eatList = new ArrayList<>();
    ArrayList<Information> news = new ArrayList<>();
    ArrayList<Information> announcements = new ArrayList<>();

    private Document documentOne;
    private Document documentTwo;
    private Elements elements;
    private String URLHomePage;
    private String URLSKS;

    public void connect(String URLHomePage, String URLSKS) {
        this.URLHomePage = URLHomePage;
        this.URLSKS = URLSKS;
        try {
            documentOne = Jsoup.connect(URLHomePage).userAgent("Chrome").get();
            documentTwo = Jsoup.connect(URLSKS).userAgent("Chrome").get();
        } catch (IOException ex) {
            Logger.getLogger(SystemClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Information> getNews() {
        elements = documentOne.getElementsByClass("contentNews");

        elements.select("div.cncItem").forEach((element) -> {
            String textTemp = element.select("a").attr("title");
            String linkTemp = URLHomePage + element.select("a").attr("href");

            news.add(new Information(textTemp, linkTemp));
        });
        return news;
    }

    public ArrayList<Information> getAnnouncements() {

        elements = documentOne.getElementsByClass("contentAnnouncements");

        elements.select("div.cncItem").forEach((element) -> {
            String textTemp = element.select("a").attr("title");
            String linkTemp = URLHomePage + element.select("a").attr("href");

            announcements.add(new Information(textTemp, linkTemp));
        });
        return announcements;
    }

    public ArrayList<String> getEat() {
        elements = documentTwo.getElementsByTag("table");

        eatList.add(elements.select("span[style=font-family:comic sans ms,cursive;]").text());

        elements.select("p[style=text-align: center;]").forEach((element) -> {
            eatList.add(element.text());
        });

        return eatList;
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" EGO PARSER WITH SELENIUM ">
    private String stationNumber;
    private WebElement element;
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public void start() {
        Properties props = System.getProperties();
        props.setProperty("webdriver.chrome.driver", "cd.exe");
        driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
    }

    public String client() {
        driver.get("https://www.ego.gov.tr/otobusnerede?durak_no=&hat_no=");
        driver.get("https://www.ego.gov.tr/otobusnerede?durak_no=" + this.stationNumber + "&hat_no=");

        element = driver.findElement(By.cssSelector("input[class='btn red']"));
        element.click();

        element = driver.findElement(By.cssSelector("table[class='list']"));

        return element.getText();
    }

    public void stop() {
        driver.close();
    }

// </editor-fold>
}
