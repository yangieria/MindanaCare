import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class Appointments1 {

    private static final Color PRIMARY_BLUE = new Color(74, 130, 209);
    private static final Color BG_LIGHT_BLUE = new Color(225, 235, 248);
    private static final Color SUCCESS_GREEN = new Color(74, 222, 128);

    public static JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_LIGHT_BLUE);
        mainPanel.setPreferredSize(new Dimension(1100, 900));

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BG_LIGHT_BLUE);
        wrapper.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel header = new JLabel("<html><div style='text-align: center;'><font color='#4A82D1'>Book</font> Appointment - <b>MINDANA</b><font color='#4A82D1'>CARE</font></div></html>", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(header);
        wrapper.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel stepperContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        stepperContainer.setBackground(BG_LIGHT_BLUE);
        stepperContainer.setMaximumSize(new Dimension(1000, 60));
        stepperContainer.add(createStepper());
        wrapper.add(stepperContainer);
        wrapper.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel contentGrid = new JPanel(new BorderLayout(30, 0));
        contentGrid.setBackground(BG_LIGHT_BLUE);
        contentGrid.setMaximumSize(new Dimension(1000, 600));
        contentGrid.add(createConfirmedDetailsCard(), BorderLayout.CENTER);
        contentGrid.add(createDoctorSummaryCard(), BorderLayout.EAST);
        wrapper.add(contentGrid);

        mainPanel.add(wrapper, BorderLayout.NORTH);
        return mainPanel;
    }

    private static JPanel createStepper() {
        RoundedPanel stepper = new RoundedPanel(10, new Color(200, 215, 240));
        stepper.setLayout(new GridLayout(1, 4));
        stepper.setPreferredSize(new Dimension(980, 55));

        String[] steps = {"Select Doctor", "Confirm Doctor", "Confirm Details", "Confirm Appointment"};
        for (int i = 0; i < steps.length; i++) {
            JLabel s = new JLabel(steps[i], SwingConstants.CENTER);
            s.setFont(new Font("Segoe UI", Font.BOLD, 15));
            if (i == 0) {
                s.setOpaque(true);
                s.setBackground(PRIMARY_BLUE);
                s.setForeground(Color.WHITE);
            } else {
                s.setForeground(PRIMARY_BLUE);
                s.setOpaque(false);
            }
            stepper.add(s);
        }
        return stepper;
    }

    private static JPanel createConfirmedDetailsCard() {
        RoundedPanel card = new RoundedPanel(40, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Confirmed Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        card.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);

        JPanel profileRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 20));
        profileRow.setBackground(Color.WHITE);

        JLayeredPane imgContainer = new JLayeredPane();
        imgContainer.setPreferredSize(new Dimension(130, 130));

        RoundedPanel bgBox = new RoundedPanel(20, PRIMARY_BLUE);
        bgBox.setBounds(0, 0, 130, 130);
        imgContainer.add(bgBox, JLayeredPane.DEFAULT_LAYER);

        URL iconURL = Appointments1.class.getResource("About.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setBounds(0, 0, 130, 130);
            imgContainer.add(imgLabel, JLayeredPane.PALETTE_LAYER);
        } else {
            JLabel drText = new JLabel("Dr.", SwingConstants.CENTER);
            drText.setForeground(Color.WHITE);
            drText.setFont(new Font("Segoe UI", Font.BOLD, 40));
            drText.setBounds(0, 0, 130, 130);
            imgContainer.add(drText, JLayeredPane.MODAL_LAYER);
        }

        profileRow.add(imgContainer);

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setBackground(Color.WHITE);

        // --- USE SELECTED DOCTOR AND SPECIALTY ---
        String doctor = AppointmentsMainPanel.getSelectedDoctor().isEmpty()
                ? "Dr. Aria Pelobello" : AppointmentsMainPanel.getSelectedDoctor();
        String specialty = AppointmentsMainPanel.getSelectedSpecialty().isEmpty()
                ? "Neurologist" : AppointmentsMainPanel.getSelectedSpecialty();

        JLabel drName = new JLabel(doctor);
        drName.setFont(new Font("Segoe UI", Font.BOLD, 28));
        drName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel specRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        specRow.setBackground(Color.WHITE);
        specRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel specLabel = new JLabel(specialty + " | ");
        specLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        specLabel.setForeground(Color.GRAY);

        JLabel badge = new JLabel(" Available Today ", SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(SUCCESS_GREEN);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 13));

        specRow.add(specLabel);
        specRow.add(badge);

        JLabel stats = new JLabel("<html><font color='#FFC107'>★★★★★</font>  • 24 Consultations</html>");

        txt.add(drName);
        txt.add(specRow);
        txt.add(Box.createRigidArea(new Dimension(0, 10)));
        txt.add(stats);
        profileRow.add(txt);
        center.add(profileRow);

        JTextArea bio = new JTextArea("Specialized in " + specialty
                + ". Providing patient-centered care at the University of Mindanao.");
        bio.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        bio.setLineWrap(true);
        bio.setWrapStyleWord(true);
        bio.setEditable(false);
        bio.setBorder(new EmptyBorder(10, 10, 10, 10));
        center.add(bio);

        card.add(center, BorderLayout.CENTER);

        JButton btn = new JButton("Book Appointment");
        btn.setBackground(PRIMARY_BLUE);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(0, 55));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        btn.addActionListener(e -> {
            if (UserManager.getInstance().getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null,
                        "Please log in first to book an appointment.",
                        "Login Required", JOptionPane.WARNING_MESSAGE);
                MindanaCare.loadPage(LoginPanel1.createPanel());
                return;
            }
            AppointmentsMainPanel.nextPage();
        });

        card.add(btn, BorderLayout.SOUTH);
        return card;
    }

    private static JPanel createDoctorSummaryCard() {
        RoundedPanel card = new RoundedPanel(40, Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel topSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topSection.setOpaque(false);
        topSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLayeredPane smallImg = new JLayeredPane();
        smallImg.setPreferredSize(new Dimension(90, 90));
        RoundedPanel sBg = new RoundedPanel(15, PRIMARY_BLUE);
        sBg.setBounds(0, 0, 90, 90);
        smallImg.add(sBg, JLayeredPane.DEFAULT_LAYER);

        URL iconURL = Appointments1.class.getResource("About.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setBounds(0, 0, 90, 90);
            smallImg.add(imgLabel, JLayeredPane.PALETTE_LAYER);
        }

        topSection.add(smallImg);
        topSection.add(Box.createRigidArea(new Dimension(15, 0)));

        JPanel textInfo = new JPanel();
        textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
        textInfo.setOpaque(false);

        // --- USE SELECTED DOCTOR ---
        String doctor = AppointmentsMainPanel.getSelectedDoctor().isEmpty()
                ? "Dr. Aria Pelobello" : AppointmentsMainPanel.getSelectedDoctor();

        JLabel dName = new JLabel(doctor);
        dName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel av = new JLabel("Available Today");
        av.setForeground(Color.GRAY);
        av.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton changeBtn = new JButton("Change");
        changeBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        changeBtn.setBackground(PRIMARY_BLUE);
        changeBtn.setForeground(Color.WHITE);
        changeBtn.setOpaque(true);
        changeBtn.setBorderPainted(false);
        changeBtn.addActionListener(e -> MindanaCare.loadPage(AppointmentsPage.createPanel()));

        textInfo.add(dName);
        textInfo.add(av);
        textInfo.add(Box.createVerticalStrut(8));
        textInfo.add(changeBtn);

        topSection.add(textInfo);
        card.add(topSection);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        String[] info = {"📍 University of Mindanao", "📅 9 Years Experience", "👤 In-Person"};
        for (String i : info) {
            JLabel lbl = new JLabel(i);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.add(lbl);
            card.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel time = new JLabel("🕒 9:00 AM - 5:00 PM");
        time.setFont(new Font("Segoe UI", Font.BOLD, 14));
        time.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(time);
        card.add(Box.createVerticalGlue());

        return card;
    }

    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bgColor;
        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius;
            this.bgColor = bgColor;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        }
    }
}