package view;

public class Roomservice extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Roomservice.class.getName());

    public Roomservice() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelHeaderTitle = new javax.swing.JLabel();
        jLabelGuestRole = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanelCard = new javax.swing.JPanel();
        jPanelMenu = new javax.swing.JPanel();
        jLabelGuestServices = new javax.swing.JLabel();
        jTextFieldRoomId = new javax.swing.JTextField();
        jLabelMenuTitle = new javax.swing.JLabel();
        jPanelItem1 = new javax.swing.JPanel();
        jLabelItem1Name = new javax.swing.JLabel();
        jLabelItem1Price = new javax.swing.JLabel();
        jButtonAdd1 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jPanelItem2 = new javax.swing.JPanel();
        jLabelItem1Name1 = new javax.swing.JLabel();
        jLabelItem1Price1 = new javax.swing.JLabel();
        jButtonAdd2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelItem3 = new javax.swing.JPanel();
        jLabelItem1Name2 = new javax.swing.JLabel();
        jLabelItem1Price2 = new javax.swing.JLabel();
        jButtonAdd3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanelItem4 = new javax.swing.JPanel();
        jLabelItem1Name3 = new javax.swing.JLabel();
        jLabelItem1Price3 = new javax.swing.JLabel();
        jButtonAdd4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanelItem5 = new javax.swing.JPanel();
        jLabelItem1Name4 = new javax.swing.JLabel();
        jLabelItem1Price4 = new javax.swing.JLabel();
        jButtonAdd5 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jPanelOrder = new javax.swing.JPanel();
        jLabelYourOrder = new javax.swing.JLabel();
        jLabelOrderBadge = new javax.swing.JLabel();
        jLabelOrderItem1Name = new javax.swing.JLabel();
        jLabelOrderItem1Price = new javax.swing.JLabel();
        jLabelOrderItem2Name = new javax.swing.JLabel();
        jLabelOrderItem2Price = new javax.swing.JLabel();
        jButtonItem2Remove = new javax.swing.JButton();
        jSeparatorOrder = new javax.swing.JSeparator();
        jLabelTotal = new javax.swing.JLabel();
        jLabelTotalValue = new javax.swing.JLabel();
        jButtonComplete = new javax.swing.JButton();
        jButtonItem2Remove1 = new javax.swing.JButton();
        jPanelSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnRooms = new javax.swing.JButton();
        btnDiscounts = new javax.swing.JButton();
        btnStaffs = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        btnSystemSetting = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HMS - Room Service");
        setMinimumSize(new java.awt.Dimension(810, 535));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jPanelHeader.setBackground(new java.awt.Color(211, 228, 245));
        jPanelHeader.setLayout(null);

        jLabelHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelHeaderTitle.setForeground(new java.awt.Color(75, 75, 85));
        jLabelHeaderTitle.setText("Room Service");
        jPanelHeader.add(jLabelHeaderTitle);
        jLabelHeaderTitle.setBounds(15, 12, 200, 20);

        jLabelGuestRole.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelGuestRole.setForeground(new java.awt.Color(75, 75, 85));
        jLabelGuestRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelGuestRole.setText("Guest");
        jPanelHeader.add(jLabelGuestRole);
        jLabelGuestRole.setBounds(480, 12, 60, 20);

        jPanel1.add(jPanelHeader);
        jPanelHeader.setBounds(10, 10, 590, 45);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator6);
        jSeparator6.setBounds(0, 0, 3, 500);

        jPanelCard.setBackground(new java.awt.Color(211, 228, 245));
        jPanelCard.setLayout(null);

        jPanelMenu.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMenu.setLayout(null);

        jLabelGuestServices.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelGuestServices.setForeground(new java.awt.Color(35, 40, 60));
        jLabelGuestServices.setText("Guest Services");
        jPanelMenu.add(jLabelGuestServices);
        jLabelGuestServices.setBounds(15, 10, 120, 20);

        jTextFieldRoomId.setForeground(new java.awt.Color(160, 165, 180));
        jTextFieldRoomId.addActionListener(this::jTextFieldRoomIdActionPerformed);
        jPanelMenu.add(jTextFieldRoomId);
        jTextFieldRoomId.setBounds(270, 20, 100, 24);

        jLabelMenuTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabelMenuTitle.setForeground(new java.awt.Color(25, 30, 50));
        jLabelMenuTitle.setText("Room Service Menu");
        jPanelMenu.add(jLabelMenuTitle);
        jLabelMenuTitle.setBounds(15, 40, 250, 25);

        jPanelItem1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItem1.setLayout(null);

        jLabelItem1Name.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Name.setText("Infinity Pool");
        jPanelItem1.add(jLabelItem1Name);
        jLabelItem1Name.setBounds(0, 0, 130, 16);

        jLabelItem1Price.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Price.setText("$8.00");
        jPanelItem1.add(jLabelItem1Price);
        jLabelItem1Price.setBounds(0, 30, 60, 16);

        jButtonAdd1.setBackground(new java.awt.Color(30, 35, 55));
        jButtonAdd1.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonAdd1.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd1.setText("+");
        jButtonAdd1.setBorderPainted(false);
        jButtonAdd1.addActionListener(this::jButtonAdd1ActionPerformed);
        jPanelItem1.add(jButtonAdd1);
        jButtonAdd1.setBounds(290, 20, 50, 22);
        jPanelItem1.add(jSeparator5);
        jSeparator5.setBounds(0, 50, 340, 10);

        jPanelMenu.add(jPanelItem1);
        jPanelItem1.setBounds(20, 330, 350, 60);

        jPanelItem2.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItem2.setLayout(null);

        jLabelItem1Name1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Name1.setText("Room Cleaning");
        jPanelItem2.add(jLabelItem1Name1);
        jLabelItem1Name1.setBounds(0, 0, 130, 16);

        jLabelItem1Price1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Price1.setText("$5.00");
        jPanelItem2.add(jLabelItem1Price1);
        jLabelItem1Price1.setBounds(0, 30, 60, 16);

        jButtonAdd2.setBackground(new java.awt.Color(30, 35, 55));
        jButtonAdd2.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonAdd2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd2.setText("+");
        jButtonAdd2.setBorderPainted(false);
        jButtonAdd2.addActionListener(this::jButtonAdd2ActionPerformed);
        jPanelItem2.add(jButtonAdd2);
        jButtonAdd2.setBounds(290, 20, 50, 22);
        jPanelItem2.add(jSeparator1);
        jSeparator1.setBounds(0, 50, 340, 10);

        jPanelMenu.add(jPanelItem2);
        jPanelItem2.setBounds(20, 80, 350, 60);

        jPanelItem3.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItem3.setLayout(null);

        jLabelItem1Name2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Name2.setText("Extra Blanket");
        jPanelItem3.add(jLabelItem1Name2);
        jLabelItem1Name2.setBounds(0, 0, 130, 16);

        jLabelItem1Price2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Price2.setText("$2.00");
        jPanelItem3.add(jLabelItem1Price2);
        jLabelItem1Price2.setBounds(0, 30, 60, 16);

        jButtonAdd3.setBackground(new java.awt.Color(30, 35, 55));
        jButtonAdd3.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonAdd3.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd3.setText("+");
        jButtonAdd3.setBorderPainted(false);
        jButtonAdd3.addActionListener(this::jButtonAdd3ActionPerformed);
        jPanelItem3.add(jButtonAdd3);
        jButtonAdd3.setBounds(290, 20, 50, 22);
        jPanelItem3.add(jSeparator2);
        jSeparator2.setBounds(0, 50, 340, 10);

        jPanelMenu.add(jPanelItem3);
        jPanelItem3.setBounds(20, 140, 350, 60);

        jPanelItem4.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItem4.setLayout(null);

        jLabelItem1Name3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Name3.setText("Laundry");
        jPanelItem4.add(jLabelItem1Name3);
        jLabelItem1Name3.setBounds(0, 0, 130, 16);

        jLabelItem1Price3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Price3.setText("$5.00");
        jPanelItem4.add(jLabelItem1Price3);
        jLabelItem1Price3.setBounds(0, 30, 60, 16);

        jButtonAdd4.setBackground(new java.awt.Color(30, 35, 55));
        jButtonAdd4.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonAdd4.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd4.setText("+");
        jButtonAdd4.setBorderPainted(false);
        jButtonAdd4.addActionListener(this::jButtonAdd4ActionPerformed);
        jPanelItem4.add(jButtonAdd4);
        jButtonAdd4.setBounds(290, 20, 50, 22);
        jPanelItem4.add(jSeparator3);
        jSeparator3.setBounds(0, 50, 340, 10);

        jPanelMenu.add(jPanelItem4);
        jPanelItem4.setBounds(20, 200, 350, 60);

        jPanelItem5.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItem5.setLayout(null);

        jLabelItem1Name4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Name4.setText("Gym AND Jumba");
        jPanelItem5.add(jLabelItem1Name4);
        jLabelItem1Name4.setBounds(0, 0, 130, 16);

        jLabelItem1Price4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabelItem1Price4.setText("$10.00");
        jPanelItem5.add(jLabelItem1Price4);
        jLabelItem1Price4.setBounds(0, 30, 60, 16);

        jButtonAdd5.setBackground(new java.awt.Color(30, 35, 55));
        jButtonAdd5.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jButtonAdd5.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAdd5.setText("+");
        jButtonAdd5.setBorderPainted(false);
        jButtonAdd5.addActionListener(this::jButtonAdd5ActionPerformed);
        jPanelItem5.add(jButtonAdd5);
        jButtonAdd5.setBounds(290, 20, 50, 22);
        jPanelItem5.add(jSeparator4);
        jSeparator4.setBounds(0, 50, 340, 10);

        jPanelMenu.add(jPanelItem5);
        jPanelItem5.setBounds(20, 260, 350, 60);

        jLabel1.setText("Room no.");
        jPanelMenu.add(jLabel1);
        jLabel1.setBounds(270, 0, 70, 20);

        jPanelCard.add(jPanelMenu);
        jPanelMenu.setBounds(10, 10, 390, 400);

        jPanelOrder.setBackground(new java.awt.Color(211, 228, 245));
        jPanelOrder.setLayout(null);

        jLabelYourOrder.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabelYourOrder.setForeground(new java.awt.Color(35, 40, 60));
        jLabelYourOrder.setText("Your Order");
        jPanelOrder.add(jLabelYourOrder);
        jLabelYourOrder.setBounds(10, 10, 80, 20);

        jLabelOrderBadge.setBackground(new java.awt.Color(37, 99, 235));
        jLabelOrderBadge.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabelOrderBadge.setForeground(new java.awt.Color(255, 255, 255));
        jLabelOrderBadge.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOrderBadge.setText("2");
        jLabelOrderBadge.setOpaque(true);
        jPanelOrder.add(jLabelOrderBadge);
        jLabelOrderBadge.setBounds(115, 10, 25, 20);

        jLabelOrderItem1Name.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabelOrderItem1Name.setText("Room Cleaning");
        jPanelOrder.add(jLabelOrderItem1Name);
        jLabelOrderItem1Name.setBounds(10, 50, 100, 20);

        jLabelOrderItem1Price.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabelOrderItem1Price.setText("$5.00");
        jPanelOrder.add(jLabelOrderItem1Price);
        jLabelOrderItem1Price.setBounds(135, 50, 40, 10);

        jLabelOrderItem2Name.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabelOrderItem2Name.setText("Extra Blanket");
        jPanelOrder.add(jLabelOrderItem2Name);
        jLabelOrderItem2Name.setBounds(10, 95, 120, 20);

        jLabelOrderItem2Price.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabelOrderItem2Price.setText("$2.00");
        jPanelOrder.add(jLabelOrderItem2Price);
        jLabelOrderItem2Price.setBounds(135, 95, 40, 15);

        jButtonItem2Remove.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jButtonItem2Remove.setForeground(new java.awt.Color(255, 0, 0));
        jButtonItem2Remove.setText("Remove");
        jButtonItem2Remove.setBorderPainted(false);
        jButtonItem2Remove.setContentAreaFilled(false);
        jPanelOrder.add(jButtonItem2Remove);
        jButtonItem2Remove.setBounds(10, 120, 65, 18);

        jSeparatorOrder.setForeground(new java.awt.Color(180, 195, 230));
        jPanelOrder.add(jSeparatorOrder);
        jSeparatorOrder.setBounds(10, 330, 160, 30);

        jLabelTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotal.setForeground(new java.awt.Color(25, 30, 50));
        jLabelTotal.setText("Total");
        jPanelOrder.add(jLabelTotal);
        jLabelTotal.setBounds(10, 340, 60, 18);

        jLabelTotalValue.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabelTotalValue.setForeground(new java.awt.Color(25, 30, 50));
        jLabelTotalValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelTotalValue.setText("$42.00");
        jPanelOrder.add(jLabelTotalValue);
        jLabelTotalValue.setBounds(100, 340, 70, 18);

        jButtonComplete.setBackground(new java.awt.Color(30, 35, 55));
        jButtonComplete.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jButtonComplete.setForeground(new java.awt.Color(255, 255, 255));
        jButtonComplete.setText("Request Service");
        jButtonComplete.setBorderPainted(false);
        jButtonComplete.setFocusPainted(false);
        jPanelOrder.add(jButtonComplete);
        jButtonComplete.setBounds(10, 370, 160, 35);

        jButtonItem2Remove1.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jButtonItem2Remove1.setForeground(new java.awt.Color(255, 0, 0));
        jButtonItem2Remove1.setText("Remove");
        jButtonItem2Remove1.setBorderPainted(false);
        jButtonItem2Remove1.setContentAreaFilled(false);
        jPanelOrder.add(jButtonItem2Remove1);
        jButtonItem2Remove1.setBounds(10, 70, 65, 18);

        jPanelCard.add(jPanelOrder);
        jPanelOrder.setBounds(410, 0, 180, 420);

        jPanel1.add(jPanelCard);
        jPanelCard.setBounds(10, 70, 590, 420);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(180, 0, 620, 500);

        jPanelSidebar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSidebar.setLayout(null);

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(37, 99, 235));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("HMS");
        jPanelSidebar.add(lblLogo);
        lblLogo.setBounds(10, 30, 140, 30);

        btnRooms.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnRooms.setText("  Room Browsing");
        btnRooms.setBorderPainted(false);
        btnRooms.setContentAreaFilled(false);
        btnRooms.setFocusPainted(false);
        btnRooms.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRooms.addActionListener(this::btnRoomsActionPerformed);
        jPanelSidebar.add(btnRooms);
        btnRooms.setBounds(10, 120, 160, 35);

        btnDiscounts.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnDiscounts.setText("  Order Food");
        btnDiscounts.setBorderPainted(false);
        btnDiscounts.setContentAreaFilled(false);
        btnDiscounts.setFocusPainted(false);
        btnDiscounts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDiscounts.addActionListener(this::btnDiscountsActionPerformed);
        jPanelSidebar.add(btnDiscounts);
        btnDiscounts.setBounds(10, 160, 160, 35);

        btnStaffs.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnStaffs.setText("  Feedback");
        btnStaffs.setBorderPainted(false);
        btnStaffs.setContentAreaFilled(false);
        btnStaffs.setFocusPainted(false);
        btnStaffs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStaffs.addActionListener(this::btnStaffsActionPerformed);
        jPanelSidebar.add(btnStaffs);
        btnStaffs.setBounds(10, 200, 160, 35);

        btnReports.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnReports.setText("  Logout");
        btnReports.setBorderPainted(false);
        btnReports.setContentAreaFilled(false);
        btnReports.setFocusPainted(false);
        btnReports.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReports.addActionListener(this::btnReportsActionPerformed);
        jPanelSidebar.add(btnReports);
        btnReports.setBounds(10, 240, 160, 35);

        btnSystemSetting.setBackground(new java.awt.Color(211, 228, 245));
        btnSystemSetting.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnSystemSetting.setForeground(new java.awt.Color(37, 99, 235));
        btnSystemSetting.setText("  Dashboard");
        btnSystemSetting.setBorderPainted(false);
        btnSystemSetting.setFocusPainted(false);
        btnSystemSetting.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSystemSetting.addActionListener(this::btnSystemSettingActionPerformed);
        jPanelSidebar.add(btnSystemSetting);
        btnSystemSetting.setBounds(10, 80, 160, 35);

        getContentPane().add(jPanelSidebar);
        jPanelSidebar.setBounds(0, 0, 180, 500);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomsActionPerformed
        // Handled by Controller
    }//GEN-LAST:event_btnRoomsActionPerformed

    private void btnDiscountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountsActionPerformed
        // Handled in other navigation sections
    }//GEN-LAST:event_btnDiscountsActionPerformed

    private void btnStaffsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffsActionPerformed
        // Handled by Controller
    }//GEN-LAST:event_btnStaffsActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        // Handled in other navigation sections
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnSystemSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSystemSettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSystemSettingActionPerformed

    private void jButtonAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdd1ActionPerformed
        // Handled in controller
    }//GEN-LAST:event_jButtonAdd1ActionPerformed

    private void jButtonAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAdd2ActionPerformed

    private void jButtonAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdd3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAdd3ActionPerformed

    private void jButtonAdd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdd4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAdd4ActionPerformed

    private void jButtonAdd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdd5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAdd5ActionPerformed

    private void jTextFieldRoomIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRoomIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRoomIdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiscounts;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton btnStaffs;
    private javax.swing.JButton btnSystemSetting;
    private javax.swing.JButton jButtonAdd1;
    private javax.swing.JButton jButtonAdd2;
    private javax.swing.JButton jButtonAdd3;
    private javax.swing.JButton jButtonAdd4;
    private javax.swing.JButton jButtonAdd5;
    private javax.swing.JButton jButtonComplete;
    private javax.swing.JButton jButtonItem2Remove;
    private javax.swing.JButton jButtonItem2Remove1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelGuestRole;
    private javax.swing.JLabel jLabelGuestServices;
    private javax.swing.JLabel jLabelHeaderTitle;
    private javax.swing.JLabel jLabelItem1Name;
    private javax.swing.JLabel jLabelItem1Name1;
    private javax.swing.JLabel jLabelItem1Name2;
    private javax.swing.JLabel jLabelItem1Name3;
    private javax.swing.JLabel jLabelItem1Name4;
    private javax.swing.JLabel jLabelItem1Price;
    private javax.swing.JLabel jLabelItem1Price1;
    private javax.swing.JLabel jLabelItem1Price2;
    private javax.swing.JLabel jLabelItem1Price3;
    private javax.swing.JLabel jLabelItem1Price4;
    private javax.swing.JLabel jLabelMenuTitle;
    private javax.swing.JLabel jLabelOrderBadge;
    private javax.swing.JLabel jLabelOrderItem1Name;
    private javax.swing.JLabel jLabelOrderItem1Price;
    private javax.swing.JLabel jLabelOrderItem2Name;
    private javax.swing.JLabel jLabelOrderItem2Price;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalValue;
    private javax.swing.JLabel jLabelYourOrder;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelItem1;
    private javax.swing.JPanel jPanelItem2;
    private javax.swing.JPanel jPanelItem3;
    private javax.swing.JPanel jPanelItem4;
    private javax.swing.JPanel jPanelItem5;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelOrder;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparatorOrder;
    private javax.swing.JTextField jTextFieldRoomId;
    private javax.swing.JLabel lblLogo;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnDashboard() { return btnSystemSetting; }
    public javax.swing.JButton getBtnRoomBrowsing() { return btnRooms; }
    public javax.swing.JButton getBtnOrderFood() { return btnDiscounts; }
    public javax.swing.JButton getBtnFeedback() { return btnStaffs; }
    public javax.swing.JButton getBtnLogout() { return btnReports; }
    public javax.swing.JButton getBtnSubmitRequest() { return jButtonComplete; }

    public javax.swing.JTextField getTxtRoomNo() { return jTextFieldRoomId; }

    // New getters for service selection and ordering
    public javax.swing.JButton getBtnAdd1() { return jButtonAdd1; }
    public javax.swing.JButton getBtnAdd2() { return jButtonAdd2; }
    public javax.swing.JButton getBtnAdd3() { return jButtonAdd3; }
    public javax.swing.JButton getBtnAdd4() { return jButtonAdd4; }
    public javax.swing.JButton getBtnAdd5() { return jButtonAdd5; }
    public javax.swing.JButton getBtnItem1Remove() { return jButtonItem2Remove; }
    public javax.swing.JButton getBtnItem2Remove() { return jButtonItem2Remove1; }
    public javax.swing.JLabel getLblOrderItem1Name() { return jLabelOrderItem1Name; }
    public javax.swing.JLabel getLblOrderItem1Price() { return jLabelOrderItem1Price; }
    public javax.swing.JLabel getLblOrderItem2Name() { return jLabelOrderItem2Name; }
    public javax.swing.JLabel getLblOrderItem2Price() { return jLabelOrderItem2Price; }
    public javax.swing.JLabel getLblTotalValue() { return jLabelTotalValue; }
    public javax.swing.JLabel getLblOrderBadge() { return jLabelOrderBadge; }
}
