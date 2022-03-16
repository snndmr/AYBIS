package frames;

import codes.SystemClass;
import java.awt.Frame;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import users.*;

public class FrameLogin extends javax.swing.JFrame {

    private Point initialClick;

    private static SystemClass systemClass;

    private FrameMenu frameMenu = null;

    public FrameLogin() {
        systemClass = new SystemClass();

        checkEGOConnection();
        checkDatabaseConnection();

        initComponents();
        setFocusable(true);

        passwordFieldSign.setEchoChar((char) 0);

        if (deserializable() != null) {
            UserSave temp = deserializable();
            textFieldUserNumberLog.setText(temp.getSchoolNumber());
            passwordFieldLog.setText(temp.getPassword());
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" SERIALIZATION ">
    public void serializable(UserSave userSave) {
        try (FileOutputStream fos = new FileOutputStream("user.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userSave);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrameLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrameLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UserSave deserializable() {
        try (FileInputStream fos = new FileInputStream("user.ser");
                ObjectInputStream ois = new ObjectInputStream(fos)) {
            return (UserSave) ois.readObject();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" DATABASE CONNECTION ">
    public void checkDatabaseConnection() {
        new Thread(() -> {
            while (true) {
                if (!systemClass.connect()) {
                    panelLoading.setVisible(true);
                } else {
                    panelLoading.setVisible(false);
                    panelLog.setVisible(true);
                    break;
                }
            }
        }).start();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" EGO CONNECTION ">
    public void checkEGOConnection() {
        new Thread(() -> {
            systemClass.start();
        }).start();
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTop = new javax.swing.JPanel();
        labelExit = new javax.swing.JLabel();
        labelMin = new javax.swing.JLabel();
        panelBackLogo = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        panelBackSignLog = new javax.swing.JPanel();
        panelBack = new javax.swing.JPanel();
        panelLoading = new javax.swing.JPanel();
        labelLoading = new javax.swing.JLabel();
        labelLoadingText = new javax.swing.JLabel();
        panelLog = new javax.swing.JPanel();
        labelLog = new javax.swing.JLabel();
        textFieldUserNumberLog = new javax.swing.JTextField();
        passwordFieldLog = new javax.swing.JPasswordField();
        buttonLog = new javax.swing.JButton();
        labelWarn = new javax.swing.JLabel();
        labelEye = new javax.swing.JLabel();
        CheckBoxRememberMe = new javax.swing.JCheckBox();
        panelSignOne = new javax.swing.JPanel();
        textFieldUserNumberSign = new javax.swing.JTextField();
        passwordFieldSign = new javax.swing.JPasswordField();
        textFieldUserName = new javax.swing.JTextField();
        textFieldUserSurname = new javax.swing.JTextField();
        textFieldUserMail = new javax.swing.JTextField();
        buttonSign = new javax.swing.JButton();
        labelEyeTwo = new javax.swing.JLabel();
        LabelSignTwo1 = new javax.swing.JLabel();
        panelSignTwo = new javax.swing.JPanel();
        LabelSignTwo = new javax.swing.JLabel();
        buttonSignTwo = new javax.swing.JButton();
        comboBoxFac = new javax.swing.JComboBox<>();
        comboBoxDep = new javax.swing.JComboBox<>();
        comboBoxClass = new javax.swing.JComboBox<>();
        labelWarnSign = new javax.swing.JLabel();
        buttonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        panelTop.setBackground(new java.awt.Color(76, 76, 76));
        panelTop.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        panelTop.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelTopMouseDragged(evt);
            }
        });
        panelTop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelTopMousePressed(evt);
            }
        });

        labelExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        labelExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelExitMouseClicked(evt);
            }
        });

        labelMin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/down.png"))); // NOI18N
        labelMin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelMin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelMinMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTopLayout = new javax.swing.GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTopLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelMin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelExit, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        panelBackLogo.setBackground(new java.awt.Color(242, 217, 132));

        labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logIn.png"))); // NOI18N

        javax.swing.GroupLayout panelBackLogoLayout = new javax.swing.GroupLayout(panelBackLogo);
        panelBackLogo.setLayout(panelBackLogoLayout);
        panelBackLogoLayout.setHorizontalGroup(
            panelBackLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackLogoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelBackLogoLayout.setVerticalGroup(
            panelBackLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackLogoLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(labelLogo)
                .addContainerGap(174, Short.MAX_VALUE))
        );

        panelBackSignLog.setBackground(new java.awt.Color(255, 250, 220));

        panelBack.setLayout(new java.awt.CardLayout());

        panelLoading.setBackground(new java.awt.Color(204, 102, 0));

        labelLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bookGif.gif"))); // NOI18N

        labelLoadingText.setBackground(new java.awt.Color(204, 102, 0));
        labelLoadingText.setFont(new java.awt.Font("Constantia", 1, 16)); // NOI18N
        labelLoadingText.setForeground(new java.awt.Color(255, 255, 255));
        labelLoadingText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLoadingText.setText("Lütfen bekleyiniz...");

        javax.swing.GroupLayout panelLoadingLayout = new javax.swing.GroupLayout(panelLoading);
        panelLoading.setLayout(panelLoadingLayout);
        panelLoadingLayout.setHorizontalGroup(
            panelLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelLoadingText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelLoading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLoadingLayout.setVerticalGroup(
            panelLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoadingLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(labelLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelLoadingText, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelBack.add(panelLoading, "card5");

        panelLog.setBackground(new java.awt.Color(108, 122, 137));
        panelLog.setPreferredSize(new java.awt.Dimension(350, 450));

        labelLog.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        labelLog.setForeground(new java.awt.Color(255, 255, 255));
        labelLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLog.setText("Giriş");

        textFieldUserNumberLog.setBackground(new java.awt.Color(108, 122, 137));
        textFieldUserNumberLog.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        textFieldUserNumberLog.setForeground(new java.awt.Color(204, 204, 204));
        textFieldUserNumberLog.setText("Öğrenci Numarası");
        textFieldUserNumberLog.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        textFieldUserNumberLog.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUserNumberLogFocusGained(evt);
            }
        });

        passwordFieldLog.setBackground(new java.awt.Color(108, 122, 137));
        passwordFieldLog.setFont(new java.awt.Font("Consolas", 0, 16)); // NOI18N
        passwordFieldLog.setForeground(new java.awt.Color(204, 204, 204));
        passwordFieldLog.setText("Şifre");
        passwordFieldLog.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        passwordFieldLog.setEchoChar('*');
        passwordFieldLog.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldLogFocusGained(evt);
            }
        });

        buttonLog.setBackground(new java.awt.Color(108, 122, 137));
        buttonLog.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonLog.setText("Giriş Yap");
        buttonLog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonLogMouseClicked(evt);
            }
        });

        labelWarn.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        labelWarn.setForeground(new java.awt.Color(255, 255, 255));
        labelWarn.setText("Hesabın yok mu? Hemen kayıt ol!");
        labelWarn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelWarn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelWarnMouseClicked(evt);
            }
        });

        labelEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye.png"))); // NOI18N
        labelEye.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        labelEye.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelEye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEyeMouseClicked(evt);
            }
        });

        CheckBoxRememberMe.setBackground(new java.awt.Color(108, 122, 137));
        CheckBoxRememberMe.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        CheckBoxRememberMe.setForeground(new java.awt.Color(240, 240, 240));
        CheckBoxRememberMe.setSelected(true);
        CheckBoxRememberMe.setText(" Beni hatırla!");
        CheckBoxRememberMe.setBorder(null);
        CheckBoxRememberMe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CheckBoxRememberMe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelLogLayout = new javax.swing.GroupLayout(panelLog);
        panelLog.setLayout(panelLogLayout);
        panelLogLayout.setHorizontalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLogLayout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(labelLog))
                    .addGroup(panelLogLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelLogLayout.createSequentialGroup()
                                .addComponent(passwordFieldLog)
                                .addGap(0, 0, 0)
                                .addComponent(labelEye))
                            .addComponent(textFieldUserNumberLog, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelLogLayout.createSequentialGroup()
                                .addComponent(CheckBoxRememberMe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonLog)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLogLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(labelWarn)
                .addGap(73, 73, 73))
        );
        panelLogLayout.setVerticalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(labelLog)
                .addGap(25, 25, 25)
                .addComponent(textFieldUserNumberLog, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordFieldLog, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEye, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CheckBoxRememberMe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonLog, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                .addComponent(labelWarn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        panelBack.add(panelLog, "card2");

        panelSignOne.setBackground(new java.awt.Color(108, 122, 137));
        panelSignOne.setPreferredSize(new java.awt.Dimension(350, 450));

        textFieldUserNumberSign.setBackground(new java.awt.Color(108, 122, 137));
        textFieldUserNumberSign.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        textFieldUserNumberSign.setForeground(new java.awt.Color(204, 204, 204));
        textFieldUserNumberSign.setText("Öğrenci Numarası");
        textFieldUserNumberSign.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        textFieldUserNumberSign.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUserNumberSignFocusGained(evt);
            }
        });

        passwordFieldSign.setBackground(new java.awt.Color(108, 122, 137));
        passwordFieldSign.setFont(new java.awt.Font("Consolas", 0, 16)); // NOI18N
        passwordFieldSign.setForeground(new java.awt.Color(215, 215, 215));
        passwordFieldSign.setText("Şifre");
        passwordFieldSign.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        passwordFieldSign.setEchoChar('*');
        passwordFieldSign.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldSignFocusGained(evt);
            }
        });

        textFieldUserName.setBackground(new java.awt.Color(108, 122, 137));
        textFieldUserName.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        textFieldUserName.setForeground(new java.awt.Color(215, 215, 215));
        textFieldUserName.setText("Ad");
        textFieldUserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        textFieldUserName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUserNameFocusGained(evt);
            }
        });

        textFieldUserSurname.setBackground(new java.awt.Color(108, 122, 137));
        textFieldUserSurname.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        textFieldUserSurname.setForeground(new java.awt.Color(215, 215, 215));
        textFieldUserSurname.setText("Soyad");
        textFieldUserSurname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        textFieldUserSurname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUserSurnameFocusGained(evt);
            }
        });

        textFieldUserMail.setBackground(new java.awt.Color(108, 122, 137));
        textFieldUserMail.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        textFieldUserMail.setForeground(new java.awt.Color(215, 215, 215));
        textFieldUserMail.setText("E-Mail");
        textFieldUserMail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        textFieldUserMail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUserMailFocusGained(evt);
            }
        });

        buttonSign.setBackground(new java.awt.Color(108, 122, 137));
        buttonSign.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonSign.setText("İleri");
        buttonSign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonSign.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSignMouseClicked(evt);
            }
        });

        labelEyeTwo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye.png"))); // NOI18N
        labelEyeTwo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        labelEyeTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelEyeTwo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEyeTwoMouseClicked(evt);
            }
        });

        LabelSignTwo1.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        LabelSignTwo1.setForeground(new java.awt.Color(255, 255, 255));
        LabelSignTwo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelSignTwo1.setText("Kayıt");

        javax.swing.GroupLayout panelSignOneLayout = new javax.swing.GroupLayout(panelSignOne);
        panelSignOne.setLayout(panelSignOneLayout);
        panelSignOneLayout.setHorizontalGroup(
            panelSignOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignOneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelSignOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSignOneLayout.createSequentialGroup()
                        .addComponent(passwordFieldSign, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(labelEyeTwo))
                    .addGroup(panelSignOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(buttonSign)
                        .addComponent(textFieldUserMail, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(textFieldUserNumberSign, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSignOneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelSignTwo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSignOneLayout.setVerticalGroup(
            panelSignOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignOneLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(LabelSignTwo1)
                .addGap(25, 25, 25)
                .addComponent(textFieldUserNumberSign, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(panelSignOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordFieldSign, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEyeTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(textFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(textFieldUserSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(textFieldUserMail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(buttonSign, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        panelBack.add(panelSignOne, "card2");

        panelSignTwo.setBackground(new java.awt.Color(108, 122, 137));
        panelSignTwo.setPreferredSize(new java.awt.Dimension(350, 450));

        LabelSignTwo.setFont(new java.awt.Font("Constantia", 0, 24)); // NOI18N
        LabelSignTwo.setForeground(new java.awt.Color(255, 255, 255));
        LabelSignTwo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelSignTwo.setText("Kayıt");

        buttonSignTwo.setBackground(new java.awt.Color(108, 122, 137));
        buttonSignTwo.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonSignTwo.setText("Kayıt Ol");
        buttonSignTwo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonSignTwo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSignTwoMouseClicked(evt);
            }
        });

        comboBoxFac.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        comboBoxFac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mühendislik ve Doğa Bilimleri", "Tıp" }));
        comboBoxFac.setToolTipText("Fakültenizi Seçin");
        comboBoxFac.setBorder(null);

        comboBoxDep.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        comboBoxDep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bilgisayar Mühendisliği", "Tıp" }));
        comboBoxDep.setToolTipText("Bölümünüzü Seçin");
        comboBoxDep.setBorder(null);

        comboBoxClass.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        comboBoxClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        comboBoxClass.setToolTipText("Sınıfınızı Seçin");
        comboBoxClass.setBorder(null);

        labelWarnSign.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        labelWarnSign.setForeground(new java.awt.Color(255, 255, 255));
        labelWarnSign.setText("Hesabın mı var? Giriş yap!");
        labelWarnSign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelWarnSign.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelWarnSignMouseClicked(evt);
            }
        });

        buttonBack.setBackground(new java.awt.Color(108, 122, 137));
        buttonBack.setFont(new java.awt.Font("Constantia", 0, 16)); // NOI18N
        buttonBack.setText("Geri");
        buttonBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSignTwoLayout = new javax.swing.GroupLayout(panelSignTwo);
        panelSignTwo.setLayout(panelSignTwoLayout);
        panelSignTwoLayout.setHorizontalGroup(
            panelSignTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignTwoLayout.createSequentialGroup()
                .addGroup(panelSignTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSignTwoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LabelSignTwo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelSignTwoLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelSignTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelSignTwoLayout.createSequentialGroup()
                                .addComponent(buttonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonSignTwo))
                            .addComponent(comboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxDep, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxFac, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelSignTwoLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(labelWarnSign)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelSignTwoLayout.setVerticalGroup(
            panelSignTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSignTwoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(LabelSignTwo)
                .addGap(25, 25, 25)
                .addComponent(comboBoxFac, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(comboBoxDep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(comboBoxClass, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(panelSignTwoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSignTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBack, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(labelWarnSign, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        panelBack.add(panelSignTwo, "card2");

        javax.swing.GroupLayout panelBackSignLogLayout = new javax.swing.GroupLayout(panelBackSignLog);
        panelBackSignLog.setLayout(panelBackSignLogLayout);
        panelBackSignLogLayout.setHorizontalGroup(
            panelBackSignLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackSignLogLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        panelBackSignLogLayout.setVerticalGroup(
            panelBackSignLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackSignLogLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelBackLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelBackSignLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBackSignLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBackLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldUserNumberLogFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUserNumberLogFocusGained
        textFieldUserNumberLog.selectAll();
    }//GEN-LAST:event_textFieldUserNumberLogFocusGained

    private void passwordFieldLogFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldLogFocusGained
        passwordFieldLog.selectAll();
        passwordFieldLog.setEchoChar('*');
    }//GEN-LAST:event_passwordFieldLogFocusGained

    private void labelEyeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEyeMouseClicked
        if (passwordFieldLog.getEchoChar() != '*') {
            passwordFieldLog.setEchoChar('*');
        } else {
            passwordFieldLog.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_labelEyeMouseClicked

    private void buttonLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonLogMouseClicked
        char[] input = passwordFieldLog.getPassword();
        String password = new String(input);

        if (systemClass.checkAccuracy(textFieldUserNumberLog.getText(), password)) {
            if (CheckBoxRememberMe.isSelected()) {
                UserSave userSave = new UserSave(textFieldUserNumberLog.getText(), password);
                serializable(userSave);
            }

            JOptionPane.showMessageDialog(this, "\n\nHoşgeldiniz!\n\n",
                    "Giriş Yap", JOptionPane.INFORMATION_MESSAGE);

            frameMenu = new FrameMenu(systemClass.getUser(textFieldUserNumberLog.getText()), systemClass);
            frameMenu.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "\n\nYanlış okul numarası ya da şifre!\n\n",
                    "Giriş Yap", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_buttonLogMouseClicked

    private void labelWarnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelWarnMouseClicked
        panelLog.setVisible(false);
        panelSignOne.setVisible(true);
        labelLogo.setIcon(new ImageIcon("src/images/signUp.png"));
    }//GEN-LAST:event_labelWarnMouseClicked

    private void textFieldUserNumberSignFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUserNumberSignFocusGained
        textFieldUserNumberSign.selectAll();
    }//GEN-LAST:event_textFieldUserNumberSignFocusGained

    private void passwordFieldSignFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldSignFocusGained
        passwordFieldSign.selectAll();
        passwordFieldLog.setEchoChar('*');
    }//GEN-LAST:event_passwordFieldSignFocusGained

    private void textFieldUserNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUserNameFocusGained
        textFieldUserName.selectAll();
    }//GEN-LAST:event_textFieldUserNameFocusGained

    private void textFieldUserSurnameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUserSurnameFocusGained
        textFieldUserSurname.selectAll();
    }//GEN-LAST:event_textFieldUserSurnameFocusGained

    private void textFieldUserMailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUserMailFocusGained
        textFieldUserMail.selectAll();
    }//GEN-LAST:event_textFieldUserMailFocusGained

    private void labelEyeTwoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEyeTwoMouseClicked
        if (passwordFieldSign.getEchoChar() != '*') {
            passwordFieldSign.setEchoChar('*');
        } else {
            passwordFieldSign.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_labelEyeTwoMouseClicked

    private void buttonSignMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSignMouseClicked
        panelSignOne.setVisible(false);
        panelSignTwo.setVisible(true);
    }//GEN-LAST:event_buttonSignMouseClicked

    private void buttonSignTwoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSignTwoMouseClicked
        char[] input = passwordFieldSign.getPassword();
        String password = new String(input);

        User user = new User(comboBoxDep.getSelectedItem().toString(),
                comboBoxClass.getSelectedItem().toString(),
                comboBoxFac.getSelectedItem().toString(),
                textFieldUserNumberSign.getText(),
                password, textFieldUserName.getText(),
                textFieldUserSurname.getText(), textFieldUserMail.getText(), 1);

        switch (systemClass.addUser(user)) {
            case -1:
                JOptionPane.showMessageDialog(this, "\n\nKayıt başarısız!\n\n"
                        + "Okul numarası daha önce alınmış.\n\n",
                        "Kayıt Ol", JOptionPane.INFORMATION_MESSAGE);
                panelSignTwo.setVisible(false);
                panelSignOne.setVisible(true);
                break;
            case 0:
                JOptionPane.showMessageDialog(this, "\n\nKayıt başarısız!\n\n"
                        + "Posta adresi daha önce alınmış.\n\n",
                        "Kayıt Ol", JOptionPane.INFORMATION_MESSAGE);

                panelSignTwo.setVisible(false);
                panelSignOne.setVisible(true);
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "\n\nKayıt başarıyla alındı!\n\n",
                        "Kayıt Ol", JOptionPane.INFORMATION_MESSAGE);
                panelSignTwo.setVisible(false);
                labelLogo.setIcon(new ImageIcon("src/images/logIn.png"));
                panelLog.setVisible(true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_buttonSignTwoMouseClicked

    private void labelWarnSignMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelWarnSignMouseClicked
        panelLog.setVisible(true);
        panelSignTwo.setVisible(false);

        labelLogo.setIcon(new ImageIcon("src/images/logIn.png"));
    }//GEN-LAST:event_labelWarnSignMouseClicked
    private void labelMinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelMinMouseClicked
        setState(Frame.ICONIFIED);
    }//GEN-LAST:event_labelMinMouseClicked

    private void labelExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_labelExitMouseClicked

    private void panelTopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMousePressed
        initialClick = evt.getPoint();
        getComponentAt(initialClick);
    }//GEN-LAST:event_panelTopMousePressed

    private void panelTopMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelTopMouseDragged
        setLocation(getLocation().x + evt.getX() - initialClick.x,
                getLocation().y + evt.getY() - initialClick.y);
    }//GEN-LAST:event_panelTopMouseDragged

    private void buttonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackActionPerformed
        panelSignTwo.setVisible(false);
        panelSignOne.setVisible(true);
    }//GEN-LAST:event_buttonBackActionPerformed

    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            new FrameLogin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CheckBoxRememberMe;
    private javax.swing.JLabel LabelSignTwo;
    private javax.swing.JLabel LabelSignTwo1;
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonLog;
    private javax.swing.JButton buttonSign;
    private javax.swing.JButton buttonSignTwo;
    private javax.swing.JComboBox<String> comboBoxClass;
    private javax.swing.JComboBox<String> comboBoxDep;
    private javax.swing.JComboBox<String> comboBoxFac;
    private javax.swing.JLabel labelExit;
    private javax.swing.JLabel labelEye;
    private javax.swing.JLabel labelEyeTwo;
    private javax.swing.JLabel labelLoading;
    private javax.swing.JLabel labelLoadingText;
    private javax.swing.JLabel labelLog;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel labelMin;
    private javax.swing.JLabel labelWarn;
    private javax.swing.JLabel labelWarnSign;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelBackLogo;
    private javax.swing.JPanel panelBackSignLog;
    private javax.swing.JPanel panelLoading;
    private javax.swing.JPanel panelLog;
    private javax.swing.JPanel panelSignOne;
    private javax.swing.JPanel panelSignTwo;
    private javax.swing.JPanel panelTop;
    private javax.swing.JPasswordField passwordFieldLog;
    private javax.swing.JPasswordField passwordFieldSign;
    private javax.swing.JTextField textFieldUserMail;
    private javax.swing.JTextField textFieldUserName;
    public javax.swing.JTextField textFieldUserNumberLog;
    private javax.swing.JTextField textFieldUserNumberSign;
    private javax.swing.JTextField textFieldUserSurname;
    // End of variables declaration//GEN-END:variables
}
