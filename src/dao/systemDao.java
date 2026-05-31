package dao;

import model.systemModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for handling system configuration persistence.
 * Since a full database connection is optional at this stage, this class
 * realisticly reads and writes settings to a local properties file: 'hotel_config.properties'.
 *
 * @author i3
 */
public class systemDao {
    private static final Logger logger = Logger.getLogger(systemDao.class.getName());
    private static final String CONFIG_FILE_PATH = "hotel_config.properties";

    /**
     * Loads the current hotel settings from the local properties file.
     * If the file does not exist, returns a systemModel with default empty values.
     *
     * @return the loaded systemModel
     */
    public systemModel getSystemSettings() {
        Properties props = new Properties();
        File configFile = new File(CONFIG_FILE_PATH);

        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load system settings from property file.", e);
            }
        }

        systemModel model = new systemModel();
        model.setHotelName(props.getProperty("hotelName", ""));
        model.setHotelId(props.getProperty("hotelId", ""));
        model.setAddress(props.getProperty("address", ""));
        model.setPanNumber(props.getProperty("panNumber", ""));
        model.setOwner(props.getProperty("owner", ""));
        model.setEmail(props.getProperty("email", ""));
        model.setPhone(props.getProperty("phone", ""));
        model.setWebsite(props.getProperty("website", ""));

        return model;
    }

    /**
     * Saves the provided hotel settings to the local properties file.
     *
     * @param model the systemModel containing updated settings
     * @return true if save was successful, false otherwise
     */
    public boolean updateSystemSettings(systemModel model) {
        Properties props = new Properties();
        props.setProperty("hotelName", model.getHotelName() != null ? model.getHotelName() : "");
        props.setProperty("hotelId", model.getHotelId() != null ? model.getHotelId() : "");
        props.setProperty("address", model.getAddress() != null ? model.getAddress() : "");
        props.setProperty("panNumber", model.getPanNumber() != null ? model.getPanNumber() : "");
        props.setProperty("owner", model.getOwner() != null ? model.getOwner() : "");
        props.setProperty("email", model.getEmail() != null ? model.getEmail() : "");
        props.setProperty("phone", model.getPhone() != null ? model.getPhone() : "");
        props.setProperty("website", model.getWebsite() != null ? model.getWebsite() : "");

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE_PATH)) {
            props.store(fos, "Hotel Management System - System Settings");
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save system settings to property file.", e);
            return false;
        }
    }
}
