/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hotel_management;

import database.DatabaseSetup;
import view.loginpage;

/**
 *
 * @author i3
 */
public class Hotel_Management {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Initialize database schema and default admin user
        DatabaseSetup.initializeDatabase();
        
        // Start login UI
        java.awt.EventQueue.invokeLater(() -> {
            new loginpage().setVisible(true);
        });
    }
    
}
