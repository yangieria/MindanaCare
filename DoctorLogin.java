import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DoctorLogin {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Doctor Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.add(createPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static JPanel createPanel() {
        // --- MAIN PANEL WITH GRADIENT ---
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, Color.decode("#7DA2DE"), getWidth(), 0, Color.decode("#EBF2FF"));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Container for the two cards
        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setPreferredSize(new Dimension(1100, 650));
        container.setOpaque(false);

        // --- LEFT SIDE: DOCTOR LOGIN FORM ---
        JPanel leftCard = MindanaCare.createRoundedPanel(30, Color.WHITE, false);
        leftCard.setLayout(new BoxLayout(leftCard, BoxLayout.Y_AXIS));
        leftCard.setBorder(new EmptyBorder(50, 60, 50, 60));

        // Header Section
        JLabel welcomeTitle = new JLabel("Doctor Login");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeSub = new JLabel("Access the medical portal to manage patient records.");
        welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcomeSub.setForeground(MindanaCare.TEXT_GRAY);
        welcomeSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input Field Wrapper
        JPanel inputWrapper = new JPanel();
        inputWrapper.setLayout(new BoxLayout(inputWrapper, BoxLayout.Y_AXIS));
        inputWrapper.setOpaque(false);
        inputWrapper.setMaximumSize(new Dimension(450, 300));
        inputWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Email / ID (Fixed Alignment) ---
        JLabel lblEmail = new JLabel("Email Address or Doctor ID");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setForeground(MindanaCare.BRAND_BLUE);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT); // Gi-left align na kini

        JTextField txtEmail = createStyledTextField();
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Password Section (Fixed Color and Alignment) ---
        JPanel passLabelRow = new JPanel(new BorderLayout());
        passLabelRow.setOpaque(false);
        passLabelRow.setMaximumSize(new Dimension(450, 20));
        passLabelRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // Gihimo nakong BRAND_BLUE para parehas sila sa Email label
        lblPass.setForeground(MindanaCare.BRAND_BLUE);
        passLabelRow.add(lblPass, BorderLayout.WEST);

        JPasswordField txtPass = createStyledPasswordField();
        txtPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bottom row (Remember me + Forgot Password)
        JPanel extraRow = new JPanel(new BorderLayout());
        extraRow.setOpaque(false);
        extraRow.setMaximumSize(new Dimension(450, 20));
        extraRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox rememberMe = new JCheckBox("Remember Me");
        rememberMe.setOpaque(false);
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel forgotLinkBot = new JLabel("Forgot Password?");
        forgotLinkBot.setForeground(MindanaCare.TEXT_GRAY);
        forgotLinkBot.setFont(new Font("Segoe UI", Font.BOLD, 12));
        forgotLinkBot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        extraRow.add(rememberMe, BorderLayout.WEST);
        extraRow.add(forgotLinkBot, BorderLayout.EAST);

        // Login Button
        JButton btnLogin = MindanaCare.createRoundedButton("Login", MindanaCare.BRAND_BLUE, Color.WHITE, false);
        btnLogin.setPreferredSize(new Dimension(450, 50));
        btnLogin.setMaximumSize(new Dimension(450, 50));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Back link
        JLabel backToPatient = new JLabel("Back to Patient Login");
        backToPatient.setForeground(MindanaCare.BRAND_BLUE);
        backToPatient.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backToPatient.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToPatient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MindanaCare.loadPage(LoginPanel1.createPanel());
            }
        });

        // Adding components to inputWrapper
        inputWrapper.add(lblEmail);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtEmail);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 25)));
        inputWrapper.add(passLabelRow);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtPass);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 15)));
        inputWrapper.add(extraRow);

        // Adding everything to Left Card
        leftCard.add(welcomeTitle);
        leftCard.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCard.add(welcomeSub);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCard.add(inputWrapper);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCard.add(btnLogin);
        leftCard.add(Box.createRigidArea(new Dimension(0, 20)));
        leftCard.add(backToPatient);

        // --- RIGHT SIDE: INFO PANEL ---
        JPanel rightPanel = MindanaCare.createRoundedPanel(40, new Color(255, 255, 255, 180), false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(60, 50, 60, 50));

        JLabel secureTitle = new JLabel("Secure & Confidential");
        secureTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel secureDesc = new JLabel("<html>Professional access is restricted to authorized<br>medical personnel only.</html>");
        secureDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JSeparator line = new JSeparator();
        line.setMaximumSize(new Dimension(500, 1));

        JLabel progTitle = new JLabel("Doctor Dashboard Features");
        progTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        String[] features = {
                "Manage patient records", "Update clinical notes",
                "Real-time schedule management", "Secure HIPAA-compliant data",
                "Diagnostic tool integration"
        };

        rightPanel.add(secureTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(secureDesc);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(line);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(progTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (String f : features) {
            JLabel item = new JLabel("<html><font color='#3B82F6'>✔</font> &nbsp;&nbsp;" + f + "</html>");
            item.setFont(new Font("Segoe UI", Font.BOLD, 15));
            rightPanel.add(item);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        container.add(leftCard);
        container.add(rightPanel);
        mainPanel.add(container);

        return mainPanel;
    }

    private static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(450, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    private static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(450, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }
}