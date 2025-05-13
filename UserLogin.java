package bankingproject;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UserLogin {
    
     public static void show(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Constants.white);
        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(0, 0, 650, 100);
        welcomePanel.setBackground(Constants.green);
        
        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(Constants.headerFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 30, 590, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(titleLabel);
        
        JLabel credLabel = new JLabel("Please enter your credentials");
        credLabel.setFont(new Font("Roboto", Font.ITALIC, 12));;
        credLabel.setForeground(Color.WHITE);
        credLabel.setBounds(30, 70, 590, 20);
        credLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(credLabel);
        
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
            welcomePanel.add(logo1Label);
            welcomePanel.setComponentZOrder(logo1Label, 0);
            
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
            welcomePanel.add(logo2Label);
            welcomePanel.setComponentZOrder(logo2Label, 0);
                
        } catch (Exception e) {
            System.out.println("Logo not found: " + e.getMessage());
            e.printStackTrace();
    }
        
        frame.add(welcomePanel);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Constants.labelFont);
        usernameLabel.setBounds(190, 130, 100, 25);
        frame.add(usernameLabel);
        
        JTextField usernameField = new JTextField();
        usernameField.setBounds(190, 155, 280, 30);
        frame.add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Constants.labelFont);
        passwordLabel.setBounds(190, 195, 100, 25);
        frame.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(190, 220, 280, 30);
        frame.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(190, 270, 130, 40);
        loginButton.setFont(Constants.button1Font);
        loginButton.setBackground(Constants.green);
        loginButton.setForeground(Color.BLACK);
        frame.add(loginButton);
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter both username and password", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User user = authenticateUser(username, password);
            
            if (user != null) {
                UserDashboard.show(frame, user);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid username or password", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(340, 270, 130, 40);
        cancelButton.setFont(Constants.button1Font);
        cancelButton.setBackground(Constants.green);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> LandingPage.show(frame));
        frame.add(cancelButton);
        
        JLabel copyrightLabel = new JLabel("© 2025 Delgado Banking Incorporated. All rights reserved.");
        copyrightLabel.setFont(Constants.text2Font);
        copyrightLabel.setBounds(30, 360, 590, 20);
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(copyrightLabel);
        
        frame.revalidate();
        frame.repaint();
    }
    
    //user authentication
    private static User authenticateUser(String username, String password) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.username = rs.getString("username");
                user.password = rs.getString("password");
                user.fullName = rs.getString("full_name");
                user.contact = rs.getString("contact");
                user.birthDate = rs.getString("birth_date");
                user.employment = rs.getString("employment");
                user.sourceOfFunds = rs.getString("source_of_funds");
                user.monthlyFunds = rs.getString("monthly_funds");
                user.accountNumber = rs.getString("account_number");
                user.balance = rs.getDouble("balance");
                
                rs.close();
                stmt.close();
                
                return user;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database error: " + e.getMessage(), 
                "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
}