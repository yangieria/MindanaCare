import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDashboard {

    static JPanel contentContainer;
    static JButton btnDash, btnUsers, btnAppts, btnDoctors, btnPresc, btnPending;

    public static JPanel createPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel logoLeft = new JLabel("MINDANA CARE");
        logoLeft.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLeft.setForeground(MindanaCare.DARK_BLUE);
        java.net.URL iconURL = AdminDashboard.class.getResource("img_1.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoLeft.setIcon(new ImageIcon(scaled));
            logoLeft.setIconTextGap(10);
        }

        JLabel adminBadge = new JLabel("ADMIN DASHBOARD");
        adminBadge.setFont(new Font("Segoe UI", Font.BOLD, 24));
        adminBadge.setForeground(MindanaCare.DARK_BLUE);
        adminBadge.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(logoLeft, BorderLayout.WEST);
        topBar.add(adminBadge, BorderLayout.CENTER);
        main.add(topBar, BorderLayout.NORTH);

        JPanel sidebarWrapper = new JPanel(new BorderLayout());
        sidebarWrapper.setBackground(Color.WHITE);
        sidebarWrapper.setBorder(new EmptyBorder(0, 20, 20, 20));

        JPanel sidebar = MindanaCare.createRoundedPanel(40, Color.decode("#D8B4FE"), false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(40, 10, 40, 10));

        btnDash    = createSidebarButton("🏠  Dashboard");
        btnUsers   = createSidebarButton("👤  Users");
        btnAppts   = createSidebarButton("📅  Appointments");
        btnPending = createSidebarButton("⏳  Pending Bookings");
        btnDoctors = createSidebarButton("👨️  Doctors");
        btnPresc   = createSidebarButton("💊  Prescriptions");

        sidebar.add(btnDash);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnUsers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnAppts);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnPending);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnDoctors);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnPresc);
        sidebar.add(Box.createVerticalGlue());

        JButton btnSignOut = createSidebarButton("↪  Sign Out");
        btnSignOut.setForeground(Color.decode("#DC2626"));
        btnSignOut.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Admin logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            MindanaCare.showMainApp();
        });
        sidebar.add(btnSignOut);

        sidebarWrapper.add(sidebar, BorderLayout.CENTER);
        main.add(sidebarWrapper, BorderLayout.WEST);

        contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBackground(Color.WHITE);
        main.add(contentContainer, BorderLayout.CENTER);

        setSidebarActive(btnDash);
        loadContent(createDashboardView());

        btnDash.addActionListener(e -> { setSidebarActive(btnDash); loadContent(createDashboardView()); });
        btnUsers.addActionListener(e -> { setSidebarActive(btnUsers); loadContent(createUsersView()); });
        btnAppts.addActionListener(e -> { setSidebarActive(btnAppts); loadContent(createAppointmentsView()); });
        btnPending.addActionListener(e -> { setSidebarActive(btnPending); loadContent(createPendingBookingsView()); });
        btnDoctors.addActionListener(e -> { setSidebarActive(btnDoctors); loadContent(createDoctorsView()); });
        btnPresc.addActionListener(e -> { setSidebarActive(btnPresc); loadContent(createPrescriptionsView()); });

        return main;
    }

    static JPanel createDashboardView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel card = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 60, 60, 60));

        JLabel greeting = new JLabel("<html><span style='font-weight:normal;'>Welcome,</span> <b>Administrator!</b></html>");
        greeting.setFont(new Font("Segoe UI", Font.PLAIN, 38));
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel statsGrid = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        statsGrid.setOpaque(false);

        int totalUsers = UserManager.getInstance().getAllUsers().size();
        int totalAppts = AppointmentManager.getInstance().getAllAppointments().size();
        long upcoming  = AppointmentManager.getInstance().getAllAppointments()
                .stream().filter(a -> a.getStatus().equals("Upcoming")).count();
        long cancelled = AppointmentManager.getInstance().getAllAppointments()
                .stream().filter(a -> a.getStatus().equals("Cancelled")).count();

        statsGrid.add(createBigStatCard(String.valueOf(totalUsers),  "Registered<br>Users"));
        statsGrid.add(createBigStatCard(String.valueOf(totalAppts),  "Total<br>Appointments"));
        statsGrid.add(createBigStatCard(String.valueOf(upcoming),    "Upcoming<br>Appointments"));
        statsGrid.add(createBigStatCard(String.valueOf(cancelled),   "Cancelled<br>Appointments"));

        card.add(greeting);
        card.add(Box.createRigidArea(new Dimension(0, 50)));
        card.add(statsGrid);

        wrapper.add(card);
        return wrapper;
    }

    static JPanel createUsersView() {
        String[] cols = {"#", "Full Name", "Email", "Contact Number", "Total Appointments"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        List<User> users = UserManager.getInstance().getAllUsers();
        int i = 1;
        for (User u : users) {
            long count = AppointmentManager.getInstance().getForUser(u.getEmail()).size();
            model.addRow(new Object[]{i++, u.getFullName(), u.getEmail(), u.getContactNumber(), count});
        }
        return buildTableView("Registered Users", model,
                statChip("Total: " + users.size(), new Color(59, 130, 246)));
    }

    static JPanel createAppointmentsView() {
        String[] cols = {"#", "Patient Email", "Doctor", "Specialty", "Date", "Time", "Location", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        List<Appointment> all = AppointmentManager.getInstance().getAllAppointments();
        int i = 1;
        for (Appointment a : all)
            model.addRow(new Object[]{i++, a.getUserEmail(), a.getDoctorName(), a.getSpecialty(),
                    a.getDate(), a.getTime(), a.getLocation(), a.getStatus()});

        long upcoming  = all.stream().filter(a -> a.getStatus().equals("Upcoming")).count();
        long cancelled = all.stream().filter(a -> a.getStatus().equals("Cancelled")).count();

        JPanel chips = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chips.setOpaque(false);
        chips.add(statChip("Total: " + all.size(), new Color(59, 130, 246)));
        chips.add(statChip("Upcoming: " + upcoming, new Color(22, 163, 74)));
        chips.add(statChip("Cancelled: " + cancelled, new Color(220, 38, 38)));
        return buildTableView("All Booked Appointments", model, chips);
    }

    static JPanel createPendingBookingsView() {
        List<Appointment> pending = AppointmentManager.getInstance().getAllAppointments()
                .stream().filter(a -> a.getStatus().equals("Upcoming"))
                .collect(Collectors.toList());

        String[] cols = {"#", "Patient Email", "Doctor", "Specialty", "Date", "Time", "Approve", "Reject"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return c == 6 || c == 7; }
        };

        int i = 1;
        for (Appointment a : pending)
            model.addRow(new Object[]{i++, a.getUserEmail(), a.getDoctorName(),
                    a.getSpecialty(), a.getDate(), a.getTime(), "✔ Approve", "✕ Reject"});

        JTable table = styledTable(model);
        table.setRowHeight(45);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        // Approve column
        table.getColumn("Approve").setCellRenderer((t, val, sel, foc, row, col) -> {
            JButton btn = new JButton("✔ Approve");
            btn.setBackground(new Color(22, 163, 74));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            return btn;
        });
        table.getColumn("Approve").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            public Component getTableCellEditorComponent(JTable t, Object val, boolean sel, int row, int col) {
                JButton btn = new JButton("✔ Approve");
                btn.setBackground(new Color(22, 163, 74));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
                btn.setBorderPainted(false);
                btn.setOpaque(true);
                btn.addActionListener(e -> {
                    if (row < pending.size()) {
                        pending.get(row).setStatus("Approved");
                        AppointmentManager.getInstance().saveAll();
                        JOptionPane.showMessageDialog(null, "Appointment approved!", "Approved", JOptionPane.INFORMATION_MESSAGE);
                        fireEditingStopped();
                        loadContent(createPendingBookingsView());
                    }
                });
                return btn;
            }
        });

        // Reject column
        table.getColumn("Reject").setCellRenderer((t, val, sel, foc, row, col) -> {
            JButton btn = new JButton("✕ Reject");
            btn.setBackground(new Color(220, 38, 38));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            return btn;
        });
        table.getColumn("Reject").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            public Component getTableCellEditorComponent(JTable t, Object val, boolean sel, int row, int col) {
                JButton btn = new JButton("✕ Reject");
                btn.setBackground(new Color(220, 38, 38));
                btn.setForeground(Color.WHITE);
                btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
                btn.setBorderPainted(false);
                btn.setOpaque(true);
                btn.addActionListener(e -> {
                    if (row < pending.size()) {
                        pending.get(row).setStatus("Cancelled");
                        AppointmentManager.getInstance().saveAll();
                        JOptionPane.showMessageDialog(null, "Appointment rejected.", "Rejected", JOptionPane.WARNING_MESSAGE);
                        fireEditingStopped();
                        loadContent(createPendingBookingsView());
                    }
                });
                return btn;
            }
        });

        JPanel chips = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chips.setOpaque(false);
        chips.add(statChip("Pending: " + pending.size(), new Color(234, 179, 8)));

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel mainCard = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainCard.setPreferredSize(new Dimension(900, 550));

        JLabel title = new JLabel("Pending Bookings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(titlePanel, BorderLayout.NORTH);
        chips.setBorder(new EmptyBorder(0, 0, 15, 0));
        north.add(chips, BorderLayout.SOUTH);

        JPanel tableCard = MindanaCare.createRoundedPanel(30, Color.decode("#DFE7F3"), false);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        mainCard.add(north, BorderLayout.NORTH);
        mainCard.add(tableCard, BorderLayout.CENTER);
        wrapper.add(mainCard);
        return wrapper;
    }

    static JPanel createDoctorsView() {
        String[][] doctorData = {
                {"Vinese Yap",       "Cardiologist",       "₱800"},
                {"Aria Pelobello",   "Neurologist",        "₱900"},
                {"Celine Mohammad",  "Orthopedic",         "₱750"},
                {"Princess Lascuna", "Cardiologist",       "₱500"},
                {"Aimy Asupan",      "Dermatologist",      "₱450"},
                {"Dave Malunao",     "Neurologist",        "₱600"},
                {"John Smith",       "General Physician",  "₱400"},
                {"Jane Doe",         "Pediatrician",       "₱550"},
                {"Mark Reyes",       "Surgeon",            "₱1200"},
                {"Sara Tan",         "Dentist",            "₱300"},
                {"Paul Lim",         "Psychiatrist",       "₱850"},
                {"Maria Santos",     "Gastroenterologist", "₱700"},
                {"Ramon Cruz",       "Physical Therapy",   "₱500"},
                {"Anna Reyes",       "OPD",                "₱350"}
        };

        String[] cols = {"#", "Doctor Name", "Specialty", "Consultation Fee", "Appointments Booked"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        int i = 1;
        for (String[] d : doctorData) {
            long booked = AppointmentManager.getInstance().getAllAppointments()
                    .stream().filter(a -> a.getDoctorName().equals("Dr. " + d[0])).count();
            model.addRow(new Object[]{i++, "Dr. " + d[0], d[1], d[2], booked});
        }
        return buildTableView("Doctor Registry", model,
                statChip("Total Doctors: " + doctorData.length, new Color(59, 130, 246)));
    }

    static JPanel createPrescriptionsView() {
        String[] cols = {"#", "Patient Email", "Doctor", "Specialty", "Date", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        List<Appointment> all = AppointmentManager.getInstance().getAllAppointments();
        int i = 1;
        for (Appointment a : all)
            model.addRow(new Object[]{i++, a.getUserEmail(), a.getDoctorName(),
                    a.getSpecialty(), a.getDate(), a.getTime(), a.getStatus()});
        return buildTableView("Prescription Records", model,
                statChip("Total Records: " + all.size(), new Color(59, 130, 246)));
    }

    private static JPanel buildTableView(String titleText, DefaultTableModel model, JComponent chipsPanel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel mainCard = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(titlePanel, BorderLayout.NORTH);
        if (chipsPanel != null) {
            chipsPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
            north.add(chipsPanel, BorderLayout.SOUTH);
        }

        JTable table = styledTable(model);
        table.setFillsViewportHeight(true);

        // Make columns fill full width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        mainCard.add(north, BorderLayout.NORTH);
        mainCard.add(scroll, BorderLayout.CENTER);
        wrapper.add(mainCard, BorderLayout.CENTER);
        return wrapper;
    }

    static JPanel createBigStatCard(String number, String text) {
        JPanel card = MindanaCare.createRoundedPanel(30, Color.decode("#A8C7E6"), false);
        card.setPreferredSize(new Dimension(180, 160));
        card.setMaximumSize(new Dimension(180, 160));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 10, 20, 10));

        JLabel numLbl = new JLabel(number);
        numLbl.setFont(new Font("Segoe UI", Font.BOLD, 48));
        numLbl.setForeground(Color.BLACK);
        numLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel txtLbl = new JLabel("<html><div style='text-align:center;'>" + text + "</div></html>");
        txtLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtLbl.setForeground(Color.BLACK);
        txtLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(numLbl);
        card.add(Box.createVerticalGlue());
        card.add(txtLbl);
        return card;
    }

    private static JTable styledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(MindanaCare.DARK_BLUE);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(MindanaCare.DARK_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                                                           boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setHorizontalAlignment(SwingConstants.CENTER); // <-- add this
                if (!sel) setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 255));
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (val != null && val.toString().equals("Upcoming"))
                    setForeground(new Color(22, 163, 74));
                else if (val != null && val.toString().equals("Cancelled"))
                    setForeground(new Color(220, 38, 38));
                else if (val != null && val.toString().equals("Approved"))
                    setForeground(new Color(59, 130, 246));
                else
                    setForeground(Color.BLACK);
                return this;
            }
        });
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        return table;
    }

    private static JLabel statChip(String text, Color color) {
        JLabel lbl = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBorder(new EmptyBorder(5, 15, 5, 15));
        lbl.setOpaque(false);
        return lbl;
    }

    static void setSidebarActive(JButton activeBtn) {
        JButton[] btns = {btnDash, btnUsers, btnAppts, btnPending, btnDoctors, btnPresc};
        for (JButton b : btns) {
            b.putClientProperty("active", b == activeBtn);
            b.repaint();
        }
    }

    static void loadContent(JPanel page) {
        contentContainer.removeAll();
        contentContainer.add(page, BorderLayout.CENTER);
        contentContainer.revalidate();
        contentContainer.repaint();
    }

    static JButton createSidebarButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            public void paintComponent(Graphics g) {
                Boolean isActive = (Boolean) getClientProperty("active");
                if (isActive != null && isActive) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.BLACK);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setMaximumSize(new Dimension(200, 45));
        return btn;
    }
}