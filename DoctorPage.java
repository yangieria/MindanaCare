import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;

public class DoctorPage extends JFrame {

    public static JPanel createPanel() {
        // Main Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(Color.WHITE);
        // Added a ScrollPane wrapper in your main class might be needed if content is long
        wrapper.setBorder(new EmptyBorder(40, 60, 40, 60));

        // =================================================================
        // 1. HEADER SECTION
        // =================================================================
        JLabel header = new JLabel("Top Doctor to Book");
        header.setFont(new Font("Segoe UI", Font.BOLD, 40));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subHeader = new JLabel("Providing accessible healthcare solutions for the University of Mindanao community.");
        subHeader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subHeader.setForeground(Color.GRAY); // Replaced mindanaCare.TEXT_GRAY for standalone fix
        subHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(header);
        wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
        wrapper.add(subHeader);
        wrapper.add(Box.createRigidArea(new Dimension(0, 40)));

        // =================================================================
        // 2. FEATURED DOCTORS (Centering Wrapper)
        // =================================================================
        JPanel featuredWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        featuredWrapper.setOpaque(false);
        featuredWrapper.setMaximumSize(new Dimension(1200, 400)); // Limits stretching

        featuredWrapper.add(createDoctorCard("Dr. Vinese Yap", "Cardiologist", "Aria.webp"));
        featuredWrapper.add(createDoctorCard("Dr. Aria Pelobello", "Neurologist", "Aria.png"));
        featuredWrapper.add(createDoctorCard("Dr. Celine Mohammad", "Orthopedic", "doctor.jpg"));

        wrapper.add(featuredWrapper);
        wrapper.add(Box.createRigidArea(new Dimension(0, 60)));

        // =================================================================
        // 3. ALL AVAILABLE DOCTORS GRID
        // =================================================================
        JLabel gridTitle = new JLabel("Available Doctors for Appointment");
        gridTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gridTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(gridTitle);
        wrapper.add(Box.createRigidArea(new Dimension(0, 30)));

        // Use a container to keep the grid centered and not stretched
        JPanel gridContainer = new JPanel(new GridBagLayout());
        gridContainer.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(0, 4, 25, 25));
        grid.setOpaque(false);

        String[][] docs = {
                {"Vinese Yap", "Cardiologist"}, {"Aria Pelobello", "Neurologist"},
                {"Celine Mohammad", "Orthopedic"}, {"Princess Lascuna", "Cardiologist"},
                {"Aimy Asupan", "Cardiologist"}, {"Dave Malunao", "Neurologist"},
                {"Celine Mohammad", "Orthopedic"}, {"Vinese Yap", "Cardiologist"}
        };

        for (String[] d : docs) {
            grid.add(createGridCard(d[0], d[1]));
        }

        gridContainer.add(grid);
        wrapper.add(gridContainer);
        wrapper.add(Box.createRigidArea(new Dimension(0, 60)));

        // =================================================================
        // 4. BOTTOM BANNER SECTION
        // =================================================================
        JPanel banner = createBottomBanner();
        banner.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(banner);

        return wrapper;
    }

    private static JPanel createDoctorCard(String name, String type, String imgPath) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);
        container.setPreferredSize(new Dimension(280, 380));

        // Use a helper or direct panel if mindanaCare is unavailable
        JPanel imgBg = new JPanel();
        imgBg.setBackground(new Color(33, 113, 229)); // Brand Blue
        imgBg.setPreferredSize(new Dimension(280, 220));
        imgBg.setMaximumSize(new Dimension(280, 220));
        imgBg.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeLbl = new JLabel(type);
        typeLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        typeLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnView = new JButton("VIEW PROFILE"); // Placeholder for your custom button
        btnView.setAlignmentX(Component.CENTER_ALIGNMENT);

        container.add(imgBg);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(nameLbl);
        container.add(typeLbl);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(btnView);

        return container;
    }

    private static JPanel createGridCard(String name, String spec) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.setColor(new Color(229, 231, 235));
                g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 30, 30));
                g2d.dispose();
            }
        };
        card.setPreferredSize(new Dimension(220, 300));
        card.setOpaque(false);

        // Header Blue Area
        JPanel imgBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(33, 113, 229));
                // Only round top corners
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.fillRect(0, getHeight()-20, getWidth(), 20);
                g2d.dispose();
            }
        };
        imgBox.setPreferredSize(new Dimension(220, 140));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);
        info.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel n = new JLabel("Dr. " + name);
        n.setFont(new Font("Segoe UI", Font.BOLD, 15));
        n.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel s = new JLabel(spec);
        s.setForeground(Color.GRAY);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btn = new JButton("VIEW PROFILE");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 10));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        info.add(n);
        info.add(Box.createRigidArea(new Dimension(0, 5)));
        info.add(s);
        info.add(Box.createVerticalGlue()); // Pushes button to bottom
        info.add(btn);

        card.add(imgBox, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        return card;
    }

    private static JPanel createBottomBanner() {
        // Using a Wrapper to prevent the banner from stretching to infinity
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setOpaque(false);

        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(new Color(33, 113, 229));
        banner.setPreferredSize(new Dimension(1000, 140));
        banner.setBorder(new EmptyBorder(0, 50, 0, 50));

        JLabel txt = new JLabel("<html><font color='white' size='6'>Book Appointment With<br>No. #1 Expert Doctors</font></html>");
        banner.add(txt, BorderLayout.WEST);

        JButton btn = new JButton("Book Here");
        JPanel btnWrap = new JPanel(new GridBagLayout());
        btnWrap.setOpaque(false);
        btnWrap.add(btn);
        banner.add(btnWrap, BorderLayout.EAST);

        outer.add(banner);
        return outer;
    }
}