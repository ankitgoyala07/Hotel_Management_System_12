package controller;

/**
 * Class representing an Administrator/Manager role.
 */
public class AdminRole implements UserRole {
    @Override
    public void openDashboard() {
        new admindashboardController();
    }
}
