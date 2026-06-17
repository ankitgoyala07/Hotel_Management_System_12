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

        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanelContent = new javax.swing.JPanel();
        jLabelSearch = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jLabelDateRange = new javax.swing.JLabel();
        jTextFieldDateRange = new javax.swing.JTextField();
        jComboBoxRoomType = new javax.swing.JComboBox();
        jComboBoxStatus = new javax.swing.JComboBox();
        jLabelRoomType = new javax.swing.JLabel();
        jLabelStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPaneBookings = new javax.swing.JScrollPane();
        jTableBookings = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        Guest = new javax.swing.JButton();
        Booking = new javax.swing.JButton();
        Mealtime = new javax.swing.JButton();
        Billing = new javax.swing.JButton();
        Logout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Booking Management");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelHeader.setBackground(new java.awt.Color(211, 228, 245));
        jPanelHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(75, 85, 99));
        jLabelTitle.setText("Booking Management");
        jPanelHeader.add(jLabelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 7, 220, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(75, 85, 99));
        jLabel1.setText("FrontDesk");
        jPanelHeader.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 80, -1));

        jPanelMain.add(jPanelHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 590, 45));

        jPanelContent.setBackground(new java.awt.Color(211, 228, 245));
        jPanelContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelSearch.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelSearch.setForeground(new java.awt.Color(75, 85, 99));
        jLabelSearch.setText("SEARCH GUEST / ID");
        jPanelContent.add(jLabelSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, 15));

        jTextFieldSearch.setForeground(new java.awt.Color(156, 163, 169));
        jTextFieldSearch.addActionListener(this::jTextFieldSearchActionPerformed);
        jPanelContent.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 120, 28));

        jLabelDateRange.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelDateRange.setForeground(new java.awt.Color(75, 85, 99));
        jLabelDateRange.setText("DATE RANGE");
        jPanelContent.add(jLabelDateRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 140, 15));

        jTextFieldDateRange.setForeground(new java.awt.Color(55, 65, 81));
        jPanelContent.add(jTextFieldDateRange, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 140, 28));
        jPanelContent.add(jComboBoxRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 140, 28));
        jPanelContent.add(jComboBoxStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 120, 28));

        jLabelRoomType.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelRoomType.setForeground(new java.awt.Color(75, 85, 99));
        jLabelRoomType.setText("ROOM TYPE");
        jPanelContent.add(jLabelRoomType, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 90, 15));

        jLabelStatus.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(75, 85, 99));
        jLabelStatus.setText("STATUS");
        jPanelContent.add(jLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 80, 15));

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
        jTableBookings.setGridColor(new java.awt.Color(229, 231, 235));
        jTableBookings.setRowHeight(38);
        jTableBookings.setSelectionBackground(new java.awt.Color(239, 246, 255));
        jTableBookings.setSelectionForeground(new java.awt.Color(30, 64, 175));
        jTableBookings.setShowHorizontalLines(true);
        jTableBookings.getTableHeader().setReorderingAllowed(false);
        jScrollPaneBookings.setViewportView(jTableBookings);

        jScrollPane1.setViewportView(jScrollPaneBookings);

        jPanelContent.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 570, 300));

        jPanelMain.add(jPanelContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 85, 590, 400));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanelMain.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 500));

        getContentPane().add(jPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 620, 500));

        jPanelSidebar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSidebar.setLayout(null);

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(37, 99, 235));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("HMS");
        jPanelSidebar.add(lblLogo);
        lblLogo.setBounds(10, 30, 140, 30);

        Dashboard.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Dashboard.setText("  Dashboard");
        Dashboard.setBorderPainted(false);
        Dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Dashboard.addActionListener(this::DashboardActionPerformed);
        jPanelSidebar.add(Dashboard);
        Dashboard.setBounds(10, 80, 160, 35);

        Guest.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Guest.setText(" Guest");
        Guest.setBorderPainted(false);
        Guest.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Guest.addActionListener(this::GuestActionPerformed);
        jPanelSidebar.add(Guest);
        Guest.setBounds(10, 120, 160, 35);

        Booking.setBackground(new java.awt.Color(211, 228, 245));
        Booking.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Booking.setForeground(new java.awt.Color(37, 99, 235));
        Booking.setText(" Booking");
        Booking.setBorderPainted(false);
        Booking.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Booking.addActionListener(this::BookingActionPerformed);
        jPanelSidebar.add(Booking);
        Booking.setBounds(10, 160, 160, 35);

        Mealtime.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Mealtime.setText(" Mealtime");
        Mealtime.setBorderPainted(false);
        Mealtime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Mealtime.addActionListener(this::MealtimeActionPerformed);
        jPanelSidebar.add(Mealtime);
        Mealtime.setBounds(10, 200, 160, 35);

        Billing.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Billing.setText(" Billing");
        Billing.setBorderPainted(false);
        Billing.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Billing.addActionListener(this::BillingActionPerformed);
        jPanelSidebar.add(Billing);
        Billing.setBounds(10, 240, 160, 35);

        Logout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Logout.setText(" Logout");
        Logout.setBorderPainted(false);
        Logout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Logout.addActionListener(this::LogoutActionPerformed);
        jPanelSidebar.add(Logout);
        Logout.setBounds(10, 280, 160, 35);

        getContentPane().add(jPanelSidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DashboardActionPerformed

    private void GuestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GuestActionPerformed

    private void BookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BookingActionPerformed

    private void MealtimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MealtimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MealtimeActionPerformed

    private void BillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BillingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BillingActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoutActionPerformed

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
    private javax.swing.JButton Billing;
    private javax.swing.JButton Booking;
    private javax.swing.JButton Dashboard;
    private javax.swing.JButton Guest;
    private javax.swing.JButton Logout;
    private javax.swing.JButton Mealtime;
    private javax.swing.JComboBox jComboBoxRoomType;
    private javax.swing.JComboBox jComboBoxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDateRange;
    private javax.swing.JLabel jLabelRoomType;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneBookings;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableBookings;
    private javax.swing.JTextField jTextFieldDateRange;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JLabel lblLogo;
    // End of variables declaration//GEN-END:variables
}
