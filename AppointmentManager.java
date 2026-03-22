import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentManager {
    private static AppointmentManager instance;
    private final List<Appointment> appointments = new ArrayList<>();
    private static final String FILE_PATH = "appointments.txt";

    private AppointmentManager() { loadFromFile(); }

    public static AppointmentManager getInstance() {
        if (instance == null) instance = new AppointmentManager();
        return instance;
    }

    public void book(String userEmail, String doctorName, String specialty,
                     String date, String time, String location) {
        Appointment appt = new Appointment(userEmail, doctorName, specialty, date, time, location, "Upcoming");
        appointments.add(appt);
        saveAllToFile();
    }

    public List<Appointment> getForUser(String userEmail) {
        return appointments.stream()
                .filter(a -> a.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }

    public List<Appointment> getUpcoming(String userEmail) {
        return getForUser(userEmail).stream()
                .filter(a -> a.getStatus().equals("Upcoming"))
                .collect(Collectors.toList());
    }

    public List<Appointment> getHistory(String userEmail) {
        return getForUser(userEmail).stream()
                .filter(a -> a.getStatus().equals("Cancelled")
                        || a.getStatus().equals("Completed")
                        || a.getStatus().equals("Approved"))
                .collect(Collectors.toList());
    }

    public void cancel(Appointment appt) {
        appt.setStatus("Cancelled");
        saveAllToFile();
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public boolean isSlotTaken(String doctorName, String date, String time) {
        return appointments.stream().anyMatch(a ->
                a.getDoctorName().equals(doctorName) &&
                        a.getDate().equals(date) &&
                        a.getTime().equals(time) &&
                        a.getStatus().equals("Upcoming"));
    }

    private void saveAllToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Appointment a : appointments) {
                bw.write(a.toString());
                bw.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Appointment a = Appointment.fromString(line);
                if (a != null) appointments.add(a);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void saveAll() { saveAllToFile(); }
}