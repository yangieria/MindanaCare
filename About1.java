import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.net.URL;

public class About1 { // Removed "extends JFrame" to match your other classes

    // Static colors to keep them accessible to the static method
    static Color primaryBlue = new Color(74, 134, 232);
    static Color bgTop = new Color(210, 225, 255);
    static Color bgBottom = Color.WHITE;

    public static JPanel createPanel() {
        // Naggamit og LayeredPane para sa overflow effect sa picture
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        // IMPORTANT: Set preferred size so the main container knows how big this is
        layeredPane.setPreferredSize(new Dimension(1300, 800));

        // 1. GRADIENT BACKGROUND (Layer 0)
        JPanel bgPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, bgTop, 0, getHeight(), bgBottom);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Set background size to cover a wide area
        bgPanel.setBounds(0, 0, 1920, 1080);
        layeredPane.add(bgPanel, Integer.valueOf(0));

        // --- NAVBAR ---


        // --- CONTENT AREA ---
        JPanel mainContent = new JPanel(null);
        mainContent.setOpaque(false);
        mainContent.setBounds(0, 70, 1920, 1000); // Shifted down to account for navbar
        layeredPane.add(mainContent, Integer.valueOf(1));

        // Heading
        JLabel heading = new JLabel("<html><h1 style='font-size:28px; font-weight:bold; color:#333;'>" +
                "About MINDANA<span style='color:#4a86e8;'>CARE</span></h1></html>");
        heading.setBounds(80, 40, 800, 50);
        mainContent.add(heading);

        // Description
        JLabel desc = new JLabel("<html><div style='width: 800px; font-family: Segoe UI; font-size: 13px; color: #444; line-height: 1.5;'>" +
                "MINDANACARE is the official digital doctor appointment booking system of the University of Mindanao. " +
                "Designed to improve access to healthcare, the platform allows students, faculty, and staff to " +
                "conveniently connect with university-accredited medical professionals.</div></html>");
        desc.setBounds(80, 90, 850, 80);
        mainContent.add(desc);

        // Mission & Vision
        JPanel mvGrid = new JPanel(new GridLayout(1, 2, 80, 0));
        mvGrid.setOpaque(false);
        mvGrid.setBounds(200, 200, 950, 180);
        mvGrid.add(createListSection("Our Mission", new String[]{"Easy appointment scheduling", "Verified medical professionals", "Organized records"}));
        mvGrid.add(createListSection("Our Vision", new String[]{"Easy accommodation", "Student friendly interface", "Secure confidential documents handling"}));
        mainContent.add(mvGrid);

        // --- 2. THE WHITE BOX (Rounded) ---
        RoundedPanel whyCard = new RoundedPanel(40, Color.WHITE);
        whyCard.setLayout(null);
        whyCard.setBounds(85, 440, 1180, 180);
        mainContent.add(whyCard);

        JLabel whyText = new JLabel("<html><b style='font-size:18px;'>Why MINDANA<span style='color:#4a86e8;'>CARE</span>?</b><br><br>" +
                "<div style='width:600px; font-size:12px; color:#555; line-height:1.4;'>" +
                "MindanaCare is a clinic appointment booking system designed to make healthcare access easier and faster. " +
                "It allows patients to book appointments conveniently, helps clinics manage schedules efficiently, " +
                "and ensures a smooth and reliable healthcare experience.</div></html>");
        whyText.setBounds(40, 30, 1100, 120);
        whyCard.add(whyText);

        // --- 3. ANG PICTURE (Layer 2 - Overflow) ---
        URL iconURL = About1.class.getResource("img_2.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(320, 320, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));

            // X: 1000, Y: 400 - Kept your original positioning for overflow
            imgLabel.setBounds(1000, 400, 300, 300);
            layeredPane.add(imgLabel, Integer.valueOf(2));
        }

        // Return the final wrapper panel
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(layeredPane, BorderLayout.CENTER);
        return wrapper;
    }

    private static JPanel createListSection(String title, String[] items) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        JLabel t = new JLabel("<html><span style='font-size:18px; color:#1a3c7a; font-weight:bold;'>" + title + "</span></html>");
        t.setBorder(new MatteBorder(0, 0, 2, 0, new Color(210, 210, 210)));
        p.add(t);
        p.add(Box.createVerticalStrut(15));
        for (String item : items) {
            JLabel li = new JLabel("<html><div style='font-size:12px; margin-bottom:5px;'><font color='#4a86e8'><b>✓</b></font> &nbsp;" + item + "</div></html>");
            p.add(li);
        }
        return p;
    }

    static class RoundedPanel extends JPanel {
        private int radius; Color color;
        public RoundedPanel(int r, Color c) { this.radius = r; this.color = c; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
        }
    }
}