package controller;

/**
 * Controller class to handle business logic for Admin Dashboard view and query DAO layers
 */

import dao.admindashboardDAO;
import model.admindashboardModel;

public class admindashboardController {
    
    private final admindashboardDAO dashboardDAO;

    public admindashboardController() {
        this.dashboardDAO = new admindashboardDAO();
    }

    public admindashboardModel getDashboardData() {
        return dashboardDAO.getDashboardData();
    }
}
