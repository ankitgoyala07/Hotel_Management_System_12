/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import model.AdminDashboardModel;

public class AdminDashboardDAO {

    public AdminDashboardModel getDashboardData() {
        AdminDashboardModel model = new AdminDashboardModel();

        try (Connection conn = DBConnection.getConnection()) {

            ResultSet rs1 = conn.createStatement().executeQuery(
                "SELECT COUNT(*) FROM bookings WHERE check_in = CURDATE()");
            if (rs1.next()) model.setTodayCheckIn(rs1.getInt(1));

            ResultSet rs2 = conn.createStatement().executeQuery(
                "SELECT COUNT(*) FROM bookings WHERE check_out = CURDATE()");
            if (rs2.next()) model.setTodayCheckOut(rs2.getInt(1));

            ResultSet rs3 = conn.createStatement().executeQuery(
                "SELECT COUNT(*) FROM rooms");
            if (rs3.next()) model.setTotalRooms(rs3.getInt(1));

            ResultSet rs4 = conn.createStatement().executeQuery(
                "SELECT COUNT(*) FROM rooms WHERE status = 'Available'");
            if (rs4.next()) model.setAvailableRooms(rs4.getInt(1));

            ResultSet rs5 = conn.createStatement().executeQuery(
                "SELECT COUNT(*) FROM rooms WHERE status = 'Occupied'");
            if (rs5.next()) model.setOccupiedRooms(rs5.getInt(1));

        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }

        return model;
    }
}