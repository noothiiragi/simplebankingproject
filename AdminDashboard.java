package bankingproject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard {
    private static JFrame mainFrame;
    private static User adminUser;
    private static JTable userTable;
    private static ArrayList<User> users;
    
    
    public static void show(JFrame frame, User admin) {
        
        mainFrame = frame;
        adminUser = admin;
        
        if (adminUser == null || !adminUser.isAdmin) {
            JOptionPane.showMessageDialog(mainFrame, 
                "Access denied. Admin credentials required.", 
                "Authentication Error", JOptionPane.ERROR_MESSAGE);
            LandingPage.show(mainFrame);
            return;
        }
        
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Constants.green);
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel topSpacer1 = new JLabel(" ");
        topSpacer1.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSpacer1.setPreferredSize(new Dimension(0, 15));
        topSpacer1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        leftPanel.add(topSpacer1);

        JLabel topSpacer2 = new JLabel(" ");
        topSpacer2.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSpacer2.setPreferredSize(new Dimension(0, 15));
        topSpacer2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 15));
        leftPanel.add(topSpacer2);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Constants.green);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(new Font(Constants.titleFont.getName(), Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel adminLabel = new JLabel(adminUser.username);
        adminLabel.setFont(new Font(Constants.titleFont.getName(), Font.BOLD, 20));
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomePanel.add(adminLabel);
        leftPanel.add(welcomePanel);
        
        JLabel preDashboardSpacer = new JLabel(" ");
        preDashboardSpacer.setAlignmentX(Component.CENTER_ALIGNMENT);
        preDashboardSpacer.setPreferredSize(new Dimension(0, 10));
        preDashboardSpacer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        leftPanel.add(preDashboardSpacer);

        JLabel topSpacer3 = new JLabel("");
        topSpacer3.setFont(Constants.titleFont);
        topSpacer3.setForeground(Color.WHITE);
        topSpacer3.setAlignmentX(Component.CENTER_ALIGNMENT);
        topSpacer3.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        leftPanel.add(topSpacer3);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] menuItems = {"Create Account", "Close Account", "Logout"};
        for (String item : menuItems) {
        JButton menuButton = new JButton(item);
        menuButton.setMaximumSize(new Dimension(180, 40));
        menuButton.setPreferredSize(new Dimension(180, 40));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setForeground(Color.BLACK);
        menuButton.setFont(new Font(Constants.button1Font.getName(), Font.BOLD, 14));
        menuButton.setBackground(Constants.white);
        menuButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Constants.green1));
        menuButton.setFocusPainted(false);

        if (item.equals("Close Account")) {
            menuButton.setForeground(Color.BLACK); 
            menuButton.setFont(new Font(Constants.button1Font.getName(), Font.BOLD, Constants.button1Font.getSize()));
        }

        menuButton.addActionListener(e -> {
            switch (item) {
                case "Create Account":
                    CreateAccount.showStep1(frame, admin);
                    break;
                case "Close Account":
                    int selectedRow = userTable.getSelectedRow();
                    if (selectedRow >= 0 && selectedRow < users.size()) {
                        User selectedUser = users.get(selectedRow);

                        if (selectedUser.balance > 0) {
                            JOptionPane.showMessageDialog(mainFrame, 
                                "You can only close accounts with ₱0 balance\n" +
                                "Current balance: ₱" + String.format("%.2f", selectedUser.balance),
                                "Account Closure Error", 
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int confirmResult = JOptionPane.showConfirmDialog(mainFrame,
                            "Are you sure you want to close this account?\n" +
                            "User: " + selectedUser.fullName + "\n" +
                            "Account Number: " + selectedUser.accountNumber + "\n\n" +
                            "This account will be closed permanently.",
                            "Confirm Account Closure",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                        if (confirmResult == JOptionPane.YES_OPTION) {
                            if (deleteUserAccount(selectedUser.id)) {
                                JOptionPane.showMessageDialog(mainFrame,
                                    "Account successfully closed.",
                                    "Account Closed",
                                    JOptionPane.INFORMATION_MESSAGE);

                                refreshDashboard();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, 
                            "Please select a user", 
                            "Selection Required", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case "Logout":
                    int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                        "Are you sure you want to log out?", 
                        "Confirm Logout", 
                        JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        adminUser = null;
                        LandingPage.show(mainFrame);
                    }
                    break;
            }
        });

       leftPanel.add(menuButton);
       leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

        mainFrame.add(leftPanel, BorderLayout.WEST);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Constants.white);

                JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(Constants.green2);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Constants.green2);
        JLabel dashboardTitle = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        dashboardTitle.setFont(Constants.headerFont);
        titlePanel.add(dashboardTitle, BorderLayout.CENTER);
        topBar.add(titlePanel, BorderLayout.CENTER);

        JPanel leftLogoPanel = new JPanel();
        leftLogoPanel.setLayout(new BorderLayout());
        leftLogoPanel.setBackground(Constants.green2);
        leftLogoPanel.setPreferredSize(new Dimension(120, 60));

        JPanel rightLogoPanel = new JPanel();
        rightLogoPanel.setLayout(new BorderLayout());
        rightLogoPanel.setBackground(Constants.green2);
        rightLogoPanel.setPreferredSize(new Dimension(120, 60));

        topBar.add(leftLogoPanel, BorderLayout.WEST);
        topBar.add(rightLogoPanel, BorderLayout.EAST);

        int leftLogoWidth = 50;
        int leftLogoHeight = 50;
        int rightLogoWidth = 40;
        int rightLogoHeight = 40;

            try {
                ImageIcon logo1 = new ImageIcon("logos/logo1.png");
                Image getLogo1 = logo1.getImage();
                Image resizeLogo1 = getLogo1.getScaledInstance(leftLogoWidth, leftLogoHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedLogo1 = new ImageIcon(resizeLogo1);

                JLabel logo1Label = new JLabel(resizedLogo1);
                leftLogoPanel.add(logo1Label, BorderLayout.CENTER);
                leftLogoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 0));
            } catch (Exception e) {
                System.out.println("Logo not found: " + e.getMessage());
                e.printStackTrace();
    }

            try {
                ImageIcon logo2 = new ImageIcon("logos/logo2.png");
                Image getLogo2 = logo2.getImage();
                Image resizeLogo2 = getLogo2.getScaledInstance(rightLogoWidth, rightLogoHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedLogo2 = new ImageIcon(resizeLogo2);
                JLabel logo2Label = new JLabel(resizedLogo2);
                rightLogoPanel.add(logo2Label, BorderLayout.CENTER);
                rightLogoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 20));
            } catch (Exception e) {
                System.out.println("Logo not found: " + e.getMessage());
                e.printStackTrace();
    }
        
        mainPanel.add(topBar, BorderLayout.NORTH);
        
        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(new BorderLayout());
        dashboardContent.setBackground(Constants.gray);
        
        JLabel usersLabel = new JLabel("User Accounts");
        usersLabel.setFont(Constants.titleFont);
        usersLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        dashboardContent.add(usersLabel, BorderLayout.NORTH);
        
        users = getAllUsers();
        userTable(users);
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        dashboardContent.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setPreferredSize(new Dimension(600, 80));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 3, 5, 3));
        buttonPanel.setBackground(Constants.gray);
        
        JButton viewButton = new JButton("View");
        viewButton.setFont(Constants.button2Font);
        viewButton.setBackground(Constants.green);
        viewButton.setPreferredSize(new Dimension(120, 25));
        viewButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showUserProfile(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton editButton = new JButton("Edit");
        editButton.setFont(Constants.button2Font);
        editButton.setBackground(Constants.green);
        editButton.setPreferredSize(new Dimension(120, 25));
        editButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showEditUser(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setFont(Constants.button2Font);
        resetPasswordButton.setBackground(Constants.green);
        resetPasswordButton.setPreferredSize(new Dimension(125, 25));
        resetPasswordButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showResetPassword(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton depositButton = new JButton("Deposit");
        depositButton.setFont(Constants.button2Font);
        depositButton.setBackground(Constants.green);
        depositButton.setPreferredSize(new Dimension(120, 25));
        depositButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showDeposit(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(Constants.button2Font);
        withdrawButton.setBackground(Constants.green);
        withdrawButton.setPreferredSize(new Dimension(120, 25));
        withdrawButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showWithdraw(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        JButton historyButton = new JButton("Transactions");
        historyButton.setFont(Constants.button2Font);
        historyButton.setBackground(Constants.green);
        historyButton.setPreferredSize(new Dimension(120, 25));
        historyButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < users.size()) {
                showTransactionHistory(users.get(selectedRow));
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Please select a user", 
                    "Selection Required", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(viewButton);
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(editButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(historyButton);
        
        dashboardContent.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(dashboardContent, BorderLayout.CENTER);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    
    private static void refreshDashboard() {
        int selectedRow = userTable.getSelectedRow();
        Integer userId = (selectedRow >= 0) ? users.get(selectedRow).id : null;

        users = getAllUsers();
        userTable(users);

        ((JScrollPane) userTable.getParent().getParent()).revalidate();
        ((JScrollPane) userTable.getParent().getParent()).repaint();

        if (userId != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).id == userId) {
                    userTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        }
    }
    
    //accpunt deletion
    private static boolean deleteUserAccount(int userId) {
        Connection conn = null;
        boolean success = false;
        
        try {
            conn = Database.getConnection();
            
            conn.setAutoCommit(false);
            
            PreparedStatement deleteTransactions = conn.prepareStatement(
                "DELETE FROM transactions WHERE user_id = ?");
            deleteTransactions.setInt(1, userId);
            deleteTransactions.executeUpdate();
            deleteTransactions.close();
            
            PreparedStatement deleteUser = conn.prepareStatement(
                "DELETE FROM users WHERE id = ?");
            deleteUser.setInt(1, userId);
            int rowsAffected = deleteUser.executeUpdate();
            deleteUser.close();
            
            if (rowsAffected > 0) {
                conn.commit();
                success = true;
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(mainFrame, 
                    "No user found with the ID.", 
                    "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
            
            conn.setAutoCommit(true);
            
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            JOptionPane.showMessageDialog(mainFrame, 
                "Database error: " + e.getMessage(), 
                "Account Deletion Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            success = false;
        }
        
        return success;
    }
    
    //get users from database
    private static ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
    
        try {
            Connection conn = Database.getConnection();
            if (conn == null) {
                System.out.println("Database connection is null!");
                return userList;
            }
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY id");
            
            System.out.println("Executing query: SELECT * FROM users ORDER BY id");
            
            int count = 0;
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.password = rs.getString("password");
                user.fullName = rs.getString("full_name");
                user.contact = rs.getString("contact");
                user.address = rs.getString("address");
                user.birthDate = rs.getString("birth_date");
                user.employment = rs.getString("employment");
                user.sourceOfFunds = rs.getString("source_of_funds");
                user.monthlyFunds = rs.getString("monthly_funds");
                user.accountNumber = rs.getString("account_number");
                user.balance = rs.getDouble("balance");

                String[] nameParts = user.fullName.split(" ");
                user.firstName = nameParts.length > 0 ? nameParts[0] : "";
                user.middleName = nameParts.length > 2 ? nameParts[1] : "";
                user.lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

                userList.add(user);
            }
            
            System.out.println("Found " + count + " users in the database");
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    
        return userList;
    }
    
    //user table
    private static void userTable(ArrayList<User> userList) {
        String[] columnNames = {"Name", "Account Number", "Balance"};

        if (userTable == null) {
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            userTable = new JTable(tableModel);
            userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userTable.setRowHeight(25);
            userTable.getTableHeader().setFont(Constants.labelFont);
        }

        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        model.setRowCount(0);

        if (userList.isEmpty()) {
        Object[] emptyRow = {"-", "No current users", "-"};
        model.addRow(emptyRow);
        } else {
            for (User user : userList) {
                Object[] row = {
                    user.fullName,
                    user.accountNumber,
                    String.format("₱%.2f", user.balance)
                };
                model.addRow(row);
            }
        }
    }

    // view profile
    private static void showUserProfile(User user) {
        JDialog profileDialog = new JDialog(mainFrame, "User Profile", true);
        profileDialog.setSize(500, 575);
        profileDialog.setLocationRelativeTo(mainFrame);
        profileDialog.setLayout(null);

        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(Constants.green);
        topPanel.setBounds(0, 0, 500, 60);

        JLabel titleLabel = new JLabel("User Profile", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 15, 500, 30);
        topPanel.add(titleLabel);
        profileDialog.add(topPanel);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 60, 500, 440); 
        mainPanel.setBackground(Constants.green2);

        int y = 20;
        int labelWidth = 130;
        int fieldX = 150;
        int fieldWidth = 280;
        int height = 25;
        int gap = 35;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, y, labelWidth, height);
        usernameLabel.setFont(Constants.labelFont);
        mainPanel.add(usernameLabel);

        JTextField usernameField = new JTextField(user.username);
        usernameField.setBounds(fieldX, y, fieldWidth, height);
        usernameField.setEditable(false);
        mainPanel.add(usernameField);

        y += gap;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, y, labelWidth, height);
        passwordLabel.setFont(Constants.labelFont);
        mainPanel.add(passwordLabel);

        JTextField passwordField = new JTextField(user.password);
        passwordField.setBounds(fieldX, y, fieldWidth, height);
        passwordField.setEditable(false);
        mainPanel.add(passwordField);

        y += gap;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(30, y, labelWidth, height);
        nameLabel.setFont(Constants.labelFont);
        mainPanel.add(nameLabel);

        JTextField nameField = new JTextField(user.fullName);
        nameField.setBounds(fieldX, y, fieldWidth, height);
        nameField.setEditable(false);
        mainPanel.add(nameField);

        y += gap;
        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(30, y, labelWidth, height);
        contactLabel.setFont(Constants.labelFont);
        mainPanel.add(contactLabel);

        JTextField contactField = new JTextField(user.contact != null ? user.contact : "");
        contactField.setBounds(fieldX, y, fieldWidth, height);
        contactField.setEditable(false);
        mainPanel.add(contactField);

        y += gap;
        JLabel birthDateLabel = new JLabel("Birthdate:");
        birthDateLabel.setBounds(30, y, labelWidth, height);
        birthDateLabel.setFont(Constants.labelFont);
        mainPanel.add(birthDateLabel);

        JTextField birthDateField = new JTextField(user.birthDate != null ? user.birthDate : "");
        birthDateField.setBounds(fieldX, y, fieldWidth, height);
        birthDateField.setEditable(false);
        mainPanel.add(birthDateField);
        
        y += gap;
        JLabel addressLabel = new JLabel("Home Address:");
        addressLabel.setBounds(30, y, labelWidth, height);
        addressLabel.setFont(Constants.labelFont);
        mainPanel.add(addressLabel);

        JTextArea addressField = new JTextArea(user.address != null ? user.address : "");
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);
        addressField.setEditable(false);

        JScrollPane addressScrollPane = new JScrollPane(addressField);
        addressScrollPane.setBounds(fieldX, y, fieldWidth, height * 2);
        mainPanel.add(addressScrollPane);

        y += gap * 2;
        JLabel employmentLabel = new JLabel("Employment Status:");
        employmentLabel.setBounds(30, y, labelWidth, height);
        employmentLabel.setFont(Constants.labelFont);
        mainPanel.add(employmentLabel);

        JTextField employmentField = new JTextField(user.employment != null ? user.employment : "");
        employmentField.setBounds(fieldX, y, fieldWidth, height);
        employmentField.setEditable(false);
        mainPanel.add(employmentField);

        y += gap;
        JLabel sourceLabel = new JLabel("Source of Funds:");
        sourceLabel.setBounds(30, y, labelWidth, height);
        sourceLabel.setFont(Constants.labelFont);
        mainPanel.add(sourceLabel);

        JTextField sourceField = new JTextField(user.sourceOfFunds != null ? user.sourceOfFunds : "");
        sourceField.setBounds(fieldX, y, fieldWidth, height);
        sourceField.setEditable(false);
        mainPanel.add(sourceField);
        
        y += gap;
        JLabel monthlyFundsLabel = new JLabel("Monthly Funds:");
        monthlyFundsLabel.setBounds(30, y, labelWidth, height);
        monthlyFundsLabel.setFont(Constants.labelFont);
        mainPanel.add(monthlyFundsLabel);

        JTextField monthlyFundsField = new JTextField(user.monthlyFunds != null ? user.monthlyFunds : "");
        monthlyFundsField.setBounds(fieldX, y, fieldWidth, height);
        monthlyFundsField.setEditable(false);
        mainPanel.add(monthlyFundsField);

        y += gap;
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(30, y, labelWidth, height);
        accountLabel.setFont(Constants.labelFont);
        mainPanel.add(accountLabel);

        JTextField accountField = new JTextField(user.accountNumber);
        accountField.setBounds(fieldX, y, fieldWidth, height);
        accountField.setEditable(false);
        mainPanel.add(accountField);

        y += gap;
        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(30, y, labelWidth, height);
        balanceLabel.setFont(Constants.labelFont);
        mainPanel.add(balanceLabel);

        JTextField balanceField = new JTextField(String.format("₱%.2f", user.balance));
        balanceField.setBounds(fieldX, y, fieldWidth, height);
        balanceField.setEditable(false);
        balanceField.setFont(new Font("Arial", Font.BOLD, 14));
        balanceField.setForeground(Constants.green);
        mainPanel.add(balanceField);

        profileDialog.add(mainPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 500, 500, 40);
        buttonPanel.setBackground(Constants.gray);
        JButton closeButton = new JButton("Close");
        closeButton.setFont(Constants.button1Font);
        closeButton.setBackground(Constants.green);
        closeButton.addActionListener(e -> {
        refreshDashboard(); 
        profileDialog.dispose();
    });
        buttonPanel.add(closeButton);
        
        profileDialog.add(buttonPanel);

        profileDialog.setVisible(true);
    }
    
    //edit user
    private static void showEditUser(User user) {
        JDialog editDialog = new JDialog(mainFrame, "Edit User", true);
        editDialog.setSize(500, 585); 
        editDialog.setLocationRelativeTo(mainFrame);
        editDialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Constants.green);
        topPanel.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("Edit User Details", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        editDialog.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Constants.green2);

        int y = 20;
        int labelWidth = 130;
        int fieldX = 150;
        int fieldWidth = 280;
        int height = 25;
        int gap = 35;

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(30, y, labelWidth, height);
        accountNumberLabel.setFont(Constants.labelFont);
        mainPanel.add(accountNumberLabel);

        JTextField accountNumberField = new JTextField(user.accountNumber);
        accountNumberField.setBounds(fieldX, y, fieldWidth, height);
        accountNumberField.setEditable(false);
        accountNumberField.setFont(Constants.textFont);
        mainPanel.add(accountNumberField);

        y += gap;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, y, labelWidth, height);
        usernameLabel.setFont(Constants.labelFont);
        mainPanel.add(usernameLabel);

        JTextField usernameField = new JTextField(user.username);
        usernameField.setBounds(fieldX, y, fieldWidth, height);
        usernameField.setFont(Constants.text2Font);
        mainPanel.add(usernameField);

        y += gap;
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, y, labelWidth, height);
        firstNameLabel.setFont(Constants.labelFont);
        mainPanel.add(firstNameLabel);

        JTextField firstNameField = new JTextField(user.firstName);
        firstNameField.setBounds(fieldX, y, fieldWidth, height);
        firstNameField.setFont(Constants.text2Font);
        mainPanel.add(firstNameField);

        y += gap;
        JLabel middleNameLabel = new JLabel("Middle Name:");
        middleNameLabel.setBounds(30, y, labelWidth, height);
        middleNameLabel.setFont(Constants.labelFont);
        mainPanel.add(middleNameLabel);

        JTextField middleNameField = new JTextField(user.middleName);
        middleNameField.setBounds(fieldX, y, fieldWidth, height);
        middleNameField.setFont(Constants.text2Font);
        mainPanel.add(middleNameField);

        y += gap;
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(30, y, labelWidth, height);
        lastNameLabel.setFont(Constants.labelFont);
        mainPanel.add(lastNameLabel);

        JTextField lastNameField = new JTextField(user.lastName);
        lastNameField.setBounds(fieldX, y, fieldWidth, height);
        lastNameField.setFont(Constants.text2Font);
        mainPanel.add(lastNameField);

        y += gap;
        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(30, y, labelWidth, height);
        contactLabel.setFont(Constants.labelFont);
        mainPanel.add(contactLabel);

        JTextField contactField = new JTextField(user.contact);
        contactField.setBounds(fieldX, y, fieldWidth, height);
        contactField.setFont(Constants.text2Font);
        mainPanel.add(contactField);

        y += gap;
        JLabel birthDateLabel = new JLabel("Birthdate:");
        birthDateLabel.setBounds(30, y, labelWidth, height);
        birthDateLabel.setFont(Constants.labelFont);
        mainPanel.add(birthDateLabel);

        JTextField birthDateField = new JTextField(user.birthDate);
        birthDateField.setBounds(fieldX, y, fieldWidth, height);
        birthDateField.setEditable(false);
        birthDateField.setFont(Constants.text2Font);
        mainPanel.add(birthDateField);

        y += gap;
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, y, labelWidth, height);
        addressLabel.setFont(Constants.labelFont);
        mainPanel.add(addressLabel);

        JTextArea addressField = new JTextArea(user.address);
        addressField.setFont(Constants.text2Font);
        addressField.setLineWrap(true);
        addressField.setWrapStyleWord(true);

        JScrollPane addressScrollPane = new JScrollPane(addressField);
        addressScrollPane.setBounds(fieldX, y, fieldWidth, height * 2);
        mainPanel.add(addressScrollPane);

        y += gap * 2;
        JLabel employmentLabel = new JLabel("Employment Status:");
        employmentLabel.setBounds(30, y, labelWidth, height);
        employmentLabel.setFont(Constants.labelFont);
        mainPanel.add(employmentLabel);

        String[] employmentOptions = {"Student", "Employed", "Self-employed", "Retired"};
        JComboBox<String> employmentField = new JComboBox<>(employmentOptions);
        employmentField.setSelectedItem(user.employment);
        employmentField.setBounds(fieldX, y, fieldWidth, height);
        mainPanel.add(employmentField);

        y += gap;
        JLabel fundsSourceLabel = new JLabel("Source of Funds:");
        fundsSourceLabel.setBounds(30, y, labelWidth, height);
        fundsSourceLabel.setFont(Constants.labelFont);
        mainPanel.add(fundsSourceLabel);

        String[] fundsOptions = {"Savings", "Allowance", "Salary", "Pension"};
        JComboBox<String> fundsSourceField = new JComboBox<>(fundsOptions);
        fundsSourceField.setSelectedItem(user.sourceOfFunds);
        fundsSourceField.setBounds(fieldX, y, fieldWidth, height);
        mainPanel.add(fundsSourceField);

        y += gap;
        JLabel monthlyFundsLabel = new JLabel("Monthly Funds:");
        monthlyFundsLabel.setBounds(30, y, labelWidth, height);
        monthlyFundsLabel.setFont(Constants.labelFont);
        mainPanel.add(monthlyFundsLabel);

        String[] incomeOptions = {"Under ₱5k", "₱5k-₱10k", "₱10k-₱15k", "Above ₱15k"};
        JComboBox<String> monthlyFundsField = new JComboBox<>(incomeOptions);
        monthlyFundsField.setSelectedItem(user.monthlyFunds);
        monthlyFundsField.setBounds(fieldX, y, fieldWidth, height);
        mainPanel.add(monthlyFundsField);

        editDialog.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Constants.gray);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(Constants.button1Font);
        saveButton.setBackground(Constants.green);
        saveButton.addActionListener(e -> {
       
        user.username = usernameField.getText().trim();
        user.firstName = firstNameField.getText().trim();
        user.middleName = middleNameField.getText().trim();
        user.lastName = lastNameField.getText().trim();
        user.fullName = user.firstName + " " + 
                        (user.middleName.isEmpty() ? "" : user.middleName + " ") + 
                        user.lastName;
        user.contact = contactField.getText().trim();
        user.address = addressField.getText().trim();
        user.employment = (String) employmentField.getSelectedItem();
        user.sourceOfFunds = (String) fundsSourceField.getSelectedItem();
        user.monthlyFunds = (String) monthlyFundsField.getSelectedItem();

        if (Database.checkUsername(user.username, user.id)) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Username already exists. Please choose a different username.", 
                    "Signup Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        if (updateUser(user)) {
            JOptionPane.showMessageDialog(editDialog, 
                "User details updated successfully", 
                "Update Successful", JOptionPane.INFORMATION_MESSAGE);
            editDialog.dispose();
            refreshDashboard();
        }
    });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        cancelButton.addActionListener(e -> {
        refreshDashboard(); 
        editDialog.dispose();
    });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        editDialog.setVisible(true);

        refreshDashboard();
    }
    
    //update database
    private static boolean updateUser(User user) {
        try {
        Connection conn = Database.getConnection();
        
        user.fullName = user.firstName + " " + 
                        (user.middleName.isEmpty() ? "" : user.middleName + " ") +
                        user.lastName;

        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE users SET username = ?, full_name = ?, first_name = ?,middle_name = ?,last_name = ?,contact = ?, address = ?, " +
            "employment = ?, source_of_funds = ?, monthly_funds = ? " +
            "WHERE id = ?");
        
            stmt.setString(1, user.username);
            stmt.setString(2, user.fullName);
            stmt.setString(3, user.firstName);
            stmt.setString(4, user.middleName);
            stmt.setString(5, user.lastName);
            stmt.setString(6, user.contact);
            stmt.setString(7, user.address);
            stmt.setString(8, user.employment);
            stmt.setString(9, user.sourceOfFunds);
            stmt.setString(10, user.monthlyFunds);
            stmt.setInt(11, user.id);

            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, 
                "Database error: " + e.getMessage(), 
                "Update Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    //reset password
    private static void showResetPassword(User user) {
        JDialog resetDialog = new JDialog(mainFrame, "Reset Password", true);
        resetDialog.setSize(400, 250);
        resetDialog.setLocationRelativeTo(mainFrame);
        resetDialog.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Constants.green);
        topPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("Reset User Password", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        resetDialog.add(topPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Constants.green2);
        
        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(30, 30, 120, 25);
        passwordLabel.setFont(Constants.labelFont);
        mainPanel.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 30, 200, 25);
        mainPanel.add(passwordField);
        
        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(30, 65, 120, 25);
        confirmLabel.setFont(Constants.labelFont);
        mainPanel.add(confirmLabel);
        
        JPasswordField confirmField = new JPasswordField();
        confirmField.setBounds(150, 65, 200, 25);
        mainPanel.add(confirmField);
        
        resetDialog.add(mainPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Constants.gray);
        
        JButton resetButton = new JButton("Reset Password");
        resetButton.setFont(Constants.button1Font);
        resetButton.setBackground(Constants.green);
        resetButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());
            
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(resetDialog, 
                    "Password cannot be empty", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(resetDialog, 
                    "Passwords do not match", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (password.length() < 8) {
            JOptionPane.showMessageDialog(resetDialog, 
                "Password must be at least 8 characters long", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
                JOptionPane.showMessageDialog(resetDialog, 
                    "Password must contain both letters and numbers", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (updatePassword(user.id, password)) {
                JOptionPane.showMessageDialog(resetDialog, 
                    "Password reset successfully", 
                    "Password Reset", JOptionPane.INFORMATION_MESSAGE);
                resetDialog.dispose();
                
                refreshDashboard();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        cancelButton.addActionListener(e -> {
        refreshDashboard(); 
        resetDialog.dispose();
    });
        
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);
        
        resetDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        resetDialog.setVisible(true);
    }
    
    //update password in database
    private static boolean updatePassword(int userId, String newPassword) {
        try {
            Connection conn = Database.getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE users SET password = ? WHERE id = ?");
            
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            stmt.close();
            
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame, 
                "Database error: " + e.getMessage(), 
                "Password Reset Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    //deposit
    private static void showDeposit(User user) {
        JDialog depositDialog = new JDialog(mainFrame, "Deposit Amount", true);
        depositDialog.setSize(400, 220);
        depositDialog.setLocationRelativeTo(mainFrame);
        depositDialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Constants.green);
        topPanel.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("Deposit Amount", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        depositDialog.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Constants.green2);

        JLabel amountLabel = new JLabel("User Amount:");
        amountLabel.setBounds(30, 30, 120, 25);
        amountLabel.setFont(Constants.labelFont);
        mainPanel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 30, 200, 25);
        mainPanel.add(amountField);

        depositDialog.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Constants.gray);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(Constants.button1Font);
        confirmButton.setBackground(Constants.green);
        confirmButton.addActionListener(e -> {
            String amountStr = amountField.getText().trim();
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(depositDialog, 
                        "Amount cannot be 0", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount < 100) {
                    JOptionPane.showMessageDialog(depositDialog, 
                        "The minimum deposit is ₱100", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (processDeposit(user.id, amount)) {
                    JOptionPane.showMessageDialog(depositDialog, 
                        "Current Balance: ₱" + String.format("%.2f", user.balance+amount), 
                        "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                    depositDialog.dispose();
                    refreshDashboard();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(depositDialog, 
                    "Invalid amount. Please enter a valid amount.", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        cancelButton.addActionListener(e -> {
        refreshDashboard(); 
        depositDialog.dispose();
    });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        depositDialog.add(buttonPanel, BorderLayout.SOUTH);

        depositDialog.setVisible(true);
    }
    
    //process deposit & update balance in databse
    private static boolean processDeposit(int userId, double amount) {
         try {
            Connection conn = Database.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement updateBalanceStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE id = ?");
            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setInt(2, userId);

            int rowsAffected = updateBalanceStmt.executeUpdate();
            updateBalanceStmt.close();

            PreparedStatement recordTransactionStmt = conn.prepareStatement(
                "INSERT INTO transactions (user_id, transaction_id, transaction_type, amount) VALUES (?, ?, ?, ?)");
            String transactionId = generateTransactionId(12);
            recordTransactionStmt.setInt(1, userId);
            recordTransactionStmt.setString(2, transactionId);
            recordTransactionStmt.setString(3, "deposit");
            recordTransactionStmt.setDouble(4, amount);

            int transactionRowsAffected = recordTransactionStmt.executeUpdate();
            recordTransactionStmt.close();

            conn.commit();
            conn.setAutoCommit(true);
            JOptionPane.showMessageDialog(mainFrame,
                "Deposit successful. Transaction ID: " + transactionId,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            try {
                Connection conn = Database.getConnection();
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            JOptionPane.showMessageDialog(mainFrame,
                "Database error: " + e.getMessage(),
                "Deposit Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    //withdraw
    private static void showWithdraw(User user) {
        JDialog withdrawDialog = new JDialog(mainFrame, "Withdraw Amount", true);
        withdrawDialog.setSize(400, 220);
        withdrawDialog.setLocationRelativeTo(mainFrame);
        withdrawDialog.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Constants.green);
        topPanel.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("Withdraw Amount", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        withdrawDialog.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Constants.green2);

        JLabel amountLabel = new JLabel("User Amount:");
        amountLabel.setBounds(30, 30, 120, 25);
        amountLabel.setFont(Constants.labelFont);
        mainPanel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 30, 200, 25);
        mainPanel.add(amountField);

        withdrawDialog.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Constants.gray);
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(Constants.button1Font);
        confirmButton.setBackground(Constants.green);
        confirmButton.addActionListener(e -> {
            String amountStr = amountField.getText().trim();
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "Amount cannot be 0", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (amount > user.balance) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "Insufficient funds. Current balance: ₱" + String.format("%.2f", user.balance), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount < 100) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "The minimum withdrawal is ₱100", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (processWithdraw(user.id, amount)) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "Remaining Balance: ₱" + String.format("%.2f", user.balance-amount), 
                        "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
                    withdrawDialog.dispose();
                    refreshDashboard();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(withdrawDialog, 
                    "Invalid amount. Please enter a valid amount.", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        cancelButton.addActionListener(e -> {
        refreshDashboard(); 
        withdrawDialog.dispose();
    });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        withdrawDialog.add(buttonPanel, BorderLayout.SOUTH);

        withdrawDialog.setVisible(true);
    }
    
    //process witdhraw & update balance in databse
    private static boolean processWithdraw(int userId, double amount) {
        try {
            Connection conn = Database.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement updateBalanceStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance - ? WHERE id = ?");
            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setInt(2, userId);

            int rowsAffected = updateBalanceStmt.executeUpdate();
            updateBalanceStmt.close();

            PreparedStatement recordTransactionStmt = conn.prepareStatement(
                "INSERT INTO transactions (user_id, transaction_id, transaction_type, amount) VALUES (?, ?, ?, ?)");
            String transactionId = generateTransactionId(12);
            recordTransactionStmt.setInt(1, userId);
            recordTransactionStmt.setString(2, transactionId);
            recordTransactionStmt.setString(3, "withdrawal");
            recordTransactionStmt.setDouble(4, amount);

            int transactionRowsAffected = recordTransactionStmt.executeUpdate();
            recordTransactionStmt.close();

            conn.commit();
            conn.setAutoCommit(true);
            JOptionPane.showMessageDialog(mainFrame,
                "Withdrawal successful. Transaction ID: " + transactionId,
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (SQLException e) {
            try {
                Connection conn = Database.getConnection();
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }

            JOptionPane.showMessageDialog(mainFrame,
                "Database error: " + e.getMessage(),
                "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    private static void showTransactionHistory(User user) {
        JDialog historyDialog = new JDialog(mainFrame, "Transaction History", true);
        historyDialog.setSize(500, 400);
        historyDialog.setLocationRelativeTo(mainFrame);
        historyDialog.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Constants.green);
        topPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        historyDialog.add(topPanel, BorderLayout.NORTH);
        
        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC");
            stmt.setInt(1, user.id);
            ResultSet rs = stmt.executeQuery();
            
            String[] columnNames = {"Transaction ID", "Type", "Amount", "Date"};
            ArrayList<Object[]> data = new ArrayList<>();
            
            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("transaction_id");
                row[1] = rs.getString("transaction_type");
                row[2] = String.format("₱%.2f", rs.getDouble("amount"));
                row[3] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTimestamp("transaction_date"));
                data.add(row);
            }
            
            Object[][] dataArray = new Object[data.size()][];
            dataArray = data.toArray(dataArray);
            
            JTable table = new JTable(dataArray, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            
            historyDialog.add(scrollPane, BorderLayout.CENTER);
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(historyDialog, 
                "Error retrieving transaction history: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Constants.gray);
        JButton closeButton = new JButton("Close");
        closeButton.setFont(Constants.button1Font);
        closeButton.setBackground(Constants.green);
        closeButton.addActionListener(e -> {
        refreshDashboard(); 
        historyDialog.dispose();
    });
        buttonPanel.add(closeButton);
        historyDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        historyDialog.setVisible(true);
    }
    
    private static String generateTransactionId(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}