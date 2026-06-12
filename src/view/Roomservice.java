package view;

public class Roomservice extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Roomservice.class.getName());

    public Roomservice() {
        initComponents();
        
        // Sidebar navigation
        jButton1.addActionListener(e -> {
            new gest_dashbord().setVisible(true);
            this.dispose();
        });
        jButton2.addActionListener(e -> {
            new BookRoom().setVisible(true);
            this.dispose();
        });
        jButton3.addActionListener(e -> {
            new OrderFood().setVisible(true);
            this.dispose();
        });
        jButton4.addActionListener(e -> {
            new Feedback().setVisible(true);
            this.dispose();
        });
        jButton5.addActionListener(e -> {
            new loginpage().setVisible(true);
            this.dispose();
        });

        // Submit service request
        jButton6.addActionListener(e -> {
            String serviceType = (String) jComboBox1.getSelectedItem();
            String roomNo = jTextField1.getText().trim();
            String instructions = jTextArea1.getText().trim();

            if (roomNo.isEmpty() || instructions.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please enter your Room Number and request details.", "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Success
            javax.swing.JOptionPane.showMessageDialog(this, "Your request for " + serviceType + " in room " + roomNo + " has been submitted. Our staff is on the way!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields
            jTextField1.setText("");
            jTextArea1.setText("");

            // Navigate back to dashboard
            new gest_dashbord().setVisible(true);
            this.dispose();
        });

        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HMS - Room Service");
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 255));
        jLabel1.setText("HMS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 17, 34, 31);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(null);

        jButton1.setText("Dashboard");
        jPanel2.add(jButton1);
        jButton1.setBounds(6, 54, 114, 32);

        jButton2.setText("Room browsing");
        jPanel2.add(jButton2);
        jButton2.setBounds(6, 104, 114, 32);

        jButton3.setText("Order Food");
        jPanel2.add(jButton3);
        jButton3.setBounds(6, 155, 114, 32);

        jButton4.setText("Feedback");
        jPanel2.add(jButton4);
        jButton4.setBounds(6, 206, 114, 32);

        jButton5.setText("Logout");
        jPanel2.add(jButton5);
        jButton5.setBounds(6, 258, 114, 32);

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 150, 540);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));
        jPanel6.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Room Service");
        jPanel6.add(jLabel2);
        jLabel2.setBounds(6, 0, 131, 31);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Guest");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(430, 0, 64, 31);

        jPanel1.add(jPanel6);
        jPanel6.setBounds(40, 10, 500, 31);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Request Room Service");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(23, 10, 392, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Housekeeping, towels, amenities, and more");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(23, 35, 392, 15);

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Service Type");
        jPanel4.add(jLabel6);
        jLabel6.setBounds(23, 70, 150, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Housekeeping", "Towels/Linen", "Toiletries", "Luggage Assistance", "Other" }));
        jPanel4.add(jComboBox1);
        jComboBox1.setBounds(23, 95, 200, 30);

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Room Number");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(270, 70, 150, 20);
        jPanel4.add(jTextField1);
        jTextField1.setBounds(270, 95, 150, 30);

        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Special Instructions / Detailed Description");
        jPanel4.add(jLabel8);
        jLabel8.setBounds(23, 140, 300, 20);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(23, 165, 440, 150);

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Submit Request");
        jPanel4.add(jButton6);
        jButton6.setBounds(160, 330, 170, 35);

        jPanel3.add(jPanel4);
        jPanel4.setBounds(6, 6, 488, 428);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(40, 60, 500, 440);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(150, 0, 590, 540);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Roomservice().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
