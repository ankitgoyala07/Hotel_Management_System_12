package controller;

import dao.loginDao;
import model.loginModel;
import javax.swing.JOptionPane;
import java.awt.Component;

public class LoginController {
    private final loginDao dao = new loginDao();

    public loginModel handleLogin(Component parent, String username, String password) {

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                "Please enter both username and password.",
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        loginModel user = dao.authenticateUser(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(parent,
                "Invalid username or password.",
                "Login Failed", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JOptionPane.showMessageDialog(parent,
            "Welcome, " + user.getUsername() + "!",
            "Login Successful", JOptionPane.INFORMATION_MESSAGE);

        new view.SystemSetting().setVisible(true);

        return user;
    }
}