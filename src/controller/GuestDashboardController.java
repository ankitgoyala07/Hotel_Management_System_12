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
            String username = LoginController.loggedInUsername;
            GuestDashboardModel currentGuest = null;
            
            if (username != null) {
                String[] details = dao.getUserDetails(username);
                if (details != null) {
                    String email = details[0];
                    String phone = details[1];
                    currentGuest = dao.getActiveBooking(email, phone);
                }
            }

            if (currentGuest != null) {
                // Populate view labels dynamically from database model
                view.getLblWelcome().setText("Welcome, " + currentGuest.getName() + " 👋");
                
                String[] parts = currentGuest.getRoomType().split(";");
                String roomType = parts[0];
                String roomNo = parts.length > 1 ? parts[1] : "00";
                
                view.getLblRoomType().setText("  " + roomType);
                view.getLblRoomNoSub().setText("Room " + roomNo);
                
                view.getLblCheckIn().setText(currentGuest.getCheckIn().toString());
                view.getLblCheckOut().setText(currentGuest.getCheckOut().toString());
                view.getLblExpenses().setText("$ " + (int)currentGuest.getExpenses());
            } else {
                // Display 00 initially before booking the room
                String welcomeName = (username != null) ? username : "Guest";
                view.getLblWelcome().setText("Welcome, " + welcomeName + " 👋");
                view.getLblRoomType().setText("  00");
                view.getLblRoomNoSub().setText("Room 00");
                view.getLblCheckIn().setText("00");
                view.getLblCheckOut().setText("00");
                view.getLblExpenses().setText("$ 00");
            }
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
        Roomservice roomServiceView = new Roomservice();
        new RoomServiceController(roomServiceView);
        roomServiceView.setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
        view.dispose();
    }

    private void refreshDashboard() {
        new GuestDashboardController(new gest_dashbord());
        view.dispose();
    }
}
// git push