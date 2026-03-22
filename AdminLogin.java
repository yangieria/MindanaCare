import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminLogin {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    public static JPanel createPanel() {
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

        JPanel container = new JPanel(new GridLayout(1, 2, 40, 0));
        container.setPreferredSize(new Dimension(1100, 650));
        container.setOpaque(false);

        JPanel leftCard = MindanaCare.createRoundedPanel(30, Color.WHITE, false);
        leftCard.setLayout(new BoxLayout(leftCard, BoxLayout.Y_AXIS));
        leftCard.setBorder(new EmptyBorder(50, 60, 50, 60));

        JLabel welcomeTitle = new JLabel("Admin Login");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeSub = new JLabel("Access the administration portal to manage the system.");
        welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcomeSub.setForeground(MindanaCare.TEXT_GRAY);
        welcomeSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputWrapper = new JPanel();
        inputWrapper.setLayout(new BoxLayout(inputWrapper, BoxLayout.Y_AXIS));
        inputWrapper.setOpaque(false);
        inputWrapper.setMaximumSize(new Dimension(450, 300));
        inputWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(MindanaCare.BRAND_BLUE);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtUser = createStyledTextField();
        txtUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passLabelRow = new JPanel(new BorderLayout());
        passLabelRow.setOpaque(false);
        passLabelRow.setMaximumSize(new Dimension(450, 20));
        passLabelRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(MindanaCare.BRAND_BLUE);
        passLabelRow.add(lblPass, BorderLayout.WEST);

        JPasswordField txtPass = createStyledPasswordField();
        txtPass.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel extraRow = new JPanel(new BorderLayout());
        extraRow.setOpaque(false);
        extraRow.setMaximumSize(new Dimension(450, 20));
        extraRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox rememberMe = new JCheckBox("Remember Me");
        rememberMe.setOpaque(false);
        rememberMe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        extraRow.add(rememberMe, BorderLayout.WEST);

        JButton btnLogin = MindanaCare.createRoundedButton("Login", MindanaCare.BRAND_BLUE, Color.WHITE, false);
        btnLogin.setPreferredSize(new Dimension(450, 50));
        btnLogin.setMaximumSize(new Dimension(450, 50));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action for login
        Runnable doLogin = () -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
                MindanaCare.showAdminDashboard(); // <-- changed from loadPage
            } else {
                JOptionPane.showMessageDialog(null,
                        "Invalid admin credentials.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        };

        btnLogin.addActionListener(e -> doLogin.run());
        txtPass.addActionListener(e -> doLogin.run());
        txtUser.addActionListener(e -> txtPass.requestFocus());

        JLabel backToLogin = new JLabel("Back to Patient Login");
        backToLogin.setForeground(MindanaCare.BRAND_BLUE);
        backToLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backToLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MindanaCare.loadPage(LoginPanel1.createPanel());
            }
        });

        inputWrapper.add(lblUser);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtUser);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 25)));
        inputWrapper.add(passLabelRow);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtPass);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 15)));
        inputWrapper.add(extraRow);

        leftCard.add(welcomeTitle);
        leftCard.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCard.add(welcomeSub);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCard.add(inputWrapper);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCard.add(btnLogin);
        leftCard.add(Box.createRigidArea(new Dimension(0, 20)));
        leftCard.add(backToLogin);

        JPanel rightPanel = MindanaCare.createRoundedPanel(40, new Color(255, 255, 255, 180), false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(60, 50, 60, 50));

        JLabel secureTitle = new JLabel("System Administration");
        secureTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));

        JLabel secureDesc = new JLabel("<html>Admin access is restricted to authorized<br>system administrators only.</html>");
        secureDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JSeparator line = new JSeparator();
        line.setMaximumSize(new Dimension(500, 1));

        JLabel progTitle = new JLabel("Admin Panel Features");
        progTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        String[] features = {
                "View all registered users",
                "Monitor all booked appointments",
                "Manage doctor records",
                "View prescription records",
                "Full system oversight"
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

        // Set default button so Enter key works correctly
        SwingUtilities.invokeLater(() -> {
            JRootPane root = SwingUtilities.getRootPane(btnLogin);
            if (root != null) root.setDefaultButton(btnLogin);
        });

        return mainPanel;
    }

    private static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(450, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        return field;
    }

    private static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(450, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        return field;
    }
}