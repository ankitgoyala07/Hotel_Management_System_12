package view;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
public class reports extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(reports.class.getName());
    private JPanel jPanel1;
    private JPanel jPanel2;
    public reports() {
        initComponents();
    }
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(930, 650);
    }
}
