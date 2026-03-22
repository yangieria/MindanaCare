import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class UserDashboard {

    private static final Color PRIMARY_BLUE = new Color(26, 79, 149);
    private static final Color ACCENT_BLUE = new Color(33, 113, 229);
    private static final Color GRADIENT_START = new Color(240, 245, 255);
    private static final Color GRADIENT_END = Color.WHITE;

    private static ArrayList<JButton> tabButtons;
    private static JPanel dynamicContentPanel;
    private static CardLayout cardLayout;

    public static JPanel createPanel() {
        tabButtons = new ArrayList<>();
        cardLayout = new CardLayout();

        JPanel mainWrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, GRADIENT_START, 0, getHeight(), GRADIENT_END);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel mainContent = new JPanel();
        mainContent.setOpaque(false);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(new EmptyBorder(50, 80, 40, 70));

        JLabel lblTitle = new JLabel("User Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(PRIMARY_BLUE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(lblTitle);
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        mainContent.add(createGradientGreeting());
        mainContent.add(Box.createRigidArea(new Dimension(0, 30)));

        mainContent.add(createTabRow());
        mainContent.add(Box.createRigidArea(new Dimension(0, 25)));

        dynamicContentPanel = new JPanel(cardLayout);
        dynamicContentPanel.setOpaque(false);

        dynamicContentPanel.add(createUpcomingPage(), "Upcoming");
        dynamicContentPanel.add(createHistoryPage(), "History");
        dynamicContentPanel.add(createPrescriptionPage(), "Prescriptions");

        mainContent.add(dynamicContentPanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 40)));
        mainContent.add(createBookingFooter());

        mainWrapper.add(mainContent, BorderLayout.NORTH);
        return mainWrapper;
    }

    private static JPanel createGradientGreeting() {
        User user = UserManager.getInstance().getCurrentUser();
        String name = user != null ? user.getFullName() : "Guest";

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(69, 105, 173), getWidth(), 0, new Color(33, 113, 229));
                g2d.setPaint(gp);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
            }
        };
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(1500, 80));
        panel.setBorder(new EmptyBorder(15, 25, 15, 25));

        // Get time of day for greeting
        java.time.LocalTime now = java.time.LocalTime.now();
        String timeGreeting = now.getHour() < 12 ? "Good Morning" : now.getHour() < 17 ? "Good Afternoon" : "Good Evening";

        JLabel greeting = new JLabel(timeGreeting + ", " + name + "!");
        greeting.setForeground(Color.WHITE);
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(greeting, BorderLayout.WEST);

        String timeStr = new java.text.SimpleDateFormat("hh:mm a").format(new java.util.Date());
        JLabel info = new JLabel("Davao City, Philippines | " + timeStr);
        info.setForeground(new Color(220, 220, 220));
        panel.add(info, BorderLayout.EAST);

        return panel;
    }

    private static JPanel createTabRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        row.setOpaque(false);
        row.add(createTabButton("Upcoming Appointment", "Upcoming", true));
        row.add(createTabButton("Medical History", "History", false));
        row.add(createTabButton("Prescriptions", "Prescriptions", false));
        return row;
    }

    private static JButton createTabButton(String text, String cardName, boolean active) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        updateTabStyle(btn, active);
        tabButtons.add(btn);

        btn.addActionListener(e -> {
            for (JButton b : tabButtons) updateTabStyle(b, false);
            updateTabStyle(btn, true);
            cardLayout.show(dynamicContentPanel, cardName);
        });

        return btn;
    }

    private static void updateTabStyle(JButton btn, boolean active) {
        btn.setBorder(active
                ? BorderFactory.createMatteBorder(0, 0, 4, 0, ACCENT_BLUE)
                : BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btn.setForeground(active ? ACCENT_BLUE : Color.GRAY);
    }

    private static JPanel createUpcomingPage() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        User user = UserManager.getInstance().getCurrentUser();
        if (user == null) return p;

        List<Appointment> upcoming = AppointmentManager.getInstance().getUpcoming(user.getEmail());

        if (upcoming.isEmpty()) {
            JLabel empty = new JLabel("You have no upcoming appointments.");
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            p.add(Box.createRigidArea(new Dimension(0, 20)));
            p.add(empty);
        } else {
            for (Appointment a : upcoming) {
                p.add(createAppointmentCard(a));
                p.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        return p;
    }

    private static JPanel createHistoryPage() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);

        User user = UserManager.getInstance().getCurrentUser();
        if (user == null) return p;

        List<Appointment> history = AppointmentManager.getInstance().getHistory(user.getEmail());

        if (history.isEmpty()) {
            JLabel empty = new JLabel("No appointment history found.");
            empty.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            p.add(Box.createRigidArea(new Dimension(0, 20)));
            p.add(empty);
        } else {
            for (Appointment a : history) {
                p.add(createAppointmentCard(a));
                p.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        return p;
    }

    private static JPanel createAppointmentCard(Appointment a) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2d.setColor(new Color(230, 235, 245));
                g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(1500, 120));
        card.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();

        // Left: doctor info
        JPanel textGroup = new JPanel();
        textGroup.setLayout(new BoxLayout(textGroup, BoxLayout.Y_AXIS));
        textGroup.setOpaque(false);

        JLabel title = new JLabel(a.getDoctorName());
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel subtitle = new JLabel(a.getSpecialty() + " — " + a.getLocation());
        subtitle.setForeground(Color.GRAY);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel statusLbl = new JLabel(a.getStatus());
        statusLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLbl.setForeground(
                a.getStatus().equals("Upcoming") || a.getStatus().equals("Approved")
                        ? new Color(22, 163, 74)
                        : new Color(220, 38, 38)
        );

        textGroup.add(title);
        textGroup.add(subtitle);
        textGroup.add(statusLbl);

        gbc.gridx = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        card.add(textGroup, gbc);

        // Center: date/time
        JLabel dateLbl = new JLabel(a.getDate() + " at " + a.getTime());
        dateLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLbl.setForeground(ACCENT_BLUE);
        gbc.gridx = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 20, 0, 20);
        card.add(dateLbl, gbc);

        // Right: cancel button (only for upcoming)
        if (a.getStatus().equals("Upcoming")) {
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            cancelBtn.setForeground(Color.WHITE);
            cancelBtn.setBackground(new Color(220, 38, 38));
            cancelBtn.setBorderPainted(false);
            cancelBtn.setOpaque(true);
            cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cancelBtn.setPreferredSize(new Dimension(90, 35));

            cancelBtn.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Cancel appointment with " + a.getDoctorName() + "\non " + a.getDate() + " at " + a.getTime() + "?",
                        "Cancel Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    AppointmentManager.getInstance().cancel(a);
                    JOptionPane.showMessageDialog(null, "Appointment cancelled successfully.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    MindanaCare.loadPage(UserDashboard.createPanel());
                }
            });

            gbc.gridx = 2; gbc.insets = new Insets(0, 0, 0, 0);
            card.add(cancelBtn, gbc);
        }

        return card;
    }

    private static JPanel createPrescriptionPage() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(createRoundedAppointmentCard("Amoxicillin 500mg", "Take 3x a day after meals", "Active"));
        p.add(Box.createRigidArea(new Dimension(0, 15)));
        p.add(createRoundedAppointmentCard("Paracetamol 500mg", "For fever or headache", "As needed"));
        return p;
    }

    private static JPanel createRoundedAppointmentCard(String mainText, String subText, String dateText) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2d.setColor(new Color(230, 235, 245));
                g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 20, 20));
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(1500, 120));
        card.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel textGroup = new JPanel();
        textGroup.setLayout(new BoxLayout(textGroup, BoxLayout.Y_AXIS));
        textGroup.setOpaque(false);

        JLabel title = new JLabel(mainText);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel subtitle = new JLabel(subText);
        subtitle.setForeground(Color.GRAY);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        textGroup.add(title);
        textGroup.add(subtitle);

        gbc.gridx = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        card.add(textGroup, gbc);

        JLabel dateLbl = new JLabel(dateText);
        dateLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dateLbl.setForeground(ACCENT_BLUE);
        gbc.gridx = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        card.add(dateLbl, gbc);

        return card;
    }

    private static JPanel createBookingFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);

        JLabel msg = new JLabel("Need a new checkup?");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        msg.setForeground(PRIMARY_BLUE);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton bookBtn = MindanaCare.createRoundedButton("Book Appointment", ACCENT_BLUE, Color.WHITE, false);
        bookBtn.setMaximumSize(new Dimension(250, 55));
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookBtn.addActionListener(e -> MindanaCare.loadPage(AppointmentsPage.createPanel()));

        footer.add(msg);
        footer.add(Box.createRigidArea(new Dimension(0, 15)));
        footer.add(bookBtn);
        return footer;
    }
}