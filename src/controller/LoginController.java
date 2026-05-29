package controller;

import dao.userDao;
import model.userModel;
import javax.swing.JOptionPane;
import java.awt.Component;

public class LoginController {
    private final userDao dao = new userDao();

    public userModel handleLogin(Component parent, String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Please enter both username and password.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        userModel user = dao.authenticateUser(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(parent,
                "Invalid username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JOptionPane.showMessageDialog(parent,
            "Welcome, " + user.getName() + "!",
            "Login Successful", JOptionPane.INFORMATION_MESSAGE);

        String role = user.getRole();
        switch (role) {
            case "Manager":
                JOptionPane.showMessageDialog(parent, "Opening Manager Dashboard...");
                // new ManagerDashboard(user).setVisible(true);
                break;
            case "Frontdesk Staff":
                JOptionPane.showMessageDialog(parent, "Opening Frontdesk Staff Dashboard...");
                // new FrontdeskDashboard(user).setVisible(true);
                break;
            case "Guest":
                JOptionPane.showMessageDialog(parent, "Opening Guest Dashboard...");
                new view.gest_dashbord().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(parent, "Unknown role: " + role,
                    "Error", JOptionPane.ERROR_MESSAGE);
                return null;
        }

        return user;
    }
}