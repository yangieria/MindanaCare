import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DoctorsProfile {

    private static final Color ACCENT_BLUE = new Color(33, 113, 229);
    private static final Color LIGHT_BG = new Color(240, 245, 255);

    public static JPanel createPanel(String name, String spec, String fee) {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(LIGHT_BG);
        main.setBorder(new EmptyBorder(40, 60, 40, 60));

        // 1. Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        JButton btnBack = MindanaCare.createRoundedButton("← Back", Color.WHITE, ACCENT_BLUE, false);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> MindanaCare.loadPage(Doctors.createPanel()));

        JLabel title = new JLabel("Doctor's Profile Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(btnBack, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        topBar.setBorder(new EmptyBorder(0, 0, 40, 0));

        main.add(topBar, BorderLayout.NORTH);

        // 2. White Card
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 40, 40));
                g2d.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(50, 50, 50, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 40);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        // Left: Image
        JPanel imgPlaceholder = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(220, 230, 250));
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.dispose();
            }
        };
        imgPlaceholder.setPreferredSize(new Dimension(320, 380));
        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(imgPlaceholder, gbc);

        // Right: Details
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);

        Component[] comps = {
                createLabel("Dr. " + name + ", MD", new Font("Segoe UI", Font.BOLD, 34), Color.BLACK),
                createLabel("MBBS, Specialized Senior Consultant", new Font("Segoe UI", Font.PLAIN, 16), Color.GRAY),
                Box.createRigidArea(new Dimension(0, 20)),
                createLabel("Specialization in " + spec, new Font("Segoe UI", Font.BOLD, 18), ACCENT_BLUE),
                Box.createRigidArea(new Dimension(0, 15)),
                new JSeparator(SwingConstants.HORIZONTAL),
                Box.createRigidArea(new Dimension(0, 20)),
                createLabel("Working at", new Font("Segoe UI", Font.PLAIN, 14), Color.GRAY),
                createLabel("Mindana Care General Hospital", new Font("Segoe UI", Font.BOLD, 20), Color.DARK_GRAY),
                Box.createRigidArea(new Dimension(0, 25))
        };

        for (Component c : comps) {
            if (c instanceof JComponent) ((JComponent) c).setAlignmentX(Component.LEFT_ALIGNMENT);
            details.add(c);
        }

        // Fee Row
        JPanel feeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        feeRow.setOpaque(false);
        feeRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel feeLabel = new JLabel("Consultation Fee:  ");
        feeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel feeValue = new JLabel(fee);
        feeValue.setForeground(ACCENT_BLUE);
        feeValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        feeRow.add(feeLabel);
        feeRow.add(feeValue);

        details.add(feeRow);
        details.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnBook = MindanaCare.createRoundedButton("Book Appointment Now", ACCENT_BLUE, Color.WHITE, false);
        btnBook.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBook.setMaximumSize(new Dimension(300, 60));
        btnBook.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnBook.addActionListener(e -> {
            if (UserManager.getInstance().getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null,
                        "Please log in first to book an appointment.",
                        "Login Required", JOptionPane.WARNING_MESSAGE);
                MindanaCare.loadPage(LoginPanel1.createPanel());
                return;
            }
            // Pass the selected doctor and specialty into the booking flow
            AppointmentsMainPanel.setSelectedSpecialty(spec);
            AppointmentsMainPanel.setSelectedDoctor("Dr. " + name);
            MindanaCare.loadPage(AppointmentsMainPanel.createPanel());
        });

        details.add(btnBook);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(details, gbc);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(card);

        main.add(centerWrapper, BorderLayout.CENTER);
        return main;
    }

    private static JLabel createLabel(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        return l;
    }
}