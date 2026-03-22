import java.awt.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AppointmentsPage {

    public static JPanel createPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.decode("#C4D7ED"));
        wrapper.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel card = MindanaCare.createRoundedPanel(40, Color.WHITE, false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 20, 60, 20));

        JLabel title = new JLabel("Find by Specialty");
        title.setFont(new Font("Segoe UI", Font.BOLD, 55));
        title.setForeground(Color.decode("#1E40AF"));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("Access a broad selection of trusted specialists and book your appointment seamlessly.");
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subTitle.setForeground(MindanaCare.TEXT_DARK);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel grid = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        grid.setOpaque(false);

        grid.add(createSpecialtyItem("General\nPhysician", "gp.png"));
        grid.add(createSpecialtyItem("Neurologist", "neuro.png"));
        grid.add(createSpecialtyItem("Cardiologist", "cardio.png"));
        grid.add(createSpecialtyItem("Gasterologist", "gastro.png"));
        grid.add(createSpecialtyItem("Orthopedic", "ortho.png"));
        grid.add(createSpecialtyItem("Physical\nTherapy", "pt.png"));
        grid.add(createSpecialtyItem("OPD", "opd.png"));

        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(subTitle);
        card.add(Box.createRigidArea(new Dimension(0, 70)));
        card.add(grid);

        JPanel cardContainer = new JPanel(new GridBagLayout());
        cardContainer.setOpaque(false);
        cardContainer.add(card);

        wrapper.add(cardContainer, BorderLayout.CENTER);

        return wrapper;
    }

    static JPanel createSpecialtyItem(String name, String imageName) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.setPreferredSize(new Dimension(110, 160));
        container.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel imgLabel = new JLabel();
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.setPreferredSize(new Dimension(85, 85));
        imgLabel.setMaximumSize(new Dimension(85, 85));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // ✅ FIXED: Removed the hardcoded "cardio.png" prefix
        URL iconURL = MindanaCare.class.getResource(imageName);

        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        } else {
            imgLabel.setText("picture");
            imgLabel.setForeground(MindanaCare.BRAND_BLUE);
            imgLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        String htmlName = "<html><div style='text-align: center; width: 100px;'>" + name.replace("\n", "<br>") + "</div></html>";
        JLabel nameLbl = new JLabel(htmlName);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLbl.setForeground(MindanaCare.TEXT_DARK);
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLbl.setHorizontalAlignment(SwingConstants.CENTER);

        container.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                AppointmentsMainPanel.setSelectedSpecialty(name.replace("\n", " "));
                MindanaCare.loadPage(DoctorsbySpecialty.createPanel(name));
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                nameLbl.setForeground(MindanaCare.BRAND_BLUE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                nameLbl.setForeground(MindanaCare.TEXT_DARK);
            }
        });

        container.add(imgLabel);
        container.add(Box.createRigidArea(new Dimension(0, 15)));
        container.add(nameLbl);

        return container;
    }
}