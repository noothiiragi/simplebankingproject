package bankingproject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

public class CreateAccount {
    //temporary storage nung page 1
    private static String firstName;
    private static String middleName;
    private static String lastName;
    private static Date birthDate;
    private static String contactNumber;
    private static String address;

    //step 1
    public static void showStep1(JFrame frame, User admin) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Constants.white);
        
        firstName = null;
        middleName = null;
        lastName = null;
        birthDate = null;
        contactNumber = null;
        address = null;
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBounds(0, 0, 650, 100);
        titlePanel.setBackground(Constants.green);

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Create New Account");
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 20, 590, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);
        
        JLabel credLabel = new JLabel("Enter user information");
        credLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
        credLabel.setForeground(Color.WHITE);
        credLabel.setBounds(30, 60, 590, 20);
        credLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(credLabel);
        
        int rightLogoHeight = 65;
        int rightLogoWidth = 65;
        
        int leftLogoHeight = 75; 
        int leftLogoWidth = 75;
        
        try {
            ImageIcon logo1 = new ImageIcon("logos/logo1.png");
            Image getlogo1 = logo1.getImage();
            Image reziselogo1 = getlogo1.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            ImageIcon resizedlogo1 = new ImageIcon(reziselogo1);

            JLabel logo1Label = new JLabel(resizedlogo1);
            logo1Label.setBounds(80, (100 - leftLogoHeight) / 2, leftLogoWidth, leftLogoHeight);
            titlePanel.add(logo1Label);
            titlePanel.setComponentZOrder(logo1Label, 0);
            
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
            e.printStackTrace();
    }
        try {
            ImageIcon logo2 = new ImageIcon("logos/logo2.png");
            Image getLogo2 = logo2.getImage();
            Image resizeLogo2 = getLogo2.getScaledInstance(rightLogoWidth, rightLogoHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedlogo2 = new ImageIcon(resizeLogo2);

            JLabel logo2Label = new JLabel(resizedlogo2);
            logo2Label.setBounds(650 - rightLogoWidth - 80, (100 - rightLogoHeight) / 2, rightLogoWidth, rightLogoHeight);
            titlePanel.add(logo2Label);
            titlePanel.setComponentZOrder(logo2Label, 0);
                
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
            e.printStackTrace();
    }
        
        frame.add(titlePanel);
        
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(0, 100, 650, 350);
        formPanel.setBackground(Constants.white);

        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setText("First Name:");
        firstNameLabel.setFont(Constants.labelFont);
        firstNameLabel.setBounds(135, 20, 120, 30);
        formPanel.add(firstNameLabel);
        
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(270, 20, 250, 30);
        if (firstName != null) firstNameField.setText(firstName);
        formPanel.add(firstNameField);

        JLabel middleNameLabel = new JLabel();
        middleNameLabel.setText("Middle Name:");
        middleNameLabel.setFont(Constants.labelFont);
        middleNameLabel.setBounds(135, 60, 120, 30);
        formPanel.add(middleNameLabel);

        JTextField middleNameField = new JTextField();
        middleNameField.setBounds(270, 60, 250, 30);
        if (middleName != null) middleNameField.setText(middleName);
        formPanel.add(middleNameField);

        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setText("Last Name:");
        lastNameLabel.setFont(Constants.labelFont);
        lastNameLabel.setBounds(135, 100, 120, 30);
        formPanel.add(lastNameLabel);

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(270, 100, 250, 30);
        if (lastName != null) lastNameField.setText(lastName);
        formPanel.add(lastNameField);

        JLabel birthdateLabel = new JLabel();
        birthdateLabel.setText("Birthdate:");
        birthdateLabel.setFont(Constants.labelFont);
        birthdateLabel.setBounds(135, 140, 120, 30);
        formPanel.add(birthdateLabel);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner birthdateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(birthdateSpinner, "MM/dd/yyyy");
        birthdateSpinner.setEditor(dateEditor);
        if (birthDate != null) birthdateSpinner.setValue(birthDate);
        birthdateSpinner.setBounds(270, 140, 250, 30);
        formPanel.add(birthdateSpinner);
        
        JLabel contactLabel = new JLabel();
        contactLabel.setText("Contact Number: ");
        contactLabel.setFont(Constants.labelFont);
        contactLabel.setBounds(135, 180, 120, 30);
        formPanel.add(contactLabel);

        JTextField contactField = new JTextField();
        contactField.setBounds(270, 180, 250, 30);
        if (contactNumber != null) contactField.setText(contactNumber);
        formPanel.add(contactField);
        
        JLabel addressLabel = new JLabel();
        addressLabel.setText("Home Adress:");
        addressLabel.setFont(Constants.labelFont);
        addressLabel.setBounds(135, 220, 120, 30);
        formPanel.add(addressLabel);

        JTextField addressField = new JTextField();
        addressField.setBounds(270, 220, 250, 30);
        if (address != null) addressField.setText(address);
        formPanel.add(addressField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 360, 650, 60);
        buttonPanel.setBackground(Constants.gray);

        JButton nextButton = new JButton();
        nextButton.setText("Next");
        nextButton.setBounds(200, 8, 120, 35);
        nextButton.setFont(Constants.button1Font);
        nextButton.setBackground(Constants.green);
        buttonPanel.add(nextButton);
        
        JButton cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setBounds(340, 8, 120, 35);
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        buttonPanel.add(cancelButton);
        
        nextButton.addActionListener(e -> {
            firstName = firstNameField.getText().trim();
            middleName = middleNameField.getText().trim();
            lastName = lastNameField.getText().trim();
            birthDate = (Date) birthdateSpinner.getValue();
            contactNumber = contactField.getText().trim();
            address = addressField.getText().trim();
            
             LocalDate userBirthDate = birthDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    
            LocalDate today = LocalDate.now();
            LocalDate minimumDate = today.minusYears(1);

            if (firstName.isEmpty() || lastName.isEmpty() ||middleName.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please fill up all fields.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
             if (userBirthDate.isAfter(today)) {
                JOptionPane.showMessageDialog(frame,
                        "Invalid birthdate, Please enter a valid birthdate.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userBirthDate.isAfter(minimumDate)) {
                JOptionPane.showMessageDialog(frame,
                        "The user must be atleast 1 year old to register.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            showStep2(frame, admin);
        });

        cancelButton.addActionListener(e -> {
            User adminUser = new User();
            adminUser.isAdmin = true;
            adminUser.username= admin.username;
            AdminDashboard.show(frame, admin);
        });

        frame.add(buttonPanel);
        frame.add(formPanel);
        frame.revalidate();
        frame.repaint();
    }
    
    //step 2
     public static void showStep2(JFrame frame, User admin) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setBackground(Constants.white);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBounds(0, 0, 650, 100);
        titlePanel.setBackground(Constants.green);

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Create New Account");
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 20, 590, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel);

        JLabel credLabel = new JLabel("Enter user information");
        credLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
        credLabel.setForeground(Color.WHITE);
        credLabel.setBounds(30, 60, 590, 20);
        credLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(credLabel);
        
        int rightLogoHeight = 65;
        int rightLogoWidth = 65;
        
        int leftLogoHeight = 75; 
        int leftLogoWidth = 75;
        
        try {
            ImageIcon logo1 = new ImageIcon("logos/logo1.png");
            Image getlogo1 = logo1.getImage();
            Image reziselogo1 = getlogo1.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            ImageIcon resizedlogo1 = new ImageIcon(reziselogo1);

            JLabel logo1Label = new JLabel(resizedlogo1);
            logo1Label.setBounds(80, (100 - leftLogoHeight) / 2, leftLogoWidth, leftLogoHeight);
            titlePanel.add(logo1Label);
            titlePanel.setComponentZOrder(logo1Label, 0);
            
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
            e.printStackTrace();
    }
        try {
            ImageIcon logo2 = new ImageIcon("logos/logo2.png");
            Image getLogo2 = logo2.getImage();
            Image resizeLogo2 = getLogo2.getScaledInstance(rightLogoWidth, rightLogoHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedlogo2 = new ImageIcon(resizeLogo2);

            JLabel logo2Label = new JLabel(resizedlogo2);
            logo2Label.setBounds(650 - rightLogoWidth - 80, (100 - rightLogoHeight) / 2, rightLogoWidth, rightLogoHeight);
            titlePanel.add(logo2Label);
            titlePanel.setComponentZOrder(logo2Label, 0);
                
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
            e.printStackTrace();
    }

        frame.add(titlePanel);
  
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(0, 100, 650, 350);
        formPanel.setBackground(Constants.white);

        JLabel employmentLabel = new JLabel("Employment Status:");
        employmentLabel.setFont(Constants.labelFont);
        employmentLabel.setBounds(135, 20, 120, 30);
        formPanel.add(employmentLabel);

        String[] employmentOptions = {"Student", "Employed", "Self-employed", "Retired"};
        JComboBox<String> employmentComboBox = new JComboBox<>(employmentOptions);
        employmentComboBox.setBounds(270, 20, 250, 30);
        formPanel.add(employmentComboBox);

        JLabel fundsSourceLabel = new JLabel("Source of Funds:");
        fundsSourceLabel.setFont(Constants.labelFont);
        fundsSourceLabel.setBounds(135, 60, 120, 30);
        formPanel.add(fundsSourceLabel);

        String[] fundsOptions = {"Savings", "Allowance", "Salary", "Pension"};
        JComboBox<String> fundsComboBox = new JComboBox<>(fundsOptions);
        fundsComboBox.setBounds(270, 60, 250, 30);
        formPanel.add(fundsComboBox);

        JLabel incomeLabel = new JLabel("Monthly Funds:");
        incomeLabel.setFont(Constants.labelFont);
        incomeLabel.setBounds(135, 100, 120, 30);
        formPanel.add(incomeLabel);

        String[] incomeOptions = {"Under ₱5k", "₱5k-₱10k", "₱10k-₱15k", "Above ₱15k"};
        JComboBox<String> incomeComboBox = new JComboBox<>(incomeOptions);
        incomeComboBox.setBounds(270, 100, 250, 30);
        formPanel.add(incomeComboBox);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Constants.labelFont);
        usernameLabel.setBounds(135, 140, 120, 30);
        formPanel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(270, 140, 250, 30);
        formPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Constants.labelFont);
        passwordLabel.setBounds(135, 180, 120, 30);
        formPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(270, 180, 250, 30);
        formPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(Constants.labelFont);
        confirmPasswordLabel.setBounds(135, 220, 120, 30);
        formPanel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(270, 220, 250, 30);
        formPanel.add(confirmPasswordField);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 360, 650, 60);
        buttonPanel.setBackground(Constants.gray);

        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 8, 120, 35);
        backButton.setFont(Constants.button1Font);
        backButton.setBackground(Constants.green);
        backButton.addActionListener(e -> showStep1(frame, admin));
        buttonPanel.add(backButton);

        JButton createButton = new JButton("Proceed");
        createButton.setBounds(330, 8, 120, 35);
        createButton.setFont(Constants.button1Font);
        createButton.setBackground(Constants.green);
        buttonPanel.add(createButton);

        frame.add(buttonPanel);
        frame.add(formPanel);
        
        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String employment = (String) employmentComboBox.getSelectedItem();
            String sourceOfFunds = (String) fundsComboBox.getSelectedItem();
            String monthlyFunds = (String) incomeComboBox.getSelectedItem();
            
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please input your username and password", 
                    "Error Creating Account", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, 
                    "Passwords do not match", 
                    "Error Creating Account", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 8) {
            JOptionPane.showMessageDialog(frame, 
                "Password must be at least 8 characters long", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
                JOptionPane.showMessageDialog(frame, 
                    "Password must contain both letters and numbers", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (Database.checkUsername(username)) {
                JOptionPane.showMessageDialog(frame, 
                    "Username already exists. Please choose a different username.", 
                    "Error Creating Account", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String fullName = firstName + " " + 
                             (middleName.isEmpty() ? "" : middleName + " ") + 
                             lastName;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String birthDateStr = sdf.format(birthDate);
            
            if (createAccount(username, password, fullName, firstName, middleName, lastName, 
                  contactNumber, address, birthDateStr, employment, sourceOfFunds, monthlyFunds)) {
                JOptionPane.showMessageDialog(frame, 
                    "Account created successfully! You can now login.", 
                    "Account Created", JOptionPane.INFORMATION_MESSAGE);

                User adminUser = new User();
                adminUser.isAdmin = true;
                adminUser.username= admin.username;
                AdminDashboard.show(frame, adminUser);
            }
        });
        
        
        frame.revalidate();
        frame.repaint();
    }

    //insert sa databse
    private static boolean createAccount(String username, String password, String fullName,
                                         String firstName, String middleName, String lastName,
                                         String contact, String address, String birthDate,
                                         String employment, String sourceOfFunds, String monthlyFunds) {
        try {
            Connection conn = Database.getConnection();

            String accountNumber;
            do {
                accountNumber = generateAccountNumber();
            } while (Database.checkAccountNumber(accountNumber));

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, full_name, first_name, middle_name, last_name, contact, address, birth_date, " +
                            "employment, source_of_funds, monthly_funds, account_number, balance) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0.0)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullName);
            stmt.setString(4, firstName);
            stmt.setString(5, middleName);
            stmt.setString(6, lastName);
            stmt.setString(7, contact);
            stmt.setString(8, address);
            stmt.setString(9, birthDate);
            stmt.setString(10, employment);
            stmt.setString(11, sourceOfFunds);
            stmt.setString(12, monthlyFunds);
            stmt.setString(13, accountNumber);

            int rowsInserted = stmt.executeUpdate();
            stmt.close();

            return rowsInserted > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Database error: " + e.getMessage(),
                    "Account Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    //generate a random account number
    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        
        for (int i = 0; i < 10; i++) {
            accountNumber.append((int) (Math.random() * 10));
        }
        
        return accountNumber.toString();
    }
}