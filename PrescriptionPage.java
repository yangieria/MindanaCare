import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PrescriptionPage {

    public static JPanel createPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel mainCard = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainCard.setPreferredSize(new Dimension(850, 500));

        JLabel title = new JLabel("Prescription");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        mainCard.add(titlePanel, BorderLayout.NORTH);

        JPanel tableCard = MindanaCare.createRoundedPanel(30, Color.decode("#DFE7F3"), false);
        tableCard.setLayout(new BoxLayout(tableCard, BoxLayout.Y_AXIS));
        tableCard.setBorder(new EmptyBorder(20, 30, 30, 30));

        JPanel headerRow = new JPanel();
        headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
        headerRow.setOpaque(false);

        JLabel hNo = new JLabel("No.");
        hNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hNo.setPreferredSize(new Dimension(80, 30));

        JLabel hDate = new JLabel("Date");
        hDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hDate.setPreferredSize(new Dimension(150, 30));

        JLabel hName = new JLabel("Name");
        hName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hName.setPreferredSize(new Dimension(220, 30));

        JLabel hPresc = new JLabel("Prescription");
        hPresc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hPresc.setPreferredSize(new Dimension(180, 30));

        headerRow.add(Box.createRigidArea(new Dimension(20, 0)));
        headerRow.add(hNo);
        headerRow.add(hDate);
        headerRow.add(hName);
        headerRow.add(hPresc);
        headerRow.add(Box.createHorizontalGlue());

        JPanel separator = new JPanel();
        separator.setBackground(Color.BLACK);
        separator.setMaximumSize(new Dimension(800, 2));

        tableCard.add(headerRow);
        tableCard.add(Box.createRigidArea(new Dimension(0, 10)));
        tableCard.add(separator);
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));

        tableCard.add(createTableRow("01", "07-25-26", "Aimy Asupan"));

        mainCard.add(tableCard, BorderLayout.CENTER);
        wrapper.add(mainCard);

        return wrapper;
    }

    static JPanel createTableRow(String no, String date, String name) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(800, 40));

        JLabel lNo = new JLabel(no);
        lNo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lNo.setPreferredSize(new Dimension(80, 30));

        JLabel lDate = new JLabel(date);
        lDate.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lDate.setPreferredSize(new Dimension(150, 30));

        JLabel lName = new JLabel(name);
        lName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lName.setPreferredSize(new Dimension(220, 30));

        JPanel inputPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        inputField.setOpaque(false);
        inputField.setBorder(new EmptyBorder(5, 15, 5, 15));
        inputField.setText("Prescription");
        inputField.setForeground(Color.GRAY);
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (inputField.getText().equals("Prescription")) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (inputField.getText().isEmpty()) {
                    inputField.setForeground(Color.GRAY);
                    inputField.setText("Prescription");
                }
            }
        });
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.setPreferredSize(new Dimension(140, 35));
        inputPanel.setMaximumSize(new Dimension(140, 35));

        JLabel sendIcon = new JLabel(" ➤");
        sendIcon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        sendIcon.setForeground(MindanaCare.BRAND_BLUE);
        sendIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.add(inputPanel);
        actionPanel.add(sendIcon);

        row.add(Box.createRigidArea(new Dimension(20, 0)));
        row.add(lNo);
        row.add(lDate);
        row.add(lName);
        row.add(actionPanel);
        row.add(Box.createHorizontalGlue());

        return row;
    }
}