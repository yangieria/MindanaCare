import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private final List<User> users = new ArrayList<>();
    private static final String FILE_PATH = "users.txt";
    private User currentUser = null;

    private UserManager() {
        loadFromFile();
    }

    public static UserManager getInstance() {
        if (instance == null) instance = new UserManager();
        return instance;
    }

    public User getCurrentUser() { return currentUser; }
    public void setCurrentUser(User user) { currentUser = user; }

    public String register(String fullName, String email, String contactNumber,
                           String password, String confirmPassword) {
        if (fullName.isBlank() || email.isBlank() || contactNumber.isBlank() || password.isBlank() || confirmPassword.isBlank())
            return "All fields must be filled.";
        if (!fullName.trim().matches("^[A-Za-z]+(?:\\s+[A-Za-z]+)+$"))
            return "Please enter your full name (first and last name).";
        if (!email.trim().toLowerCase().matches("^[a-zA-Z0-9._%+\\-]+@gmail\\.com$"))
            return "Email must be a valid @gmail.com address.";
        if (!contactNumber.matches("^\\d{10}$"))
            return "Contact number must be exactly 10 digits.";
        if (!password.equals(confirmPassword))
            return "Passwords do not match.";
        if (emailExists(email))
            return "An account with this email already exists.";

        User newUser = new User(fullName, email.trim().toLowerCase(), "+63" + contactNumber, password);
        users.add(newUser);
        saveToFile(newUser);
        return null;
    }

    public User login(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean emailExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }

    private void saveToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(user.getFullName() + "," +
                    user.getEmail() + "," +
                    user.getContactNumber() + "," +
                    user.getPassword());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4)
                    users.add(new User(parts[0], parts[1], parts[2], parts[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() { return users; }
}