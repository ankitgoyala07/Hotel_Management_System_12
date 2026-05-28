package view;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
public class MealTime extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(MealTime.class.getName());
    private JPanel jPanelSidebar;
    private JPanel jPanelContent;
    private JPanel jPanelHeader;
    private JPanel jPanelCard;
    private JButton jButtonDashboard;
    private JButton jButtonRooms;
    private JButton jButtonBookings;
    private JButton jButtonMealTime;
    public MealTime() {
        initComponents();
    }
    private void initComponents() {
        jPanelSidebar = new JPanel();
        jPanelContent = new JPanel();
        jPanelHeader = new JPanel();
        jPanelCard = new JPanel();
        jButtonDashboard = new JButton();
        jButtonRooms = new JButton();
        jButtonBookings = new JButton();
        jButtonMealTime = new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
    }
}
