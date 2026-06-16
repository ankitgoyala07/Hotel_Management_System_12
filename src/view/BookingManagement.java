/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

/**
 * Booking Management View Class
 * Handles customer bookings, booking details form, and records management.
 */

/**
 *
 * @author rikes
 */
public class BookingManagement extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BookingManagement.class.getName());
    private final controller.BookingController bookingController = new controller.BookingController();

    /**
     * Creates new form BookingManagement
     */
    public BookingManagement() {
        initComponents();
        populateComboBoxes();
        loadBookingsData();
        setupListeners();
    }

    private void populateComboBoxes() {
        jComboBoxRoomType.removeAllItems();
        jComboBoxRoomType.addItem("All Types");
        jComboBoxRoomType.addItem("Single");
        jComboBoxRoomType.addItem("Double");
        jComboBoxRoomType.addItem("Suite");
        jComboBoxRoomType.addItem("Deluxe");

        jComboBoxStatus.removeAllItems();
        jComboBoxStatus.addItem("All Status");
        jComboBoxStatus.addItem("Confirmed");
        jComboBoxStatus.addItem("Checked-in");
        jComboBoxStatus.addItem("Checked-out");
        jComboBoxStatus.addItem("Cancelled");
    }

    private void loadBookingsData() {
        String search = jTextFieldSearch.getText().trim();
        if (search.equals("e.g. John Doe")) {
            search = "";
        }
        String roomType = String.valueOf(jComboBoxRoomType.getSelectedItem());
        String status = String.valueOf(jComboBoxStatus.getSelectedItem());
        
        try {
            java.util.List<model.BookingModel> bookings = bookingController.getBookings(search, roomType, status, 1, 100);
            
            javax.swing.table.DefaultTableModel tblModel = (javax.swing.table.DefaultTableModel) jTableBookings.getModel();
            tblModel.setRowCount(0);
            
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MMM dd, yyyy");
            
            for (model.BookingModel b : bookings) {
                String stay = "";
                if (b.getCheckInDate() != null && b.getCheckOutDate() != null) {
                    stay = df.format(b.getCheckInDate()) + " - " + df.format(b.getCheckOutDate());
                }
                
                String statusText = b.getStatus();
                String badgeHtml = statusText;
                if (statusText != null) {
                    if (statusText.equalsIgnoreCase("Confirmed")) {
                        badgeHtml = "<html><span style='color: #10B981; background: #D1FAE5; padding: 2px 10px; border-radius: 9999px; font-weight: bold;'>Confirmed</span></html>";
                    } else if (statusText.equalsIgnoreCase("CheckedIn") || statusText.equalsIgnoreCase("Checked-in")) {
                        badgeHtml = "<html><span style='color: #2563EB; background: #DBEAFE; padding: 2px 10px; border-radius: 9999px; font-weight: bold;'>Checked In</span></html>";
                    } else if (statusText.equalsIgnoreCase("CheckedOut") || statusText.equalsIgnoreCase("Checked-out")) {
                        badgeHtml = "<html><span style='color: #9CA3AF; background: #F3F4F6; padding: 2px 10px; border-radius: 9999px; font-weight: bold;'>Checked Out</span></html>";
                    } else if (statusText.equalsIgnoreCase("Cancelled")) {
                        badgeHtml = "<html><span style='color: #EF4444; background: #FEE2E2; padding: 2px 10px; border-radius: 9999px; font-weight: bold;'>Cancelled</span></html>";
                    }
                }
                
                tblModel.addRow(new Object[]{
                    b.getGuestName(),
                    b.getRoomNumber(),
                    b.getRoomType(),
                    stay,
                    badgeHtml,
                    String.format("$%.2f", b.getTotalAmount())
                });
            }
        } catch (Exception e) {
            System.out.println("Error loading bookings data: " + e.getMessage());
        }
    }

    private void setupListeners() {
        jComboBoxRoomType.addActionListener(e -> loadBookingsData());
        jComboBoxStatus.addActionListener(e -> loadBookingsData());
        
        jTextFieldSearch.addActionListener(e -> loadBookingsData());
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadBookingsData();
            }
        });
        
        jTextFieldSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (jTextFieldSearch.getText().equals("e.g. John Doe")) {
                    jTextFieldSearch.setText("");
                    jTextFieldSearch.setForeground(new java.awt.Color(55, 65, 81));
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (jTextFieldSearch.getText().trim().isEmpty()) {
                    jTextFieldSearch.setText("e.g. John Doe");
                    jTextFieldSearch.setForeground(new java.awt.Color(156, 163, 169));
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSidebar = new javax.swing.JPanel();
        jLabelLogo = new javax.swing.JLabel();
        jButtonDashboard = new javax.swing.JButton();
        jButtonGuests = new javax.swing.JButton();
        jButtonBookings = new javax.swing.JButton();
        jButtonMealTime = new javax.swing.JButton();
        jButtonBilling = new javax.swing.JButton();
        jButtonLogout = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelContent = new javax.swing.JPanel();
        jPanelCard = new javax.swing.JPanel();
        jLabelSearch = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jLabelDateRange = new javax.swing.JLabel();
        jTextFieldDateRange = new javax.swing.JTextField();
        jLabelRoomType = new javax.swing.JLabel();
        jComboBoxRoomType = new javax.swing.JComboBox();
        jLabelStatus = new javax.swing.JLabel();
        jComboBoxStatus = new javax.swing.JComboBox();
        jScrollPaneBookings = new javax.swing.JScrollPane();
        jTableBookings = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Booking Management");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelSidebar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSidebar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanelSidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelLogo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelLogo.setForeground(new java.awt.Color(37, 99, 235));
        jLabelLogo.setText("    HMS");
        jPanelSidebar.add(jLabelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 130, 35));

        jButtonDashboard.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonDashboard.setForeground(new java.awt.Color(75, 85, 99));
        jButtonDashboard.setText("    Dashboard");
        jButtonDashboard.setBorderPainted(false);
        jButtonDashboard.setContentAreaFilled(false);
        jButtonDashboard.setFocusPainted(false);
        jButtonDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonDashboard.addActionListener(this::jButtonDashboardActionPerformed);
        jPanelSidebar.add(jButtonDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 70, 140, 32));

        jButtonGuests.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonGuests.setForeground(new java.awt.Color(75, 85, 99));
        jButtonGuests.setText("    Guests");
        jButtonGuests.setBorderPainted(false);
        jButtonGuests.setContentAreaFilled(false);
        jButtonGuests.setFocusPainted(false);
        jButtonGuests.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanelSidebar.add(jButtonGuests, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 110, 140, 32));

        jButtonBookings.setBackground(new java.awt.Color(219, 234, 254));
        jButtonBookings.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonBookings.setForeground(new java.awt.Color(37, 99, 235));
        jButtonBookings.setText("    Bookings");
        jButtonBookings.setBorderPainted(false);
        jButtonBookings.setFocusPainted(false);
        jButtonBookings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBookings.addActionListener(this::jButtonBookingsActionPerformed);
        jPanelSidebar.add(jButtonBookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 150, 140, 32));

        jButtonMealTime.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonMealTime.setForeground(new java.awt.Color(75, 85, 99));
        jButtonMealTime.setText("    Meal time");
        jButtonMealTime.setBorderPainted(false);
        jButtonMealTime.setContentAreaFilled(false);
        jButtonMealTime.setFocusPainted(false);
        jButtonMealTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonMealTime.addActionListener(this::jButtonMealTimeActionPerformed);
        jPanelSidebar.add(jButtonMealTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 190, 140, 32));

        jButtonBilling.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonBilling.setForeground(new java.awt.Color(75, 85, 99));
        jButtonBilling.setText("    Billing");
        jButtonBilling.setBorderPainted(false);
        jButtonBilling.setContentAreaFilled(false);
        jButtonBilling.setFocusPainted(false);
        jButtonBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonBilling.addActionListener(this::jButtonBillingActionPerformed);
        jPanelSidebar.add(jButtonBilling, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 230, 140, 32));

        jButtonLogout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jButtonLogout.setForeground(new java.awt.Color(75, 85, 99));
        jButtonLogout.setText("    Logout");
        jButtonLogout.setBorderPainted(false);
        jButtonLogout.setContentAreaFilled(false);
        jButtonLogout.setFocusPainted(false);
        jButtonLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonLogout.addActionListener(this::jButtonLogoutActionPerformed);
        jPanelSidebar.add(jButtonLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 270, 140, 32));

        getContentPane().add(jPanelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 500));

        jPanelMain.setBackground(new java.awt.Color(249, 250, 251));
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(229, 231, 235));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(75, 85, 99));
        jLabelTitle.setText("Booking Management");
        jPanelHeader.add(jLabelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 7, 220, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(75, 85, 99));
        jLabel1.setText("FrontDesk");
        jPanelHeader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 80, -1));

        jPanelMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 600, 45));

        jPanelContent.setBackground(new java.awt.Color(209, 213, 219));
        jPanelContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelCard.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSearch.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelSearch.setForeground(new java.awt.Color(75, 85, 99));
        jLabelSearch.setText("SEARCH GUEST / ID");
        jPanelCard.add(jLabelSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 10, 120, 15));

        jTextFieldSearch.setForeground(new java.awt.Color(156, 163, 169));
        jTextFieldSearch.setText("e.g. John Doe");
        jPanelCard.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 28, 120, 28));

        jLabelDateRange.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelDateRange.setForeground(new java.awt.Color(75, 85, 99));
        jLabelDateRange.setText("DATE RANGE");
        jPanelCard.add(jLabelDateRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 10, 140, 15));

        jTextFieldDateRange.setForeground(new java.awt.Color(55, 65, 81));
        jTextFieldDateRange.setText("Oct 12 - Oct 19, 2023");
        jPanelCard.add(jTextFieldDateRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 28, 140, 28));

        jLabelRoomType.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelRoomType.setForeground(new java.awt.Color(75, 85, 99));
        jLabelRoomType.setText("ROOM TYPE");
        jPanelCard.add(jLabelRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 10, 120, 15));
        jPanelCard.add(jComboBoxRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 28, 120, 28));

        jLabelStatus.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(75, 85, 99));
        jLabelStatus.setText("STATUS");
        jPanelCard.add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 10, 120, 15));
        jPanelCard.add(jComboBoxStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 28, 120, 28));

        jTableBookings.setRowHeight(38);
        jTableBookings.setGridColor(new java.awt.Color(229, 231, 235));
        jTableBookings.setSelectionBackground(new java.awt.Color(239, 246, 255));
        jTableBookings.setSelectionForeground(new java.awt.Color(30, 64, 175));
        jTableBookings.getTableHeader().setReorderingAllowed(false);
        jTableBookings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "GUEST NAME", "ROOM", "ROOM TYPE", "STAY DURATION", "STATUS", "TOTAL AMOUNT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPaneBookings.setViewportView(jTableBookings);

        jPanelCard.add(jScrollPaneBookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 70, 540, 225));

        jPanelContent.add(jPanelCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 50, 570, 350));

        jPanelMain.add(jPanelContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 70, 600, 415));

        getContentPane().add(jPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, 630, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDashboardActionPerformed
        new view.admindashboard().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonDashboardActionPerformed

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        new view.loginpage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonBookingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBookingsActionPerformed

    private void jButtonMealTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMealTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonMealTimeActionPerformed

    private void jButtonBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBillingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonBillingActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new BookingManagement().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBilling;
    private javax.swing.JButton jButtonBookings;
    private javax.swing.JButton jButtonDashboard;
    private javax.swing.JButton jButtonGuests;
    private javax.swing.JButton jButtonLogout;
    private javax.swing.JButton jButtonMealTime;
    private javax.swing.JComboBox jComboBoxRoomType;
    private javax.swing.JComboBox jComboBoxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDateRange;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelRoomType;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPaneBookings;
    private javax.swing.JTable jTableBookings;
    private javax.swing.JTextField jTextFieldDateRange;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
