package controller;

import view.gest_dashbord;

/**
 * Class representing a Guest role.
 */
public class GuestRole implements UserRole {
    @Override
    public void openDashboard() {
        new GuestDashboardController(new gest_dashbord());
    }
}
