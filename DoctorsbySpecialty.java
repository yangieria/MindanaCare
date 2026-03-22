import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

public class DoctorsbySpecialty {

    private static final Color ACCENT_BLUE = new Color(33, 113, 229);
    private static final Color LIGHT_BG = new Color(240, 245, 255);

    // Map specialty to list of doctors {name, fee}
    private static final Map<String, String[][]> SPECIALTY_DOCTORS = new HashMap<>() {{
        put("Neurologist",        new String[][]{{"Aria Pelobello", "₱900"}, {"Dave Malunao", "₱600"}});
        put("Cardiologist",       new String[][]{{"Vinese Yap", "₱800"}, {"Princess Lascuna", "₱500"}});
        put("Orthopedic",         new String[][]{{"Celine Mohammad", "₱750"}});
        put("General\nPhysician", new String[][]{{"John Smith", "₱400"}});
        put("Dermatologist",      new String[][]{{"Aimy Asupan", "₱450"}});
        put("Pediatrician",       new String[][]{{"Jane Doe", "₱550"}});
        put("Surgeon",            new String[][]{{"Mark Reyes", "₱1200"}});
        put("Dentist",            new String[][]{{"Sara Tan", "₱300"}});
        put("Psychiatrist",       new String[][]{{"Paul Lim", "₱850"}});
        put("Gasterologist",      new String[][]{{"Maria Santos", "₱700"}});
        put("Physical\nTherapy",  new String[][]{{"Ramon Cruz", "₱500"}});
        put("OPD",                new String[][]{{"Anna Reyes", "₱350"}});
    }};

    public static JPanel createPanel(String specialty) {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(LIGHT_BG);
        main.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(0, 0, 30, 0));

        JButton btnBack = MindanaCare.createRoundedButton("← Back", Color.WHITE, ACCENT_BLUE, false);
        btnBack.addActionListener(e -> MindanaCare.loadPage(AppointmentsPage.createPanel()));

        JLabel title = new JLabel("Doctors for " + specialty.replace("\n", " "));
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(btnBack, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
        main.add(topBar, BorderLayout.NORTH);

        // Doctor grid
        JPanel gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(0, 3, 25, 25));
        grid.setOpaque(false);

        String[][] doctors = SPECIALTY_DOCTORS.getOrDefault(specialty, new String[][]{});

        if (doctors.length == 0) {
            JLabel none = new JLabel("No doctors available for this specialty.");
            none.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            none.setForeground(Color.GRAY);
            gridWrapper.add(none);
        } else {
            for (String[] doc : doctors) {
                grid.add(createDoctorCard(doc[0], specialty.replace("\n", " "), doc[1]));
            }
            gridWrapper.add(grid);
        }

        main.add(gridWrapper, BorderLayout.CENTER);
        return main;
    }

    private static JPanel createDoctorCard(String name, String spec, String fee) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.setColor(new Color(230, 235, 245));
                g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 30, 30));
                g2d.dispose();
            }
        };
        card.setPreferredSize(new Dimension(240, 340));
        card.setOpaque(false);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 2, true));
                card.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBorder(null);
                card.repaint();
            }
        });

        JPanel imgBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(ACCENT_BLUE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.fillRect(0, getHeight()-20, getWidth(), 20);
                g2d.dispose();
            }
        };
        imgBox.setPreferredSize(new Dimension(240, 160));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblName = new JLabel("Dr. " + name);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSpec = new JLabel(spec);
        lblSpec.setForeground(Color.GRAY);
        lblSpec.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblFee = new JLabel("Fee: " + fee);
        lblFee.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblFee.setForeground(ACCENT_BLUE);
        lblFee.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnView = MindanaCare.createRoundedButton("VIEW PROFILE", new Color(225, 238, 255), ACCENT_BLUE, false);
        btnView.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnView.addActionListener(e -> {
            if (UserManager.getInstance().getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null,
                        "Please log in first to book an appointment.",
                        "Login Required", JOptionPane.WARNING_MESSAGE);
                MindanaCare.loadPage(LoginPanel1.createPanel());
                return;
            }
            MindanaCare.loadPage(DoctorsProfile.createPanel(name, spec, fee));
        });

        info.add(lblName);
        info.add(Box.createRigidArea(new Dimension(0, 2)));
        info.add(lblSpec);
        info.add(Box.createRigidArea(new Dimension(0, 5)));
        info.add(lblFee);
        info.add(Box.createVerticalGlue());
        info.add(btnView);

        card.add(imgBox, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        return card;
    }
}