import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class RegisterPanel {

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

        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        JPanel card = MindanaCare.createRoundedPanel(30, Color.WHITE, false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(50, 80, 50, 80));
        card.setPreferredSize(new Dimension(600, 750));

        JLabel regTitle = new JLabel("Create Account");
        regTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
        regTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel regSub = new JLabel("Join Mindana Care today");
        regSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        regSub.setForeground(MindanaCare.TEXT_GRAY);
        regSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputWrapper = new JPanel();
        inputWrapper.setLayout(new BoxLayout(inputWrapper, BoxLayout.Y_AXIS));
        inputWrapper.setOpaque(false);
        inputWrapper.setMaximumSize(new Dimension(400, 500));
        inputWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Fields ---
        JTextField nameField     = createStyledTextField();
        JTextField emailField    = createStyledTextField();
        JPasswordField passField = createStyledPasswordField();
        JPasswordField confField = createStyledPasswordField();

        // Contact field with +63 inside the textbox
        JTextField contactField = new JTextField();
        contactField.setMaximumSize(new Dimension(400, 40));
        contactField.setHorizontalAlignment(JTextField.CENTER);
        contactField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        final String PREFIX = "+63 ";

        // Set text BEFORE attaching the filter
        contactField.setText(PREFIX);

        ((AbstractDocument) contactField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                String filtered = string.replaceAll("[^0-9]", "");
                int digits = fb.getDocument().getLength() - PREFIX.length();
                if (digits + filtered.length() <= 10)
                    super.insertString(fb, fb.getDocument().getLength(), filtered, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String filtered = text.replaceAll("[^0-9]", "");
                int digits = fb.getDocument().getLength() - PREFIX.length();
                if (digits - length + filtered.length() <= 10)
                    super.replace(fb, Math.max(offset, PREFIX.length()), length, filtered, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                if (offset < PREFIX.length()) return;
                super.remove(fb, offset, length);
            }
        });

        addField(inputWrapper, "Full Name",        nameField);
        addField(inputWrapper, "Email Address",    emailField);
        addField(inputWrapper, "Contact Number",   contactField);
        addField(inputWrapper, "Password",         passField);
        addField(inputWrapper, "Confirm Password", confField);

        // --- Sign Up Button ---
        JButton btnSignUp = MindanaCare.createRoundedButton("Sign Up", MindanaCare.BRAND_BLUE, Color.WHITE, false);
        btnSignUp.setPreferredSize(new Dimension(400, 50));
        btnSignUp.setMaximumSize(new Dimension(400, 50));
        btnSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSignUp.addActionListener(e -> {
            String error = UserManager.getInstance().register(
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    contactField.getText().replace("+63 ", "").trim(),
                    new String(passField.getPassword()),
                    new String(confField.getPassword())
            );

            if (error != null) {
                JOptionPane.showMessageDialog(null, error, "Validation Error", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Account created! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                MindanaCare.loadPage(LoginPanel1.createPanel());
            }
        });

        JLabel backToLogin = new JLabel("Already have an account? Login");
        backToLogin.setForeground(MindanaCare.BRAND_BLUE);
        backToLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backToLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backToLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                MindanaCare.loadPage(LoginPanel1.createPanel());
            }
        });

        card.add(regTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(regSub);
        card.add(Box.createRigidArea(new Dimension(0, 40)));
        card.add(inputWrapper);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(btnSignUp);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(backToLogin);

        container.add(card);
        mainPanel.add(container);

        return mainPanel;
    }

    private static void addField(JPanel panel, String labelText, JComponent field) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(MindanaCare.BRAND_BLUE);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));
    }

    private static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(400, 40));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(400, 40));
        field.setHorizontalAlignment(JPasswordField.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MindanaCare.BORDER_GRAY, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
}