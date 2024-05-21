import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginMenu frame = new LoginMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LoginMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 70, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder (5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground (Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font. PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        contentPane.add(passwordField);
        
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground (Color.BLACK);
        lblUsername.setForeground (Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground (Color.BLACK);
        lblPassword.setBackground (Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        contentPane.add(lblPassword);

        btnNewButton = new JButton("Login");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds (481, 392, 162, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed (ActionEvent e) {
                String userName = textField.getText();
                char[] password = passwordField.getPassword();
                String passwordString = String.valueOf(password);
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/dbmahasiswa", "root", "");
                    
                    PreparedStatement st = (PreparedStatement) connection.prepareStatement("select nama, password from user where nama=? and password=?");
                    
                    st.setString(1, userName);
                    st.setString(2, passwordString);
                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        String name = rs.getString("nama");
                        JOptionPane.showMessageDialog(btnNewButton, "Login sukses!");
                        dispose();
                        EventQueue.invokeLater(() -> {
                            try {
                                WelcomeFrame welcomeFrame = new WelcomeFrame(name);
                                welcomeFrame.setVisible(true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Login gagal. Username atau Password salah");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        
        contentPane.add(btnNewButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    }
}

class WelcomeFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public WelcomeFrame(String name) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 70, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Selamat Datang, " + name);
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblWelcome.setBounds(100, 50, 250, 50);
        contentPane.add(lblWelcome);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnLogout.setBounds(150, 150, 150, 72);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                EventQueue.invokeLater(() -> {
                    try {
                        Login loginFrame = new Login();
                        loginFrame.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });

        contentPane.add(btnLogout);
    }
}