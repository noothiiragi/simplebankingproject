package bankingproject;

import javax.swing.*;
import java.awt.*;

public class AdminLogin {
    
     public static void show(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Constants.white);
        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(0, 0, 650, 100);
        welcomePanel.setBackground(Constants.green);
        
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
         
        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(30, 30, 590, 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(titleLabel);
        
        JLabel welcomeLabel = new JLabel("Please enter your credentials");
        welcomeLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(30, 65, 590, 30);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel);
        
        frame.add(welcomePanel);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Constants.labelFont);
        usernameLabel.setBounds(190, 110, 100, 25);
        frame.add(usernameLabel);
        
        JTextField usernameField = new JTextField();
        usernameField.setBounds(190, 135, 280, 30);
        frame.add(usernameField);
        
        JLabel adminIdLabel = new JLabel("Admin ID:");
        adminIdLabel.setFont(Constants.labelFont);
        adminIdLabel.setBounds(190, 175, 100, 25);
        frame.add(adminIdLabel);
        
        JTextField adminIdField = new JTextField();
        adminIdField.setBounds(190, 200, 280, 30);
        frame.add(adminIdField);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Constants.labelFont);
        passwordLabel.setBounds(190, 240, 100, 25);
        frame.add(passwordLabel);
        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(190, 265, 280, 30);
        frame.add(passwordField);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(190, 310, 130, 40);
        loginButton.setFont(Constants.button1Font);
        loginButton.setBackground(Constants.green);
        loginButton.setForeground(Color.BLACK);
        frame.add(loginButton);
        
        loginButton.addActionListener(e -> {
            String adminusername = usernameField.getText().trim();
            String adminId = adminIdField.getText().trim();
            String adminpassword = new String(passwordField.getPassword());
            
            if (adminusername.isEmpty() || adminId.isEmpty() || adminpassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter all credentials", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean validAdmin = false;
            for (String[] admin : Constants.ADMIN_USERS) {
                if (admin[0].equals(adminusername) && admin[1].equals(adminId) && admin[2].equals(adminpassword)) {
                    validAdmin = true;
                    break;
                }
            }
            
            if (validAdmin) {
                User adminUser = new User();
                adminUser.username = adminusername;
                adminUser.password = adminpassword;
                adminUser.fullName = "Admin " + adminusername;
                adminUser.isAdmin = true;
                
                AdminDashboard.show(frame, adminUser);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid admin credentials", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(340, 310, 130, 40);
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
}