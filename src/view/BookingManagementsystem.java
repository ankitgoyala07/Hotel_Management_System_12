package view;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import dao.DBConnection;
import com.toedter.calendar.JDateChooser;

public class BookingManagementsystem extends javax.swing.JFrame {

    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    
    private JTextField txtSearch;
    private JComboBox<String> cbRoomType;
    private JComboBox<String> cbStatus;
    
    // Modern colors matching Figma
    private static final Color COLOR_PRIMARY = new Color(51, 122, 255);      // Brand Blue
    private static final Color COLOR_PRIMARY_LIGHT = new Color(225, 238, 255); // Highlight Blue
    private static final Color COLOR_BG = new Color(240, 242, 245);           // Light Grey BG
    private static final Color COLOR_SIDEBAR_BG = Color.WHITE;
    private static final Color COLOR_CARD_BG = Color.WHITE;
    private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);        // Dark Charcoal
    private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);    // Muted Grey
    
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    
    public BookingManagementsystem() {
        setTitle("Hotel Management System - Bookings");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 680));
        setSize(1050, 680);
        setLocationRelativeTo(null);
        
        initComponents();
        loadRoomTypes();
        loadBookingsData();
    }

    private void initComponents() {
        // Main container panel
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(COLOR_BG);
        setContentPane(mainContainer);
        
        // -------------------------------------------------------------
        // 1. LEFT SIDEBAR PANEL
        // -------------------------------------------------------------
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 680));
        sidebarPanel.setBackground(COLOR_SIDEBAR_BG);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 224, 230)));
        
        // Logo Label
        JLabel lblLogo = new JLabel("HMS", JLabel.LEFT);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setForeground(COLOR_PRIMARY);
        lblLogo.setBorder(BorderFactory.createEmptyBorder(25, 25, 30, 25));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(lblLogo);
        
        // Navigation Buttons
        sidebarPanel.add(createSidebarButton("Dashboard", "👤", false, e -> {
            new admindashboard().setVisible(true);
            this.dispose();
        }));
        
        sidebarPanel.add(createSidebarButton("Rooms", "🏨", false, e -> {
            new roommanagement().setVisible(true);
            this.dispose();
        }));
        
        sidebarPanel.add(createSidebarButton("Bookings", "📅", true, e -> {
            // Already on this page
        }));
        
        sidebarPanel.add(createSidebarButton("Meal time", "🍳", false, e -> {
            JOptionPane.showMessageDialog(this, "Meal orders management is currently under construction.", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        }));
        
        sidebarPanel.add(createSidebarButton("Billing", "💳", false, e -> {
            JOptionPane.showMessageDialog(this, "Billing system is currently under construction.", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        }));
        
        // Vertical spacer
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Logout Button
        sidebarPanel.add(createSidebarButton("Logout", "🚪", false, e -> {
            new loginpage().setVisible(true);
            this.dispose();
        }));
        
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        mainContainer.add(sidebarPanel, BorderLayout.WEST);
        
        // -------------------------------------------------------------
        // 2. MAIN CENTER CONTENT AREA
        // -------------------------------------------------------------
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(COLOR_BG);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        
        // Top Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel lblTitle = new JLabel("Booking Management");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_DARK);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        // Profile Chip Panel
        JPanel profilePanel = new RoundedPanel(15, Color.WHITE);
        profilePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 8));
        profilePanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        
        JLabel lblProfile = new JLabel("Frontdesk", JLabel.CENTER);
        lblProfile.setFont(FONT_SUBTITLE);
        lblProfile.setForeground(COLOR_TEXT_DARK);
        
        JLabel lblProfileIcon = new JLabel("👤");
        lblProfileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        profilePanel.add(lblProfile);
        profilePanel.add(lblProfileIcon);
        headerPanel.add(profilePanel, BorderLayout.EAST);
        
        centerPanel.add(headerPanel, BorderLayout.NORTH);
        
        // -------------------------------------------------------------
        // 3. CENTRAL BOOKINGS CARD PANEL (White background, rounded corners)
        // -------------------------------------------------------------
        JPanel cardPanel = new RoundedPanel(20, COLOR_CARD_BG);
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(cardPanel, BorderLayout.CENTER);
        
        // Filter Controls Panel (Search, Date, Room Type, Status, New Booking Button)
        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setOpaque(false);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 12);
        gbc.weighty = 1.0;
        
        // Search Input
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(Color.WHITE);
        searchBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        
        JLabel lblSearchIcon = new JLabel("🔍 ");
        lblSearchIcon.setForeground(COLOR_TEXT_MUTED);
        txtSearch = new JTextField(12);
        txtSearch.setBorder(null);
        txtSearch.setFont(FONT_BODY);
        txtSearch.putClientProperty("JTextField.placeholderText", "e.g. John Doe");
        // Add real-time key search listener
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                loadBookingsData();
            }
        });
        
        searchBox.add(lblSearchIcon, BorderLayout.WEST);
        searchBox.add(txtSearch, BorderLayout.CENTER);
        
        JLabel lblSearchTag = new JLabel("SEARCH GUEST ID / NAME");
        lblSearchTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblSearchTag.setForeground(COLOR_TEXT_MUTED);
        
        JPanel pnlSearchField = new JPanel(new BorderLayout(0, 4));
        pnlSearchField.setOpaque(false);
        pnlSearchField.add(lblSearchTag, BorderLayout.NORTH);
        pnlSearchField.add(searchBox, BorderLayout.CENTER);
        
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        filterPanel.add(pnlSearchField, gbc);
        
        // Date Range Placeholder
        JLabel lblDateTag = new JLabel("DATE RANGE");
        lblDateTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblDateTag.setForeground(COLOR_TEXT_MUTED);
        
        String[] dates = {"All Dates", "Today", "This Week", "This Month"};
        JComboBox<String> cbDate = new JComboBox<>(dates);
        cbDate.setFont(FONT_BODY);
        cbDate.setBackground(Color.WHITE);
        cbDate.setPreferredSize(new Dimension(130, 32));
        
        JPanel pnlDateField = new JPanel(new BorderLayout(0, 4));
        pnlDateField.setOpaque(false);
        pnlDateField.add(lblDateTag, BorderLayout.NORTH);
        pnlDateField.add(cbDate, BorderLayout.CENTER);
        
        gbc.gridx = 1;
        gbc.weightx = 0.15;
        filterPanel.add(pnlDateField, gbc);
        
        // Room Type Combobox
        JLabel lblTypeTag = new JLabel("ROOM TYPE");
        lblTypeTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblTypeTag.setForeground(COLOR_TEXT_MUTED);
        
        cbRoomType = new JComboBox<>(new String[]{"All Types"});
        cbRoomType.setFont(FONT_BODY);
        cbRoomType.setBackground(Color.WHITE);
        cbRoomType.setPreferredSize(new Dimension(120, 32));
        cbRoomType.addActionListener(e -> loadBookingsData());
        
        JPanel pnlTypeField = new JPanel(new BorderLayout(0, 4));
        pnlTypeField.setOpaque(false);
        pnlTypeField.add(lblTypeTag, BorderLayout.NORTH);
        pnlTypeField.add(cbRoomType, BorderLayout.CENTER);
        
        gbc.gridx = 2;
        gbc.weightx = 0.15;
        filterPanel.add(pnlTypeField, gbc);
        
        // Status Combobox
        JLabel lblStatusTag = new JLabel("STATUS");
        lblStatusTag.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblStatusTag.setForeground(COLOR_TEXT_MUTED);
        
        String[] statuses = {"All Status", "Confirmed", "Checked-in", "Pending", "Cancelled"};
        cbStatus = new JComboBox<>(statuses);
        cbStatus.setFont(FONT_BODY);
        cbStatus.setBackground(Color.WHITE);
        cbStatus.setPreferredSize(new Dimension(120, 32));
        cbStatus.addActionListener(e -> loadBookingsData());
        
        JPanel pnlStatusField = new JPanel(new BorderLayout(0, 4));
        pnlStatusField.setOpaque(false);
        pnlStatusField.add(lblStatusTag, BorderLayout.NORTH);
        pnlStatusField.add(cbStatus, BorderLayout.CENTER);
        
        gbc.gridx = 3;
        gbc.weightx = 0.15;
        filterPanel.add(pnlStatusField, gbc);
        
        // New Booking Button
        RoundedButton btnNewBooking = new RoundedButton("+ New Booking", 10, COLOR_PRIMARY, Color.WHITE);
        btnNewBooking.setPreferredSize(new Dimension(130, 34));
        btnNewBooking.addActionListener(e -> {
            NewBookingDialog dialog = new NewBookingDialog(this);
            dialog.setVisible(true);
        });
        
        JPanel pnlButtonField = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 14));
        pnlButtonField.setOpaque(false);
        pnlButtonField.add(btnNewBooking);
        
        gbc.gridx = 4;
        gbc.weightx = 0.25;
        gbc.insets = new Insets(0, 0, 0, 0);
        filterPanel.add(pnlButtonField, gbc);
        
        cardPanel.add(filterPanel, BorderLayout.NORTH);
        
        // -------------------------------------------------------------
        // 4. BOOKINGS TABLE
        // -------------------------------------------------------------
        tableModel = new DefaultTableModel(
            new Object[][]{},
            new String[]{"GUEST NAME", "ROOM", "ROOM TYPE", "STAY DURATION", "STATUS", "TOTAL AMOUNT"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingsTable = new JTable(tableModel);
        bookingsTable.setFont(FONT_BODY);
        bookingsTable.setRowHeight(48); // Spacious row height
        bookingsTable.setShowGrid(false);
        bookingsTable.setIntercellSpacing(new Dimension(0, 0));
        bookingsTable.setSelectionBackground(COLOR_PRIMARY_LIGHT);
        bookingsTable.setSelectionForeground(COLOR_TEXT_DARK);
        
        // Custom Table Header
        JTableHeader tableHeader = bookingsTable.getTableHeader();
        tableHeader.setFont(FONT_TABLE_HEADER);
        tableHeader.setBackground(new Color(245, 247, 250));
        tableHeader.setForeground(COLOR_TEXT_MUTED);
        tableHeader.setPreferredSize(new Dimension(0, 36));
        tableHeader.setReorderingAllowed(false);
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 224, 230)));
        
        // Align headers and set renderers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);
        
        // Set column alignments/renderers
        TableColumnModel colModel = bookingsTable.getColumnModel();
        
        // Status badge column renderer
        colModel.getColumn(4).setCellRenderer(new BadgeCellRenderer());
        
        // Amount currency column renderer
        colModel.getColumn(5).setCellRenderer(new CurrencyRenderer());
        
        // Centered alignment for room column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        colModel.getColumn(1).setCellRenderer(centerRenderer);
        
        // JScrollPane around table
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        
        // -------------------------------------------------------------
        // 5. PAGINATION PANEL
        // -------------------------------------------------------------
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        paginationPanel.setOpaque(false);
        
        paginationPanel.add(createPageButton("<", false));
        paginationPanel.add(createPageButton("1", true));
        paginationPanel.add(createPageButton("2", false));
        paginationPanel.add(createPageButton("3", false));
        paginationPanel.add(new JLabel(" ... "));
        paginationPanel.add(createPageButton("10", false));
        paginationPanel.add(createPageButton(">", false));
        
        cardPanel.add(paginationPanel, BorderLayout.SOUTH);
    }
    
    // Sidebar Button creation helper
    private JButton createSidebarButton(String text, String icon, boolean isActive, ActionListener listener) {
        JButton btn = new JButton(icon + "   " + text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isActive) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(COLOR_PRIMARY_LIGHT);
                    g2.fillRoundRect(8, 2, getWidth() - 16, getHeight() - 4, 10, 10);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_SUBTITLE);
        btn.setForeground(isActive ? COLOR_PRIMARY : COLOR_TEXT_MUTED);
        btn.setBackground(COLOR_SIDEBAR_BG);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Hover effects
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isActive) {
                    btn.setForeground(COLOR_PRIMARY);
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isActive) {
                    btn.setForeground(COLOR_TEXT_MUTED);
                }
            }
        });
        
        if (listener != null) {
            btn.addActionListener(listener);
        }
        
        return btn;
    }
    
    // Page Button creation helper
    private JButton createPageButton(String text, boolean isActive) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isActive) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(COLOR_PRIMARY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(isActive ? Color.WHITE : COLOR_TEXT_MUTED);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(isActive ? COLOR_PRIMARY : new Color(220, 224, 230), 1, true));
        btn.setMargin(new Insets(0, 0, 0, 0));
        return btn;
    }
    
    // Loads available Room Types from DB
    private void loadRoomTypes() {
        cbRoomType.removeAllItems();
        cbRoomType.addItem("All Types");
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT room_type FROM rooms")) {
            
            while (rs.next()) {
                cbRoomType.addItem(rs.getString("room_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Core database loader method
    public void loadBookingsData() {
        tableModel.setRowCount(0);
        
        String search = txtSearch.getText().trim();
        String typeFilter = cbRoomType.getSelectedItem() != null ? cbRoomType.getSelectedItem().toString() : "All Types";
        String statusFilter = cbStatus.getSelectedItem() != null ? cbStatus.getSelectedItem().toString() : "All Status";
        
        StringBuilder sql = new StringBuilder(
            "SELECT g.first_name, g.last_name, b.room_number, r.room_type, b.check_in_date, b.check_out_date, b.status, bi.amount, b.booking_id " +
            "FROM bookings b " +
            "JOIN guests g ON b.guest_id = g.guest_id " +
            "LEFT JOIN rooms r ON b.room_number = r.room_number " +
            "LEFT JOIN billings bi ON b.booking_id = bi.booking_id " +
            "WHERE 1=1"
        );
        
        java.util.List<Object> params = new ArrayList<>();
        
        if (!search.isEmpty()) {
            sql.append(" AND (g.first_name LIKE ? OR g.last_name LIKE ? OR CONCAT(g.first_name, ' ', g.last_name) LIKE ?)");
            String searchParam = "%" + search + "%";
            params.add(searchParam);
            params.add(searchParam);
            params.add(searchParam);
        }
        
        if (!typeFilter.equals("All Types")) {
            sql.append(" AND r.room_type = ?");
            params.add(typeFilter);
        }
        
        if (!statusFilter.equals("All Status")) {
            String dbStatus = statusFilter;
            if (statusFilter.equals("Checked-in")) {
                dbStatus = "CheckedIn";
            }
            sql.append(" AND b.status = ?");
            params.add(dbStatus);
        }
        
        sql.append(" ORDER BY b.booking_id DESC");
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String guestName = rs.getString("first_name") + " " + rs.getString("last_name");
                    String roomNum = rs.getString("room_number");
                    if (roomNum == null) {
                        roomNum = "---";
                    }
                    String roomType = rs.getString("room_type");
                    if (roomType == null) {
                        roomType = "---";
                    }
                    java.sql.Date checkIn = rs.getDate("check_in_date");
                    java.sql.Date checkOut = rs.getDate("check_out_date");
                    String stayDuration = formatStayDuration(checkIn, checkOut);
                    String status = rs.getString("status");
                    double amount = rs.getDouble("amount");
                    
                    tableModel.addRow(new Object[]{
                        guestName,
                        roomNum,
                        roomType,
                        stayDuration,
                        status,
                        amount
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String formatStayDuration(java.sql.Date checkIn, java.sql.Date checkOut) {
        if (checkIn == null || checkOut == null) return "---";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd", java.util.Locale.ENGLISH);
        String inStr = sdf.format(checkIn);
        String outStr = sdf.format(checkOut);
        
        long diff = checkOut.getTime() - checkIn.getTime();
        long nights = diff / (1000 * 60 * 60 * 24);
        if (nights <= 0) nights = 1;
        
        return String.format("<html>%s - %s<br><font size='3' color='#888888'>%d Night%s</font></html>", 
            inStr, outStr, nights, nights > 1 ? "s" : "");
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BookingManagementsystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new BookingManagementsystem().setVisible(true));
    }
    
    // -----------------------------------------------------------------
    // INNER HELPER CLASSES FOR PREMIUM UI
    // -----------------------------------------------------------------
    
    // Rounded Panel Class
    public static class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }
    
    // Rounded Button Class
    public static class RoundedButton extends JButton {
        private int radius;
        private Color bgColor;
        private Color hoverBgColor;

        public RoundedButton(String text, int radius, Color bgColor, Color textColor) {
            super(text);
            this.radius = radius;
            this.bgColor = bgColor;
            this.hoverBgColor = bgColor.darker();
            setForeground(textColor);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setBackground(hoverBgColor);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(bgColor);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground() != null ? getBackground() : bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Custom Table Cell Renderers
    public static class BadgeCellRenderer implements TableCellRenderer {
        private final JPanel container = new JPanel(new GridBagLayout());
        private final JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        public BadgeCellRenderer() {
            container.setOpaque(true);
            label.setOpaque(false);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
            container.add(label);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (isSelected) {
                container.setBackground(table.getSelectionBackground());
            } else {
                container.setBackground(table.getBackground());
            }

            String status = value != null ? value.toString().toUpperCase() : "";
            
            if (status.equals("CHECKEDIN") || status.equals("CHECKED_IN")) {
                status = "CHECKED-IN";
            }

            label.setText(status);

            if (status.equals("CONFIRMED")) {
                label.setBackground(new Color(220, 245, 230)); // light green
                label.setForeground(new Color(15, 120, 60));   // dark green
            } else if (status.equals("CHECKED-IN")) {
                label.setBackground(new Color(225, 240, 255)); // light blue
                label.setForeground(new Color(15, 100, 220));  // dark blue
            } else if (status.equals("PENDING")) {
                label.setBackground(new Color(255, 243, 215)); // light orange
                label.setForeground(new Color(180, 100, 10));  // dark orange
            } else if (status.equals("CANCELLED")) {
                label.setBackground(new Color(255, 230, 230)); // light red
                label.setForeground(new Color(200, 30, 30));   // dark red
            } else {
                label.setBackground(new Color(240, 240, 240));
                label.setForeground(Color.DARK_GRAY);
            }
            
            return container;
        }
    }
    
    public static class CurrencyRenderer extends DefaultTableCellRenderer {
        public CurrencyRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            
            if (value instanceof Number) {
                setText(String.format("$%,.2f", ((Number) value).doubleValue()));
            } else if (value != null) {
                try {
                    double val = Double.parseDouble(value.toString());
                    setText(String.format("$%,.2f", val));
                } catch (NumberFormatException e) {
                    setText(value.toString());
                }
            }
            return this;
        }
    }
    
    // -----------------------------------------------------------------
    // "+ NEW BOOKING" MODAL DIALOG
    // -----------------------------------------------------------------
    public static class NewBookingDialog extends JDialog {
        private JComboBox<String> cbGuests;
        private JTextField txtFirstName;
        private JTextField txtLastName;
        private JTextField txtEmail;
        private JTextField txtPhone;
        private JTextField txtDocId;
        
        private JComboBox<String> cbRoomTypeSelect;
        private JComboBox<String> cbRoomSelect;
        private JDateChooser dcCheckIn;
        private JDateChooser dcCheckOut;
        private JComboBox<String> cbStatusSelect;
        
        private BookingManagementsystem parent;
        private Map<String, Integer> guestMap = new HashMap<>(); // Holds guestName -> guestID

        public NewBookingDialog(BookingManagementsystem parent) {
            super(parent, "Create New Booking", true);
            this.parent = parent;
            setSize(520, 560);
            setLocationRelativeTo(parent);
            setResizable(false);
            
            initComponents();
            loadGuests();
            loadRoomTypes();
            updateRoomSelection();
        }

        private void initComponents() {
            JPanel container = new JPanel(new BorderLayout());
            container.setBackground(COLOR_BG);
            container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            setContentPane(container);
            
            // Header
            JLabel lblHeader = new JLabel("Add New Booking", JLabel.CENTER);
            lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblHeader.setForeground(COLOR_TEXT_DARK);
            lblHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            container.add(lblHeader, BorderLayout.NORTH);
            
            // Form content inside a scrollable/scroll-free panel
            JPanel formPanel = new RoundedPanel(15, Color.WHITE);
            formPanel.setLayout(new GridBagLayout());
            formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            container.add(formPanel, BorderLayout.CENTER);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.weightx = 1.0;
            
            // 1. Guest Selection Row
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
            JLabel lblSelectGuest = new JLabel("Select Guest:");
            lblSelectGuest.setFont(FONT_TABLE_HEADER);
            formPanel.add(lblSelectGuest, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            cbGuests = new JComboBox<>();
            cbGuests.setFont(FONT_BODY);
            cbGuests.addActionListener(e -> toggleGuestFields());
            formPanel.add(cbGuests, gbc);
            
            // 2. New Guest details (collapsible or enabled/disabled)
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblFirstName = new JLabel("First Name:");
            lblFirstName.setFont(FONT_BODY);
            formPanel.add(lblFirstName, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtFirstName = new JTextField();
            txtFirstName.setFont(FONT_BODY);
            formPanel.add(txtFirstName, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblLastName = new JLabel("Last Name:");
            lblLastName.setFont(FONT_BODY);
            formPanel.add(lblLastName, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtLastName = new JTextField();
            txtLastName.setFont(FONT_BODY);
            formPanel.add(txtLastName, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblPhone = new JLabel("Phone:");
            lblPhone.setFont(FONT_BODY);
            formPanel.add(lblPhone, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtPhone = new JTextField();
            txtPhone.setFont(FONT_BODY);
            formPanel.add(txtPhone, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblEmail = new JLabel("Email:");
            lblEmail.setFont(FONT_BODY);
            formPanel.add(lblEmail, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtEmail = new JTextField();
            txtEmail.setFont(FONT_BODY);
            formPanel.add(txtEmail, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblDoc = new JLabel("ID / Document:");
            lblDoc.setFont(FONT_BODY);
            formPanel.add(lblDoc, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            txtDocId = new JTextField();
            txtDocId.setFont(FONT_BODY);
            formPanel.add(txtDocId, gbc);
            
            // Separator line
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 3;
            JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
            sep.setForeground(new Color(230, 230, 230));
            formPanel.add(sep, gbc);
            
            // 3. Booking Details
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblRoomType = new JLabel("Room Type:");
            lblRoomType.setFont(FONT_BODY);
            formPanel.add(lblRoomType, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            cbRoomTypeSelect = new JComboBox<>();
            cbRoomTypeSelect.setFont(FONT_BODY);
            cbRoomTypeSelect.addActionListener(e -> updateRoomSelection());
            formPanel.add(cbRoomTypeSelect, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblRoomNum = new JLabel("Room Number:");
            lblRoomNum.setFont(FONT_BODY);
            formPanel.add(lblRoomNum, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            cbRoomSelect = new JComboBox<>();
            cbRoomSelect.setFont(FONT_BODY);
            formPanel.add(cbRoomSelect, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblCheckIn = new JLabel("Check-in:");
            lblCheckIn.setFont(FONT_BODY);
            formPanel.add(lblCheckIn, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            dcCheckIn = new JDateChooser();
            dcCheckIn.setFont(FONT_BODY);
            dcCheckIn.setDate(new java.util.Date()); // default to today
            formPanel.add(dcCheckIn, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblCheckOut = new JLabel("Check-out:");
            lblCheckOut.setFont(FONT_BODY);
            formPanel.add(lblCheckOut, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            dcCheckOut = new JDateChooser();
            dcCheckOut.setFont(FONT_BODY);
            // Default to tomorrow
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 1);
            dcCheckOut.setDate(cal.getTime());
            formPanel.add(dcCheckOut, gbc);
            
            gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 1;
            JLabel lblStatus = new JLabel("Status:");
            lblStatus.setFont(FONT_BODY);
            formPanel.add(lblStatus, gbc);
            
            gbc.gridx = 1; gbc.gridwidth = 2;
            cbStatusSelect = new JComboBox<>(new String[]{"Confirmed", "CheckedIn", "Pending"});
            cbStatusSelect.setFont(FONT_BODY);
            formPanel.add(cbStatusSelect, gbc);
            
            // 4. Action Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
            
            RoundedButton btnCancel = new RoundedButton("Cancel", 8, new Color(220, 224, 230), COLOR_TEXT_DARK);
            btnCancel.setPreferredSize(new Dimension(90, 32));
            btnCancel.addActionListener(e -> dispose());
            
            RoundedButton btnSave = new RoundedButton("Save", 8, COLOR_PRIMARY, Color.WHITE);
            btnSave.setPreferredSize(new Dimension(90, 32));
            btnSave.addActionListener(e -> saveBooking());
            
            buttonPanel.add(btnCancel);
            buttonPanel.add(btnSave);
            container.add(buttonPanel, BorderLayout.SOUTH);
        }

        private void loadGuests() {
            cbGuests.removeAllItems();
            cbGuests.addItem("[Create New Guest]");
            guestMap.clear();
            
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT guest_id, first_name, last_name FROM guests ORDER BY first_name")) {
                
                while (rs.next()) {
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    int id = rs.getInt("guest_id");
                    cbGuests.addItem(name);
                    guestMap.put(name, id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        private void loadRoomTypes() {
            cbRoomTypeSelect.removeAllItems();
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT DISTINCT room_type FROM rooms")) {
                
                while (rs.next()) {
                    cbRoomTypeSelect.addItem(rs.getString("room_type"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        private void updateRoomSelection() {
            cbRoomSelect.removeAllItems();
            String type = cbRoomTypeSelect.getSelectedItem() != null ? cbRoomTypeSelect.getSelectedItem().toString() : "";
            if (type.isEmpty()) return;
            
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT room_number FROM rooms WHERE room_type = ? AND status = 'Available'")) {
                
                pstmt.setString(1, type);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        cbRoomSelect.addItem(rs.getString("room_number"));
                    }
                }
                
                // Add none option if no room available
                if (cbRoomSelect.getItemCount() == 0) {
                    cbRoomSelect.addItem("No available rooms");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void toggleGuestFields() {
            boolean isNew = cbGuests.getSelectedIndex() == 0;
            txtFirstName.setEnabled(isNew);
            txtLastName.setEnabled(isNew);
            txtEmail.setEnabled(isNew);
            txtPhone.setEnabled(isNew);
            txtDocId.setEnabled(isNew);
            
            if (!isNew) {
                // Fetch and populate details
                String selectedName = cbGuests.getSelectedItem().toString();
                int guestId = guestMap.get(selectedName);
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM guests WHERE guest_id = ?")) {
                    
                    pstmt.setInt(1, guestId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            txtFirstName.setText(rs.getString("first_name"));
                            txtLastName.setText(rs.getString("last_name"));
                            txtEmail.setText(rs.getString("email"));
                            txtPhone.setText(rs.getString("phone"));
                            txtDocId.setText(rs.getString("document_id"));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                txtFirstName.setText("");
                txtLastName.setText("");
                txtEmail.setText("");
                txtPhone.setText("");
                txtDocId.setText("");
            }
        }
        
        private void saveBooking() {
            // Validate dates
            java.util.Date dIn = dcCheckIn.getDate();
            java.util.Date dOut = dcCheckOut.getDate();
            
            if (dIn == null || dOut == null) {
                JOptionPane.showMessageDialog(this, "Please select check-in and check-out dates.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (dOut.before(dIn) || dOut.equals(dIn)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String roomNum = cbRoomSelect.getSelectedItem() != null ? cbRoomSelect.getSelectedItem().toString() : "";
            if (roomNum.isEmpty() || roomNum.equals("No available rooms")) {
                JOptionPane.showMessageDialog(this, "Please select an available room.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean isNewGuest = cbGuests.getSelectedIndex() == 0;
            String fName = txtFirstName.getText().trim();
            String lName = txtLastName.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String docId = txtDocId.getText().trim();
            
            if (isNewGuest && (fName.isEmpty() || lName.isEmpty() || phone.isEmpty())) {
                JOptionPane.showMessageDialog(this, "First Name, Last Name and Phone are required for new guests.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            java.sql.Date sqlCheckIn = new java.sql.Date(dIn.getTime());
            java.sql.Date sqlCheckOut = new java.sql.Date(dOut.getTime());
            String status = cbStatusSelect.getSelectedItem().toString();
            
            try (Connection conn = DBConnection.getConnection()) {
                conn.setAutoCommit(false);
                
                int guestId = -1;
                if (isNewGuest) {
                    // Create guest
                    String insertGuestSql = "INSERT INTO guests (first_name, last_name, email, phone, document_id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertGuestSql, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, fName);
                        pstmt.setString(2, lName);
                        pstmt.setString(3, email);
                        pstmt.setString(4, phone);
                        pstmt.setString(5, docId);
                        pstmt.executeUpdate();
                        
                        try (ResultSet rsKeys = pstmt.getGeneratedKeys()) {
                            if (rsKeys.next()) {
                                guestId = rsKeys.getInt(1);
                            }
                        }
                    }
                } else {
                    String selectedName = cbGuests.getSelectedItem().toString();
                    guestId = guestMap.get(selectedName);
                }
                
                if (guestId == -1) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Could not retrieve guest ID.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Fetch room price per night to calculate billing amount
                double pricePerNight = 0.0;
                try (PreparedStatement pstmt = conn.prepareStatement("SELECT price_per_night FROM rooms WHERE room_number = ?")) {
                    pstmt.setString(1, roomNum);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            pricePerNight = rs.getDouble("price_per_night");
                        }
                    }
                }
                
                // Calculate stay duration
                long diff = dOut.getTime() - dIn.getTime();
                long nights = diff / (1000 * 60 * 60 * 24);
                if (nights <= 0) nights = 1;
                double totalBilling = pricePerNight * nights;
                
                // Insert booking
                int bookingId = -1;
                String insertBookingSql = "INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertBookingSql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, guestId);
                    pstmt.setString(2, roomNum);
                    pstmt.setDate(3, sqlCheckIn);
                    pstmt.setDate(4, sqlCheckOut);
                    pstmt.setString(5, status);
                    pstmt.executeUpdate();
                    
                    try (ResultSet rsKeys = pstmt.getGeneratedKeys()) {
                        if (rsKeys.next()) {
                            bookingId = rsKeys.getInt(1);
                        }
                    }
                }
                
                if (bookingId == -1) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Could not insert booking.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Insert billing
                String insertBillingSql = "INSERT INTO billings (booking_id, amount, payment_status) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertBillingSql)) {
                    pstmt.setInt(1, bookingId);
                    pstmt.setDouble(2, totalBilling);
                    // If checked-in, mark paid, otherwise pending
                    pstmt.setString(3, status.equals("CheckedIn") ? "Paid" : "Pending");
                    pstmt.executeUpdate();
                }
                
                // Update room status
                if (status.equals("CheckedIn")) {
                    try (PreparedStatement pstmt = conn.prepareStatement("UPDATE rooms SET status = 'Occupied' WHERE room_number = ?")) {
                        pstmt.setString(1, roomNum);
                        pstmt.executeUpdate();
                    }
                }
                
                conn.commit();
                conn.setAutoCommit(true);
                
                JOptionPane.showMessageDialog(this, "Booking saved successfully!\nTotal Amount: $" + String.format("%.2f", totalBilling), "Success", JOptionPane.INFORMATION_MESSAGE);
                parent.loadBookingsData();
                dispose();
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
