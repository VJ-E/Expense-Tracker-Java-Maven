package com.expenseTracker.util;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;


public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USERNAME = "root";
    private static final String PASSWORD  = Dotenv.load().get("DB_PASSWORD");

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
            System.out.println("Missing JDBC Driver");
        }
    }

    public static Connection getDBConnection() throws SQLException{
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }
}