import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ResumeBuilder {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static final String DATA_FILE = "users.dat";

    public static void main(String[] args) {
        loadUsers();
        showHomePage();
    }

    private static void showHomePage() {
        JFrame frame = new JFrame("Resume Builder - Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Professional Resume Builder", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JTextArea infoArea = new JTextArea();
        infoArea.setText("Welcome to Professional Resume Builder!\n\n" +
                "Features:\n" +
                "• Create professional resumes in minutes\n" +
                "• Easy-to-use interface\n" +
                "• Customizable templates\n" +
                "• Save and edit your resume anytime\n" +
                "• Export to PDF format\n" +
                "• Job matching based on your skills\n\n" +
                "Get started by signing up or logging in!");
        infoArea.setFont(new Font("Arial", Font.PLAIN, 16));
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(240, 240, 240));
        infoArea.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setBorder(null);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 102, 204));
        signupButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);
        signupButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        signupButton.setPreferredSize(new Dimension(120, 40));

        loginButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupPage();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(20));
        buttonPanel.add(signupButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showLoginPage() {
        JFrame frame = new JFrame("Resume Builder - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login to Your Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                currentUser = users.get(username);
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                showResumeForm();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showHomePage();
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void showSignupPage() {
        JFrame frame = new JFrame("Resume Builder - Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Create New Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 51, 102));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0;
        mainPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        mainPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        mainPanel.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField confirmPasswordField = new JPasswordField(15);
        mainPanel.add(confirmPasswordField, gbc);

        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(0, 153, 76));
        signupButton.setForeground(Color.WHITE);
        mainPanel.add(signupButton, gbc);

        gbc.gridy = 5;
        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, gbc);

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match!");
                return;
            }
            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(frame, "Username already exists!");
                return;
            }
            users.put(username, new User(username, password));
            saveUsers();
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            showLoginPage();
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showHomePage();
        });

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // ✅ Updated Resume Form with navigation buttons
    private static void showResumeForm() {
    JFrame frame = new JFrame("Resume Builder - Step-by-Step");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(900, 600);
    frame.setLocationRelativeTo(null);

    // 🔹 Create a custom JTabbedPane that ignores direct clicks
    JTabbedPane tabbedPane = new JTabbedPane() {
        @Override
        public void setSelectedIndex(int index) {
            // Only allow selection through Next/Back
            int current = getSelectedIndex();
            if (index == current + 1 || index == current - 1 || index == current) {
                super.setSelectedIndex(index);
            }
        }
    };

    // 🔹 Add all sections
    JPanel personalPanel = createPersonalInfoPanel(tabbedPane);
    JPanel educationPanel = createEducationPanel(tabbedPane);
    JPanel experiencePanel = createExperiencePanel(tabbedPane);
    JPanel skillsPanel = createSkillsPanel(tabbedPane);
    JPanel previewPanel = createPreviewPanel();

    tabbedPane.addTab("Personal Info", personalPanel);
    tabbedPane.addTab("Education", educationPanel);
    tabbedPane.addTab("Experience", experiencePanel);
    tabbedPane.addTab("Skills", skillsPanel);
    tabbedPane.addTab("Preview & Jobs", previewPanel);

    // 🔹 Disable direct tab navigation (except the first)
    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
        tabbedPane.setEnabledAt(i, false);
    }
    tabbedPane.setEnabledAt(0, true); // Enable only the first tab initially

    frame.add(tabbedPane);
    frame.setVisible(true);
}

    // ✅ Panels with Next/Back buttons + validation
    private static JPanel createPersonalInfoPanel(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Full Name:", "Email:", "Phone:", "Address:", "LinkedIn:", "Summary:"};
        JTextField[] fields = new JTextField[labels.length - 1];
        JTextArea summaryArea = new JTextArea(4, 30);

        for (int i = 0; i < labels.length - 1; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            fields[i] = new JTextField(20);
            panel.add(fields[i], gbc);
        }

        gbc.gridx = 0; gbc.gridy = labels.length - 1;
        panel.add(new JLabel(labels[labels.length - 1]), gbc);
        gbc.gridx = 1;
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        panel.add(summaryScroll, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton nextButton = new JButton("Next →");
        saveButton.setBackground(new Color(0, 102, 204));
        saveButton.setForeground(Color.WHITE);
        nextButton.setBackground(new Color(0, 153, 76));
        nextButton.setForeground(Color.WHITE);

        saveButton.addActionListener(e -> {
            if (currentUser.getResume() == null) currentUser.setResume(new Resume());
            Resume resume = currentUser.getResume();
            resume.setFullName(fields[0].getText());
            resume.setEmail(fields[1].getText());
            resume.setPhone(fields[2].getText());
            resume.setAddress(fields[3].getText());
            resume.setLinkedIn(fields[4].getText());
            resume.setSummary(summaryArea.getText());
            saveUsers();
            JOptionPane.showMessageDialog(panel, "Personal Info Saved!");
        });

        nextButton.addActionListener(e -> {
            for (JTextField field : fields) {
                if (field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill all fields before continuing!");
                    return;
                }
            }
            if (summaryArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a summary!");
                return;
            }
            tabbedPane.setSelectedIndex(1);
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(nextButton);
        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        return panel;
    }

    // ✅ Education Panel
    private static JPanel createEducationPanel(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<Education> listModel = new DefaultListModel<>();
        JList<Education> list = new JList<>(listModel);

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField degree = new JTextField();
        JTextField inst = new JTextField();
        JTextField year = new JTextField();
        JTextField gpa = new JTextField();
        form.add(new JLabel("Degree:")); form.add(degree);
        form.add(new JLabel("Institution:")); form.add(inst);
        form.add(new JLabel("Year:")); form.add(year);
        form.add(new JLabel("GPA:")); form.add(gpa);

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton back = new JButton("← Back");
        JButton next = new JButton("Next →");
        add.setBackground(new Color(0, 153, 76)); add.setForeground(Color.WHITE);
        remove.setBackground(Color.RED); remove.setForeground(Color.WHITE);
        back.setBackground(Color.GRAY); back.setForeground(Color.WHITE);
        next.setBackground(new Color(0, 102, 204)); next.setForeground(Color.WHITE);

        add.addActionListener(e -> {
            if (degree.getText().isEmpty() || inst.getText().isEmpty() || year.getText().isEmpty() || gpa.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Fill all fields!");
                return;
            }
            Education edu = new Education(degree.getText(), inst.getText(), year.getText(), gpa.getText());
            listModel.addElement(edu);
            if (currentUser.getResume() == null) currentUser.setResume(new Resume());
            currentUser.getResume().addEducation(edu);
            saveUsers();
            degree.setText(""); inst.setText(""); year.setText(""); gpa.setText("");
        });

        remove.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                listModel.remove(idx);
                currentUser.getResume().getEducation().remove(idx);
                saveUsers();
            }
        });

        back.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        next.addActionListener(e -> {
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Add at least one education record!");
                return;
            }
            tabbedPane.setSelectedIndex(2);
        });

        JPanel buttons = new JPanel();
        buttons.add(add); buttons.add(remove); buttons.add(back); buttons.add(next);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(form, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    // ✅ Experience Panel
    private static JPanel createExperiencePanel(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<Experience> listModel = new DefaultListModel<>();
        JList<Experience> list = new JList<>(listModel);
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        JTextField comp = new JTextField();
        JTextField pos = new JTextField();
        JTextField dur = new JTextField();
        JTextArea desc = new JTextArea(3, 20);

        form.add(new JLabel("Company:")); form.add(comp);
        form.add(new JLabel("Position:")); form.add(pos);
        form.add(new JLabel("Duration:")); form.add(dur);
        form.add(new JLabel("Description:")); form.add(new JScrollPane(desc));

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton back = new JButton("← Back");
        JButton next = new JButton("Next →");
        add.setBackground(new Color(0, 153, 76)); add.setForeground(Color.WHITE);
        remove.setBackground(Color.RED); remove.setForeground(Color.WHITE);
        back.setBackground(Color.GRAY); back.setForeground(Color.WHITE);
        next.setBackground(new Color(0, 102, 204)); next.setForeground(Color.WHITE);

        add.addActionListener(e -> {
            if (comp.getText().isEmpty() || pos.getText().isEmpty() || dur.getText().isEmpty() || desc.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Fill all fields!");
                return;
            }
            Experience exp = new Experience(comp.getText(), pos.getText(), dur.getText(), desc.getText());
            listModel.addElement(exp);
            if (currentUser.getResume() == null) currentUser.setResume(new Resume());
            currentUser.getResume().addExperience(exp);
            saveUsers();
            comp.setText(""); pos.setText(""); dur.setText(""); desc.setText("");
        });

        remove.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                listModel.remove(idx);
                currentUser.getResume().getExperience().remove(idx);
                saveUsers();
            }
        });

        back.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        next.addActionListener(e -> {
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Add at least one experience!");
                return;
            }
            tabbedPane.setSelectedIndex(3);
        });

        JPanel buttons = new JPanel();
        buttons.add(add); buttons.add(remove); buttons.add(back); buttons.add(next);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(form, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }
    private static JPanel createSkillsPanel(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        JTextField skill = new JTextField();

        JButton add = new JButton("Add");
        JButton remove = new JButton("Remove");
        JButton back = new JButton("← Back");
        JButton next = new JButton("Next →");
        add.setBackground(new Color(0, 153, 76)); add.setForeground(Color.WHITE);
        remove.setBackground(Color.RED); remove.setForeground(Color.WHITE);
        back.setBackground(Color.GRAY); back.setForeground(Color.WHITE);
        next.setBackground(new Color(0, 102, 204)); next.setForeground(Color.WHITE);

        add.addActionListener(e -> {
            if (skill.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Enter a skill!");
                return;
            }
            model.addElement(skill.getText());
            if (currentUser.getResume() == null) currentUser.setResume(new Resume());
            currentUser.getResume().addSkill(skill.getText());
            saveUsers();
            skill.setText("");
        });

        remove.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                model.remove(idx);
                currentUser.getResume().getSkills().remove(idx);
                saveUsers();
            }
        });

        back.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        next.addActionListener(e -> {
            if (model.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Add at least one skill!");
                return;
            }
            tabbedPane.setSelectedIndex(4);
        });

        JPanel buttons = new JPanel();
        buttons.add(add); buttons.add(remove); buttons.add(back); buttons.add(next);

        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(skill, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    // ✅ Preview Panel
    private static JPanel createPreviewPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);

    JTextArea previewArea = new JTextArea();
    previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    previewArea.setEditable(false);

    JPanel buttonPanel = new JPanel(new FlowLayout());

    JButton generatePreviewButton = new JButton("Generate Preview");
    generatePreviewButton.setBackground(new Color(0, 102, 204));
    generatePreviewButton.setForeground(Color.WHITE);

    JButton findJobsButton = new JButton("Find Suitable Jobs");
    findJobsButton.setBackground(new Color(153, 0, 153));
    findJobsButton.setForeground(Color.WHITE);

    JButton exportPdfButton = new JButton("Export to PDF");
    exportPdfButton.setBackground(new Color(220, 20, 60));
    exportPdfButton.setForeground(Color.WHITE);

    // 🆕 Add dropdown for template selection
    JLabel templateLabel = new JLabel("Template:");
    JComboBox<String> templateSelector = new JComboBox<>(new String[]{
        "Classic", "Modern", "Creative"
    });

    // === Button Actions ===
    generatePreviewButton.addActionListener(e -> {
        if (currentUser.getResume() != null) {
            previewArea.setText(currentUser.getResume().generateResumeText());
        } else {
            previewArea.setText("No resume data available. Please fill in the forms.");
        }
    });

    findJobsButton.addActionListener(e -> {
        if (currentUser.getResume() != null) {
            String jobSuggestions = currentUser.getResume().suggestJobs();
            JOptionPane.showMessageDialog(panel, jobSuggestions, "Job Suggestions", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(panel, "Please create your resume first!");
        }
    });

    // 🆕 Export to PDF based on selected template
    exportPdfButton.addActionListener(e -> {
        if (currentUser.getResume() != null) {
            String selectedTemplate = (String) templateSelector.getSelectedItem();
            exportToPDF(selectedTemplate);  // call with template
        } else {
            JOptionPane.showMessageDialog(panel, "Please create your resume first!");
        }
    });

    // === Add all components ===
    buttonPanel.add(generatePreviewButton);
    buttonPanel.add(findJobsButton);
    buttonPanel.add(templateLabel);
    buttonPanel.add(templateSelector);
    buttonPanel.add(exportPdfButton);

    panel.add(buttonPanel, BorderLayout.NORTH);
    panel.add(new JScrollPane(previewArea), BorderLayout.CENTER);

    return panel;
}


    private static void exportToPDF(String template) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save PDF As");
    fileChooser.setSelectedFile(new File(currentUser.getResume().getFullName() + "_Resume.pdf"));

    int userSelection = fileChooser.showSaveDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".pdf")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
        }

        try {
            PDFExporter.exportResumeToPDF(currentUser.getResume(), fileToSave.getAbsolutePath(), template);
            JOptionPane.showMessageDialog(null,
                    "Resume successfully exported to PDF!\nLocation: " + fileToSave.getAbsolutePath(),
                    "PDF Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error exporting to PDF: " + ex.getMessage(),
                    "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


    // 🖨 Print/Export to PDF
    private static void printComponent(JTextArea area) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            Graphics2D g2 = (Graphics2D) graphics;
            g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            area.printAll(graphics);
            return Printable.PAGE_EXISTS;
        });
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

  
    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (HashMap<String, User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            users = new HashMap<>();
        }
    }
}