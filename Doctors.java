import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Doctors {

    private static final Color ACCENT_BLUE = new Color(33, 113, 229);

    public static JPanel createPanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.WHITE);

        JLabel topTitle = new JLabel("Top Doctors to Book");
        topTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        topTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        topTitle.setBorder(new EmptyBorder(40, 0, 20, 0));
        main.add(topTitle);

        JPanel featuredWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        featuredWrapper.setBackground(Color.WHITE);
        featuredWrapper.setMaximumSize(new Dimension(1200, 400));
        featuredWrapper.add(createDoctorCard("Vinese Yap", "Cardiologist", "₱800"));
        featuredWrapper.add(createDoctorCard("Aria Pelobello", "Neurologist", "₱900"));
        featuredWrapper.add(createDoctorCard("Celine Mohammad", "Orthopedic", "₱750"));
        main.add(featuredWrapper);
        main.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel gridTitle = new JLabel("Available Doctors");
        gridTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gridTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridTitle.setBorder(new EmptyBorder(20, 0, 20, 0));
        main.add(gridTitle);

        JPanel gridCenteringContainer = new JPanel(new GridBagLayout());
        gridCenteringContainer.setBackground(Color.WHITE);

        JPanel grid = new JPanel(new GridLayout(0, 4, 25, 25));
        grid.setBackground(Color.WHITE);

        String[][] doctorData = {
                {"Princess Lascuna", "Cardiologist", "₱500"},
                {"Aimy Asupan", "Dermatologist", "₱450"},
                {"Dave Malunao", "Neurologist", "₱600"},
                {"John Smith", "General Physician", "₱400"},
                {"Jane Doe", "Pediatrician", "₱550"},
                {"Mark Reyes", "Surgeon", "₱1200"},
                {"Sara Tan", "Dentist", "₱300"},
                {"Paul Lim", "Psychiatrist", "₱850"}
        };

        for (String[] doc : doctorData) {
            grid.add(createDoctorCard(doc[0], doc[1], doc[2]));
        }

        gridCenteringContainer.add(grid);
        main.add(gridCenteringContainer);
        main.add(Box.createRigidArea(new Dimension(0, 40)));

        return main;
    }

    private static JPanel createDoctorCard(String name, String spec, String fee) {
        final boolean[] hovered = {false};

        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.setColor(hovered[0] ? ACCENT_BLUE : new Color(230, 235, 245));
                g2d.setStroke(new BasicStroke(hovered[0] ? 2.5f : 1f));
                g2d.draw(new RoundRectangle2D.Double(1, 1, getWidth() - 2, getHeight() - 2, 30, 30));
                g2d.dispose();
            }
        };
        card.setPreferredSize(new Dimension(240, 340));
        card.setOpaque(false);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered[0] = true;
                card.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered[0] = false;
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
                g2d.fillRect(0, getHeight() - 20, getWidth(), 20);
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
                        "Please log in first to view doctor profiles and book appointments.",
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