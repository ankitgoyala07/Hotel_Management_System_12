/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package database;
import java.sql.*;
/**
 *
 * @author i3
 */
public interface Database {
    Connection Openconnection();
    void closeConnection (Connection conn);
    ResultSet runQuery (Connection conn, String query);
    int executeUpdate (Connection conn, String query);
    
}

