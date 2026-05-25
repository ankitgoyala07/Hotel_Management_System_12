package controller;

import dao.userDao;
import model.userModel;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Controller class to handle login actions and business logic.
 *
 * @author i3
 */
public class LoginController {
    private final userDao dao;

    public LoginController() {
        this.dao = new userDao();
    }

    /**
     * Processes login authentication.
     *
     * @param parent component reference for displaying dialogs
     * @param username user input username
     * @param password user input password
     * @return true if authentication succeeded, false otherwise
     */
    public boolean handleLogin(Component parent, String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Please enter both username and password.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        userModel user = dao.authenticateUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(parent, "Login successful! Welcome, " + user.getName() + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(parent, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
