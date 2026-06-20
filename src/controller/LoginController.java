package controller;

import dao.loginDao;
import model.loginModel;
import view.login;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Controller class to handle all logic and actions for the login process.
 *
 * @author i3
 */
public class LoginController {
    public static String loggedInUsername = null;
    private final login view;
    private final loginDao dao = new loginDao();

    public LoginController() {
        this.view = new login();
        initController();
    }

    private void initController() {
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

        // Create loginModel and validate with DAO
        loginModel credentials = new loginModel(username, password);
        boolean success = dao.validateUser(credentials);

        if (success) {
            loggedInUsername = username;
            String role = credentials.getRole();
            if (role != null) {
                role = role.trim();
                if (role.equalsIgnoreCase("Manager") || role.equalsIgnoreCase("Admin")) {
                    new admindashboardController();
                } else if (role.equalsIgnoreCase("Frontdesk Staff") || role.equalsIgnoreCase("Frontdesk")) {
                    new FrontdeskDeshboardControler();
                } else if (role.equalsIgnoreCase("Guest")) {
                    new GuestDashboardController(new view.gest_dashbord());
                } else {
                    new FrontdeskDeshboardControler();
                }
            } else {
                new FrontdeskDeshboardControler();
            }
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
