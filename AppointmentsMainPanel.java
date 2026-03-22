import javax.swing.*;
import java.awt.*;

public class AppointmentsMainPanel {
    private static JPanel cardPanel;
    private static CardLayout cl;
    private static String selectedSpecialty = "";
    private static String selectedDate      = "";
    private static String selectedTime      = "";
    private static String selectedDoctor    = "";
    private static String selectedMonth     = "";
    private static String selectedYear      = "";

    public static void setSelectedDoctor(String doctor)       { selectedDoctor  = doctor;   }
    public static String getSelectedDoctor()                  { return selectedDoctor;       }

    public static void setSelectedDate(String date)           { selectedDate    = date;      }
    public static void setSelectedTime(String time)           { selectedTime    = time;      }
    public static String getSelectedDate()                    { return selectedDate;         }
    public static String getSelectedTime()                    { return selectedTime;         }

    public static void setSelectedMonth(String month)         { selectedMonth   = month;     }
    public static void setSelectedYear(String year)           { selectedYear    = year;      }
    public static String getSelectedMonth()                   { return selectedMonth;        }
    public static String getSelectedYear()                    { return selectedYear;         }

    public static void setSelectedSpecialty(String specialty) { selectedSpecialty = specialty; }
    public static String getSelectedSpecialty()               { return selectedSpecialty;      }

    public static JPanel createPanel() {
        cl = new CardLayout();
        cardPanel = new JPanel(cl);

        JPanel container = new JPanel(new BorderLayout());

        cardPanel.add(Appointments1.createPanel(), "STEP1");
        cardPanel.add(Appointments2.createPanel(), "STEP2");
        cardPanel.add(Appointments3.createPanel(), "STEP3");
        cardPanel.add(Appointments4.createPanel(), "STEP4");

        cl.show(cardPanel, "STEP1");
        container.add(cardPanel, BorderLayout.CENTER);
        return container;
    }

    public static void nextPage() {
        if (cl != null && cardPanel != null) {
            cl.next(cardPanel);
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    public static void showStep(String stepName) {
        if (cl != null && cardPanel != null) {
            cl.show(cardPanel, stepName);
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }
}