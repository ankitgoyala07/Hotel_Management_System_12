package view;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
public class reports extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(reports.class.getName());
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    public reports() {
        initComponents();
    }
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(930, 650);
    }
}
