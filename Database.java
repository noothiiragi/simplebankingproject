package bankingproject;

import java.sql.*;
import javax.swing.*;

public class Database {
    private static Connection connection = null;
    
    //establish connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingdb", "root" , "");
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                "MySQL JDBC driver not found. Please include it in your classpath.",
                "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Database connection error: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return connection;
    }
    
    //close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
   
    //check username for account creation
    public static boolean checkUsername(String username) {
        try {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT username FROM users WHERE username = ?");
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            
            rs.close();
            stmt.close();
            
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //check username for updating
    public static boolean checkUsername(String username, int currentUserId) {
        try {
            Connection conn = getConnection();

            // Modified query to exclude the current user's ID
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT username FROM users WHERE username = ? AND id != ?");
            stmt.setString(1, username);
            stmt.setInt(2, currentUserId);

            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();

            rs.close();
            stmt.close();

            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //check if there are exixting account number
    public static boolean checkAccountNumber(String accountNumber) {
        try {
            Connection conn = getConnection();
            
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT account_number FROM users WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            
            rs.close();
            stmt.close();
            
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}