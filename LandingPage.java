package bankingproject;
import javax.swing.*;
import java.awt.*;

public class LandingPage {
    
   
    public static void show(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Constants.white);
        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(null);
        welcomePanel.setBounds(0, 0, 650, 100);
        welcomePanel.setBackground(Constants.green);
        
        JLabel bankLabel = new JLabel("Delgado Banking Inc.");
        bankLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        bankLabel.setForeground(Color.WHITE);
        bankLabel.setBounds(30, 30, 590, 40);
        bankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(bankLabel);
        
        JLabel welcomeLabel = new JLabel("₱ Where Money Matters ₱");
        welcomeLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(30, 65, 590, 30);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel);
        
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
        
        int buttonWidth = 170;
        int buttonHeight = 40;
        int buttonX = 225; 
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(buttonX, 160, buttonWidth, buttonHeight);
        loginButton.setFont(Constants.button1Font);
        loginButton.setBackground(Constants.green2);
        loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(e -> UserLogin.show(frame));
        frame.add(loginButton);
        
        JButton adminButton = new JButton("Login as Admin");

        adminButton.setBounds(buttonX, 220, buttonWidth, buttonHeight);
        adminButton.setFont(Constants.button1Font); 
        adminButton.setBackground(Constants.green2);
        adminButton.setForeground(Color.BLACK);
        adminButton.addActionListener(e -> AdminLogin.show(frame));
        frame.add(adminButton);
        
        JLabel copyrightLabel = new JLabel("© 2025 Delgado Banking Incorporated. All rights reserved.");
        copyrightLabel.setFont(Constants.text2Font);
        copyrightLabel.setBounds(30, 360, 590, 20);
        copyrightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(copyrightLabel);
        
        frame.revalidate();
        frame.repaint();
    }
}