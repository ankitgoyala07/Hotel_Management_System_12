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
        
        // Premium UI Polishing
        setSize(800, 500);
        setMinimumSize(new java.awt.Dimension(800, 500));
        setLocationRelativeTo(null); // Center on screen
        setResizable(false);
        
        applyPremiumStyles();
        
        // Load data into fields on startup
        controller.loadSettings(this);
    }

    /**
     * Applies rounded borders, paddings, and dynamic UI highlights.
     */
    private void applyPremiumStyles() {
        java.awt.Color fieldBorderColor = new java.awt.Color(203, 213, 225); // Slate 200
        java.awt.Color whiteColor = java.awt.Color.WHITE;
        java.awt.Color transparentColor = null;

        // Apply 20px Rounded Corners and Padding to TextFields
        RoundedBorder fieldBorder = new RoundedBorder(20, fieldBorderColor, whiteColor, new java.awt.Insets(6, 12, 6, 12));
        
        txtHotelName.setOpaque(false);
        txtHotelName.setBorder(fieldBorder);
        
        txtHotelId.setOpaque(false);
        txtHotelId.setBorder(fieldBorder);
        
        txtAddress.setOpaque(false);
        txtAddress.setBorder(fieldBorder);
        
        txtPanNumber.setOpaque(false);
        txtPanNumber.setBorder(fieldBorder);
        
        txtOwner.setOpaque(false);
        txtOwner.setBorder(fieldBorder);
        
        txtEmail.setOpaque(false);
        txtEmail.setBorder(fieldBorder);
        
        txtPhone.setOpaque(false);
        txtPhone.setBorder(fieldBorder);
        
        txtWebsite.setOpaque(false);
        txtWebsite.setBorder(fieldBorder);

        // Apply 20px Rounded Corners to Card and Title Banner
        jPanelCard.setOpaque(false);
        jPanelCard.setBorder(new RoundedBorder(20, transparentColor, new java.awt.Color(217, 217, 217), new java.awt.Insets(0, 0, 0, 0)));

        jPanelTitleBanner.setOpaque(false);
        jPanelTitleBanner.setBorder(new RoundedBorder(20, transparentColor, whiteColor, new java.awt.Insets(0, 0, 0, 0)));

        // Apply 20px Rounded Corners to Header Bar
        jPanelHeader.setOpaque(false);
        jPanelHeader.setBorder(new RoundedBorder(20, transparentColor, new java.awt.Color(232, 236, 239), new java.awt.Insets(0, 0, 0, 0)));

        // Style the active Sidebar Highlight with 20px rounded corners
        btnSystemSetting.setOpaque(false);
        btnSystemSetting.setContentAreaFilled(false);
        btnSystemSetting.setBorder(new RoundedBorder(20, transparentColor, new java.awt.Color(211, 228, 245), new java.awt.Insets(4, 16, 4, 16)));
        btnSystemSetting.setForeground(new java.awt.Color(37, 99, 235));   // Vibrant primary blue

        // Style other Sidebar Buttons (padding only)
        javax.swing.border.Border menuPadding = new javax.swing.border.EmptyBorder(4, 16, 4, 16);
        btnDashboard.setBorder(menuPadding);
        btnRooms.setBorder(menuPadding);
        btnDiscounts.setBorder(menuPadding);
        btnStaffs.setBorder(menuPadding);
        btnReports.setBorder(menuPadding);
        btnLogout.setBorder(menuPadding);

        // Style the Save Button with 20px rounded corners
        btnSave.setOpaque(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setBorder(new RoundedBorder(20, new java.awt.Color(37, 99, 235), new java.awt.Color(37, 99, 235), new java.awt.Insets(4, 15, 4, 15)));
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

    public String getEmailText() {
        return txtEmail.getText();
    }

    public void setEmailText(String text) {
        txtEmail.setText(text);
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
     * A highly customizable Rounded Border implementation that paints both custom
     * rounded backgrounds and solid rounded border outlines using Graphics2D antialiasing.
     */
    public static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        private final java.awt.Color borderColor;
        private final java.awt.Color backgroundColor;
        private final java.awt.Insets insets;

        public RoundedBorder(int radius, java.awt.Color borderColor, java.awt.Color backgroundColor, java.awt.Insets insets) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.backgroundColor = backgroundColor;
            this.insets = insets;
        }

        @Override
        public java.awt.Insets getBorderInsets(java.awt.Component c) {
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Paint filled background
            if (backgroundColor != null) {
                g2.setColor(backgroundColor);
                g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            }
            
            // Paint border outline
            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }
            
            g2.dispose();
        }
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
        jPanelCard = new javax.swing.JPanel();
        jPanelTitleBanner = new javax.swing.JPanel();
        lblCardTitle = new javax.swing.JLabel();
        lblHotelName = new javax.swing.JLabel();
        txtHotelName = new javax.swing.JTextField();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        lblOwner = new javax.swing.JLabel();
        txtOwner = new javax.swing.JTextField();
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblHotelId = new javax.swing.JLabel();
        txtHotelId = new javax.swing.JTextField();
        lblPanNumber = new javax.swing.JLabel();
        txtPanNumber = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblWebsite = new javax.swing.JLabel();
        txtWebsite = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("System Setting & Controls");
        getContentPane().setLayout(null);

        jPanelSidebar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSidebar.setLayout(null);

        lblLogo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(37, 99, 235));
        lblLogo.setText("HMS");
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
        btnReports.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReports.setBorderPainted(false);
        btnReports.setContentAreaFilled(false);
        btnReports.addActionListener(this::btnReportsActionPerformed);
        jPanelSidebar.add(btnReports);
        btnReports.setBounds(10, 280, 160, 35);

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLogout.setText("  Logout");
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
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

        jPanelCard.setBackground(new java.awt.Color(211, 228, 245));
        jPanelCard.setLayout(null);

        jPanelTitleBanner.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTitleBanner.setLayout(null);

        lblCardTitle.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblCardTitle.setForeground(new java.awt.Color(84, 98, 115));
        lblCardTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCardTitle.setText("Hotel Information");
        jPanelTitleBanner.add(lblCardTitle);
        lblCardTitle.setBounds(0, 0, 540, 40);

        jPanelCard.add(jPanelTitleBanner);
        jPanelTitleBanner.setBounds(20, 20, 540, 40);

        lblHotelName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHotelName.setForeground(new java.awt.Color(62, 72, 84));
        lblHotelName.setText("Hotel name");
        jPanelCard.add(lblHotelName);
        lblHotelName.setBounds(20, 80, 250, 20);

        txtHotelName.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtHotelName);
        txtHotelName.setBounds(20, 100, 250, 32);

        lblAddress.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(62, 72, 84));
        lblAddress.setText("Address");
        jPanelCard.add(lblAddress);
        lblAddress.setBounds(20, 145, 250, 20);

        txtAddress.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtAddress);
        txtAddress.setBounds(20, 165, 250, 32);

        lblOwner.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblOwner.setForeground(new java.awt.Color(62, 72, 84));
        lblOwner.setText("Owner");
        jPanelCard.add(lblOwner);
        lblOwner.setBounds(20, 210, 250, 20);

        txtOwner.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtOwner);
        txtOwner.setBounds(20, 230, 250, 32);

        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPhone.setForeground(new java.awt.Color(62, 72, 84));
        lblPhone.setText("Phone");
        jPanelCard.add(lblPhone);
        lblPhone.setBounds(20, 275, 250, 20);

        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtPhone);
        txtPhone.setBounds(20, 295, 250, 32);

        lblHotelId.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHotelId.setForeground(new java.awt.Color(62, 72, 84));
        lblHotelId.setText("Hotel ID");
        jPanelCard.add(lblHotelId);
        lblHotelId.setBounds(310, 80, 250, 20);

        txtHotelId.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtHotelId);
        txtHotelId.setBounds(310, 100, 250, 32);

        lblPanNumber.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPanNumber.setForeground(new java.awt.Color(62, 72, 84));
        lblPanNumber.setText("Pan number");
        jPanelCard.add(lblPanNumber);
        lblPanNumber.setBounds(310, 145, 250, 20);

        txtPanNumber.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtPanNumber);
        txtPanNumber.setBounds(310, 165, 250, 32);

        lblEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(62, 72, 84));
        lblEmail.setText("Email");
        jPanelCard.add(lblEmail);
        lblEmail.setBounds(310, 210, 250, 20);

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtEmail);
        txtEmail.setBounds(310, 230, 250, 32);

        lblWebsite.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblWebsite.setForeground(new java.awt.Color(62, 72, 84));
        lblWebsite.setText("Website");
        jPanelCard.add(lblWebsite);
        lblWebsite.setBounds(310, 275, 250, 20);

        txtWebsite.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jPanelCard.add(txtWebsite);
        txtWebsite.setBounds(310, 295, 250, 32);

        btnSave.setBackground(new java.awt.Color(37, 99, 235));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(this::btnSaveActionPerformed);
        jPanelCard.add(btnSave);
        btnSave.setBounds(440, 355, 120, 35);

        jPanelMain.add(jPanelCard);
        jPanelCard.setBounds(20, 70, 580, 410);

        getContentPane().add(jPanelMain);
        jPanelMain.setBounds(180, 0, 620, 500);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        controller.saveSettings(this);
    }//GEN-LAST:event_btnSaveActionPerformed

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
        // Handled in other navigation sections
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
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStaffs;
    private javax.swing.JButton btnSystemSetting;
    private javax.swing.JPanel jPanelCard;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelSidebar;
    private javax.swing.JPanel jPanelTitleBanner;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblCardTitle;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHotelId;
    private javax.swing.JLabel lblHotelName;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblOwner;
    private javax.swing.JLabel lblPanNumber;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblWebsite;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHotelId;
    private javax.swing.JTextField txtHotelName;
    private javax.swing.JTextField txtOwner;
    private javax.swing.JTextField txtPanNumber;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtWebsite;
    // End of variables declaration//GEN-END:variables
}
