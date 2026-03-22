import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

public class Appointments3 {

    private static final Color PRIMARY_BLUE = new Color(59, 130, 246);
    private static final Color GRADIENT_START = new Color(240, 247, 255);
    private static final Color GRADIENT_END = new Color(219, 234, 254);
    private static final Color TEXT_DARK = new Color(30, 41, 59);
    private static final Color TEXT_GRAY = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);

    private static JTextField nameInput, idInput, emailInput, phoneInput;

    private static JPanel mainCardPanel;
    private static CardLayout cardLayout;

    public static JPanel createPanel() {
        JPanel mainContent = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, GRADIENT_START, 0, getHeight(), GRADIENT_END);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainContent.setPreferredSize(new Dimension(1100, 1000));

        // TOP: header + global stepper
        JPanel topSection = new JPanel();
        topSection.setLayout(new BoxLayout(topSection, BoxLayout.Y_AXIS));
        topSection.setOpaque(false);
        topSection.setBorder(new EmptyBorder(30, 60, 20, 60));

        JLabel header = new JLabel("<html><font color='#3b82f6'>Book</font> Appointment - <b>MINDANA</b><font color='#3b82f6'>CARE</font></html>");
        header.setFont(new Font("Segoe UI", Font.BOLD, 36));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSection.add(header);
        topSection.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel globalStepperContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        globalStepperContainer.setOpaque(false);
        globalStepperContainer.add(createGlobalStepper());
        topSection.add(globalStepperContainer);

        mainContent.add(topSection, BorderLayout.NORTH);

        // CENTER: card panel fills remaining height
        cardLayout = new CardLayout();
        mainCardPanel = new JPanel(cardLayout);
        mainCardPanel.setOpaque(false);

        mainCardPanel.add(createStepPanel("STEP 4 of 6", "Enter Your Information",   1, createInfoForm()),       "STEP4");
        mainCardPanel.add(createStepPanel("STEP 5 of 6", "Reason for Consultation",  1, createReasonForm()),     "STEP5");
        mainCardPanel.add(createStepPanel("STEP 6 of 6", "Confirm Your Appointment", 2, createConfirmSummary()), "STEP6");

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(10, 60, 30, 60));
        centerWrapper.add(mainCardPanel);

        mainContent.add(centerWrapper, BorderLayout.CENTER);

        return mainContent;
    }

    private static JPanel createGlobalStepper() {
        RoundedPanel stepper = new RoundedPanel(10, new Color(200, 215, 240));
        stepper.setLayout(new GridLayout(1, 4));
        stepper.setPreferredSize(new Dimension(980, 50));

        String[] steps = {"Select Doctor", "Confirm Doctor", "Confirm Details", "Confirm Appointment"};
        for (int i = 0; i < steps.length; i++) {
            JLabel s = new JLabel(steps[i], SwingConstants.CENTER);
            s.setFont(new Font("Segoe UI", Font.BOLD, 14));
            if (i == 2) {
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

    private static JPanel createStepPanel(String stepLabel, String title, int stepperIdx, JPanel content) {
        RoundedPanel card = new RoundedPanel(30, Color.WHITE);
        // Fixed height so the card always fits within the visible window
        card.setPreferredSize(new Dimension(620, 700));
        card.setMaximumSize(new Dimension(620, 700));
        card.setMinimumSize(new Dimension(620, 700));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(28, 40, 22, 40));

        // NORTH: step label, title, mini-stepper, doctor card
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel sLbl = new JLabel(stepLabel);
        sLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sLbl.setForeground(PRIMARY_BLUE);
        sLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tLbl = new JLabel(title);
        tLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        tLbl.setForeground(TEXT_DARK);
        tLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel miniStepper = createRoundedStepper(stepperIdx);
        miniStepper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel drCard = createDoctorCard();
        drCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(sLbl);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        headerPanel.add(tLbl);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        headerPanel.add(miniStepper);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        headerPanel.add(drCard);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 14)));

        card.add(headerPanel, BorderLayout.NORTH);

        // CENTER: scrollable form content
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        card.add(scrollPane, BorderLayout.CENTER);

        // SOUTH: buttons always pinned to bottom of card
        JPanel footer = new JPanel(new GridLayout(1, 2, 20, 0));
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(14, 0, 0, 0));

        JButton backBtn = createStyledButton("< Back", false);
        JButton nextBtn = createStyledButton(stepperIdx == 2 ? "Confirm Appointment" : "Next Step >", true);

        backBtn.addActionListener(e -> cardLayout.previous(mainCardPanel));
        nextBtn.addActionListener(e -> {
            if (nextBtn.getText().equals("Confirm Appointment")) {
                handleConfirmAppointment();
            } else {
                if (!validateInfoForm()) return;
                cardLayout.next(mainCardPanel);
            }
        });

        footer.add(backBtn);
        footer.add(nextBtn);
        card.add(footer, BorderLayout.SOUTH);

        return card;
    }

    private static void handleConfirmAppointment() {
        String date      = AppointmentsMainPanel.getSelectedDate();
        String time      = AppointmentsMainPanel.getSelectedTime();
        String month     = AppointmentsMainPanel.getSelectedMonth();
        String year      = AppointmentsMainPanel.getSelectedYear();
        String specialty = AppointmentsMainPanel.getSelectedSpecialty().isEmpty()
                ? "General" : AppointmentsMainPanel.getSelectedSpecialty();
        String doctor    = AppointmentsMainPanel.getSelectedDoctor().isEmpty()
                ? "Dr. Aria Pelobello" : AppointmentsMainPanel.getSelectedDoctor();

        String fullDate = month + " " + date + ", " + year;

        int confirm = JOptionPane.showConfirmDialog(null,
                "Confirm your appointment with " + doctor
                        + "\non " + fullDate + " at " + time
                        + "\n\nLocation: UM Health Center",
                "Confirm Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (AppointmentManager.getInstance().isSlotTaken(doctor, date, time)) {
            JOptionPane.showMessageDialog(null,
                    "This time slot is already taken. Please choose another.",
                    "Slot Unavailable", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User currentUser = UserManager.getInstance().getCurrentUser();
        AppointmentManager.getInstance().book(
                currentUser.getEmail(), doctor, specialty, fullDate, time, "UM Health Center");

        JOptionPane.showMessageDialog(null,
                "Appointment booked successfully!\nYou will receive a confirmation shortly.",
                "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);

        AppointmentsMainPanel.nextPage();
    }

    private static boolean validateInfoForm() {
        if (nameInput  != null && nameInput.getText().isBlank())  { JOptionPane.showMessageDialog(null, "Please fill in your full name.",     "Missing Info", JOptionPane.WARNING_MESSAGE); return false; }
        if (idInput    != null && idInput.getText().isBlank())    { JOptionPane.showMessageDialog(null, "Please fill in your ID number.",     "Missing Info", JOptionPane.WARNING_MESSAGE); return false; }
        if (emailInput != null && emailInput.getText().isBlank()) { JOptionPane.showMessageDialog(null, "Please fill in your email address.", "Missing Info", JOptionPane.WARNING_MESSAGE); return false; }
        if (phoneInput != null && phoneInput.getText().isBlank()) { JOptionPane.showMessageDialog(null, "Please fill in your phone number.",  "Missing Info", JOptionPane.WARNING_MESSAGE); return false; }
        return true;
    }

    private static JPanel createInfoForm() {
        JPanel p = new JPanel(new GridLayout(8, 1, 0, 4));
        p.setOpaque(false);

        User currentUser = UserManager.getInstance().getCurrentUser();
        String prefillName  = currentUser != null ? currentUser.getFullName()      : "";
        String prefillEmail = currentUser != null ? currentUser.getEmail()         : "";
        String prefillPhone = currentUser != null ? currentUser.getContactNumber() : "";

        p.add(createGuideLabel("Full Name"));     nameInput  = createInput(prefillName);  p.add(nameInput);
        p.add(createGuideLabel("ID Number"));     idInput    = createInput("");            p.add(idInput);
        p.add(createGuideLabel("Email Address")); emailInput = createInput(prefillEmail); p.add(emailInput);
        p.add(createGuideLabel("Phone Number"));  phoneInput = createInput(prefillPhone); p.add(phoneInput);

        return p;
    }

    private static JPanel createReasonForm() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setOpaque(false);

        JLabel lbl = createGuideLabel("Describe your concern:");
        JTextArea area = new JTextArea("Experiencing chest pain and palpitations.");
        area.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane areaScroll = new JScrollPane(area);
        areaScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));

        p.add(lbl, BorderLayout.NORTH);
        p.add(areaScroll, BorderLayout.CENTER);
        return p;
    }

    private static JPanel createConfirmSummary() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        RoundedPanel box = new RoundedPanel(20, GRADIENT_START);
        box.setLayout(new BorderLayout());

        JLabel infoLabel = new JLabel();
        infoLabel.setVerticalAlignment(SwingConstants.TOP);

        p.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                String date      = AppointmentsMainPanel.getSelectedDate();
                String time      = AppointmentsMainPanel.getSelectedTime();
                String month     = AppointmentsMainPanel.getSelectedMonth();
                String year      = AppointmentsMainPanel.getSelectedYear();
                String specialty = AppointmentsMainPanel.getSelectedSpecialty().isEmpty()
                        ? "General" : AppointmentsMainPanel.getSelectedSpecialty();
                String doctor    = AppointmentsMainPanel.getSelectedDoctor().isEmpty()
                        ? "Dr. Aria Pelobello" : AppointmentsMainPanel.getSelectedDoctor();

                infoLabel.setText(
                        "<html><body style='padding:20px; font-family:Segoe UI; font-size:13pt;'>"
                                + "<b>" + doctor + "</b><br>"
                                + "<font color='gray'>" + specialty + "</font><br><br>"
                                + "📅 <b>" + month + " " + date + ", " + year + "</b><br>"
                                + "🕒 <b>" + time + "</b><br><br>"
                                + "📍 <b>UM Health Center</b><br>"
                                + "<font color='gray'>University of Mindanao</font>"
                                + "</body></html>"
                );
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            public void ancestorMoved(javax.swing.event.AncestorEvent e) {}
        });

        box.add(infoLabel, BorderLayout.CENTER);
        p.add(box, BorderLayout.CENTER);
        return p;
    }

    private static JPanel createDoctorCard() {
        RoundedPanel p = new RoundedPanel(20, GRADIENT_START);
        p.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 10));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int d = Math.min(getWidth(), getHeight());
                Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, d, d);
                g2.setClip(circle);
                URL imgURL = Appointments3.class.getResource("img.png");
                if (imgURL != null) g2.drawImage(new ImageIcon(imgURL).getImage(), 0, 0, d, d, null);
                else { g2.setColor(PRIMARY_BLUE); g2.fill(circle); }
                g2.dispose();
            }
        };
        avatar.setPreferredSize(new Dimension(48, 48));
        avatar.setOpaque(false);

        JLabel info = new JLabel(
                "<html><b style='font-size:11pt; color:#1e293b;'>"
                        + AppointmentsMainPanel.getSelectedDoctor() + "</b><br>"
                        + "<font color='gray'>" + AppointmentsMainPanel.getSelectedSpecialty() + "</font></html>"
        );
        p.add(avatar);
        p.add(info);
        return p;
    }

    private static JPanel createRoundedStepper(int activeIdx) {
        JPanel ms = new JPanel(new GridLayout(1, 3, 12, 0));
        ms.setOpaque(false);
        ms.setPreferredSize(new Dimension(530, 34));
        ms.setMaximumSize(new Dimension(530, 34));
        String[] labels = {"1. Select", "2. Details", "3. Confirm"};
        for (int i = 0; i < 3; i++) {
            RoundedPanel box = new RoundedPanel(18, i <= activeIdx ? PRIMARY_BLUE : BORDER_COLOR);
            box.setLayout(new GridBagLayout());
            JLabel l = new JLabel(labels[i]);
            l.setFont(new Font("Segoe UI", Font.BOLD, 13));
            l.setForeground(i <= activeIdx ? Color.WHITE : Color.GRAY);
            box.add(l);
            ms.add(box);
        }
        return ms;
    }

    private static JLabel createGuideLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 13));
        l.setForeground(TEXT_GRAY);
        return l;
    }

    private static JTextField createInput(String val) {
        JTextField f = new JTextField(val);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1), new EmptyBorder(10, 14, 10, 14)));
        return f;
    }

    private static JButton createStyledButton(String text, boolean primary) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(200, 46));
        if (primary) {
            b.setBackground(PRIMARY_BLUE);
            b.setForeground(Color.WHITE);
            b.setOpaque(true);
            b.setBorderPainted(false);
        } else {
            b.setBackground(Color.WHITE);
            b.setForeground(Color.GRAY);
            b.setOpaque(true);
            b.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        }
        return b;
    }

    static class RoundedPanel extends JPanel {
        private final int r; private final Color c;
        public RoundedPanel(int r, Color c) { this.r = r; this.c = c; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c); g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), r, r));
            g2.dispose();
        }
    }
}