package controller;

import model.loginModel;
import view.login;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller class to handle all logic and actions for the login process.
 *
 * @author i3
 */
public class LoginController extends BaseController<login> {
    public static String loggedInUsername = null;

    public static boolean hasBookedRoom() {
        if (loggedInUsername == null) return false;
        
        MySqlConnection mysql = new MySqlConnection();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            return false;
        }
        
        String userSql = "SELECT email, phone FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(userSql)) {
            ps.setString(1, loggedInUsername);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    
                    if (email != null || phone != null) {
                        String guestSql = "SELECT COUNT(*) FROM guest_details WHERE (email_address = ? OR phone_number = ?) LIMIT 1";
                        try (PreparedStatement ps2 = conn.prepareStatement(guestSql)) {
                            ps2.setString(1, email);
                            ps2.setString(2, phone);
                            try (ResultSet rs2 = ps2.executeQuery()) {
                                if (rs2.next()) {
                                    return rs2.getInt(1) > 0;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking if guest has booked room: " + e.getMessage());
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

    private final AuthService authService;

    public LoginController() {
        super(new login());
        this.authService = new DatabaseAuthService();
        initController();
    }

    @Override
    protected void initController() {
        // Set placeholder colors and initial text focus behavior
        setupPlaceholders();

        // Add action listener to sign in button
        view.getBtnSignin().addActionListener(e -> handleLogin());

        // Add mouse listener to Sign up label
        view.getLblSignup().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new SignupController();
                view.dispose();
            }
        });

        // Add mouse listener to Forget password label
        view.getLblForgetPassword().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(view,
                    "Please contact your administrator to reset your password.",
                    "Forget Password",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        view.setVisible(true);
    }

    private void setupPlaceholders() {
        // Username Placeholder focus listener
        view.getUsernameField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (view.getUsernameField().getText().equals("Enter your username")) {
                    view.getUsernameField().setText("");
                    view.getUsernameField().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (view.getUsernameField().getText().trim().isEmpty()) {
                    view.getUsernameField().setText("Enter your username");
                    view.getUsernameField().setForeground(new Color(153, 153, 153));
                }
            }
        });

        // Password Placeholder focus listener
        view.getPasswordField().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String password = new String(view.getPasswordField().getPassword());
                if (password.equals("Enter your password")) {
                    view.getPasswordField().setText("");
                    view.getPasswordField().setEchoChar('•');
                    view.getPasswordField().setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(view.getPasswordField().getPassword());
                if (password.trim().isEmpty()) {
                    view.getPasswordField().setText("Enter your password");
                    view.getPasswordField().setEchoChar((char) 0);
                    view.getPasswordField().setForeground(new Color(153, 153, 153));
                }
            }
        });
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());

        if (username.isEmpty() || username.equals("Enter your username") ||
            password.isEmpty() || password.equals("Enter your password")) {
            JOptionPane.showMessageDialog(view,
                "Please enter both username and password.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create loginModel and validate with AuthService
        loginModel credentials = new loginModel(username, password);
        boolean success = authService.login(credentials);

        if (success) {
            loggedInUsername = username;
            UserRole userRole = RoleFactory.getRole(credentials.getRole());
            userRole.openDashboard();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
