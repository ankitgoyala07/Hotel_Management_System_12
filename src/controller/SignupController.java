package controller;

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
public class SignupController extends BaseController<Signup> {
    private final AuthService authService;

    // UI text components retrieved via reflection
    private javax.swing.JTextField txtUsername;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSecurityAnswer;
    private javax.swing.JComboBox<String> comboRole;

    public SignupController() {
        super(new Signup());
        this.authService = new DatabaseAuthService();
        initFields();
        initController();
    }

    private void initFields() {
        txtUsername = view.getUsernameField();
        txtEmail = view.getEmailField();
        txtPhone = view.getPhoneField();
        txtPassword = view.getPasswordField();
        txtSecurityAnswer = view.getSecurityAnswerField();
        comboRole = view.getComboRole();
    }

    @Override
    protected void initController() {
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

        // 1. Username Validation
        try {
            new RequiredFieldValidator("Username").validate(username);
        } catch (ValidationException ex) {
            showWarning(ex.getMessage());
            if (txtUsername != null) txtUsername.requestFocus();
            return;
        }

        // 2. Email Validation
        try {
            new EmailValidator().validate(email);
        } catch (ValidationException ex) {
            showWarning(ex.getMessage());
            if (txtEmail != null) txtEmail.requestFocus();
            return;
        }

        // 3. Phone Validation
        try {
            new PhoneValidator().validate(phone);
        } catch (ValidationException ex) {
            showWarning(ex.getMessage());
            if (txtPhone != null) txtPhone.requestFocus();
            return;
        }

        // 4. Password Validation
        try {
            new PasswordValidator().validate(password);
        } catch (ValidationException ex) {
            showWarning(ex.getMessage());
            if (txtPassword != null) txtPassword.requestFocus();
            return;
        }

        // 5. Role Validation
        try {
            new RequiredFieldValidator("Role").validate(role);
        } catch (ValidationException ex) {
            showWarning("Please select a valid role.");
            if (comboRole != null) comboRole.requestFocus();
            return;
        }

        // 6. Security Answer Validation
        try {
            new RequiredFieldValidator("Security answer (primary school name)").validate(securityAnswer);
        } catch (ValidationException ex) {
            showWarning(ex.getMessage());
            if (txtSecurityAnswer != null) txtSecurityAnswer.requestFocus();
            return;
        }

        // 5. Unique checks in the database
        if (authService.usernameExists(username)) {
            showWarning("Username already exists. Please select a different username.");
            if (txtUsername != null) txtUsername.requestFocus();
            return;
        }
        if (authService.emailExists(email)) {
            showWarning("Email address already registered. Please use a different email.");
            if (txtEmail != null) txtEmail.requestFocus();
            return;
        }

        // Save new user using AuthService
        signupModel user = new signupModel(username, email, phone, password, role, securityAnswer);
        boolean success = authService.register(user);

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
