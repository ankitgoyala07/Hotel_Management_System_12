package controller;

/**
 * Class representing a Frontdesk Staff role.
 */
public class FrontdeskRole implements UserRole {
    @Override
    public void openDashboard() {
        new FrontdeskDeshboardControler();
    }
}
