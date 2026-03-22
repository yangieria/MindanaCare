import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginPanel1 {

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

        JLabel welcomeTitle = new JLabel("Welcome Back");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeSub = new JLabel("Login to manage your appointments and health records.");
        welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcomeSub.setForeground(MindanaCare.TEXT_GRAY);
        welcomeSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputWrapper = new JPanel();
        inputWrapper.setLayout(new BoxLayout(inputWrapper, BoxLayout.Y_AXIS));
        inputWrapper.setOpaque(false);
        inputWrapper.setMaximumSize(new Dimension(450, 300));
        inputWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setForeground(MindanaCare.BRAND_BLUE);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEmail = createStyledTextField();
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        JLabel forgotLinkBot = new JLabel("Forgot Password?");
        forgotLinkBot.setForeground(MindanaCare.TEXT_GRAY);
        forgotLinkBot.setFont(new Font("Segoe UI", Font.BOLD, 12));
        forgotLinkBot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        extraRow.add(rememberMe, BorderLayout.WEST);
        extraRow.add(forgotLinkBot, BorderLayout.EAST);

        JButton btnLogin = MindanaCare.createRoundedButton("Login", MindanaCare.BRAND_BLUE, Color.WHITE, false);
        btnLogin.setPreferredSize(new Dimension(450, 50));
        btnLogin.setMaximumSize(new Dimension(450, 50));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        Runnable doLogin = () -> {
            String email = txtEmail.getText().trim();
            String password = new String(txtPass.getPassword());
            if (email.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(null, "All fields must be filled.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!email.toLowerCase().matches("^[a-zA-Z0-9._%+\\-]+@gmail\\.com$")) {
                JOptionPane.showMessageDialog(null, "Email must be a valid @gmail.com address.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User user = UserManager.getInstance().login(email.toLowerCase(), password);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                UserManager.getInstance().setCurrentUser(user);
                MindanaCare.showLoggedInButtons();
                JOptionPane.showMessageDialog(null, "Welcome, " + user.getFullName() + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                MindanaCare.loadPage(UserDashboard.createPanel());
            }
        };

        btnLogin.addActionListener(e -> doLogin.run());
        txtPass.addActionListener(e -> doLogin.run());
        txtEmail.addActionListener(e -> txtPass.requestFocus());

        JLabel createAccount = new JLabel("Create New Account");
        createAccount.setForeground(MindanaCare.BRAND_BLUE);
        createAccount.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccount.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MindanaCare.loadPage(RegisterPanel.createPanel());
            }
        });

        // --- DOCTOR LOGIN ROW ---
        JPanel docContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        docContainer.setOpaque(false);
        docContainer.setMaximumSize(new Dimension(450, 30));

        JLabel docLabel = new JLabel("Doctor Login →");
        docLabel.setForeground(MindanaCare.BRAND_BLUE);
        docLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel clickHere = new JLabel("Click Here");
        clickHere.setForeground(new Color(220, 38, 38));
        clickHere.setFont(new Font("Segoe UI", Font.BOLD, 12));
        clickHere.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clickHere.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MindanaCare.loadPage(DoctorLogin.createPanel());
            }
        });

        docContainer.add(docLabel);
        docContainer.add(clickHere);

        // --- ADMIN LINK ---
        JLabel adminLink = new JLabel("Admin? Login here");
        adminLink.setForeground(MindanaCare.TEXT_GRAY);
        adminLink.setFont(new Font("Segoe UI", Font.BOLD, 13));
        adminLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        adminLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JRootPane root = SwingUtilities.getRootPane(adminLink);
                if (root != null) root.setDefaultButton(null);
                MindanaCare.loadPage(AdminLogin.createPanel());
            }
        });

        leftCard.add(welcomeTitle);
        leftCard.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCard.add(welcomeSub);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));

        inputWrapper.add(lblEmail);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtEmail);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 25)));
        inputWrapper.add(passLabelRow);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 8)));
        inputWrapper.add(txtPass);
        inputWrapper.add(Box.createRigidArea(new Dimension(0, 15)));
        inputWrapper.add(extraRow);

        leftCard.add(inputWrapper);
        leftCard.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCard.add(btnLogin);
        leftCard.add(Box.createRigidArea(new Dimension(0, 20)));
        leftCard.add(createAccount);
        leftCard.add(Box.createRigidArea(new Dimension(0, 5)));
        leftCard.add(docContainer);
        leftCard.add(Box.createRigidArea(new Dimension(0, 5)));
        leftCard.add(adminLink);

        JPanel rightPanel = MindanaCare.createRoundedPanel(40, new Color(255, 255, 255, 180), false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(new EmptyBorder(60, 50, 60, 50));

        JLabel secureTitle = new JLabel("Secure & Confidential");
        secureTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        JLabel secureDesc = new JLabel("<html>Your personal health information is protected<br>and accessible only to you.</html>");
        secureDesc.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JSeparator line = new JSeparator();
        line.setMaximumSize(new Dimension(500, 1));
        JLabel progTitle = new JLabel("Appointment Programs");
        progTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        String[] features = {
                "Your personal data", "Organized health data",
                "Effortless appointment scheduling", "Privacy-focused and secure",
                "24/7 access to your information"
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