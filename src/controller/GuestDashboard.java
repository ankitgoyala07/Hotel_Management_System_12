package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import view classes
import view.BookRoom;
//import view.Gust_Details;
import view.OrderFood;
import view.Feedback;
import view.gest_dashbord;
/**
 * GuestDashboard.java
 * Main Dashboard for Guest after successful login
 * Location: C:\Users\Dell\OneDrive\Desktop\sem-2 java project\Hotel_Management_System_12\src\controller\GuestDashboard.java
 */
public class GuestDashboard extends JFrame {

    // UI Components (Buttons)
    private JButton btnBookRoom;
    private JButton btnOrderFood;
    private JButton btnFeedback;

    /**
     * Constructor - Sets up the dashboard window
     */
    public GuestDashboard() {
        initComponents();                    // Initialize all UI elements
        setTitle("gest_dashbord");         // Window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Exit program when closed
        setSize(420, 320);                   // Window size
        setLocationRelativeTo(null);         // Center on screen
    }

    /**
     * Initialize all components and layout
     */
    private void initComponents() {
        // Create buttons
        btnBookRoom = new JButton("Book Room");
        btnOrderFood = new JButton("Order Food");
        btnFeedback = new JButton("Feedback");

        // Add Action Listeners (using Lambda)
        btnBookRoom.addActionListener(e -> jButton2ActionPerformed(e));
        btnOrderFood.addActionListener(e -> jButton3ActionPerformed(e));
        btnFeedback.addActionListener(e -> jButton4ActionPerformed(e));

        // Main Panel with Grid Layout (3 buttons vertically)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 15, 15));   // 3 rows, 1 column, spacing
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Add buttons to panel
        panel.add(btnBookRoom);
        panel.add(btnOrderFood);
        panel.add(btnFeedback);

        // Add panel to frame
        add(panel);
    }

    /**
     * Action when "Book Room" button clicked
     */
    

  
    private void jButton2ActionPerformed(ActionEvent evt) {
        new BookRoom().setVisible(true);   // Open Book Room window
        this.dispose();                    // Close current dashboard
    }

    /**
     * Action when "Order Food" button clicked
     */
    private void jButton3ActionPerformed(ActionEvent evt) {
        new OrderFood().setVisible(true);   // Open OrderFood window
        this.dispose();                    // Close current dashboard

    }

    /**
     * Action when "Feedback" button clicked
     */
    private void jButton4ActionPerformed(ActionEvent evt) {
        new Feedback().setVisible(true);  // Open Guest Details / Feedback
        this.dispose();                       // Close current dashboard
    }
    
    

    /**
     * Main method - For testing the dashboard independently
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GuestDashboard().setVisible(true);
        });
    }


}