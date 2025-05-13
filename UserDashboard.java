package bankingproject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class UserDashboard {
    private static JFrame mainFrame;
    private static User currentUser;
    
    public static void show(JFrame frame, User user) {
        mainFrame = frame;
        currentUser = user;
        
        getUserData();
    
        mainFrame.getContentPane().removeAll();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Constants.green);
        leftPanel.setPreferredSize(new Dimension(200, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(Constants.green);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(new Font(Constants.titleFont.getName(), Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel(currentUser.firstName);
        userLabel.setFont(new Font(Constants.titleFont.getName(), Font.BOLD, 20));
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomePanel.add(userLabel);
        leftPanel.add(welcomePanel);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        String[] menuItems = {"Profile", "Deposit", "Withdraw", "Transactions", "Logout"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setMaximumSize(new Dimension(180, 40));
            menuButton.setPreferredSize(new Dimension(180, 40));
            menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuButton.setForeground(Color.BLACK);
            menuButton.setFont(new Font(Constants.button1Font.getName(), Font.BOLD, 14));
            menuButton.setBackground(Constants.green);
            menuButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Constants.green1));
            menuButton.setFocusPainted(false);

            menuButton.addActionListener(e -> {
                switch (item) {
                    case "Profile": 
                        showUserProfile(); 
                        break;
                    case "Deposit": 
                        showDeposit(); 
                        break;
                    case "Withdraw": 
                        showWithdraw(); 
                        break;
                    case "Transactions": 
                        showTransactionHistory(); 
                        break;
                    case "Logout":
                        int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                            "Are you sure you want to log out?", 
                            "Confirm Logout", 
                            JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            currentUser = null;
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

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Constants.green2);
        topBar.setPreferredSize(new Dimension(0, 60));

        mainPanel.add(topBar, BorderLayout.NORTH);

        JPanel dashboardContent = new JPanel();
        dashboardContent.setLayout(null);
        dashboardContent.setBackground(Constants.gray);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Constants.green2);
        JLabel dashboardTitle = new JLabel("Account Summary", SwingConstants.CENTER);
        dashboardTitle.setFont(Constants.headerFont);
        titlePanel.add(dashboardTitle, BorderLayout.CENTER);
        topBar.add(titlePanel, BorderLayout.CENTER);

        JPanel leftLogoPanel = new JPanel();
        leftLogoPanel.setLayout(new BorderLayout());
        leftLogoPanel.setBackground(Constants.green2);
        leftLogoPanel.setPreferredSize(new Dimension(100, 60));  

        JPanel rightLogoPanel = new JPanel();
        rightLogoPanel.setLayout(new BorderLayout());
        rightLogoPanel.setBackground(Constants.green2);
        rightLogoPanel.setPreferredSize(new Dimension(100, 60));  

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

        JPanel accountInfoPanel = new JPanel();
        accountInfoPanel.setBounds(30, 60, 370, 180);
        accountInfoPanel.setLayout(null);
        accountInfoPanel.setBackground(Constants.white);
        accountInfoPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel accountNameLabel = new JLabel("Account Name:");
        accountNameLabel.setBounds(20, 20, 120, 25);
        accountNameLabel.setFont(Constants.labelFont);
        accountInfoPanel.add(accountNameLabel);
        
        JLabel accountNameValue = new JLabel(currentUser.fullName);
        accountNameValue.setBounds(150, 20, 200, 25);
        accountNameValue.setFont(Constants.textFont);
        accountInfoPanel.add(accountNameValue);
        
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(20, 55, 120, 25);
        accountNumberLabel.setFont(Constants.labelFont);
        accountInfoPanel.add(accountNumberLabel);
        
        JLabel accountNumberValue = new JLabel(currentUser.accountNumber);
        accountNumberValue.setBounds(150, 55, 200, 25);
        accountNumberValue.setFont(Constants.textFont);
        accountInfoPanel.add(accountNumberValue);
        
         JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 90, 120, 25);
        usernameLabel.setFont(Constants.labelFont);
        accountInfoPanel.add(usernameLabel);
        
        JLabel usernameValue = new JLabel(String.format(currentUser.username));
        usernameValue.setBounds(150, 90, 200, 25);
        usernameValue.setFont(Constants.textFont);
        accountInfoPanel.add(usernameValue);
        
        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(20, 125, 120, 25);
        balanceLabel.setFont(Constants.labelFont);
        accountInfoPanel.add(balanceLabel);
        
        JLabel balanceValue = new JLabel(String.format("₱%.2f", currentUser.balance));
        balanceValue.setBounds(150, 125, 200, 25);
        balanceValue.setFont(Constants.textFont);
        accountInfoPanel.add(balanceValue);
        
        dashboardContent.add(accountInfoPanel);
        
        mainPanel.add(dashboardContent, BorderLayout.CENTER);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        
        JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        copyrightPanel.setBounds(0, 420, 650, 40);
        copyrightPanel.setBackground(Constants.white);
        
        JLabel copyrightLabel = new JLabel("© 2025 Delgado Banking Incorporated. All Rights Reserved");
        copyrightLabel.setFont(Constants.text2Font);
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightLabel.setVerticalAlignment(SwingConstants.CENTER);
        copyrightPanel.add(copyrightLabel);
        
        mainPanel.add(copyrightPanel, BorderLayout.SOUTH);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    private static void refreshDashboard() {
        getUserData();
        show(mainFrame, currentUser);
    }
    
    //retreivinf user data from database
    private static void getUserData() {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE id = ?");
            stmt.setInt(1, currentUser.id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                currentUser.balance = rs.getDouble("balance");
                currentUser.fullName = rs.getString("full_name");
                currentUser.firstName = rs.getString("first_name");
                currentUser.accountNumber = rs.getString("account_number");
                currentUser.contact = rs.getString("contact");
                currentUser.address = rs.getString("address");
                currentUser.birthDate = rs.getString("birth_date");
                currentUser.employment = rs.getString("employment");
                currentUser.sourceOfFunds = rs.getString("source_of_funds");
                currentUser.monthlyFunds = rs.getString("monthly_funds");
                currentUser.accountNumber = rs.getString("account_number");
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void showDeposit() {
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
        amountLabel.setBounds(30, 30, 80, 25);
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

                if (processDeposit(amount)) {
                    JOptionPane.showMessageDialog(depositDialog, 
                        "Current Balance: ₱" + String.format("%.2f", currentUser.balance), 
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
        cancelButton.addActionListener(e -> depositDialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        depositDialog.add(buttonPanel, BorderLayout.SOUTH);

        depositDialog.setVisible(true);
    }
    
    
    private static boolean processDeposit(double amount) {
        try {
            Connection conn = Database.getConnection();
            
            PreparedStatement updateStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance + ? WHERE id = ?");
            updateStmt.setDouble(1, amount);
            updateStmt.setInt(2, currentUser.id);
            int rowsUpdated = updateStmt.executeUpdate();
            updateStmt.close();
            
            if (rowsUpdated > 0) {
                PreparedStatement recordTransactionStmt = conn.prepareStatement(
                "INSERT INTO transactions (user_id, transaction_id, transaction_type, amount) VALUES (?, ?, ?, ?)");
                String transactionId = generateTransactionId(12);
                recordTransactionStmt.setInt(1, currentUser.id);
                recordTransactionStmt.setString(2, transactionId);
                recordTransactionStmt.setString(3, "deposit");
                recordTransactionStmt.setDouble(4, amount);
                
                int transactionRowsAffected = recordTransactionStmt.executeUpdate();
            recordTransactionStmt.close();
                
                currentUser.balance += amount;
                return true;
            } else {
                JOptionPane.showMessageDialog(mainFrame, 
                    "Failed to deposit amount", 
                    "Deposit Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(mainFrame, 
                "Database error: " + ex.getMessage(), 
                "Deposit Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    //withdraw dialog
     private static void showWithdraw() {
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
        amountLabel.setBounds(30, 30, 80, 25);
        amountLabel.setFont(Constants.labelFont);
        mainPanel.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 30, 200, 25);
        mainPanel.add(amountField);

        withdrawDialog.add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton confirmButton = new JButton("Confirm");
        buttonPanel.setBackground(Constants.gray);
        confirmButton.setBackground(Constants.green);
        confirmButton.setFont(Constants.button1Font);
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

                if (amount > currentUser.balance) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "Insufficient funds. Current balance: ₱" + String.format("%.2f", currentUser.balance), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount < 100) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "The minimum withdrawal is ₱100", 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (processWithdraw(amount)) {
                    JOptionPane.showMessageDialog(withdrawDialog, 
                        "Remaining Balance: ₱" + String.format("%.2f", currentUser.balance-amount), 
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
        cancelButton.addActionListener(e -> withdrawDialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        withdrawDialog.add(buttonPanel, BorderLayout.SOUTH);

        withdrawDialog.setVisible(true);
    }
    
    
    private static boolean processWithdraw(double amount) {
        try {
            Connection conn = Database.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement updateBalanceStmt = conn.prepareStatement(
                "UPDATE users SET balance = balance - ? WHERE id = ?");
            updateBalanceStmt.setDouble(1, amount);
            updateBalanceStmt.setInt(2, currentUser.id);

            int rowsAffected = updateBalanceStmt.executeUpdate();
            updateBalanceStmt.close();

            PreparedStatement recordTransactionStmt = conn.prepareStatement(
                "INSERT INTO transactions (user_id, transaction_id, transaction_type, amount) VALUES (?, ?, ?, ?)");
            String transactionId = generateTransactionId(12);
            recordTransactionStmt.setInt(1, currentUser.id);
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
    
    //transaction history
    private static void showTransactionHistory() {
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
            stmt.setInt(1, currentUser.id);
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
        closeButton.addActionListener(e -> historyDialog.dispose());
        buttonPanel.add(closeButton);
        historyDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        historyDialog.setVisible(true);
    }
    
    //user profile
   private static void showUserProfile() {
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

        JTextField usernameField = new JTextField(currentUser.username);
        usernameField.setBounds(fieldX, y, fieldWidth, height);
        usernameField.setEditable(false);
        mainPanel.add(usernameField);

        y += gap;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, y, labelWidth, height);
        passwordLabel.setFont(Constants.labelFont);
        mainPanel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField(currentUser.password);
        passwordField.setBounds(fieldX, y, fieldWidth, height);
        passwordField.setEditable(false);
        mainPanel.add(passwordField);

        y += gap;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(30, y, labelWidth, height);
        nameLabel.setFont(Constants.labelFont);
        mainPanel.add(nameLabel);

        JTextField nameField = new JTextField(currentUser.fullName);
        nameField.setBounds(fieldX, y, fieldWidth, height);
        nameField.setEditable(false);
        mainPanel.add(nameField);

        y += gap;
        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(30, y, labelWidth, height);
        contactLabel.setFont(Constants.labelFont);
        mainPanel.add(contactLabel);

        JTextField contactField = new JTextField(currentUser.contact != null ? currentUser.contact : "");
        contactField.setBounds(fieldX, y, fieldWidth, height);
        contactField.setEditable(false);
        mainPanel.add(contactField);

        y += gap;
        JLabel birthDateLabel = new JLabel("Birthdate:");
        birthDateLabel.setBounds(30, y, labelWidth, height);
        birthDateLabel.setFont(Constants.labelFont);
        mainPanel.add(birthDateLabel);

        JTextField birthDateField = new JTextField(currentUser.birthDate != null ? currentUser.birthDate : "");
        birthDateField.setBounds(fieldX, y, fieldWidth, height);
        birthDateField.setEditable(false);
        mainPanel.add(birthDateField);

        y += gap;
        JLabel addressLabel = new JLabel("Home Address:");
        addressLabel.setBounds(30, y, labelWidth, height);
        addressLabel.setFont(Constants.labelFont);
        mainPanel.add(addressLabel);

        JTextArea addressField = new JTextArea(currentUser.address != null ? currentUser.address : "");
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

        JTextField employmentField = new JTextField(currentUser.employment != null ? currentUser.employment : "");
        employmentField.setBounds(fieldX, y, fieldWidth, height);
        employmentField.setEditable(false);
        mainPanel.add(employmentField);

        y += gap;
        JLabel sourceLabel = new JLabel("Source of Funds:");
        sourceLabel.setBounds(30, y, labelWidth, height);
        sourceLabel.setFont(Constants.labelFont);
        mainPanel.add(sourceLabel);

        JTextField sourceField = new JTextField(currentUser.sourceOfFunds != null ? currentUser.sourceOfFunds : "");
        sourceField.setBounds(fieldX, y, fieldWidth, height);
        sourceField.setEditable(false);
        mainPanel.add(sourceField);
        
        y += gap;
        JLabel monthlyFundsLabel = new JLabel("Monthly Funds:");
        monthlyFundsLabel.setBounds(30, y, labelWidth, height);
        monthlyFundsLabel.setFont(Constants.labelFont);
        mainPanel.add(monthlyFundsLabel);

        JTextField monthlyFundsField = new JTextField(currentUser.monthlyFunds != null ? currentUser.monthlyFunds : "");
        monthlyFundsField.setBounds(fieldX, y, fieldWidth, height);
        monthlyFundsField.setEditable(false);
        mainPanel.add(monthlyFundsField);

        y += gap;
        JLabel accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(30, y, labelWidth, height);
        accountLabel.setFont(Constants.labelFont);
        mainPanel.add(accountLabel);

        JTextField accountField = new JTextField(currentUser.accountNumber);
        accountField.setBounds(fieldX, y, fieldWidth, height);
        accountField.setEditable(false);
        mainPanel.add(accountField);

        y += gap;
        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setBounds(30, y, labelWidth, height);
        balanceLabel.setFont(Constants.labelFont);
        mainPanel.add(balanceLabel);

        JTextField balanceField = new JTextField(String.format("₱%.2f", currentUser.balance));
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
        closeButton.addActionListener(e -> profileDialog.dispose());
        buttonPanel.add(closeButton);
        
        profileDialog.add(buttonPanel);

        profileDialog.setVisible(true);
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