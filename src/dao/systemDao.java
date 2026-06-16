package dao;

import database.MySqlConnection;
import model.systemModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for handling system configuration persistence.
 * Connects to the MySQL database to read and write system configuration settings.
 *
 * @author i3
 */
public class systemDao {
    private static final Logger logger = Logger.getLogger(systemDao.class.getName());
    private final MySqlConnection mysql = new MySqlConnection();

    /**
     * Loads the current hotel settings from the MySQL database.
     * If no records exist, returns a default systemModel.
     *
     * @return the loaded systemModel
     */
    public systemModel getSystemSettings() {
        systemModel model = new systemModel();
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            logger.log(Level.SEVERE, "Database connection failed while loading settings.");
            return model;
        }

        String sql = "SELECT * FROM system_settings LIMIT 1";
        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                model.setHotelName(rs.getString("hotel_name"));
                model.setHotelId(rs.getString("hotel_id"));
                model.setAddress(rs.getString("address"));
                model.setPanNumber(rs.getString("pan_number"));
                model.setOwner(rs.getString("owner"));
                model.setQuickNote(rs.getString("quick_note"));
                model.setPhone(rs.getString("phone"));
                model.setWebsite(rs.getString("website"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load system settings from database.", e);
        } finally {
            mysql.closeConnection(conn);
        }

        return model;
    }

    /**
     * Saves the provided hotel settings to the MySQL database.
     * Inserts settings if no settings exist, or updates the existing single row.
     *
     * @param model the systemModel containing updated settings
     * @return true if save/update was successful, false otherwise
     */
    public boolean updateSystemSettings(systemModel model) {
        Connection conn = mysql.Openconnection();
        if (conn == null) {
            logger.log(Level.SEVERE, "Database connection failed while saving settings.");
            return false;
        }

        boolean exists = false;
        String checkSql = "SELECT COUNT(*) FROM system_settings";
        try (PreparedStatement checkPstm = conn.prepareStatement(checkSql);
             ResultSet rs = checkPstm.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to check existence of system settings.", e);
        }

        String sql;
        if (exists) {
            sql = "UPDATE system_settings SET hotel_name = ?, hotel_id = ?, address = ?, "
                + "pan_number = ?, owner = ?, quick_note = ?, phone = ?, website = ? "
                + "WHERE id = (SELECT id FROM (SELECT id FROM system_settings LIMIT 1) as temp)";
        } else {
            sql = "INSERT INTO system_settings (hotel_name, hotel_id, address, pan_number, "
                + "owner, quick_note, phone, website) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, model.getHotelName());
            pstm.setString(2, model.getHotelId());
            pstm.setString(3, model.getAddress());
            pstm.setString(4, model.getPanNumber());
            pstm.setString(5, model.getOwner());
            pstm.setString(6, model.getQuickNote());
            pstm.setString(7, model.getPhone());
            pstm.setString(8, model.getWebsite());
            
            int rows = pstm.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to save system settings to database.", e);
            return false;
        } finally {
            mysql.closeConnection(conn);
        }
    }
}
