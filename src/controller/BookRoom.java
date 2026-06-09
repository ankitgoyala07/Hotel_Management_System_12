/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


// Import view classes
import view.gest_dashbord;
//import view.Gust_Details;
import view.OrderFood;
import view.Feedback;
import view.Gust_Details;




/**
 * Book Room Controller (Main Navigation Dashboard)
 * @author Dell
 */
public class BookRoom extends JFrame {

    // UI Components (Buttons)
    private JButton btngest_dashbord;
    private JButton btnBookRoom;
    private JButton btnOrderFood;
    private JButton btnFeedback;
    private JButton btnBookNow;
    public BookRoom() {
        initComponents();
        setTitle("Hotel Management System - Book Room");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center on screen
    }

    private void initComponents() {
        // Create buttons
        btngest_dashbord = new JButton("Guest Dashboard");
        btnBookRoom = new JButton("Book Room");
        btnOrderFood = new JButton("Order Food");
        btnFeedback = new JButton("Feedback");
        btnBookNow=new JButton("BookNow");
        // Add Action Listeners
        btngest_dashbord.addActionListener(e -> jButton1ActionPerformed(e));
        btnBookRoom.addActionListener(e -> jButton2ActionPerformed(e));
        btnOrderFood.addActionListener(e -> jButton4ActionPerformed(e));
        btnFeedback.addActionListener(e -> jButton3ActionPerformed(e));
        btnBookNow.addActionListener(e -> jButton6ActionPerformed(e));
        btnBookNow.addActionListener(e -> jButton7ActionPerformed(e));
        btnBookNow.addActionListener(e -> jButton9ActionPerformed(e));

        // Main Panel with Grid Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15)); // 4 buttons vertically
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Add buttons to panel
        panel.add(btngest_dashbord);
        panel.add(btnBookRoom);
        panel.add(btnOrderFood);
        panel.add(btnFeedback);
        panel.add(btnBookNow);
        // Add panel to frame
        add(panel);
    }

    /**
     * Action when "Guest Dashboard" button clicked
     */
    private void jButton1ActionPerformed(ActionEvent evt) {
        new view.gest_dashbord().setVisible(true);
        this.dispose();
    }

    /**
     * Action when "Book Room" button clicked
     */
    private void jButton2ActionPerformed(ActionEvent evt) {
        new view.BookRoom().setVisible(true);   // Opens Book Room form
        this.dispose();
    }

    /**
     * Action when "Order Food" button clicked
     */
    private void jButton4ActionPerformed(ActionEvent evt) {
        new view.OrderFood().setVisible(true);
        this.dispose();
    }

    /**
     * Action when "Feedback" button clicked
     */
    private void jButton3ActionPerformed(ActionEvent evt) {
        new view.Feedback().setVisible(true);
        this.dispose();
    }

    // These methods seem to be from BookRoom view - keeping them for now
    private void jButton6ActionPerformed(ActionEvent evt) {
        new view.Gust_Details().setVisible(true);
        this.dispose();
    }

    private void jButton7ActionPerformed(ActionEvent evt) {
        new view.Gust_Details().setVisible(true);
        this.dispose();
    }

    private void jButton9ActionPerformed(ActionEvent evt) {
        new view.Gust_Details().setVisible(true);
        this.dispose();
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookRoom().setVisible(true);
        });
    }
}