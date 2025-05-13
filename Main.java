package bankingproject;

import javax.swing.*;

//main class
public class Main {
    public static JFrame mainframe;
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            mainframe = new JFrame("Delgado Banking Inc.");
            mainframe.setSize(650, 450);
            mainframe.setLayout(null);
            mainframe.setLocationRelativeTo(null);
            mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
             try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
             
            LandingPage.show(mainframe);
            
            mainframe.setVisible(true);
            
            windowClosingEvent();
        });
    }
    
    
    private static void windowClosingEvent() {
        mainframe.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Database.closeConnection();
                System.exit(0);
            }
        });
    }
}