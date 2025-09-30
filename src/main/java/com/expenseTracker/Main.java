package com.expenseTracker;

import com.expenseTracker.gui.MainGui;
import com.expenseTracker.util.DatabaseConnection;

import java.sql.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;

import com.expenseTracker.gui.MainGui;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try{
            Connection conn = dbConnection.getDBConnection();
        }
        catch(SQLException e){
            System.out.println("DB connection failed "+e.getMessage());
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
            System.out.println("Look and field error "+e.getMessage());
        }
        SwingUtilities.invokeLater(() -> {
            try{
                new MainGui().setVisible(true);
            }
            catch(Exception e){
                System.err.println("Failed to load window "+e.getMessage());
            }
            
        });
    }
}
