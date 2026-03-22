import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardPanel {

    static JPanel contentContainer;
    static JButton btnDash, btnAppt, btnPat, btnPresc;

    public static JPanel createPanel() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        topBar.setBackground(Color.WHITE);

        JLabel logo = new JLabel("MINDANA CARE");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(MindanaCare.DARK_BLUE);
        try {
            ImageIcon logoImg = new ImageIcon("pictures/logo.png");
            if (logoImg.getIconWidth() > -1) {
                Image scaledLogo = logoImg.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                logo.setIcon(new ImageIcon(scaledLogo));
                logo.setIconTextGap(15);
            }
        } catch (Exception e) {}
        topBar.add(logo);

        main.add(topBar, BorderLayout.NORTH);

        JPanel sidebarWrapper = new JPanel(new BorderLayout());
        sidebarWrapper.setBackground(Color.WHITE);
        sidebarWrapper.setBorder(new EmptyBorder(0, 20, 20, 20));

        JPanel sidebar = MindanaCare.createRoundedPanel(40, Color.decode("#D8B4FE"), false);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(40, 10, 40, 10));

        btnDash = createSidebarButton("👤  Dashboard");
        btnAppt = createSidebarButton("📅  Appointment");
        btnPat = createSidebarButton("👤  Patient");
        btnPresc = createSidebarButton("📝  Prescription");

        sidebar.add(btnDash);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnAppt);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnPat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnPresc);

        sidebar.add(Box.createVerticalGlue());

        JButton btnSignOut = createSidebarButton("↪  Sign Out");
        btnSignOut.setForeground(Color.decode("#DC2626"));
        btnSignOut.addActionListener(e -> {
            MindanaCare.showMainApp();
        });
        sidebar.add(btnSignOut);

        sidebarWrapper.add(sidebar, BorderLayout.CENTER);
        main.add(sidebarWrapper, BorderLayout.WEST);

        contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBackground(Color.WHITE);
        main.add(contentContainer, BorderLayout.CENTER);

        setSidebarActive(btnDash);
        loadDashboardPage(createDashboardView());

        btnDash.addActionListener(e -> {
            setSidebarActive(btnDash);
            loadDashboardPage(createDashboardView());
        });

        btnAppt.addActionListener(e -> {
            setSidebarActive(btnAppt);
            loadDashboardPage(createAppointmentsView());
        });

        btnPat.addActionListener(e -> {
            setSidebarActive(btnPat);
            loadDashboardPage(PatientPage.createPanel());
        });

        btnPresc.addActionListener(e -> {
            setSidebarActive(btnPresc);
            loadDashboardPage(PrescriptionPage.createPanel());
        });

        return main;
    }

    static void setSidebarActive(JButton activeBtn) {
        JButton[] btns = {btnDash, btnAppt, btnPat, btnPresc};
        for (JButton b : btns) {
            b.putClientProperty("active", b == activeBtn);
            b.repaint();
        }
    }

    static void loadDashboardPage(JPanel page) {
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

    static JPanel createDashboardView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel card = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 60, 60, 60));

        JLabel greeting = new JLabel("<html><span style='font-weight:normal;'>Good Morning,</span> <b>Dr. Mohammad!</b></html>");
        greeting.setFont(new Font("Segoe UI", Font.PLAIN, 38));
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setForeground(Color.BLACK);

        JPanel statsGrid = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        statsGrid.setOpaque(false);

        statsGrid.add(createBigStatCard("25", "Pending<br>Appointment"));
        statsGrid.add(createBigStatCard("06", "Today's<br>Appointment"));
        statsGrid.add(createBigStatCard("30", "Total<br>Treatment"));

        card.add(greeting);
        card.add(Box.createRigidArea(new Dimension(0, 50)));
        card.add(statsGrid);

        wrapper.add(card);
        return wrapper;
    }

    static JPanel createBigStatCard(String number, String text) {
        JPanel card = MindanaCare.createRoundedPanel(30, Color.decode("#A8C7E6"), false);
        card.setPreferredSize(new Dimension(180, 160));
        card.setMaximumSize(new Dimension(180, 160));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 10, 20, 10));

        JLabel numLbl = new JLabel(number);
        numLbl.setFont(new Font("Segoe UI", Font.BOLD, 55));
        numLbl.setForeground(Color.BLACK);
        numLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel txtLbl = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
        txtLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtLbl.setForeground(Color.BLACK);
        txtLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(numLbl);
        card.add(Box.createVerticalGlue());
        card.add(txtLbl);

        return card;
    }

    static JPanel createAppointmentsView() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel mainCard = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainCard.setPreferredSize(new Dimension(850, 500));

        JLabel title = new JLabel("Appointment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        mainCard.add(titlePanel, BorderLayout.NORTH);

        JPanel tableCard = MindanaCare.createRoundedPanel(30, Color.decode("#DFE7F3"), false);
        tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));
        tableCard.setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel headerRow = new JPanel();
        headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
        headerRow.setOpaque(false);

        JLabel hNo = new JLabel("No.");
        hNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hNo.setPreferredSize(new Dimension(80, 30));

        JLabel hDate = new JLabel("Date");
        hDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hDate.setPreferredSize(new Dimension(150, 30));

        JLabel hName = new JLabel("Name");
        hName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hName.setPreferredSize(new Dimension(250, 30));

        headerRow.add(Box.createRigidArea(new Dimension(20, 0)));
        headerRow.add(hNo);
        headerRow.add(hDate);
        headerRow.add(hName);
        headerRow.add(Box.createHorizontalGlue());

        JPanel separator = new JPanel();
        separator.setBackground(Color.BLACK);
        separator.setMaximumSize(new Dimension(800, 2));

        tableCard.add(headerRow);
        tableCard.add(Box.createRigidArea(new Dimension(0, 10)));
        tableCard.add(separator);
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));

        tableCard.add(createTableRow("01", "07-25-26", "Aimy Asupan"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("02", "07-29-26", "Princess Lascuna"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("03", "08-3-26", "Lyvia Navarra"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("04", "08-6-26", "Gian Batuto"));

        mainCard.add(tableCard, BorderLayout.CENTER);
        wrapper.add(mainCard);

        return wrapper;
    }

    static JPanel createTableRow(String no, String date, String name) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(800, 40));

        JLabel lNo = new JLabel(no);
        lNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lNo.setPreferredSize(new Dimension(80, 30));

        JLabel lDate = new JLabel(date);
        lDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lDate.setPreferredSize(new Dimension(150, 30));

        JLabel lName = new JLabel(name);
        lName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lName.setPreferredSize(new Dimension(250, 30));

        JLabel check = new JLabel("✔");
        check.setFont(new Font("Segoe UI", Font.BOLD, 20));
        check.setForeground(MindanaCare.BRAND_BLUE);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel cross = new JLabel("✕");
        cross.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cross.setForeground(MindanaCare.BRAND_BLUE);
        cross.setCursor(new Cursor(Cursor.HAND_CURSOR));

        row.add(Box.createRigidArea(new Dimension(20, 0)));
        row.add(lNo);
        row.add(lDate);
        row.add(lName);
        row.add(Box.createHorizontalGlue());
        row.add(check);
        row.add(Box.createRigidArea(new Dimension(20, 0)));
        row.add(cross);
        row.add(Box.createRigidArea(new Dimension(20, 0)));

        return row;
    }
}