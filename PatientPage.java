import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PatientPage {

    public static JPanel createPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        JPanel mainCard = MindanaCare.createRoundedPanel(40, Color.decode("#F4F7FB"), false);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainCard.setPreferredSize(new Dimension(850, 500));

        JLabel title = new JLabel("Patient");
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

        JLabel hContact = new JLabel("Contact No.");
        hContact.setFont(new Font("Segoe UI", Font.BOLD, 16));
        hContact.setPreferredSize(new Dimension(150, 30));

        headerRow.add(Box.createRigidArea(new Dimension(20, 0)));
        headerRow.add(hNo);
        headerRow.add(hDate);
        headerRow.add(hName);
        headerRow.add(hContact);
        headerRow.add(Box.createHorizontalGlue());

        JPanel separator = new JPanel();
        separator.setBackground(Color.BLACK);
        separator.setMaximumSize(new Dimension(800, 2));

        tableCard.add(headerRow);
        tableCard.add(Box.createRigidArea(new Dimension(0, 10)));
        tableCard.add(separator);
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));

        tableCard.add(createTableRow("01", "07-25-26", "Aimy Asupan", "09988765372"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("02", "07-29-26", "Princess Lascuna", "09637823590"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("03", "08-3-26", "Lyvia Navarra", "09347689012"));
        tableCard.add(Box.createRigidArea(new Dimension(0, 15)));
        tableCard.add(createTableRow("04", "08-6-26", "Gian Batuto", "09782356721"));

        mainCard.add(tableCard, BorderLayout.CENTER);
        wrapper.add(mainCard);

        return wrapper;
    }

    static JPanel createTableRow(String no, String date, String name, String contact) {
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

        JLabel lContact = new JLabel(contact);
        lContact.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lContact.setPreferredSize(new Dimension(150, 30));

        row.add(Box.createRigidArea(new Dimension(20, 0)));
        row.add(lNo);
        row.add(lDate);
        row.add(lName);
        row.add(lContact);
        row.add(Box.createHorizontalGlue());

        return row;
    }
}