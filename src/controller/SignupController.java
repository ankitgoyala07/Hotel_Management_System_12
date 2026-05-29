package controller;

import dao.userDao;
import model.userModel;
import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * Controller class to handle sign up actions and business logic.
 * 
 * @author i3
 */
public class SignupController {
    private final userDao dao = new userDao();

    /**
     * Processes sign up request.
     *
     * @param parent component reference for displaying dialoge
     * @param username user input username
     * @param email user input email
     * @param phone user input phone
     * @param password user input password
     * @param role user selected role
     * @return true if sign up succeeded, false otherwise
     */
    public boolean handleSignup(Component parent, String username, String email, String phone, String password, String role) {
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || role.equals("Select your role") || role.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Please fill in all fields and select a role.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if username already exists
        if (dao.usernameExists(username)) {
            JOptionPane.showMessageDialog(parent,
                "Username already exists. Please choose a different one.",
                "Signup Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if email already exists
        if (dao.emailExists(email)) {
            JOptionPane.showMessageDialog(parent,
                "Email already registered. Please use a different email or log in.",
                "Signup Failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Create the user model
        userModel user = new userModel(username, email, phone, password, role);
        boolean success = dao.createUser(user);

        if (success) {
            JOptionPane.showMessageDialog(parent,
                "Account created successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(parent,
                "Database error. Failed to create account.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}