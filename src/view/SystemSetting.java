package view;

import controller.systemController;

/**
 * JFrame View representing the System Setting & Controls page.
 * Implements a high-fidelity desktop UI with elegant layouts and interactive elements.
 * Resized to 800x500 with a modern 20px rounded corner aesthetic on all panels, fields, and buttons.
 * Fully compatible with the NetBeans GUI Builder.
 *
 * @author i3
 */
public class SystemSetting extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SystemSetting.class.getName());
    private final systemController controller = new systemController();

    /**
     * Creates new form SystemSetting
     */
    public SystemSetting() {
        initComponents();
        
        // UI Sizing and Positioning
        setSize(800, 500);
        setMinimumSize(new java.awt.Dimension(800, 500));
        setLocationRelativeTo(null); // Center on screen
        setResizable(true); // Allow resizing / maximizing
        
        // Listen for resize / maximize events to scale absolute layouts
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustLayout();
            }
        });
        
        // Load data into fields on startup
        controller.loadSettings(this);
        
        // Initially fields are read-only
        setFieldsEditable(false);
    }

    public void setFieldsEditable(boolean editable) {
        txtHotelName.setEditable(editable);
        txtHotelId.setEditable(editable);
        txtAddress.setEditable(editable);
        txtPanNumber.setEditable(editable);
        txtOwner.setEditable(editable);
        txtQuickNote.setEditable(editable);
        txtPhone.setEditable(editable);
        txtWebsite.setEditable(editable);
        
        if (btnSave != null) {
            btnSave.setEnabled(editable);
        }
        if (btnEdit != null) {
            btnEdit.setEnabled(!editable);
        }
    }

    private void adjustLayout() {
        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();
        if (width < 800) width = 800;
        if (height < 500) height = 500;
        
        // Sidebar stays fixed width 180, stretches vertically
        jPanelSidebar.setBounds(0, 0, 180, height);
        
        // Main panel fills the rest
        int mainWidth = width - 180;
        jPanelMain.setBounds(180, 0, mainWidth, height);
        
        // Inside main panel:
        // jPanelHeader stretches horizontally
        jPanelHeader.setBounds(20, 15, mainWidth - 40, 40);
        
        int headerWidth = jPanelHeader.getWidth();
        lblAvatar.setBounds(headerWidth - 50, 0, 40, 40);
        lblRole.setBounds(headerWidth - 160, 5, 100, 30);
        
        // jPanel1 stretches horizontally
        jPanel1.setBounds(20, 80, mainWidth - 40, 200);
        int p1Width = jPanel1.getWidth();
        jSeparator1.setBounds(0, 40, p1Width, 10);
        
        int rightColX = p1Width / 2 + 10;
        int rightColWidth = p1Width - rightColX - 20;
        jLabel3.setBounds(rightColX, 60, 100, 16);
        txtAddress.setBounds(rightColX, 80, rightColWidth, 40);
        jLabel11.setBounds(rightColX, 130, 100, 16);
        txtPhone.setBounds(rightColX, 150, rightColWidth, 40);
        
        // Bottom panels: jPanel2 and jPanel3
        int bottomY = 290;
        int bottomHeight = height - bottomY - 10;
        
        int gap = 20;
        int availableWidth = mainWidth - 40;
        int p2Width = (int) (availableWidth * 0.38);
        int p3Width = availableWidth - p2Width - gap;
        
        jPanel2.setBounds(20, bottomY, p2Width, bottomHeight);
        int p2WidthReal = jPanel2.getWidth();
        jSeparator2.setBounds(0, 40, p2WidthReal, 10);
        txtOwner.setBounds(10, 70, p2WidthReal - 20, 30);
        txtPanNumber.setBounds(10, 130, p2WidthReal - 20, 30);
        
        jPanel3.setBounds(20 + p2Width + gap, bottomY, p3Width, bottomHeight);
        int p3WidthReal = jPanel3.getWidth();
        jSeparator3.setBounds(0, 40, 50, 10);
        jSeparator4.setBounds(0, 40, p3WidthReal, 10);
        
        txtWebsite.setBounds(10, 70, p3WidthReal / 2 - 20, 50);
        
        int noteX = p3WidthReal / 2;
        int noteWidth = p3WidthReal - noteX - 10;
        jLabel10.setBounds(noteX + 10, 50, 70, 16);
        jScrollPane2.setBounds(noteX, 70, noteWidth, 60);
        
        if (btnEdit != null) {
            btnEdit.setBounds(noteX, 150, 100, 30);
        }
        if (btnSave != null) {
            btnSave.setBounds(noteX + 110, 150, 100, 30);
        }
        
        jSeparator6.setBounds(0, 0, 10, height);
        
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // Getters and Setters for Controller binding
    public String getHotelNameText() {
        return txtHotelName.getText();
    }

    public void setHotelNameText(String text) {
        txtHotelName.setText(text);
    }

    public String getHotelIdText() {
        return txtHotelId.getText();
    }

    public void setHotelIdText(String text) {
        txtHotelId.setText(text);
    }

    public String getAddressText() {
        return txtAddress.getText();
    }

    public void setAddressText(String text) {
        txtAddress.setText(text);
    }

    public String getPanNumberText() {
        return txtPanNumber.getText();
    }

    public void setPanNumberText(String text) {
        txtPanNumber.setText(text);
    }

    public String getOwnerText() {
        return txtOwner.getText();
    }

    public void setOwnerText(String text) {
        txtOwner.setText(text);
    }

    public String getQuickNoteText() {
        return txtQuickNote.getText();
    }

    public void setQuickNoteText(String text) {
        txtQuickNote.setText(text);
    }

    public String getPhoneText() {
        return txtPhone.getText();
    }

    public void setPhoneText(String text) {
        txtPhone.setText(text);
    }

    public String getWebsiteText() {
        return txtWebsite.getText();
    }

    public void setWebsiteText(String text) {
        txtWebsite.setText(text);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField8 = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jPanelSidebar = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnRooms = new javax.swing.JButton();
        btnDiscounts = new javax.swing.JButton();
        btnStaffs = new javax.swing.JButton();
        btnSystemSetting = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblAvatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtHotelId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtHotelName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        txtPanNumber = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtOwner = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        txtWebsite = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtQuickNote = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("System Setting & Controls");
        getContentPane().setLayout(null);

        jPanelSidebar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSidebar.setLayout(null);

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(37, 99, 235));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setText("HMS");
        jPanelSidebar.add(lblLogo);
        lblLogo.setBounds(10, 30, 140, 30);

        btnDashboard.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnDashboard.setText("  Dashboard");
        btnDashboard.setBorderPainted(false);
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        jPanelSidebar.add(btnDashboard);
        btnDashboard.setBounds(10, 80, 160, 35);

        btnRooms.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnRooms.setText("  Rooms");
        btnRooms.setBorderPainted(false);
        btnRooms.setContentAreaFilled(false);
        btnRooms.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRooms.addActionListener(this::btnRoomsActionPerformed);
        jPanelSidebar.add(btnRooms);
        btnRooms.setBounds(10, 120, 160, 35);

        btnDiscounts.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnDiscounts.setText("  Discount & Offers");
        btnDiscounts.setBorderPainted(false);
        btnDiscounts.setContentAreaFilled(false);
        btnDiscounts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDiscounts.addActionListener(this::btnDiscountsActionPerformed);
        jPanelSidebar.add(btnDiscounts);
        btnDiscounts.setBounds(10, 160, 160, 35);

        btnStaffs.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnStaffs.setText("  Staffs");
        btnStaffs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnStaffs.setBorderPainted(false);
        btnStaffs.setContentAreaFilled(false);
        btnStaffs.addActionListener(this::btnStaffsActionPerformed);
        jPanelSidebar.add(btnStaffs);
        btnStaffs.setBounds(10, 200, 160, 35);

        btnSystemSetting.setBackground(new java.awt.Color(211, 228, 245));
        btnSystemSetting.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnSystemSetting.setForeground(new java.awt.Color(37, 99, 235));
        btnSystemSetting.setText("  System Setting");
        btnSystemSetting.setBorderPainted(false);
        btnSystemSetting.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSystemSetting.addActionListener(this::btnSystemSettingActionPerformed);
        jPanelSidebar.add(btnSystemSetting);
        btnSystemSetting.setBounds(10, 240, 160, 35);

        btnReports.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnReports.setText("  Reports");
        btnReports.setBorderPainted(false);
        btnReports.setContentAreaFilled(false);
        btnReports.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReports.addActionListener(this::btnReportsActionPerformed);
        jPanelSidebar.add(btnReports);
        btnReports.setBounds(10, 280, 160, 35);

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLogout.setText("  Logout");
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.addActionListener(this::btnLogoutActionPerformed);
        jPanelSidebar.add(btnLogout);
        btnLogout.setBounds(10, 320, 160, 35);

        getContentPane().add(jPanelSidebar);
        jPanelSidebar.setBounds(0, 0, 180, 500);

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));
        jPanelMain.setLayout(null);

        jPanelHeader.setBackground(new java.awt.Color(232, 236, 239));
        jPanelHeader.setLayout(null);

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(64, 64, 64));
        lblTitle.setText("System setting");
        jPanelHeader.add(lblTitle);
        lblTitle.setBounds(10, 5, 180, 30);

        lblRole.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRole.setForeground(new java.awt.Color(64, 64, 64));
        lblRole.setText("Manager");
        lblRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanelHeader.add(lblRole);
        lblRole.setBounds(420, 5, 100, 30);

        lblAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iamges/user (1).png"))); // NOI18N
        jPanelHeader.add(lblAvatar);
        lblAvatar.setBounds(530, 0, 40, 40);

        jPanelMain.add(jPanelHeader);
        jPanelHeader.setBounds(20, 15, 580, 40);

        jPanel1.setBackground(new java.awt.Color(211, 228, 245));
        jPanel1.setLayout(null);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(0, 40, 580, 3);

        jLabel2.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel2.setText("Hotel ID");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 130, 70, 16);

        txtHotelId.addActionListener(this::txtHotelIdActionPerformed);
        jPanel1.add(txtHotelId);
        txtHotelId.setBounds(10, 150, 260, 40);

        jLabel3.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel3.setText("Property Adress");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(310, 60, 100, 16);

        txtAddress.addActionListener(this::txtAddressActionPerformed);
        jPanel1.add(txtAddress);
        txtAddress.setBounds(310, 80, 260, 40);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Identity Details");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 10, 130, 30);

        jLabel7.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel7.setText("Hotel name");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 60, 70, 16);

        txtHotelName.addActionListener(this::txtHotelNameActionPerformed);
        jPanel1.add(txtHotelName);
        txtHotelName.setBounds(10, 80, 260, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Identity Details");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 10, 130, 30);

        txtPhone.addActionListener(this::txtPhoneActionPerformed);
        jPanel1.add(txtPhone);
        txtPhone.setBounds(310, 150, 250, 40);

        jLabel11.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel11.setText("Hotel Phone");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(310, 130, 100, 16);

        jPanelMain.add(jPanel1);
        jPanel1.setBounds(20, 80, 580, 200);

        jPanel2.setBackground(new java.awt.Color(211, 228, 245));
        jPanel2.setLayout(null);
        jPanel2.add(jSeparator2);
        jSeparator2.setBounds(0, 40, 210, 3);

        txtPanNumber.addActionListener(this::txtPanNumberActionPerformed);
        jPanel2.add(txtPanNumber);
        txtPanNumber.setBounds(10, 130, 190, 30);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Compliance");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 10, 130, 30);

        jLabel12.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel12.setText("Owner");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(10, 50, 70, 16);

        txtOwner.addActionListener(this::txtOwnerActionPerformed);
        jPanel2.add(txtOwner);
        txtOwner.setBounds(10, 70, 190, 30);

        jLabel13.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel13.setText("PAN Number");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(10, 110, 80, 16);

        jPanelMain.add(jPanel2);
        jPanel2.setBounds(20, 290, 210, 190);

        jPanel3.setBackground(new java.awt.Color(211, 228, 245));
        jPanel3.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Contact Informamtion");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(10, 10, 180, 30);
        jPanel3.add(jSeparator3);
        jSeparator3.setBounds(0, 40, 0, 3);
        jPanel3.add(jSeparator4);
        jSeparator4.setBounds(0, 40, 360, 10);

        jLabel10.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel10.setText("Quick Note");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(220, 50, 70, 16);

        txtWebsite.addActionListener(this::txtWebsiteActionPerformed);
        jPanel3.add(txtWebsite);
        txtWebsite.setBounds(10, 70, 190, 50);

        jLabel14.setFont(new java.awt.Font("Aparajita", 0, 18)); // NOI18N
        jLabel14.setText("Website");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(10, 50, 70, 16);

        btnEdit.setBackground(new java.awt.Color(51, 51, 255));
        btnEdit.setFont(new java.awt.Font("Aparajita", 1, 25)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);
        jPanel3.add(btnEdit);
        btnEdit.setBounds(100, 150, 100, 30);

        txtQuickNote.setColumns(20);
        txtQuickNote.setRows(5);
        txtQuickNote.setText("We give the best\nexperience to our\ncustomers");
        jScrollPane2.setViewportView(txtQuickNote);

        jPanel3.add(jScrollPane2);
        jScrollPane2.setBounds(210, 70, 140, 60);

        btnSave.setBackground(new java.awt.Color(51, 51, 255));
        btnSave.setFont(new java.awt.Font("Aparajita", 1, 25)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        jPanel3.add(btnSave);
        btnSave.setBounds(230, 150, 100, 30);

        jPanelMain.add(jPanel3);
        jPanel3.setBounds(240, 290, 360, 190);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanelMain.add(jSeparator6);
        jSeparator6.setBounds(0, 0, 10, 500);

        getContentPane().add(jPanelMain);
        jPanelMain.setBounds(180, 0, 620, 500);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        new view.gest_dashbord().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomsActionPerformed
        new view.BookRoom().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRoomsActionPerformed

    private void btnDiscountsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscountsActionPerformed
        // Handled in other navigation sections
    }//GEN-LAST:event_btnDiscountsActionPerformed

    private void btnStaffsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffsActionPerformed
        new view.StaffManagement().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnStaffsActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        // Handled in other navigation sections
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        new view.loginpage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnSystemSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSystemSettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSystemSettingActionPerformed

    private void txtHotelIdActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtAddressActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtHotelNameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtPanNumberActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtOwnerActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void txtWebsiteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        setFieldsEditable(true);
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        boolean success = controller.saveSettings(this);
        if (success) {
            setFieldsEditable(false);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SystemSetting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnDiscounts;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStaffs;
    private javax.swing.JButton btnSystemSetting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtHotelId;
    private javax.swing.JTextField txtHotelName;
    private javax.swing.JTextField txtOwner;
    private javax.swing.JTextField txtPanNumber;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextArea txtQuickNote;
    private javax.swing.JTextField txtWebsite;
    // End of variables declaration//GEN-END:variables
}
