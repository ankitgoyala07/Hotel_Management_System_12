package controller;

import view.BookRoom;
import view.gest_dashbord;
import view.OrderFood;
import view.Feedback;
import view.Gust_Details;
import model.BookRoomModel;
import dao.BookRoomDao;
import database.MySqlConnection;
import java.sql.Connection;
import java.util.List;

/**
 * Controller class for Room Browsing and Booking.
 * Follows clean MVC architecture.
 */
public class BookRoomController {
    private BookRoom view;
    private BookRoomDao dao;

    public BookRoomController(BookRoom view) {
        this.view = view;
        initDatabase();
        loadRoomInfo();
        bindListeners();
    }

    private void initDatabase() {
        try {
            MySqlConnection mysql = new MySqlConnection();
            Connection conn = mysql.Openconnection();
            if (conn != null) {
                this.dao = new BookRoomDao(conn);
                this.dao.createTableIfNotExists();
            } else {
                System.out.println("Warning: Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Exception initializing database in controller: " + e.getMessage());
        }
    }

    private void loadRoomInfo() {
        if (dao == null) return;
        try {
            List<BookRoomModel> rooms = dao.getAllRooms();
            if (rooms.isEmpty()) {
                // Seed default rooms if table is empty
                dao.insertRoom(new BookRoomModel(
                    "VIP", 
                    850.0, 
                    "85m²", 
                    "King Size", 
                    "Our pinnacle of luxury featuring a private lounge, marble bathroom with a soaking tub, and priority concierge access."
                ));
                dao.insertRoom(new BookRoomModel(
                    "Double Bed Room", 
                    420.0, 
                    "45m²", 
                    "2 Doubles", 
                    "Perfect for families or business associates, offering spacious layouts and a fully equipped ergonomic workspace."
                ));
                dao.insertRoom(new BookRoomModel(
                    "Single Bed Room", 
                    295.0, 
                    "30m²", 
                    "1 Queen", 
                    "An efficient and cozy space designed for the modern solo traveler, prioritizing comfort and connectivity."
                ));
                rooms = dao.getAllRooms();
            }

            // Populate room details onto view labels dynamically
            for (BookRoomModel room : rooms) {
                String type = room.getRoomType();
                if ("VIP".equalsIgnoreCase(type)) {
                    view.getLblVipPrice().setText("$" + (int)room.getPrice());
                    view.getLblVipSize().setText("  " + room.getRoomSize());
                    view.getLblVipBed().setText("  " + room.getBedType());
                    view.getTxtVipDesc().setText(room.getDescription());
                } else if ("Double Bed Room".equalsIgnoreCase(type)) {
                    view.getLblDoublePrice().setText("$" + (int)room.getPrice());
                    view.getLblDoubleSize().setText("  " + room.getRoomSize());
                    view.getLblDoubleBed().setText("  " + room.getBedType());
                    view.getTxtDoubleDesc().setText(room.getDescription());
                } else if ("Single Bed Room".equalsIgnoreCase(type) || "Single Bed Room ".equalsIgnoreCase(type)) {
                    view.getLblSinglePrice().setText("$" + (int)room.getPrice());
                    view.getLblSingleSize().setText("  " + room.getRoomSize());
                    view.getLblSingleBed().setText("  " + room.getBedType());
                    view.getTxtSingleDesc().setText(room.getDescription());
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading room info onto view: " + e.getMessage());
        }
    }

    private void bindListeners() {
        // Wire up buttons with action listeners (delegated from view getters)
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnOrderFood().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> openFeedback());
        view.getBtnLogout().addActionListener(e -> logout());
        
        // Wire up Book Now buttons
        view.getBtnBookVip().addActionListener(e -> openGuestDetails("VIP"));
        view.getBtnBookDouble().addActionListener(e -> openGuestDetails("Double Bed Room"));
        view.getBtnBookSingle().addActionListener(e -> openGuestDetails("Single Bed Room "));
    }

    private void openDashboard() {
        new GuestDashboardController(new gest_dashbord());
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

    private void logout() {
        new LoginController();
        view.dispose();
    }

    private void openGuestDetails(String roomType) {
        Gust_Details details = new Gust_Details();
        details.setRoomType(roomType);
        new GuestDetailsController(details);
        details.setVisible(true);
        view.dispose();
    }
}
