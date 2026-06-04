package dao;

import model.AdminDashboardModel;

/**
 * Interface defining the Data Access Object (DAO) for the Admin Dashboard.
 */
public interface AdminDashboardDAO {
    /**
     * Retrieves the statistics for the dashboard.
     * 
     * @return AdminDashboardModel containing all dashboard statistics.
     */
    AdminDashboardModel getDashboardStats();
}
