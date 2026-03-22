public class Appointment {
    private String userEmail;
    private String doctorName;
    private String specialty;
    private String date;
    private String time;
    private String location;
    private String status; // "Upcoming" or "Cancelled"

    public Appointment(String userEmail, String doctorName, String specialty,
                       String date, String time, String location, String status) {
        this.userEmail = userEmail;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public String getUserEmail()  { return userEmail; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialty()  { return specialty; }
    public String getDate()       { return date; }
    public String getTime()       { return time; }
    public String getLocation()   { return location; }
    public String getStatus()     { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return userEmail + "|" + doctorName + "|" + specialty + "|" + date + "|" + time + "|" + location + "|" + status;
    }

    public static Appointment fromString(String line) {
        String[] p = line.split("\\|", 7);
        if (p.length == 7)
            return new Appointment(p[0], p[1], p[2], p[3], p[4], p[5], p[6]);
        return null;
    }
}