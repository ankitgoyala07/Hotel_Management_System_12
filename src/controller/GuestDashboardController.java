package controller;

import view.gest_dashbord;
import view.BookRoom;
import view.OrderFood;
import view.Feedback;
import view.Roomservice;
import model.GuestDashboardModel;
import dao.GuestDashboardDao;
import database.MySqlConnection;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class GuestDashboardController {
    private gest_dashbord view;
    private GuestDashboardDao dao;

    public GuestDashboardController(gest_dashbord view) {
        this.view = view;
        initDatabase();
        loadStayInfo();
        bindListeners();
        this.view.setVisible(true);
    }

    private void initDatabase() {
        try {
            MySqlConnection mysql = new MySqlConnection();
            Connection conn = mysql.Openconnection();
            if (conn != null) {
                this.dao = new GuestDashboardDao(conn);
                // Rule 3: Note: create a table in database if the table is not present.
                this.dao.createTableIfNotExists();
            } else {
                System.out.println("Warning: Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Exception initializing database in controller: " + e.getMessage());
        }
    }

    private void loadStayInfo() {
        if (dao == null) return;
        try {
            List<GuestDashboardModel> guests = dao.getAllGuests();
            GuestDashboardModel currentGuest;
            if (guests.isEmpty()) {
                // Seed a default mock guest if the table is empty to show correct info
                currentGuest = new GuestDashboardModel(
                    "Alexander", 
                    "alexander@hms.com", 
                    "VIP", 
                    Date.valueOf("2026-06-11"), 
                    Date.valueOf("2026-06-15"), 
                    2, 
                    3000.0
                );
                dao.insertGuest(currentGuest);
            } else {
                currentGuest = guests.get(0);
            }

            // Populate view labels dynamically from database model
            view.getLblWelcome().setText("Welcome, " + currentGuest.getName() + " 👋");
            view.getLblRoomType().setText("  " + currentGuest.getRoomType());
            view.getLblCheckIn().setText(currentGuest.getCheckIn().toString());
            view.getLblCheckOut().setText(currentGuest.getCheckOut().toString());
            view.getLblGuests().setText(String.valueOf(currentGuest.getGuestsCount()));
            view.getLblExpenses().setText("$ " + (int)currentGuest.getExpenses());
        } catch (Exception e) {
            System.out.println("Error loading stay info onto view: " + e.getMessage());
        }
    }

    private void bindListeners() {
        // Wire up buttons with lambda action listeners (Rule 4 & 5)
        view.getBtnBookRoom().addActionListener(e -> openBookRoom());
        view.getBtnOrderFoodSidebar().addActionListener(e -> openOrderFood());
        view.getBtnOrderFoodCard().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnRoomService().addActionListener(e -> openRoomService());
        view.getBtnLogout().addActionListener(e -> logout());
        view.getBtnDashboard().addActionListener(e -> refreshDashboard());
    }

    private void openBookRoom() {
        BookRoom roomView = new BookRoom();
        new BookRoomController(roomView);
        roomView.setVisible(true);
        view.dispose();
    }

    private void openOrderFood() {
        new OrderFood().setVisible(true);
        view.dispose();
    }

    private void openFeedback() {
        Feedback fbView = new Feedback();
        new FeedbackController(fbView);
        fbView.setVisible(true);
        view.dispose();
    }

    private void openRoomService() {
        new Roomservice().setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
        view.dispose();
    }

    private void refreshDashboard() {
        gest_dashbord newView = new gest_dashbord();
        newView.setVisible(true);
        view.dispose();
    }
}
// git push