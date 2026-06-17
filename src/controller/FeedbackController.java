package controller;

import view.Feedback;
import view.gest_dashbord;
import view.BookRoom;
import view.OrderFood;
import model.FeedbackModel;
import dao.FeedbackDao;
import database.MySqlConnection;
import javax.swing.JOptionPane;
import java.sql.Connection;

/**
 * Controller class for Feedback page.
 * Follows clean MVC architecture.
 */
public class FeedbackController {
    private Feedback view;
    private FeedbackDao dao;

    public FeedbackController(Feedback view) {
        this.view = view;
        initDatabase();
        bindListeners();
    }

    private void initDatabase() {
        try {
            MySqlConnection mysql = new MySqlConnection();
            Connection conn = mysql.Openconnection();
            if (conn != null) {
                this.dao = new FeedbackDao(conn);
                this.dao.createTableIfNotExists();
            } else {
                System.out.println("Warning: Database connection could not be established.");
            }
        } catch (Exception e) {
            System.out.println("Exception initializing database in controller: " + e.getMessage());
        }
    }

    private void bindListeners() {
        // Sidebar navigation links
        view.getBtnDashboard().addActionListener(e -> openDashboard());
        view.getBtnRoomBrowsing().addActionListener(e -> openRoomBrowsing());
        view.getBtnOrderFood().addActionListener(e -> openOrderFood());
        view.getBtnFeedback().addActionListener(e -> refreshFeedback());
        view.getBtnLogout().addActionListener(e -> logout());

        // Submit Review
        view.getBtnSubmitReview().addActionListener(e -> handleSubmitReview());
    }

    private void openDashboard() {
        new gest_dashbord().setVisible(true);
        view.dispose();
    }

    private void openRoomBrowsing() {
        new BookRoom().setVisible(true);
        view.dispose();
    }

    private void openOrderFood() {
        new OrderFood().setVisible(true);
        view.dispose();
    }

    private void refreshFeedback() {
        new Feedback().setVisible(true);
        view.dispose();
    }

    private void logout() {
        new LoginController();
        view.dispose();
    }

    private void handleSubmitReview() {
        if (dao == null) {
            JOptionPane.showMessageDialog(view, "Database connection not available. Cannot submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Retrieve inputs
        String serviceRatingStr = view.getTxtServiceRating().getText().trim();
        String cleanlinessRatingStr = view.getTxtCleanlinessRating().getText().trim();
        String foodRatingStr = view.getTxtFoodRating().getText().trim();
        String reviewText = view.getTxtReviewText().getText().trim();

        // 2. Validate inputs not empty
        if (serviceRatingStr.isEmpty() || cleanlinessRatingStr.isEmpty() || foodRatingStr.isEmpty() || reviewText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all rating fields and provide a detailed review.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Parse and validate integer ratings
        int serviceRating, cleanlinessRating, foodRating;
        try {
            serviceRating = Integer.parseInt(serviceRatingStr);
            cleanlinessRating = Integer.parseInt(cleanlinessRatingStr);
            foodRating = Integer.parseInt(foodRatingStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Ratings must be valid integers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Validate rating range (1-5)
        if (serviceRating < 1 || serviceRating > 5 || cleanlinessRating < 1 || cleanlinessRating > 5 || foodRating < 1 || foodRating > 5) {
            JOptionPane.showMessageDialog(view, "Ratings must be between 1 and 5.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5. Create Model
        FeedbackModel feedback = new FeedbackModel(serviceRating, cleanlinessRating, foodRating, reviewText);

        // 6. Save via DAO
        boolean success = dao.insertFeedback(feedback);

        if (success) {
            JOptionPane.showMessageDialog(view, "Thank you for your feedback! It has been submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            view.getTxtServiceRating().setText("1-5");
            view.getTxtCleanlinessRating().setText("1-5");
            view.getTxtFoodRating().setText("1-5");
            view.getTxtReviewText().setText("");

            // Navigate back to dashboard on success
            openDashboard();
        } else {
            JOptionPane.showMessageDialog(view, "Failed to submit feedback. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
