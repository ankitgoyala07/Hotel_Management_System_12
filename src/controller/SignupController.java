package controller;

import dao.signupDao;
import model.signupModel;
import view.Signup;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;

/**
 * Controller class to handle business logic, validations, and events for the Signup UI.
 * Uses Java reflection to access GUI components safely, keeping view code decoupled and unmodified.
 *
 * @author i3
 */
public class SignupController {
    private final Signup view;
    private final signupDao dao = new signupDao();

    // UI text components retrieved via reflection
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSecurityAnswer;
    private javax.swing.JComboBox<String> comboRole;

    public SignupController() {
        this.view = new Signup();
        initFields();
        initController();
    }

    /**
     * Safely retrieves the private Swing components from the view via Java reflection.
     */
    private void initFields() {
        try {
            txtUsername = getTextField("jTextField8");
            txtEmail = getTextField("jTextField7");
            txtPhone = getTextField("jTextField9");
            txtPassword = getTextField("jTextField10"); // actual Password field
            txtSecurityAnswer = getTextField("jTextField6"); // Security Answer field
            comboRole = getComboBoxField("jComboBox3"); // Role dropdown
        } catch (Exception e) {
            System.err.println("Error accessing private components in Signup view: " + e.getMessage());
        }
    }

    private javax.swing.JTextField getTextField(String name) throws Exception {
        Field field = view.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (javax.swing.JTextField) field.get(view);
    }

    @SuppressWarnings("unchecked")
    private javax.swing.JComboBox<String> getComboBoxField(String name) throws Exception {
        Field field = view.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (javax.swing.JComboBox<String>) field.get(view);
    }

    /**
     * Sets up placeholders, event listeners, and default operations for the Signup screen.
     */
    private void initController() {
        // Set placeholders for clean aesthetics
        setupPlaceholders();

        // Register action listener on the Sign up button
        view.getBtnSignup().addActionListener(e -> handleSignup());

        // Graceful navigation back to login screen when the window is closed
        view.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                new LoginController();
            }
        });

        view.setVisible(true);
    }

    /**
     * Initializes fields with gray placeholder text and registers focus behavior.
     */
    private void setupPlaceholders() {
        if (txtUsername != null) addPlaceholder(txtUsername, "Enter your username");
        if (txtEmail != null) addPlaceholder(txtEmail, "Enter your email");
        if (txtPhone != null) addPlaceholder(txtPhone, "Enter your phone number");
        if (txtPassword != null) addPlaceholder(txtPassword, "Enter your password");
        if (txtSecurityAnswer != null) addPlaceholder(txtSecurityAnswer, "Enter your primary school name");
    }

    /**
     * Adds dynamic focus listener behavior to a text field for standard placeholders.
     */
    private void addPlaceholder(javax.swing.JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(new Color(153, 153, 153));

        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(153, 153, 153));
                }
            }
        });
    }

    /**
     * Returns the trimmed value of a field, or an empty string if it contains only placeholder text.
     */
    private String getCleanInput(javax.swing.JTextField field, String placeholder) {
        if (field == null) return "";
        String val = field.getText().trim();
        return val.equals(placeholder) ? "" : val;
    }

    /**
     * Displays a warning message dialog.
     */
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(view, message, "Validation Warning", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Handles the validation logic and creates the new user record in the database.
     */
    private void handleSignup() {
        String username = getCleanInput(txtUsername, "Enter your username");
        String email = getCleanInput(txtEmail, "Enter your email");
        String phone = getCleanInput(txtPhone, "Enter your phone number");
        String password = getCleanInput(txtPassword, "Enter your password");
        String securityAnswer = getCleanInput(txtSecurityAnswer, "Enter your primary school name");

        String role = "";
        if (comboRole != null && comboRole.getSelectedItem() != null) {
            String selected = comboRole.getSelectedItem().toString();
            if (!selected.equals("Select your role")) {
                role = selected;
            }
        }

        // 1. Non-empty Validations
        if (username.isEmpty()) {
            showWarning("Username is required.");
            if (txtUsername != null) txtUsername.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            showWarning("Email address is required.");
            if (txtEmail != null) txtEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            showWarning("Phone number is required.");
            if (txtPhone != null) txtPhone.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showWarning("Password is required.");
            if (txtPassword != null) txtPassword.requestFocus();
            return;
        }
        if (role.isEmpty()) {
            showWarning("Please select a valid role.");
            if (comboRole != null) comboRole.requestFocus();
            return;
        }
        if (securityAnswer.isEmpty()) {
            showWarning("Security answer (primary school name) is required.");
            if (txtSecurityAnswer != null) txtSecurityAnswer.requestFocus();
            return;
        }

        // 2. Email format validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!email.matches(emailRegex)) {
            showWarning("Please enter a valid email address.");
            if (txtEmail != null) txtEmail.requestFocus();
            return;
        }

        // 3. Phone format validation (strictly 10 digits)
        if (!phone.matches("\\d{10}")) {
            showWarning("Phone number must be exactly 10 digits.");
            if (txtPhone != null) txtPhone.requestFocus();
            return;
        }

        // 4. Password length validation (at least 4 characters)
        if (password.length() < 4) {
            showWarning("Password must be at least 4 characters long.");
            if (txtPassword != null) txtPassword.requestFocus();
            return;
        }

        // 5. Unique checks in the database
        if (dao.usernameExists(username)) {
            showWarning("Username already exists. Please select a different username.");
            if (txtUsername != null) txtUsername.requestFocus();
            return;
        }
        if (dao.emailExists(email)) {
            showWarning("Email address already registered. Please use a different email.");
            if (txtEmail != null) txtEmail.requestFocus();
            return;
        }

        // Save new user using DAO
        signupModel user = new signupModel(username, email, phone, password, role, securityAnswer);
        boolean success = dao.createUser(user);

        if (success) {
            JOptionPane.showMessageDialog(view,
                "Registration successful! Welcome, " + username + ".",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Navigate back to login screen
            new LoginController();
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view,
                "Registration failed. Please try again.",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
