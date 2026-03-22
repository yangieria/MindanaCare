import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class Appointments2 {

    private static final Color PRIMARY_BLUE = new Color(59, 130, 246);
    private static final Color LIGHT_BLUE_BG = new Color(224, 236, 252);
    private static final Color CALENDAR_TEXT = new Color(30, 58, 138);

    // Static variables to keep track of selection within the current session
    private static JButton selectedDayBtn = null;
    private static JButton selectedTimeBtn = null;

    // Track current month/year being displayed
    private static int currentYear = LocalDate.now().getYear();
    private static int currentMonth = LocalDate.now().getMonthValue(); // 1-12

    // References to update dynamically
    private static JLabel monthYearLabel;
    private static JPanel gridPanel;
    private static JPanel timePanel;
    private static JPanel cardRef;

    public static JPanel createPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(1100, 950));

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.WHITE);

        JLabel header = new JLabel("<html><font color='#3b82f6'>Book</font> Appointment - <b>MINDANA</b><font color='#3b82f6'>CARE</font></html>");
        header.setFont(new Font("Segoe UI", Font.BOLD, 32));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBorder(new EmptyBorder(30, 0, 20, 0));
        mainContainer.add(header);

        mainContainer.add(createStepper());
        mainContainer.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel calendarSection = new JPanel(new GridBagLayout());
        calendarSection.setBackground(LIGHT_BLUE_BG);
        calendarSection.setPreferredSize(new Dimension(1100, 600));
        calendarSection.add(createCalendarCard());

        mainContainer.add(calendarSection);
        mainPanel.add(mainContainer, BorderLayout.NORTH);

        return mainPanel;
    }

    private static JPanel createStepper() {
        RoundedPanel stepper = new RoundedPanel(10, new Color(200, 215, 240));
        stepper.setLayout(new GridLayout(1, 4));
        stepper.setMaximumSize(new Dimension(980, 55));
        stepper.setPreferredSize(new Dimension(980, 55));

        String[] steps = {"Select Doctor", "Confirm Doctor", "Confirm Details", "Confirm Appointment"};
        for (int i = 0; i < steps.length; i++) {
            JLabel s = new JLabel(steps[i], SwingConstants.CENTER);
            s.setFont(new Font("Segoe UI", Font.BOLD, 15));
            if (i == 1) {
                s.setOpaque(true);
                s.setBackground(PRIMARY_BLUE);
                s.setForeground(Color.WHITE);
            } else {
                s.setOpaque(false);
                s.setForeground(PRIMARY_BLUE);
            }
            stepper.add(s);
        }
        return stepper;
    }

    private static JPanel createCalendarCard() {
        RoundedPanel card = new RoundedPanel(40, Color.WHITE);
        cardRef = card;
        card.setPreferredSize(new Dimension(580, 520));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(25, 25, 25, 25));

        // --- TOP: Month/Year label + nav arrows ---
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        // Prev/Next navigation
        JButton prevBtn = new JButton("◀");
        JButton nextBtn = new JButton("▶");
        styleNavButton(prevBtn);
        styleNavButton(nextBtn);

        monthYearLabel = new JLabel("", SwingConstants.LEFT);
        monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        updateMonthYearLabel();

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        navPanel.setOpaque(false);
        navPanel.add(prevBtn);
        navPanel.add(nextBtn);

        top.add(monthYearLabel, BorderLayout.WEST);
        top.add(navPanel, BorderLayout.EAST);
        card.add(top, BorderLayout.NORTH);

        // --- CENTER: Calendar grid + time slots ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        gridPanel = new JPanel();
        gridPanel.setOpaque(false);
        buildCalendarGrid();
        centerPanel.add(gridPanel, BorderLayout.CENTER);

        // --- TIME SLOTS ---
        timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        timePanel.setOpaque(false);
        buildTimeSlots();
        centerPanel.add(timePanel, BorderLayout.SOUTH);

        card.add(centerPanel, BorderLayout.CENTER);

        // --- BOTTOM: Next Button ---
        JButton nextPageBtn = new JButton("Confirm Selection & Continue >");
        nextPageBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nextPageBtn.setBackground(PRIMARY_BLUE);
        nextPageBtn.setForeground(Color.WHITE);
        nextPageBtn.setPreferredSize(new Dimension(0, 50));
        nextPageBtn.setOpaque(true);
        nextPageBtn.setBorderPainted(false);
        nextPageBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextPageBtn.addActionListener(e -> {
            if (selectedDayBtn == null) {
                JOptionPane.showMessageDialog(null, "Please select a date.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (selectedTimeBtn == null) {
                JOptionPane.showMessageDialog(null, "Please select a time slot.", "Missing Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            AppointmentsMainPanel.setSelectedDate(selectedDayBtn.getText());
            AppointmentsMainPanel.setSelectedTime(selectedTimeBtn.getText());
            AppointmentsMainPanel.nextPage();
        });
        card.add(nextPageBtn, BorderLayout.SOUTH);

        // Nav button actions
        prevBtn.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 1) {
                currentMonth = 12;
                currentYear--;
            }
            selectedDayBtn = null;
            selectedTimeBtn = null;
            updateMonthYearLabel();
            buildCalendarGrid();
            buildTimeSlots();
            card.revalidate();
            card.repaint();
        });

        nextBtn.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 12) {
                currentMonth = 1;
                currentYear++;
            }
            selectedDayBtn = null;
            selectedTimeBtn = null;
            updateMonthYearLabel();
            buildCalendarGrid();
            buildTimeSlots();
            card.revalidate();
            card.repaint();
        });

        return card;
    }

    private static void styleNavButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(PRIMARY_BLUE);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 215, 240), 1, true));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(36, 30));
    }

    private static void updateMonthYearLabel() {
        YearMonth ym = YearMonth.of(currentYear, currentMonth);
        String monthName = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthYearLabel.setText("<html><font color='#1e3a8a'><b>" + monthName + "</b></font> <font color='#94a3b8'>" + currentYear + "</font></html>");
    }

    private static void buildCalendarGrid() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(0, 7, 5, 5));

        // Day headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String d : days) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lbl.setForeground(new Color(100, 120, 160));
            gridPanel.add(lbl);
        }

        YearMonth ym = YearMonth.of(currentYear, currentMonth);
        int daysInMonth = ym.lengthOfMonth();
        // Day of week for the 1st (1=Mon...7=Sun in ISO, convert to Sun=0)
        int firstDayOfWeek = LocalDate.of(currentYear, currentMonth, 1).getDayOfWeek().getValue() % 7;

        // Empty cells before the 1st
        for (int i = 0; i < firstDayOfWeek; i++) {
            gridPanel.add(new JLabel(""));
        }

        LocalDate today = LocalDate.now();

        for (int i = 1; i <= daysInMonth; i++) {
            final int day = i;
            JButton dayBtn = new JButton(String.valueOf(day));
            dayBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            dayBtn.setFocusPainted(false);
            dayBtn.setBorderPainted(false);
            dayBtn.setContentAreaFilled(false);
            dayBtn.setOpaque(false);

            LocalDate thisDate = LocalDate.of(currentYear, currentMonth, day);
            boolean isPast = thisDate.isBefore(today);

            if (isPast) {
                dayBtn.setForeground(new Color(180, 190, 210));
                dayBtn.setEnabled(false);
            } else {
                dayBtn.setForeground(CALENDAR_TEXT);
                dayBtn.addActionListener(e -> {
                    if (selectedDayBtn != null) {
                        selectedDayBtn.setOpaque(false);
                        selectedDayBtn.setForeground(CALENDAR_TEXT);
                    }
                    selectedDayBtn = dayBtn;
                    selectedDayBtn.setOpaque(true);
                    selectedDayBtn.setBackground(PRIMARY_BLUE);
                    selectedDayBtn.setForeground(Color.WHITE);
                    // Refresh time slots when a new day is selected
                    buildTimeSlots();
                    timePanel.revalidate();
                    timePanel.repaint();
                    cardRef.repaint();
                });
            }

            gridPanel.add(dayBtn);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private static void buildTimeSlots() {
        timePanel.removeAll();
        selectedTimeBtn = null;

        String[] times = {"09:00 AM", "10:30 AM", "1:30 PM", "3:30 PM", "4:30 PM"};

        for (String t : times) {
            JButton tBtn = new JButton(t);
            tBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tBtn.setPreferredSize(new Dimension(100, 40));

            boolean taken = AppointmentManager.getInstance().isSlotTaken(
                    "Dr. Aria Pelobello",
                    selectedDayBtn != null ? selectedDayBtn.getText() : "",
                    t
            );

            if (taken) {
                tBtn.setBackground(new Color(220, 220, 220));
                tBtn.setForeground(Color.GRAY);
                tBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
                tBtn.setEnabled(false);
                tBtn.setToolTipText("This slot is already booked");
            } else {
                tBtn.setBackground(Color.WHITE);
                tBtn.setForeground(CALENDAR_TEXT);
                tBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
                tBtn.addActionListener(e -> {
                    if (selectedTimeBtn != null) {
                        selectedTimeBtn.setBackground(Color.WHITE);
                        selectedTimeBtn.setForeground(CALENDAR_TEXT);
                        selectedTimeBtn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
                    }
                    selectedTimeBtn = tBtn;
                    selectedTimeBtn.setBackground(PRIMARY_BLUE);
                    selectedTimeBtn.setForeground(Color.WHITE);
                    selectedTimeBtn.setBorder(null);
                });
            }

            timePanel.add(tBtn);
        }

        timePanel.revalidate();
        timePanel.repaint();
    }

    // Custom Rounded Panel Class
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
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }
    }
}