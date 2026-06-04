package controller;

import dao.AdminDashboardDAO;
import dao.AdminDashboardDAOImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.AdminDashboardModel;
import view.admindashboard;
import view.loginpage;
import view.roommanagement;

/**
 * Controller class to handle interactions between the admindashboard view 
 * and the AdminDashboardDAO.
 */
public class AdminDashboardController {
    private final admindashboard view;
    private final AdminDashboardDAO dao;

    /**
     * Constructor initializing the controller with the given view.
     * 
     * @param view the admindashboard JFrame view
     */
    public AdminDashboardController(admindashboard view) {
        this.view = view;
        this.dao = new AdminDashboardDAOImpl();
        initController();
    }

    /**
     * Constructor initializing the controller with a view and custom DAO.
     * 
     * @param view the admindashboard JFrame view
     * @param dao the AdminDashboardDAO implementation
     */
    public AdminDashboardController(admindashboard view, AdminDashboardDAO dao) {
        this.view = view;
        this.dao = dao;
        initController();
    }

    /**
     * Binds action listeners to the view buttons.
     */
    private void initController() {
        // Register refresh dashboard statistics
        this.view.getBtnDashboard().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDashboardData();
            }
        });

        // Register navigation action listeners
        this.view.getBtnGuests().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToRoomManagement("Guests");
            }
        });

        this.view.getBtnBookings().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToRoomManagement("Bookings");
            }
        });

        this.view.getBtnMealTime().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUnderDevelopmentMessage("Meal Time");
            }
        });

        this.view.getBtnBillings().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUnderDevelopmentMessage("Billings");
            }
        });

        // Register logout action
        this.view.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });
    }

    /**
     * Fetches dashboard statistics from the DAO and updates the view.
     */
    public void loadDashboardData() {
        try {
            AdminDashboardModel stats = dao.getDashboardStats();
            view.setDashboardStats(stats);
        } catch (Exception ex) {
            System.err.println("Error loading dashboard data in Controller: " + ex.getMessage());
            JOptionPane.showMessageDialog(view, "Failed to load dashboard data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Disposes current dashboard and switches to Room Management frame.
     */
    private void navigateToRoomManagement(String sectionName) {
        view.dispose();
        roommanagement roomView = new roommanagement();
        roomView.setVisible(true);
        System.out.println("Navigated to Room Management (" + sectionName + ")");
    }

    /**
     * Shows a warning dialog indicating features that are currently unimplemented.
     */
    private void showUnderDevelopmentMessage(String featureName) {
        JOptionPane.showMessageDialog(
            view, 
            featureName + " module is currently under development.", 
            "Module Under Development", 
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Logs out the user, disposes the dashboard, and displays the login page.
     */
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
            view, 
            "Are you sure you want to logout?", 
            "Logout Confirmation", 
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            view.dispose();
            loginpage loginFrame = new loginpage();
            loginFrame.setVisible(true);
            System.out.println("User logged out successfully.");
        }
    }
}
