import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MindanaCare {

    public static HashMap<String, String> registeredUsers = new HashMap<>();
    static { registeredUsers.put("test@gmail.com", "1234"); }
    static ArrayList<String> appointments = new ArrayList<>();

    public static final Color BRAND_BLUE = Color.decode("#3B82F6");
    public static final Color DARK_BLUE = Color.decode("#1E3A8A");
    public static final Color BG_LIGHT = Color.decode("#F0F6FF");
    public static final Color TEXT_DARK = Color.decode("#1F2937");
    public static final Color TEXT_GRAY = Color.decode("#6B7280");
    public static final Color BORDER_GRAY = Color.decode("#E5E7EB");

    static JPanel contentArea;
    static JScrollPane mainScrollPane;
    static JPanel authPanel;
    static JPanel mainWrapper;
    static JPanel navbar;
    public static JButton btnHome, btnUser, btnDoc, btnAppt, btnAbout, btnContact;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mindana Care");
        frame.setSize(1280, 850);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainWrapper = new JPanel(new BorderLayout());
        mainWrapper.setBackground(Color.WHITE);

        mainScrollPane = new JScrollPane(mainWrapper);
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainScrollPane.setBorder(null);
        frame.add(mainScrollPane);

        navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.WHITE);
        navbar.setPreferredSize(new Dimension(1280, 80));
        navbar.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDER_GRAY),
                new EmptyBorder(10, 40, 10, 40)
        ));

        JPanel yuem = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        yuem.setOpaque(false);
        yuem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        yuem.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { loadPage(createHomePanel()); }
        });

        URL iconURL = MindanaCare.class.getResource("img_1.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            yuem.add(imgLabel);
        }
        JLabel logoText = new JLabel("MINDANA CARE");
        logoText.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoText.setForeground(DARK_BLUE);
        yuem.add(logoText);
        navbar.add(yuem, BorderLayout.WEST);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        menuPanel.setBackground(Color.WHITE);

        btnHome    = createNavButton("Home");
        btnUser    = createNavButton("User");
        btnDoc     = createNavButton("Doctor");
        btnAppt    = createNavButton("Appointments");
        btnAbout   = createNavButton("About");
        btnContact = createNavButton("Contact");

        menuPanel.add(btnHome);
        menuPanel.add(btnUser);
        menuPanel.add(btnDoc);
        menuPanel.add(btnAppt);
        menuPanel.add(btnAbout);
        menuPanel.add(btnContact);
        navbar.add(menuPanel, BorderLayout.CENTER);

        authPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        authPanel.setBackground(Color.WHITE);
        navbar.add(authPanel, BorderLayout.EAST);

        showLoggedOutButtons();

        mainWrapper.add(navbar, BorderLayout.NORTH);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(Color.WHITE);
        mainWrapper.add(contentArea, BorderLayout.CENTER);

        loadPage(createHomePanel());

        btnHome.addActionListener(e -> loadPage(createHomePanel()));
        btnUser.addActionListener(e -> {
            if (UserManager.getInstance().getCurrentUser() == null) {
                loadPage(LoginPanel1.createPanel());
            } else {
                loadPage(UserDashboard.createPanel());
            }
        });
        btnDoc.addActionListener(e -> loadPage(Doctors.createPanel()));
        btnAppt.addActionListener(e -> loadPage(AppointmentsPage.createPanel()));
        btnAbout.addActionListener(e -> loadPage(About1.createPanel()));
        btnContact.addActionListener(e -> loadPage(About2.createPanel()));

        frame.setVisible(true);
    }

    // --- Doctor dashboard (full screen, no navbar) ---
    public static void showDashboard() {
        mainWrapper.removeAll();
        mainWrapper.add(DashboardPanel.createPanel(), BorderLayout.CENTER);
        mainWrapper.revalidate();
        mainWrapper.repaint();
    }

    // --- Admin dashboard (full screen, no navbar) ---
    public static void showAdminDashboard() {
        mainWrapper.removeAll();
        mainWrapper.add(AdminDashboard.createPanel(), BorderLayout.CENTER);
        mainWrapper.revalidate();
        mainWrapper.repaint();
    }

    // --- Restore full app with navbar ---
    public static void showMainApp() {
        mainWrapper.removeAll();
        mainWrapper.add(navbar, BorderLayout.NORTH);
        mainWrapper.add(contentArea, BorderLayout.CENTER);
        loadPage(createHomePanel());
        mainWrapper.revalidate();
        mainWrapper.repaint();
    }

    public static void showLoggedOutButtons() {
        authPanel.removeAll();

        JButton btnLoginNav = createRoundedButton("Login", Color.WHITE, TEXT_DARK, true);
        btnLoginNav.setPreferredSize(new Dimension(100, 38));

        JButton btnSignInNav = createRoundedButton("Sign in", BRAND_BLUE, Color.WHITE, false);
        btnSignInNav.setPreferredSize(new Dimension(100, 38));

        btnLoginNav.addActionListener(e -> loadPage(LoginPanel1.createPanel()));
        btnSignInNav.addActionListener(e -> loadPage(RegisterPanel.createPanel()));

        authPanel.add(btnLoginNav);
        authPanel.add(btnSignInNav);
        authPanel.revalidate();
        authPanel.repaint();
    }

    public static void showLoggedInButtons() {
        authPanel.removeAll();

        JButton btnLogout = createRoundedButton("Logout", new Color(220, 38, 38), Color.WHITE, false);
        btnLogout.setPreferredSize(new Dimension(110, 38));

        btnLogout.addActionListener(e -> {
            UserManager.getInstance().setCurrentUser(null);
            JOptionPane.showMessageDialog(null, "Logged out successfully.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            showLoggedOutButtons();
            loadPage(LoginPanel1.createPanel());
        });

        authPanel.add(btnLogout);
        authPanel.revalidate();
        authPanel.repaint();
    }

    public static void loadPage(JPanel newPage) {
        contentArea.removeAll();
        contentArea.add(newPage, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
        SwingUtilities.invokeLater(() -> mainScrollPane.getVerticalScrollBar().setValue(0));
    }

    public static JPanel createHomePanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel heroContent = createRoundedPanel(40, BG_LIGHT, false);
        heroContent.setLayout(new GridLayout(1, 2, 20, 0));
        heroContent.setBorder(new EmptyBorder(40, 50, 40, 50));
        heroContent.setPreferredSize(new Dimension(1100, 600));

        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setOpaque(false);

        JLabel title1 = new JLabel("Your Health,");
        title1.setFont(new Font("Segoe UI", Font.BOLD, 50));
        title1.setForeground(BRAND_BLUE);
        JLabel title2 = new JLabel("Our Priority");
        title2.setFont(new Font("Segoe UI", Font.BOLD, 50));
        title2.setForeground(Color.BLACK);

        JTextArea desc = new JTextArea("Experience accessible, reliable healthcare through University of Mindanao's digital appointment system.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        desc.setForeground(TEXT_GRAY);
        desc.setWrapStyleWord(true); desc.setLineWrap(true);
        desc.setOpaque(false); desc.setEditable(false);
        desc.setMaximumSize(new Dimension(600, 80));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        btnRow.setOpaque(false);
        btnRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRow.setMaximumSize(new Dimension(800, 60));

        JButton btnBook = createRoundedButton(" Book Appointment ", BRAND_BLUE, Color.WHITE, false);
        btnBook.setPreferredSize(new Dimension(220, 45));
        btnBook.addActionListener(e -> {
            if (UserManager.getInstance().getCurrentUser() == null) {
                loadPage(LoginPanel1.createPanel());
            } else {
                loadPage(AppointmentsPage.createPanel());
            }
        });

        JButton btnFind = createRoundedButton(" Find Doctors", Color.WHITE, BRAND_BLUE, true);
        btnFind.setPreferredSize(new Dimension(160, 45));
        btnFind.addActionListener(e -> loadPage(Doctors.createPanel()));

        btnRow.add(btnBook); btnRow.add(btnFind);

        JPanel statsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        statsRow.setOpaque(false);
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsRow.add(createStatCard("20+", "Medical\nProfessionals"));
        statsRow.add(createStatCard("500+", "Student\nConsultation"));
        statsRow.add(createStatCard("24/7", "System\nAccess Support"));

        leftCol.add(title1); leftCol.add(title2);
        leftCol.add(Box.createRigidArea(new Dimension(0, 15)));
        leftCol.add(desc); leftCol.add(Box.createRigidArea(new Dimension(0, 25)));
        leftCol.add(btnRow); leftCol.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCol.add(statsRow);

        JLayeredPane rightCol = new JLayeredPane();
        URL iconURL = MindanaCare.class.getResource("img.png");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaled = icon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setBounds(50, 50, 450, 450);
            rightCol.add(imgLabel, JLayeredPane.DEFAULT_LAYER);
        }

        JPanel expertCard = createExpertCard();
        expertCard.setBounds(0, 360, 230, 85);
        rightCol.add(expertCard, JLayeredPane.PALETTE_LAYER);

        heroContent.add(leftCol); heroContent.add(rightCol);
        wrapper.add(heroContent);
        return wrapper;
    }

    public static JPanel createStatCard(String number, String text) {
        JPanel card = createRoundedPanel(20, Color.WHITE, true);
        card.setPreferredSize(new Dimension(140, 110));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel numLbl = new JLabel(number);
        numLbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        numLbl.setForeground(BRAND_BLUE);
        numLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel txtLbl = new JLabel("<html><center>" + text.replace("\n", "<br>") + "</center></html>");
        txtLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        txtLbl.setForeground(TEXT_DARK);
        txtLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtLbl.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(Box.createVerticalGlue());
        card.add(numLbl);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(txtLbl);
        card.add(Box.createVerticalGlue());

        return card;
    }

    public static JPanel createExpertCard() {
        JPanel card = createRoundedPanel(20, Color.WHITE, true);
        card.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        JLabel iconLbl = new JLabel();
        iconLbl.setIcon(new Icon() {
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#DBEAFE"));
                g2.fillOval(x, y, 45, 45);
                g2.setColor(BRAND_BLUE);
                g2.fillOval(x+15, y+10, 15, 15);
                g2.fillArc(x+10, y+28, 25, 20, 0, 180);
            }
            public int getIconWidth() { return 45; }
            public int getIconHeight() { return 45; }
        });
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        JLabel title = new JLabel("Expert Care");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel subtitle = new JLabel("Certified Professionals");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_GRAY);
        textPanel.add(title); textPanel.add(subtitle);
        card.add(iconLbl); card.add(textPanel);
        return card;
    }

    public static JButton createNavButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            public void paintComponent(Graphics g) {
                Boolean isActive = (Boolean) getClientProperty("active");
                Boolean isHovered = (Boolean) getClientProperty("hovered");
                if ((isActive != null && isActive) || (isHovered != null && isHovered)) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#F3F4F6"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(TEXT_DARK);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.putClientProperty("hovered", true);
                btn.setForeground(BRAND_BLUE);
                btn.repaint();
            }
            public void mouseExited(MouseEvent e) {
                btn.putClientProperty("hovered", false);
                Boolean isActive = (Boolean) btn.getClientProperty("active");
                if (isActive == null || !isActive) btn.setForeground(TEXT_DARK);
                btn.repaint();
            }
        });
        return btn;
    }

    public static JPanel createRoundedPanel(int radius, Color bgColor, boolean drawStroke) {
        return new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
                if(drawStroke){
                    g2.setColor(BORDER_GRAY); g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,radius,radius);
                }
            }
            { setOpaque(false); }
        };
    }

    public static JButton createRoundedButton(String text, Color bg, Color fg, boolean isOutline) {
        JButton btn = new JButton(text) {
            boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                    public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                });
            }
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D)g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if(isOutline){
                    g2.setColor(hovered ? Color.decode("#EFF6FF") : Color.WHITE);
                    g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,30,30);
                    g2.setColor(BRAND_BLUE); g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,30,30);
                } else {
                    Color fill = hovered ? bg.darker() : bg;
                    g2.setColor(fill);
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),30,30);
                }
                super.paintComponent(g);
            }
        };
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setContentAreaFilled(false); btn.setFocusPainted(false);
        btn.setBorderPainted(false); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}