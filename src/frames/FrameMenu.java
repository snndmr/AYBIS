package frames;

import codes.Information;
import codes.SystemClass;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import users.Moderator;
import users.Person;
import users.Root;
import users.User;

public class FrameMenu extends javax.swing.JFrame {

    private int velX = 0;
    private int velY = 0;

    private final int MIN_WIDTH_LEFT = 80;
    private final int MAX_WIDTH_LEFT = 240;

    private final int MIN_HEIGHT_TOP = 40;
    private final int MAX_HEIGHT_TOP = 200;

    private Point initialClick;

    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<JLabel> newLabels = new ArrayList<>();
    private ArrayList<JLabel> annLabels = new ArrayList<>();
    private ArrayList<JLabel> eatLabels = new ArrayList<>();

    private int a = 25;
    private int b = 50;

    private static SystemClass systemClass;
    private static Person person;

    private User user = null;
    private Moderator mod = null;
    private Root root = null;

    String homePageURL = "";

    public FrameMenu(Person person, SystemClass systemClass) {
        FrameMenu.person = person;
        FrameMenu.systemClass = systemClass;

        initComponents();

        connectDatabase();
        getParser();
        // <editor-fold defaultstate="collapsed" desc=" ANIMATIONS ">
        new Thread(() -> {
            while (true) {
                if (velX > 0 && panelLeft.getWidth() + velX <= MAX_WIDTH_LEFT) {
                    panelLeft.setSize(panelLeft.getWidth() + velX, 720);
                } else if (velX < 0 && panelLeft.getWidth() + velX >= MIN_WIDTH_LEFT) {
                    panelLeft.setSize(panelLeft.getWidth() + velX, 720);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                if (velY > 0 && panelTop.getHeight() + velY <= MAX_HEIGHT_TOP) {
                    panelTop.setSize(panelTop.getWidth(), panelTop.getHeight() + velY);
                } else if (velY < 0 && panelTop.getHeight() + velY >= MIN_HEIGHT_TOP) {
                    panelTop.setSize(panelTop.getWidth(), panelTop.getHeight() + velY);
                }
                labelTopText.setSize(labelTopText.getWidth(), panelTop.getHeight());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

// </editor-fold>
    }

    int xCoor = 20;
    int yCoor = 50;
    int lastId;

    private void connectDatabase() {
        lastId = systemClass.getId();

        new Thread(() -> {
            while (true) {
                try {
                    checkChat();
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrameMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }

    public void checkChat() {
        if (systemClass.getId() > lastId) {
            getMessage();
            lastId = systemClass.getId();
        }
    }

    public void getMessage() {
        textAreaChat.setText(systemClass.getMessages());
    }

    public void setup() {
        getMessage();

        switch (person.getAuthority()) {
            case 1:
                user = systemClass.getUser(person.getSchoolNumber());
                if (user.getUserDep().equals("Bilgisayar Mühendisliği")) {
                    homePageURL = "https://aybu.edu.tr/muhendislik/bilgisayar/";

                } else if (user.getUserDep().equals("Tıp")) {
                    homePageURL = "https://aybu.edu.tr/tip/";
                }
                break;
            case 2:
                mod = systemClass.getMod(person.getSchoolNumber());
                homePageURL = "https://aybu.edu.tr/muhendislik/bilgisayar/";
                labelIconSyllabus.setVisible(false);
                labelTextSyllabus.setVisible(false);
                break;
            case 3:
                root = systemClass.getRoot(person.getSchoolNumber());
                homePageURL = "https://aybu.edu.tr/muhendislik/bilgisayar/";
                labelIconSyllabus.setVisible(false);
                labelTextSyllabus.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void getParser() {
        String foodURL = "https://aybu.edu.tr/sks/";

        setup();

        systemClass.connect(homePageURL, foodURL);

        // <editor-fold defaultstate="collapsed" desc=" HOMEPAGE ">
        panelHomePage.setVisible(true);

        ArrayList<Information> textNews;
        ArrayList<Information> textAnnouncements;

        textNews = systemClass.getNews();
        textAnnouncements = systemClass.getAnnouncements();

        textNews.forEach((textNew) -> {
            JLabel label = new JLabel("<html>-> " + textNew.getText() + "</html>");
            label.setBounds(xCoor, yCoor, 360, (textNew.getText().length() / 35 + 1) * 30);
            label.setFont(new Font("Monospaced", Font.BOLD, 15));
            label.setCursor(Cursor.getPredefinedCursor(12));
            label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    openWebpage(textNew.getLink());
                }
            });

            yCoor += (textNew.getText().length() / 35 + 1) * 30 + 20;
            newLabels.add(label);
        });

        yCoor = 50;

        textAnnouncements.forEach((textAnnouncement) -> {
            JLabel label = new JLabel("<html>-> " + textAnnouncement.getText() + "</html>");
            label.setBounds(xCoor, yCoor, 360, (textAnnouncement.getText().length() / 35 + 1) * 30);
            label.setFont(new Font("Monospaced", Font.BOLD, 15));
            label.setCursor(Cursor.getPredefinedCursor(12));
            label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    openWebpage(textAnnouncement.getLink());
                }
            });

            yCoor += (textAnnouncement.getText().length() / 35 + 1) * 30 + 20;
            annLabels.add(label);
        });

        newLabels.forEach((newLabel) -> {
            panelNews.add(newLabel);
        });

        annLabels.forEach((annLabel) -> {
            panelAnn.add(annLabel);
        });

// </editor-fold>
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (IOException | URISyntaxException e) {
            //e.printStackTrace();
        }
    }

    public static void openPDF() {
        File myFile = new File("src\\pdf\\a.pdf");
        try {
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
            //e.printStackTrace();
        }
    }

    public void clearPanelBack() {
        for (Component panel : panelBack.getComponents()) {
            panel.setVisible(false);
        }
        velX = -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorChooser = new javax.swing.JColorChooser();
        panelLeft = new javax.swing.JPanel();
        labelIconBus = new javax.swing.JLabel();
        labelIconChat = new javax.swing.JLabel();
        labelIconCalendar = new javax.swing.JLabel();
        labelIconEat = new javax.swing.JLabel();
        labelIconEvent = new javax.swing.JLabel();
        labelIconSyllabus = new javax.swing.JLabel();
        labelIconHome = new javax.swing.JLabel();
        labelTextChat = new javax.swing.JLabel();
        labelTextHomePage = new javax.swing.JLabel();
        labelTextEat = new javax.swing.JLabel();
        labelTextCalendar = new javax.swing.JLabel();
        labelTextBus = new javax.swing.JLabel();
        labelTextEvent = new javax.swing.JLabel();
        labelTextSyllabus = new javax.swing.JLabel();
        labelIconSettings = new javax.swing.JLabel();
        labelIconHelp = new javax.swing.JLabel();
        labelIconShare = new javax.swing.JLabel();
        panelTop = new javax.swing.JPanel();
        labelTopText = new javax.swing.JLabel();
        labelExit = new javax.swing.JLabel();
        labelMinimize = new javax.swing.JLabel();
        panelBack = new javax.swing.JPanel();
        panelHomePage = new javax.swing.JPanel();
        panelAnn = new javax.swing.JPanel();
        labelTextAnn = new javax.swing.JLabel();
        panelNews = new javax.swing.JPanel();
        labelTextNews = new javax.swing.JLabel();
        panelMessage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaMessage = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        textAreaChat = new javax.swing.JTextArea();
        panelBus = new javax.swing.JPanel();
        textFieldStationNumber = new javax.swing.JTextField();
        labelTextStationEnter = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaBus = new javax.swing.JTextArea();
        labelSearchBus = new javax.swing.JLabel();
        panelEat = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaEat = new javax.swing.JTextArea();
        monthlyFoodList = new javax.swing.JButton();
        panelCalendar = new javax.swing.JPanel();
        toDoListEraseAllFilesButton = new javax.swing.JButton();
        toDoListEraseButton = new javax.swing.JButton();
        toDoListAddButton = new javax.swing.JButton();
        toDoListTextArea = new javax.swing.JTextArea();
        yearTextField = new javax.swing.JTextField();
        yearTextLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        calendarTable = new javax.swing.JTable();
        prevMonthButton = new javax.swing.JButton();
        nextMonthButton = new javax.swing.JButton();
        monthTextLabel = new javax.swing.JLabel();
        statusTextLabel = new javax.swing.JLabel();
        panelsyllabus = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jLabel4 = new javax.swing.JLabel();
        panelEvent = new javax.swing.JPanel();
        slider = new javax.swing.JSlider();
        jScrollPane10 = new javax.swing.JScrollPane();
        labelEvent = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        panelHelp = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        scroll2 = new javax.swing.JScrollPane();
        area2 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        panelSettings = new javax.swing.JPanel();
        panelUser = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        panelSettingsRight = new javax.swing.JPanel();
        buttonChangeBackColor = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        panelMod = new javax.swing.JPanel();
        panelSettingsRight1 = new javax.swing.JPanel();
        buttonChangeBackColor1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        panelRoot = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        panelSettingsRight2 = new javax.swing.JPanel();
        buttonChangeBackColor2 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelLeft.setBackground(new java.awt.Color(51, 51, 51));
        panelLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLeftformMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelLeftformMouseExited(evt);
            }
        });

        labelIconBus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconBus.png"))); // NOI18N
        labelIconBus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconBusMouseClicked(evt);
            }
        });

        labelIconChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconSpeechBubble.png"))); // NOI18N
        labelIconChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconChatMouseClicked(evt);
            }
        });

        labelIconCalendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconCalendar.png"))); // NOI18N
        labelIconCalendar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconCalendarMouseClicked(evt);
            }
        });

        labelIconEat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconRestaurant.png"))); // NOI18N
        labelIconEat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconEat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconEatMouseClicked(evt);
            }
        });

        labelIconEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconUserGroups.png"))); // NOI18N
        labelIconEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconEventMouseClicked(evt);
            }
        });

        labelIconSyllabus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconSyllabus.png"))); // NOI18N
        labelIconSyllabus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconSyllabus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconSyllabusMouseClicked(evt);
            }
        });

        labelIconHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconHome.png"))); // NOI18N
        labelIconHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconHomeMouseClicked(evt);
            }
        });

        labelTextChat.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextChat.setForeground(new java.awt.Color(255, 255, 255));
        labelTextChat.setText("Mesajlaşma");
        labelTextChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextChatMouseClicked(evt);
            }
        });

        labelTextHomePage.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextHomePage.setForeground(new java.awt.Color(255, 255, 255));
        labelTextHomePage.setText("Ana Sayfa");
        labelTextHomePage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextHomePage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextHomePageMouseClicked(evt);
            }
        });

        labelTextEat.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextEat.setForeground(new java.awt.Color(255, 255, 255));
        labelTextEat.setText("Yemek Menüsü");
        labelTextEat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextEat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextEatMouseClicked(evt);
            }
        });

        labelTextCalendar.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextCalendar.setForeground(new java.awt.Color(255, 255, 255));
        labelTextCalendar.setText("Takvim");
        labelTextCalendar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextCalendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextCalendarMouseClicked(evt);
            }
        });

        labelTextBus.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextBus.setForeground(new java.awt.Color(255, 255, 255));
        labelTextBus.setText("Otobüs Nerede?");
        labelTextBus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextBusMouseClicked(evt);
            }
        });

        labelTextEvent.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextEvent.setForeground(new java.awt.Color(255, 255, 255));
        labelTextEvent.setText("Etkinlikler");
        labelTextEvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextEvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextEventMouseClicked(evt);
            }
        });

        labelTextSyllabus.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        labelTextSyllabus.setForeground(new java.awt.Color(255, 255, 255));
        labelTextSyllabus.setText("Ders Programı");
        labelTextSyllabus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelTextSyllabus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelTextSyllabusMouseClicked(evt);
            }
        });

        labelIconSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconSettings.png"))); // NOI18N
        labelIconSettings.setToolTipText("Ayarlar");
        labelIconSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconSettingsMouseClicked(evt);
            }
        });

        labelIconHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconQuestionMark.png"))); // NOI18N
        labelIconHelp.setToolTipText("Yardım");
        labelIconHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconHelpMouseClicked(evt);
            }
        });

        labelIconShare.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/iconShare.png"))); // NOI18N
        labelIconShare.setToolTipText("Paylaş");
        labelIconShare.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelIconShare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelIconShareMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelLeftLayout = new javax.swing.GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIconBus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIconChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIconHome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLeftLayout.createSequentialGroup()
                        .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelIconEat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIconCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIconSyllabus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelIconEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTextBus)
                            .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(labelTextHomePage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelTextChat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(labelTextEat)
                            .addComponent(labelTextCalendar)
                            .addComponent(labelTextSyllabus)
                            .addComponent(labelTextEvent)
                            .addGroup(panelLeftLayout.createSequentialGroup()
                                .addComponent(labelIconSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(labelIconHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(labelIconShare, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20))
        );
        panelLeftLayout.setVerticalGroup(
            panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLeftLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelIconHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTextHomePage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIconChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTextChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIconBus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTextBus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelLeftLayout.createSequentialGroup()
                        .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelIconEat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTextEat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(labelIconCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelTextCalendar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelLeftLayout.createSequentialGroup()
                        .addComponent(labelIconSyllabus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(labelIconEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLeftLayout.createSequentialGroup()
                        .addComponent(labelTextSyllabus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(labelTextEvent, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(120, 120, 120)
                .addGroup(panelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelIconSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIconHelp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelIconShare, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTop.setBackground(new java.awt.Color(242, 217, 132));
        panelTop.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        panelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelTopMouseDragged(evt);
            }
        });
        panelTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelTopMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelTopMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelTopMousePressed(evt);
            }
        });

        labelTopText.setFont(new java.awt.Font("Monospaced", 1, 20)); // NOI18N
        labelTopText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTopText.setText("ANKARA YILDIRIM BEYAZIT ÜNİVERSİTESİ");
        labelTopText.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));

        labelExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit_1.png"))); // NOI18N
        labelExit.setToolTipText("Exit");
        labelExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelExitMouseClicked(evt);
            }
        });

        labelMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/minimize.png"))); // NOI18N
        labelMinimize.setToolTipText("Minimize");
        labelMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelMinimizeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTopLayout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(labelTopText, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130)
                .addComponent(labelMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTopText, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(labelExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBack.setPreferredSize(new java.awt.Dimension(920, 680));
        panelBack.setLayout(new java.awt.CardLayout());

        panelHomePage.setBackground(new java.awt.Color(255, 250, 220));
        panelHomePage.setPreferredSize(new java.awt.Dimension(920, 680));

        panelAnn.setBackground(new java.awt.Color(204, 204, 255));

        labelTextAnn.setFont(new java.awt.Font("Monospaced", 1, 20)); // NOI18N
        labelTextAnn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTextAnn.setText("DUYURULAR");

        javax.swing.GroupLayout panelAnnLayout = new javax.swing.GroupLayout(panelAnn);
        panelAnn.setLayout(panelAnnLayout);
        panelAnnLayout.setHorizontalGroup(
            panelAnnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTextAnn, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelAnnLayout.setVerticalGroup(
            panelAnnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTextAnn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelNews.setBackground(new java.awt.Color(204, 204, 255));

        labelTextNews.setFont(new java.awt.Font("Monospaced", 1, 20)); // NOI18N
        labelTextNews.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTextNews.setText("HABERLER");

        javax.swing.GroupLayout panelNewsLayout = new javax.swing.GroupLayout(panelNews);
        panelNews.setLayout(panelNewsLayout);
        panelNewsLayout.setHorizontalGroup(
            panelNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNewsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTextNews, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNewsLayout.setVerticalGroup(
            panelNewsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNewsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTextNews, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(607, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelHomePageLayout = new javax.swing.GroupLayout(panelHomePage);
        panelHomePage.setLayout(panelHomePageLayout);
        panelHomePageLayout.setHorizontalGroup(
            panelHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelHomePageLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(panelAnn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(panelNews, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        panelHomePageLayout.setVerticalGroup(
            panelHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHomePageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelHomePageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelNews, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelAnn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        panelBack.add(panelHomePage, "card2");

        panelMessage.setBackground(new java.awt.Color(255, 250, 220));
        panelMessage.setPreferredSize(new java.awt.Dimension(920, 680));

        textAreaMessage.setColumns(20);
        textAreaMessage.setRows(3);
        textAreaMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textAreaMessageKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(textAreaMessage);

        jLabel1.setBackground(new java.awt.Color(255, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/send.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        textAreaChat.setEditable(false);
        textAreaChat.setColumns(20);
        textAreaChat.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        textAreaChat.setLineWrap(true);
        textAreaChat.setRows(5);
        jScrollPane6.setViewportView(textAreaChat);

        javax.swing.GroupLayout panelMessageLayout = new javax.swing.GroupLayout(panelMessage);
        panelMessage.setLayout(panelMessageLayout);
        panelMessageLayout.setHorizontalGroup(
            panelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMessageLayout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addGroup(panelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelMessageLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        panelMessageLayout.setVerticalGroup(
            panelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMessageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70))
        );

        panelBack.add(panelMessage, "card3");

        panelBus.setBackground(new java.awt.Color(255, 250, 220));
        panelBus.setPreferredSize(new java.awt.Dimension(920, 680));

        textFieldStationNumber.setFont(new java.awt.Font("Monospaced", 1, 15)); // NOI18N
        textFieldStationNumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        labelTextStationEnter.setFont(new java.awt.Font("Constantia", 1, 15)); // NOI18N
        labelTextStationEnter.setText("Durak Numarasını girin :");

        textAreaBus.setEditable(false);
        textAreaBus.setColumns(20);
        textAreaBus.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        textAreaBus.setRows(5);
        textAreaBus.setWrapStyleWord(true);
        jScrollPane2.setViewportView(textAreaBus);

        labelSearchBus.setFont(new java.awt.Font("Constantia", 1, 15)); // NOI18N
        labelSearchBus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelSearchBus.setText("Göster");
        labelSearchBus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        labelSearchBus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelSearchBus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelSearchBusMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                labelSearchBusMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                labelSearchBusMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelBusLayout = new javax.swing.GroupLayout(panelBus);
        panelBus.setLayout(panelBusLayout);
        panelBusLayout.setHorizontalGroup(
            panelBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusLayout.createSequentialGroup()
                .addGap(174, 174, 174)
                .addGroup(panelBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBusLayout.createSequentialGroup()
                        .addComponent(labelTextStationEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldStationNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelSearchBus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(167, Short.MAX_VALUE))
        );
        panelBusLayout.setVerticalGroup(
            panelBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBusLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(panelBusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelTextStationEnter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldStationNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSearchBus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
        );

        panelBack.add(panelBus, "card4");

        panelEat.setBackground(new java.awt.Color(255, 250, 220));
        panelEat.setPreferredSize(new java.awt.Dimension(920, 680));
        panelEat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelEatMouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eat.jpeg"))); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jScrollPane1MousePressed(evt);
            }
        });

        textAreaEat.setEditable(false);
        textAreaEat.setColumns(20);
        textAreaEat.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        textAreaEat.setForeground(new java.awt.Color(255, 255, 255));
        textAreaEat.setRows(8);
        textAreaEat.setWrapStyleWord(true);
        textAreaEat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textAreaEatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(textAreaEat);

        monthlyFoodList.setText("Aylık Yemek Listesi");
        monthlyFoodList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthlyFoodListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEatLayout = new javax.swing.GroupLayout(panelEat);
        panelEat.setLayout(panelEatLayout);
        panelEatLayout.setHorizontalGroup(
            panelEatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEatLayout.createSequentialGroup()
                .addContainerGap(489, Short.MAX_VALUE)
                .addGroup(panelEatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEatLayout.createSequentialGroup()
                        .addComponent(monthlyFoodList)
                        .addGap(190, 190, 190))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEatLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
            .addGroup(panelEatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelEatLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelEatLayout.setVerticalGroup(
            panelEatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEatLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(monthlyFoodList)
                .addGap(193, 193, 193))
            .addGroup(panelEatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelEatLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panelBack.add(panelEat, "card5");

        panelCalendar.setBackground(new java.awt.Color(255, 250, 220));
        panelCalendar.setPreferredSize(new java.awt.Dimension(920, 680));

        toDoListEraseAllFilesButton.setBackground(new java.awt.Color(204, 204, 255));
        toDoListEraseAllFilesButton.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        toDoListEraseAllFilesButton.setText("Tüm dosyaları sil");
        toDoListEraseAllFilesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDoListEraseAllFilesButtonActionPerformed(evt);
            }
        });

        toDoListEraseButton.setBackground(new java.awt.Color(204, 204, 255));
        toDoListEraseButton.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        toDoListEraseButton.setText("Sil");
        toDoListEraseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDoListEraseButtonActionPerformed(evt);
            }
        });

        toDoListAddButton.setBackground(new java.awt.Color(204, 204, 255));
        toDoListAddButton.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        toDoListAddButton.setText("Ekle");
        toDoListAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toDoListAddButtonActionPerformed(evt);
            }
        });

        toDoListTextArea.setColumns(20);
        toDoListTextArea.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        toDoListTextArea.setRows(5);

        yearTextField.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        yearTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearTextFieldActionPerformed(evt);
            }
        });
        yearTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yearTextFieldKeyTyped(evt);
            }
        });

        yearTextLabel.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        yearTextLabel.setText("Yıl:");

        calendarTable.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        calendarTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        calendarTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        calendarTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        calendarTable.setRowHeight(42);
        calendarTable.setRowMargin(0);
        calendarTable.setSelectionBackground(new java.awt.Color(255, 255, 255));
        calendarTable.setSelectionForeground(new java.awt.Color(0, 0, 0));
        calendarTable.setShowHorizontalLines(false);
        calendarTable.setShowVerticalLines(false);
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        calendarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                calendarTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(calendarTable);

        prevMonthButton.setBackground(new java.awt.Color(204, 204, 255));
        prevMonthButton.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        prevMonthButton.setText("<—");
        prevMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevMonthButtonActionPerformed(evt);
            }
        });

        nextMonthButton.setBackground(new java.awt.Color(204, 204, 255));
        nextMonthButton.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        nextMonthButton.setText("—>");
        nextMonthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMonthButtonActionPerformed(evt);
            }
        });

        monthTextLabel.setBackground(new java.awt.Color(204, 0, 255));
        monthTextLabel.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        monthTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        monthTextLabel.setText(" January");

        statusTextLabel.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N

        javax.swing.GroupLayout panelCalendarLayout = new javax.swing.GroupLayout(panelCalendar);
        panelCalendar.setLayout(panelCalendarLayout);
        panelCalendarLayout.setHorizontalGroup(
            panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalendarLayout.createSequentialGroup()
                .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCalendarLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCalendarLayout.createSequentialGroup()
                                .addComponent(prevMonthButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monthTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nextMonthButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCalendarLayout.createSequentialGroup()
                                .addComponent(toDoListTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(toDoListEraseAllFilesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(statusTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(toDoListEraseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(toDoListAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelCalendarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCalendarLayout.createSequentialGroup()
                                .addComponent(yearTextLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        panelCalendarLayout.setVerticalGroup(
            panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCalendarLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yearTextField)
                        .addComponent(yearTextLabel))
                    .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(prevMonthButton)
                        .addComponent(nextMonthButton)
                        .addComponent(monthTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(panelCalendarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCalendarLayout.createSequentialGroup()
                        .addComponent(toDoListAddButton)
                        .addGap(18, 18, 18)
                        .addComponent(toDoListEraseButton)
                        .addGap(18, 18, 18)
                        .addComponent(toDoListEraseAllFilesButton)
                        .addGap(18, 18, 18)
                        .addComponent(statusTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(toDoListTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        panelBack.add(panelCalendar, "card6");

        panelsyllabus.setBackground(new java.awt.Color(255, 250, 220));
        panelsyllabus.setPreferredSize(new java.awt.Dimension(920, 680));

        jScrollPane9.setViewportView(jLabel4);

        javax.swing.GroupLayout panelsyllabusLayout = new javax.swing.GroupLayout(panelsyllabus);
        panelsyllabus.setLayout(panelsyllabusLayout);
        panelsyllabusLayout.setHorizontalGroup(
            panelsyllabusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelsyllabusLayout.setVerticalGroup(
            panelsyllabusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        panelBack.add(panelsyllabus, "card7");

        panelEvent.setBackground(new java.awt.Color(255, 250, 220));
        panelEvent.setPreferredSize(new java.awt.Dimension(920, 680));
        panelEvent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        slider.setBackground(new java.awt.Color(255, 255, 255));
        slider.setForeground(new java.awt.Color(0, 0, 0));
        slider.setMaximum(4);
        slider.setMinorTickSpacing(1);
        slider.setToolTipText("Etkinlikler");
        slider.setValue(0);
        slider.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        slider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sliderMouseClicked(evt);
            }
        });
        panelEvent.add(slider, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 630, 590, 40));

        labelEvent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/event/event-1.jpg"))); // NOI18N
        jScrollPane10.setViewportView(labelEvent);

        panelEvent.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 790, 590));

        jButton2.setFont(new java.awt.Font("Constantia", 0, 15)); // NOI18N
        jButton2.setText("Tüm Etkinlikler");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelEvent.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 630, 180, 40));

        panelBack.add(panelEvent, "card8");

        panelHelp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        area.setEditable(false);
        area.setColumns(20);
        area.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        area.setLineWrap(true);
        area.setRows(5);
        area.setText("AYBİS, Ankara Yıldırım Beyazıt öğrencilerinin gün içerisinde merak ettiği her soruya cevap verebilecek bir platformdur.Bölüm içi iletişim, haftalık ders programı, kişisel plan listelerini oluşturma imkanı sunan bir takvim, etkinlikler ve gün içinde ulaşım süresince oldukça çok kullanılan otobüs saatleri gibi birçok özelliğe sahiptir. AYBİS'in asıl amacı; birden fazla internet sitesi ve gün içinde kullanılan uygulamalara ihtiyaç duymadan belirtilen tüm özelliklere hakım olup , en etkili şekilde kullanıcıya sunabilmektir. Yardım Merkezimiz sayesinde AYBİS hakkında genel bilgi ve kullanım sırasında aklına takılan bir sorunun cevabını bulabilirsin.");
        scroll.setViewportView(area);

        panelHelp.add(scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 480, 240));

        jLabel2.setBackground(new java.awt.Color(255, 255, 153));
        jLabel2.setFont(new java.awt.Font("Constantia", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 0));
        jLabel2.setText("AYBİS HAKKINDA GENEL BİLGİ");
        panelHelp.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 280, 20));

        area2.setEditable(false);
        area2.setColumns(20);
        area2.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        area2.setLineWrap(true);
        area2.setRows(5);
        area2.setText("                                                             NEDEN AYBİS’İ KULLANMALISIN ? \n\n\n  AYBİS'e giriş ile beraber anasayfada, üniversitene dair gündemdeki haber ve duyuruları görebilir ve konu ile ilgili detaylı bilgileye sahip olmak istersen üzerilerine tıklayabilirsin.\n\n-AYBİS'de chat\n  Her bölümün kendine ait bir chat sayfası bulunmaktadır.Öğrenciler arası bilgi alışverişini sağlar ve öğrencilerin birbiri ile etkilemişini arttırır. Okul ve dersler ile ilgili tüm bilgilere bölümdeki her öğrenci kolaylıkla ulaşabilir.\n  Mesaj göndermek için ;\n1-Mesajlar'ı aç.\n2-Metin alanına dokunup mesajını yaz.\n3-Göndermek için mesajı gönder düğmesine dokun.\n\n-Takvim ve Yapılacaklar Listesi\n  AYBİS'de bulunan takvim sayesinde gündelik çalışman gereken derslerinin planlamasını yapabilir ayrıca senin için önemli olay ve  randevularını istediğin ay ve güne gelerek kaydedebilir, takibini kolayca gerçekleştirebilirsin.Kendine bir hatırlatıcı veya yapılacaklar listesi oluşturmak işte bu kadar kolay !\n\n-Etkinlikler\n  Eğer düzenlenecek olan etkinlikleri merak ediyorsan, Aybü event’da bulunan etkinlik duyurularının tamamını bu platform sayesinde takip edebilirsin. Etkinlikler hakkında detaylı bilgi edinmek için ilgili etkinlik üzerine tıkla.\n\n-Yemek Listesi\n  Yemek listesi sekmesini açarak Ankara Yıldırım Beyazıt Üniversitesi kampüslerinde verilecek olan günün yemek menüsüne ulaşabilirsin.\n\n-Otobüs Durağı\n  Gün içinde kullandığın otobüslerin saatlerini öğrenebilmek için ilgili durağın durak numarasını girdikten sonra sorgulama butonuna tıklayarak duraktan geçen tüm hatların durağa gelme sürelerini öğrenebilirsin.\n\n-Ders programı/sınav takvimi\n  Bunlara ek olarak,ders programı ve sınav takviminin en güncel haline kolayca erişim sağlayabilirsin.\n\n\n  Anladığın üzere,okula dair birbirinden farklı konular hakkında olan bilgileri daha hızlı takip edebilmek için birçoğunu AYBİS ile bir araya getirmeye çalıştık. \n\n  Sevgili AYBÜ'lü yardım merkezimiz sorularının cevabı için yetersiz kaldıysa ayrıca şikayet ve önerilerin varsa İletişim kısmında bulunan aybis@aybis.com adresine mail atarak veya sosyal medya üzerinden AYBİS hesaplarını takip ederek bizlere ulaşabilirsin.\n");
        scroll2.setViewportView(area2);

        panelHelp.add(scroll2, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 410, 670, 220));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/help.jpg"))); // NOI18N
        jLabel6.setText("jLabel6");
        panelHelp.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 680));

        panelBack.add(panelHelp, "card10");

        panelSettings.setBackground(new java.awt.Color(255, 250, 220));
        panelSettings.setLayout(new java.awt.CardLayout());

        panelUser.setBackground(new java.awt.Color(255, 250, 220));

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jScrollPane8.setViewportView(jTextArea3);

        panelSettingsRight.setBackground(new java.awt.Color(255, 255, 255));

        buttonChangeBackColor.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonChangeBackColor.setText("Arka plan rengini değiştir");
        buttonChangeBackColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeBackColorActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton7.setText("Sol menü rengini değiştir");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton8.setText("Üst menü rengini değiştir");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSettingsRightLayout = new javax.swing.GroupLayout(panelSettingsRight);
        panelSettingsRight.setLayout(panelSettingsRightLayout);
        panelSettingsRightLayout.setHorizontalGroup(
            panelSettingsRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsRightLayout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(panelSettingsRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonChangeBackColor, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
        panelSettingsRightLayout.setVerticalGroup(
            panelSettingsRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsRightLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(buttonChangeBackColor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout panelUserLayout = new javax.swing.GroupLayout(panelUser);
        panelUser.setLayout(panelUserLayout);
        panelUserLayout.setHorizontalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUserLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(panelSettingsRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        panelUserLayout.setVerticalGroup(
            panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8)
                    .addGroup(panelUserLayout.createSequentialGroup()
                        .addGap(0, 368, Short.MAX_VALUE)
                        .addComponent(panelSettingsRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        panelSettings.add(panelUser, "card3");

        panelMod.setBackground(new java.awt.Color(255, 250, 220));

        panelSettingsRight1.setBackground(new java.awt.Color(255, 255, 255));

        buttonChangeBackColor1.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonChangeBackColor1.setText("Arka plan rengini değiştir");
        buttonChangeBackColor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeBackColor1ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton9.setText("Sol menü rengini değiştir");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton10.setText("Üst menü rengini değiştir");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Okul Numarası");

        jButton5.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton5.setText("Display Info");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSettingsRight1Layout = new javax.swing.GroupLayout(panelSettingsRight1);
        panelSettingsRight1.setLayout(panelSettingsRight1Layout);
        panelSettingsRight1Layout.setHorizontalGroup(
            panelSettingsRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsRight1Layout.createSequentialGroup()
                .addGroup(panelSettingsRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSettingsRight1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsRight1Layout.createSequentialGroup()
                        .addContainerGap(62, Short.MAX_VALUE)
                        .addGroup(panelSettingsRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonChangeBackColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(61, 61, 61))
            .addGroup(panelSettingsRight1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelSettingsRight1Layout.setVerticalGroup(
            panelSettingsRight1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsRight1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
                .addComponent(buttonChangeBackColor1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        javax.swing.GroupLayout panelModLayout = new javax.swing.GroupLayout(panelMod);
        panelMod.setLayout(panelModLayout);
        panelModLayout.setHorizontalGroup(
            panelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelModLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addComponent(panelSettingsRight1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        panelModLayout.setVerticalGroup(
            panelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelModLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelModLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(panelSettingsRight1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        panelSettings.add(panelMod, "card4");

        panelRoot.setBackground(new java.awt.Color(255, 250, 220));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        panelSettingsRight2.setBackground(new java.awt.Color(255, 255, 255));

        buttonChangeBackColor2.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonChangeBackColor2.setText("Arka plan rengini değiştir");
        buttonChangeBackColor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChangeBackColor2ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton11.setText("Sol menü rengini değiştir");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton12.setText("Üst menü rengini değiştir");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton4.setText("Display Info");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jButton1.setText("Delete User");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Okul Numarası");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelSettingsRight2Layout = new javax.swing.GroupLayout(panelSettingsRight2);
        panelSettingsRight2.setLayout(panelSettingsRight2Layout);
        panelSettingsRight2Layout.setHorizontalGroup(
            panelSettingsRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSettingsRight2Layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addGroup(panelSettingsRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonChangeBackColor2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(42, 42, 42))
        );
        panelSettingsRight2Layout.setVerticalGroup(
            panelSettingsRight2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsRight2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(buttonChangeBackColor2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelRootLayout = new javax.swing.GroupLayout(panelRoot);
        panelRoot.setLayout(panelRootLayout);
        panelRootLayout.setHorizontalGroup(
            panelRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRootLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addGap(50, 50, 50)
                .addComponent(panelSettingsRight2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        panelRootLayout.setVerticalGroup(
            panelRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRootLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(panelRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(panelSettingsRight2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );

        panelSettings.add(panelRoot, "card2");

        panelBack.add(panelSettings, "card9");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void labelTextChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextChatMouseClicked
        labelTopText.setText("Mesajlaşma");
        clearPanelBack();
        panelMessage.setVisible(true);
    }//GEN-LAST:event_labelTextChatMouseClicked


    private void labelTextHomePageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextHomePageMouseClicked
        labelTopText.setText("ANKARA YILDIRIM BEYAZIT ÜNİVERSİTESİ");
        clearPanelBack();
        panelHomePage.setVisible(true);
    }//GEN-LAST:event_labelTextHomePageMouseClicked

    private void labelTextEatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextEatMouseClicked
        labelTopText.setText("Yemek Menüsü");
        clearPanelBack();

        panelEat.setVisible(true);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportBorder(null);
        textAreaEat.setBorder(null);
        textAreaEat.setBackground(new Color(0, 0, 0, 64));
        ArrayList<String> eatList = systemClass.getEat();

        textAreaEat.setText("\n\tYEMEK MENÜSÜ"
                + "\n\t(" + eatList.get(0) + ")"
                + "\n------------------------------"
                + "\n Çorba : " + eatList.get(1)
                + "\n Ana Yemek : " + eatList.get(2)
                + "\n Ara Yemek : " + eatList.get(3)
                + "\n Yan Ürünler : " + eatList.get(4)
                + "\n------------------------------");

        panelEat.setVisible(true);
    }//GEN-LAST:event_labelTextEatMouseClicked

    private void labelTextCalendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextCalendarMouseClicked
        labelTopText.setText("Takvim");
        clearPanelBack();
        panelCalendar.setVisible(true);

        mod1 = new DefaultTableModel();
        calendarTable.setModel(mod1);

        mod1.addColumn("Pazar");
        mod1.addColumn("Pazartesi");
        mod1.addColumn("Salı");
        mod1.addColumn("Çarşamba");
        mod1.addColumn("Perşembe");
        mod1.addColumn("Cuma");
        mod1.addColumn("Cumartesi");
        mod1.setRowCount(6);
        yearTextField.setText("" + year);
        Type();
    }//GEN-LAST:event_labelTextCalendarMouseClicked

    private void labelTextBusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextBusMouseClicked
        labelTopText.setText("Otobüs Nerede?");
        clearPanelBack();
        panelBus.setVisible(true);
    }//GEN-LAST:event_labelTextBusMouseClicked

    private void labelTextEventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextEventMouseClicked
        labelTopText.setText("Etkinlikler");
        clearPanelBack();
        panelEvent.setVisible(true);
    }//GEN-LAST:event_labelTextEventMouseClicked

    private void labelTextSyllabusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelTextSyllabusMouseClicked
        labelTopText.setText("Ders Programı");
        clearPanelBack();
        panelsyllabus.setVisible(true);

        if (user != null) {
            if (user.getUserDep().equals("Bilgisayar Mühendisliği")) {
                ImageIcon icon = new ImageIcon("src/images/ceng.jpg");
                jLabel4.setIcon(icon);
            } else if (user.getUserDep().equals("Tıp")) {
                ImageIcon icon = new ImageIcon("src/images/tip.jpg");
                jLabel4.setIcon(icon);
            }
        }
    }//GEN-LAST:event_labelTextSyllabusMouseClicked

    private void labelIconSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconSettingsMouseClicked
        labelTopText.setText("Ayarlar");
        clearPanelBack();
        panelSettings.setVisible(true);

        for (Component panel : panelSettings.getComponents()) {
            panel.setVisible(false);
        }

        if (user != null) {
            panelUser.setVisible(true);
            jTextArea3.setText(user.toString());
        } else if (mod != null) {
            System.out.println("selam");
            panelMod.setVisible(true);
            jTextArea2.setText(mod.toString());
        } else if (root != null) {
            panelRoot.setVisible(true);
            jTextArea1.setText(root.toString());
        }
    }//GEN-LAST:event_labelIconSettingsMouseClicked

    private void labelIconHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconHelpMouseClicked
        labelTopText.setText("Yardım");
        clearPanelBack();
        panelHelp.setVisible(true);


    }//GEN-LAST:event_labelIconHelpMouseClicked

    private void labelIconShareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconShareMouseClicked
        labelTopText.setText("Paylaş");
    }//GEN-LAST:event_labelIconShareMouseClicked

    private void panelLeftformMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLeftformMouseEntered
        velX = 1;
    }//GEN-LAST:event_panelLeftformMouseEntered

    private void panelLeftformMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLeftformMouseExited
        if (!panelLeft.contains(evt.getPoint())) {
            velX = -1;
        }
    }//GEN-LAST:event_panelLeftformMouseExited

    private void panelTopMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMouseEntered
        velY = 1;
    }//GEN-LAST:event_panelTopMouseEntered

    private void panelTopMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMouseExited
        if (!panelTop.contains(evt.getPoint())) {
            velY = -1;
        }
    }//GEN-LAST:event_panelTopMouseExited

    private void panelTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMousePressed
        initialClick = evt.getPoint();
        getComponentAt(initialClick);
    }//GEN-LAST:event_panelTopMousePressed

    private void panelTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMouseDragged
        setLocation(getLocation().x + evt.getX() - initialClick.x,
                getLocation().y + evt.getY() - initialClick.y);
    }//GEN-LAST:event_panelTopMouseDragged

    private void labelExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelExitMouseClicked
        if (systemClass.getDriver() != null) {
            systemClass.stop();
        }

        System.exit(0);
    }//GEN-LAST:event_labelExitMouseClicked

    private void labelMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelMinimizeMouseClicked
        setState(FrameMenu.ICONIFIED);
    }//GEN-LAST:event_labelMinimizeMouseClicked

    private void labelSearchBusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelSearchBusMouseClicked

    }//GEN-LAST:event_labelSearchBusMouseClicked

    private void labelSearchBusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelSearchBusMouseReleased
        systemClass.setStationNumber(textFieldStationNumber.getText());
        textAreaBus.setText(systemClass.client());
        labelSearchBus.setVisible(true);
    }//GEN-LAST:event_labelSearchBusMouseReleased

    private void labelSearchBusMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelSearchBusMousePressed
        labelSearchBus.setVisible(false);
    }//GEN-LAST:event_labelSearchBusMousePressed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        systemClass.addMessage("test", textAreaMessage.getText());
        textAreaMessage.setText("");
    }//GEN-LAST:event_jLabel1MouseClicked

    Scanner sc = new Scanner(System.in);
    Date date = new Date();
    int month = date.getMonth();
    int year = 1900 + date.getYear();
    int day = 1;
    int curDay = date.getDate();
    int curDayj;
    Calendar cd = Calendar.getInstance();
    Calendar curD = Calendar.getInstance();

    private void toDoListEraseAllFilesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDoListEraseAllFilesButtonActionPerformed
        int reply = JOptionPane.showConfirmDialog(null, "Emin misiniz? Bu işlem tüm notları silecektir!");
        if (reply == JOptionPane.YES_OPTION) {
            File folder = new File(System.getProperty("user.dir"));
            File fList[] = folder.listFiles();
            for (File fList1 : fList) {
                String pes = fList1.getName();

                if (pes.endsWith(".txt")) {
                    fList1.delete();
                }
            }
            toDoListTextArea.setText("");
            JOptionPane.showMessageDialog(null, "Tüm mesajlar silindi...");
        }
    }//GEN-LAST:event_toDoListEraseAllFilesButtonActionPerformed

    private void toDoListEraseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDoListEraseButtonActionPerformed
        String fileNameDate = String.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn())) + String.valueOf(month + 1) + String.valueOf(year);
        int reply = JOptionPane.showConfirmDialog(null, "Emin misiniz? Bu işlem bu günü silecektir...");
        if (reply == JOptionPane.YES_OPTION) {
            try {
                try (PrintWriter pw = new PrintWriter(fileNameDate + ".txt")) {
                    pw.write("");
                    pw.close();
                    toDoListTextArea.setText("");
                    JOptionPane.showMessageDialog(null, "Gün Silindi...");
                }
            } catch (IOException ex2) {
                Logger.getLogger(FrameMenu.class.getName()).log(Level.SEVERE, null, ex2);
            }
        }
    }//GEN-LAST:event_toDoListEraseButtonActionPerformed

    private void toDoListAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toDoListAddButtonActionPerformed
        String fileNameDate = String.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn())) + String.valueOf(month + 1) + String.valueOf(year);
        if (calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()) != "") {
            try {
                try (PrintWriter pw = new PrintWriter(fileNameDate + ".txt")) {
                    pw.write(toDoListTextArea.getText() + "\n");
                    pw.close();
                    toDoListTextArea.setText("");
                    statusTextLabel.setText("Eklendi...");
                }
            } catch (IOException ex2) {
                Logger.getLogger(FrameMenu.class.getName()).log(Level.SEVERE, null, ex2);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Boş tarihe not eklenemez...");
        }
    }//GEN-LAST:event_toDoListAddButtonActionPerformed

    private void yearTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yearTextFieldKeyTyped
        if (evt.getKeyChar() == 10) {
            Type();
        }
    }//GEN-LAST:event_yearTextFieldKeyTyped

    private void prevMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevMonthButtonActionPerformed
        if (month != 0) {
            month--;
        } else {
            month = 11;
            year--;
            yearTextField.setText("" + year);
        }
        Type();
    }//GEN-LAST:event_prevMonthButtonActionPerformed

    private void nextMonthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMonthButtonActionPerformed
        if (month != 11) {
            month++;
        } else {
            month = 0;
            year++;
            yearTextField.setText("" + year);
        }
        Type();
    }//GEN-LAST:event_nextMonthButtonActionPerformed

    private void textAreaMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textAreaMessageKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            systemClass.addMessage("test", textAreaMessage.getText());
            textAreaMessage.setText("");
        }
    }//GEN-LAST:event_textAreaMessageKeyPressed

    private void monthlyFoodListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthlyFoodListActionPerformed
        openPDF();
    }//GEN-LAST:event_monthlyFoodListActionPerformed

    private void labelIconHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconHomeMouseClicked
        labelTopText.setText("ANKARA YILDIRIM BEYAZIT ÜNİVERSİTESİ");
        clearPanelBack();
        panelHomePage.setVisible(true);
    }//GEN-LAST:event_labelIconHomeMouseClicked

    private void labelIconChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconChatMouseClicked

        labelTopText.setText("Mesajlaşma");
        clearPanelBack();
        panelMessage.setVisible(true);
    }//GEN-LAST:event_labelIconChatMouseClicked

    private void labelIconBusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconBusMouseClicked

        labelTopText.setText("Otobüs Nerede?");
        clearPanelBack();
        panelBus.setVisible(true);
    }//GEN-LAST:event_labelIconBusMouseClicked

    private void labelIconEatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconEatMouseClicked
        labelTopText.setText("Yemek Menüsü");
        clearPanelBack();

        ArrayList<String> eatList = systemClass.getEat();
        textAreaEat.setText("\n\tYEMEK MENÜSÜ"
                + "\n\t(" + eatList.get(0) + ")"
                + "\n------------------------------"
                + "\n Çorba : " + eatList.get(1)
                + "\n Ana Yemek : " + eatList.get(2)
                + "\n Ara Yemek : " + eatList.get(3)
                + "\n Yan Ürünler : " + eatList.get(4)
                + "\n------------------------------");

        panelEat.setVisible(true);
    }//GEN-LAST:event_labelIconEatMouseClicked

    private void labelIconCalendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconCalendarMouseClicked
        labelTopText.setText("Takvim");
        clearPanelBack();
        panelCalendar.setVisible(true);

        mod1 = new DefaultTableModel();
        calendarTable.setModel(mod1);

        mod1.addColumn("Pazar");
        mod1.addColumn("Pazartesi");
        mod1.addColumn("Salı");
        mod1.addColumn("Çarşamba");
        mod1.addColumn("Perşembe");
        mod1.addColumn("Cuma");
        mod1.addColumn("Cumartesi");
        mod1.setRowCount(6);
        yearTextField.setText("" + year);
        Type();
    }//GEN-LAST:event_labelIconCalendarMouseClicked

    private void labelIconSyllabusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconSyllabusMouseClicked
        labelTopText.setText("Ders Programı");
        clearPanelBack();
        panelsyllabus.setVisible(true);

        if (user != null) {
            if (user.getUserDep().equals("Bilgisayar Mühendisliği")) {
                ImageIcon icon = new ImageIcon("src/images/ceng.jpg");
                jLabel4.setIcon(icon);
            } else if (user.getUserDep().equals("Tıp")) {
                ImageIcon icon = new ImageIcon("src/images/tip.jpg");
                jLabel4.setIcon(icon);
            }
        }
    }//GEN-LAST:event_labelIconSyllabusMouseClicked

    private void labelIconEventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelIconEventMouseClicked
        labelTopText.setText("Etkinlikler");
        clearPanelBack();
        panelEvent.setVisible(true);

    }//GEN-LAST:event_labelIconEventMouseClicked

    private void yearTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearTextFieldActionPerformed

    private void panelEatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEatMouseClicked

    private void jScrollPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MousePressed
        textAreaEat.repaint();
    }//GEN-LAST:event_jScrollPane1MousePressed

    private void textAreaEatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textAreaEatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_textAreaEatMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void sliderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sliderMouseClicked

    }//GEN-LAST:event_sliderMouseClicked

    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
        ImageIcon icon1 = new ImageIcon("src/images/event/event-1.jpg");
        ImageIcon icon2 = new ImageIcon("src/images/event/event-2.jpg");
        ImageIcon icon3 = new ImageIcon("src/images/event/event-3.jpg");
        ImageIcon icon4 = new ImageIcon("src/images/event/event-4.jpg");
        ImageIcon icon5 = new ImageIcon("src/images/event/event-5.jpg");

        switch (slider.getValue()) {
            case 0: {
                ImageIcon icon = new ImageIcon("src/images/event/event-1.jpg");
                labelEvent.setIcon(icon);
                break;
            }
            case 1: {
                ImageIcon icon = new ImageIcon("src/images/event/event-2.jpg");
                labelEvent.setIcon(icon);
                break;
            }
            case 2: {
                ImageIcon icon = new ImageIcon("src/images/event/event-3.jpg");
                labelEvent.setIcon(icon);
                break;
            }
            case 3: {
                ImageIcon icon = new ImageIcon("src/images/event/event-4.jpg");
                labelEvent.setIcon(icon);
                break;
            }
            case 4: {
                ImageIcon icon = new ImageIcon("src/images/event/event-5.jpg");
                labelEvent.setIcon(icon);
                break;
            }

            default:
                break;
        }
    }//GEN-LAST:event_sliderStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        openWebpage("http://event.ybu.edu.tr/takvim/tumu");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void calendarTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_calendarTableMouseClicked
        statusTextLabel.setText("");
        toDoListTextArea.setVisible(true);
        toDoListAddButton.setVisible(true);
        toDoListEraseButton.setVisible(true);
        toDoListEraseAllFilesButton.setVisible(true);
        String fileNameDate = String.valueOf(calendarTable.getValueAt(calendarTable.getSelectedRow(),
                calendarTable.getSelectedColumn())) + String.valueOf(month + 1) + String.valueOf(year);
        try {
            FileInputStream fis = new FileInputStream(fileNameDate + ".txt");
            int deger;
            String s = "";
            while ((deger = fis.read()) != -1) {
                s += (char) deger;
            }
            toDoListTextArea.setText(s);
        } catch (FileNotFoundException ex) {
            toDoListTextArea.setText("");
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_calendarTableMouseClicked

    private void buttonChangeBackColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangeBackColorActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(255, 250, 220));

        for (Component panel : panelBack.getComponents()) {
            panel.setBackground(newColor);
        }

        for (Component panel : panelSettings.getComponents()) {
            panel.setBackground(newColor);
        }
    }//GEN-LAST:event_buttonChangeBackColorActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(51, 51, 51));
        panelLeft.setBackground(newColor);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(242, 217, 132));
        panelTop.setBackground(newColor);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void buttonChangeBackColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangeBackColor1ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(255, 250, 220));

        for (Component panel : panelBack.getComponents()) {
            panel.setBackground(newColor);
        }

        for (Component panel : panelSettings.getComponents()) {
            panel.setBackground(newColor);
        }
    }//GEN-LAST:event_buttonChangeBackColor1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(51, 51, 51));
        panelLeft.setBackground(newColor);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(242, 217, 132));
        panelTop.setBackground(newColor);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        root.deleteUser(jTextField1.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTextArea1.setText(root.displayUserInfo(jTextField1.getText()));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(242, 217, 132));
        panelTop.setBackground(newColor);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(51, 51, 51));
        panelLeft.setBackground(newColor);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void buttonChangeBackColor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChangeBackColor2ActionPerformed
        Color newColor = JColorChooser.showDialog(null, "Arka plan rengini seçin!", new Color(255, 250, 220));

        for (Component panel : panelBack.getComponents()) {
            panel.setBackground(newColor);
        }

        for (Component panel : panelSettings.getComponents()) {
            panel.setBackground(newColor);
        }
    }//GEN-LAST:event_buttonChangeBackColor2ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.selectAll();
    }//GEN-LAST:event_jTextField1MouseClicked

    public String monthTyper() {
        switch (month) {
            case 0:
                return "Ocak";
            case 1:
                return "Şubat";
            case 2:
                return "Mart";
            case 3:
                return "Nisan";
            case 4:
                return "Mayıs";
            case 5:
                return "Haziran";
            case 6:
                return "Temmuz";
            case 7:
                return "Ağustos";
            case 8:
                return "Eylül";
            case 9:
                return "Ekim";
            case 10:
                return "Kasım";
            case 11:
                return "Aralık";
            default:
                return null;
        }
    }
    DefaultTableModel mod1;

    public void Type() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                mod1.setValueAt("", i, j);
            }
        }
        mod1.setRowCount(6);

        try {
            year = Integer.valueOf(yearTextField.getText());

            monthTextLabel.setText(monthTyper());
            cd.set(year, month, day);
            curD.set(year, month, curDay);
            int start = cd.get(Calendar.DAY_OF_WEEK);
            int maxDay = cd.getActualMaximum(Calendar.DAY_OF_MONTH);
            start--;
            int j = 0;
            for (int i = 1; i <= maxDay; i++) {
                mod1.setValueAt(i, j, start);
                start++;
                if (i == curDay) {
                    curDayj = j;
                }
                if (start == 7) {
                    start = 0;
                    j++;
                }

            }
            if (date.getMonth() == month && date.getYear() + 1900 == Integer.valueOf(yearTextField.getText())) {
                mod1.setValueAt("[" + mod1.getValueAt(curDayj, date.getDay()) + "]", curDayj, date.getDay());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Sayı yazılmalı!");
            yearTextField.setText("" + year);
            Type();
        }
    }

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new FrameMenu(person, systemClass).setVisible(true);
            System.out.println();
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area;
    private javax.swing.JTextArea area2;
    private javax.swing.JButton buttonChangeBackColor;
    private javax.swing.JButton buttonChangeBackColor1;
    private javax.swing.JButton buttonChangeBackColor2;
    private javax.swing.JTable calendarTable;
    private javax.swing.JColorChooser colorChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelEvent;
    private javax.swing.JLabel labelExit;
    private javax.swing.JLabel labelIconBus;
    private javax.swing.JLabel labelIconCalendar;
    private javax.swing.JLabel labelIconChat;
    private javax.swing.JLabel labelIconEat;
    private javax.swing.JLabel labelIconEvent;
    private javax.swing.JLabel labelIconHelp;
    private javax.swing.JLabel labelIconHome;
    private javax.swing.JLabel labelIconSettings;
    private javax.swing.JLabel labelIconShare;
    private javax.swing.JLabel labelIconSyllabus;
    private javax.swing.JLabel labelMinimize;
    private javax.swing.JLabel labelSearchBus;
    private javax.swing.JLabel labelTextAnn;
    private javax.swing.JLabel labelTextBus;
    private javax.swing.JLabel labelTextCalendar;
    private javax.swing.JLabel labelTextChat;
    private javax.swing.JLabel labelTextEat;
    private javax.swing.JLabel labelTextEvent;
    private javax.swing.JLabel labelTextHomePage;
    private javax.swing.JLabel labelTextNews;
    private javax.swing.JLabel labelTextStationEnter;
    private javax.swing.JLabel labelTextSyllabus;
    private javax.swing.JLabel labelTopText;
    private javax.swing.JLabel monthTextLabel;
    private javax.swing.JButton monthlyFoodList;
    private javax.swing.JButton nextMonthButton;
    private javax.swing.JPanel panelAnn;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelBus;
    private javax.swing.JPanel panelCalendar;
    private javax.swing.JPanel panelEat;
    private javax.swing.JPanel panelEvent;
    private javax.swing.JPanel panelHelp;
    private javax.swing.JPanel panelHomePage;
    private javax.swing.JPanel panelLeft;
    private javax.swing.JPanel panelMessage;
    private javax.swing.JPanel panelMod;
    private javax.swing.JPanel panelNews;
    private javax.swing.JPanel panelRoot;
    private javax.swing.JPanel panelSettings;
    private javax.swing.JPanel panelSettingsRight;
    private javax.swing.JPanel panelSettingsRight1;
    private javax.swing.JPanel panelSettingsRight2;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPanel panelUser;
    private javax.swing.JPanel panelsyllabus;
    private javax.swing.JButton prevMonthButton;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel statusTextLabel;
    private javax.swing.JTextArea textAreaBus;
    private javax.swing.JTextArea textAreaChat;
    private javax.swing.JTextArea textAreaEat;
    private javax.swing.JTextArea textAreaMessage;
    private javax.swing.JTextField textFieldStationNumber;
    private javax.swing.JButton toDoListAddButton;
    private javax.swing.JButton toDoListEraseAllFilesButton;
    private javax.swing.JButton toDoListEraseButton;
    private javax.swing.JTextArea toDoListTextArea;
    private javax.swing.JTextField yearTextField;
    private javax.swing.JLabel yearTextLabel;
    // End of variables declaration//GEN-END:variables
}
