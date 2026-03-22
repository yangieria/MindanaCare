import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class About2 { // Removed "extends JFrame"

    public static JPanel createPanel() {
        // Main Background Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(221, 231, 255));
        mainPanel.setLayout(new GridBagLayout());

        // CONFLICT FIX: Set preferred size so the container knows how to scale it
        mainPanel.setPreferredSize(new Dimension(1100, 650));

        // The White Contact Card
        RoundedPanel card = new RoundedPanel(50, Color.WHITE);
        card.setPreferredSize(new Dimension(950, 450));
        card.setLayout(new BorderLayout());

        // --- LEFT COLUMN: Text Info ---
        JPanel leftCol = new JPanel();
        leftCol.setOpaque(false);
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setBorder(new EmptyBorder(0, 60, 0, 0));

        JLabel titleLabel = new JLabel("<html><body style='font-family:Sans-Serif; font-size:36pt;'>"
                + "<b style='color:#333333;'>Contact MINDANA</b><b style='color:#4A90E2;'>CARE</b>"
                + "</body></html>");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel phoneLabel = new JLabel("+63 992 230 5767");
        phoneLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        phoneLabel.setForeground(new Color(26, 74, 142));
        phoneLabel.setIcon(createIcon("phone"));
        phoneLabel.setIconTextGap(25);
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("clinic@mindanacare.com");
        emailLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        emailLabel.setForeground(new Color(26, 74, 142));
        emailLabel.setIcon(createIcon("email"));
        emailLabel.setIconTextGap(25);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftCol.add(Box.createVerticalGlue());
        leftCol.add(titleLabel);
        leftCol.add(Box.createVerticalStrut(40));
        leftCol.add(phoneLabel);
        leftCol.add(Box.createVerticalStrut(20));
        leftCol.add(emailLabel);
        leftCol.add(Box.createVerticalGlue());

        // --- RIGHT COLUMN: Image (Using JLayeredPane for setBounds) ---
        JLayeredPane rightCol = new JLayeredPane();
        rightCol.setPreferredSize(new Dimension(450, 450));

        // IMONG IMAGE LOGIC
        URL iconURL = About2.class.getResource("img.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setBounds(0, 0, 450, 450);
            rightCol.add(imgLabel, JLayeredPane.DEFAULT_LAYER);
        }

        // Combine everything
        card.add(leftCol, BorderLayout.CENTER);
        card.add(rightCol, BorderLayout.EAST);

        mainPanel.add(card);

        // CONFLICT FIX: Wrapping in a BorderLayout panel ensures it expands correctly in loadPage
        JPanel finalWrapper = new JPanel(new BorderLayout());
        finalWrapper.add(mainPanel, BorderLayout.CENTER);

        return finalWrapper;
    }

    // Custom Class for Rounded Corners - Made static to work inside createPanel
    static class RoundedPanel extends JPanel {
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
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(backgroundColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
            g2.dispose();
        }
    }

    // Simple Drawing-based Icons - Made static to work inside createPanel
    private static Icon createIcon(String type) {
        return new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(26, 74, 142));
                g2.setStroke(new BasicStroke(3));
                if (type.equals("phone")) {
                    g2.drawRoundRect(x + 5, y + 2, 20, 30, 5, 5);
                    g2.fillOval(x + 12, y + 24, 6, 6);
                } else {
                    g2.drawRect(x, y + 5, 32, 22);
                    g2.drawLine(x, y + 5, x + 16, y + 16);
                    g2.drawLine(x + 32, y + 5, x + 16, y + 16);
                }
                g2.dispose();
            }
            public int getIconWidth() { return 35; }
            public int getIconHeight() { return 35; }
        };
    }
}