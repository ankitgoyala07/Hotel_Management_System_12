package view;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
public class MealTime extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(MealTime.class.getName());
    private JPanel jPanelSidebar;
    private JPanel jPanelContent;
    public MealTime() {
        initComponents();
    }
    private void initComponents() {
        jPanelSidebar = new JPanel();
        jPanelContent = new JPanel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
    }
}
