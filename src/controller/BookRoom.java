/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import view classes
import view.gest_dashbord;
import view.BookRoom;
//import view.Gust_Details;
import view.OrderFood;
import view.Feedback;

/**
 *
 * @author Dell
 */
public class BookRoom {
    // UI Components (Buttons)
    private JButton btngest_dashbord;
    private JButton btnBookRoom;
    private JButton btnOrderFood;
    private JButton btnFeedback;

    
     private void initComponents() {
        // Create buttons
        btngest_dashbord = new JButton("gest dashboard");
        btnBookRoom = new JButton("Book Room");
        btnOrderFood = new JButton("Order Food");
        btnFeedback = new JButton("Feedback");

        // Add Action Listeners (using Lambda)
        btngest_dashbord.addActionListener(e -> jButton1ActionPerformed(e));
        btnBookRoom.addActionListener(e -> jButton2ActionPerformed(e));
        btnOrderFood.addActionListener(e -> jButton4ActionPerformed(e));
        btnFeedback.addActionListener(e -> jButton3ActionPerformed(e));

        // Main Panel with Grid Layout (3 buttons vertically)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 15, 15));   // 3 rows, 1 column, spacing
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        // Add buttons to panel
        panel.add(btngest_dashbord);
        panel.add(btnBookRoom);
        panel.add(btnOrderFood);
        panel.add(btnFeedback);

        // Add panel to frame
        add(panel);
    }

    /**
     * Action when "Book Room" button clicked
     */
    

    private void jButton1ActionPerformed(ActionEvent evt) {
        new gest_dashbord().setVisible(true);   // Open gest_dashbord window
        this.dispose();                    // Close current dashboard
    }
  
    private void jButton2ActionPerformed(ActionEvent evt) {
        new BookRoom().setVisible(true);   // Open Book Room window
        this.dispose();                    // Close current dashboard
    }

    /**
     * Action when "Order Food" button clicked
     */
    private void jButton4ActionPerformed(ActionEvent evt) {
        new OrderFood().setVisible(true);   // Open OrderFood window
        this.dispose();                    // Close current dashboard

    }

    /**
     * Action when "Feedback" button clicked
     */
    private void jButton3ActionPerformed(ActionEvent evt) {
        new Feedback().setVisible(true);  // Open Guest Details / Feedback
        this.dispose();                       // Close current dashboard
    }
    
        private void jButton3ActionPerformed(ActionEvent evt) {
        new Feedback().setVisible(true);  // Open Guest Details / Feedback
        this.dispose();                       // Close current dashboard
    }

    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

    
    
}
