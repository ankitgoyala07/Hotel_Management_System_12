package controller;

import dao.UserDao;
import model.User;
import view.SignupPage;
import view.loginpage;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Controller class for coordinating the SignupPage view with the User model and UserDao.
 */
public class UserController {
    private final SignupPage view;
    private final UserDao dao;

    public UserController(SignupPage view) {
        this.view = view;
        this.dao = new UserDao();
        initController();
    }

    private void initController() {
        // Register Action Listener on the Signup Button
        view.getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignup();
            }
        });

        // Populate Roles in ComboBox
        setupComboBox();

        // Register Focus Listeners for Placeholder Text behavior
        addPlaceholderBehavior();
    }

    private void setupComboBox() {
        view.getRoleComboBox().removeAllItems();
        view.getRoleComboBox().addItem("Select your role");
        view.getRoleComboBox().addItem("Manager");
        view.getRoleComboBox().addItem("Frontdesk Staff");
        view.getRoleComboBox().addItem("Guest");
        view.getRoleComboBox().setSelectedIndex(0);
    }

    private void addPlaceholderBehavior() {
        setupPlaceholder(view.getFullNameField(), "Enter your full name ");
        setupPlaceholder(view.getEmailField(), "Enter your email address");
        setupPlaceholder(view.getPhoneField(), "Enter your phone number");
        setupPlaceholder(view.getPasswordField(), "Create a password");
    }

    private void setupPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().trim().equals(placeholder.trim())) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void setupPlaceholder(javax.swing.JTextArea textArea, String placeholder) {
        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textArea.getText().trim().equals(placeholder.trim())) {
                    textArea.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textArea.getText().trim().isEmpty()) {
                    textArea.setText(placeholder);
                }
            }
        });
    }

    private void handleSignup() {
        String fullName = view.getFullNameField().getText().trim();
        String email = view.getEmailField().getText().trim();
        String phone = view.getPhoneField().getText().trim();
        String password = view.getPasswordField().getText().trim();
        String role = (String) view.getRoleComboBox().getSelectedItem();

        // 1. Validation for Empty / Placeholder Values
        if (fullName.isEmpty() || fullName.equalsIgnoreCase("Enter your full name")) {
            JOptionPane.showMessageDialog(view, "Please enter your full name.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (email.isEmpty() || email.equalsIgnoreCase("Enter your email address")) {
            JOptionPane.showMessageDialog(view, "Please enter your email address.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(view, "Please enter a valid email address.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (phone.isEmpty() || phone.equalsIgnoreCase("Enter your phone number")) {
            JOptionPane.showMessageDialog(view, "Please enter your phone number.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (password.isEmpty() || password.equalsIgnoreCase("Create a password")) {
            JOptionPane.showMessageDialog(view, "Please enter a password.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (role == null || role.equals("Select your role") || role.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please select a valid role.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Email duplicate check
        if (dao.isEmailExists(email)) {
            JOptionPane.showMessageDialog(view, "This email is already registered. Please use another one or log in.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Create Model
        User user = new User(fullName, email, phone, password, role);

        // 4. Save to Database using DAO
        boolean success = dao.saveUser(user);
        if (success) {
            JOptionPane.showMessageDialog(view, "Signup successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Navigate to Login Page and dispose the Signup Page
            java.awt.EventQueue.invokeLater(() -> {
                new loginpage().setVisible(true);
            });
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Signup failed. Please try again later or check your database connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
