package com.expenseTracker.dao;

import com.expenseTracker.util.*;
import com.expenseTracker.model.*;

import java.util.List;

import javax.swing.JOptionPane;

import java.util.ArrayList;

import java.time.LocalDateTime;

import java.sql.*;

public class ExpenseDAO {

    private static final String SELECT_ALL_EXPENSE = "SELECT * FROM Expense ORDER BY created_at DESC";
    private static final String INSERT_EXPENSE = "INSERT INTO Expense(amount, description, created_at, cate_id) VALUES(?,?,?,?)";
    private static final String UPDATE_EXPENSE = "UPDATE Expense SET amount = ? , description = ?, cate_id = ? WHERE exp_id = ?";
    
    private static final String SELECT_CATEGORY_ID = "SELECT id FROM Category WHERE name = ?";
    private static final String SELECT_CATEGORY_NAME = "SELECT name FROM Category WHERE id = ?";

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Category";
    private static final String INSERT_CATEGORY = "INSERT INTO Category(name) VALUES(?)";
    private static final String UPDATE_CATEGORY = "UPDATE Category SET name = ? WHERE id = ?";

    public String getCategoryName(int cate_id) throws SQLException{
        String category = "";
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_CATEGORY_NAME);
        )
        {
            stmt.setInt(1,cate_id);
            try(ResultSet res = stmt.executeQuery()){   
                if(res.next()){
                    category = res.getString("name");
                }
                
            }
        }
        return category;

    }

    public Expense getExpenseRow(ResultSet res) throws SQLException{
        int id = res.getInt("exp_id");
        int Amount = res.getInt("amount");
        String describtion = res.getString("description");
        int cate_id = res.getInt("cate_id");
        LocalDateTime craetedAt = res.getTimestamp("created_at").toLocalDateTime();
        String category = getCategoryName(cate_id);
        
        return new Expense(id, Amount,describtion, craetedAt, cate_id,category);


    }

    public List<Expense> getAllExpenses() throws SQLException{
        List<Expense> expense = new ArrayList<>();

        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_EXPENSE);
            ResultSet res = stmt.executeQuery();
        )
        {
            while(res.next()){
                expense.add(getExpenseRow(res));
            }
        }
        return expense;
    }

    public int getCategoryId(String category) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt1 = conn.prepareStatement(SELECT_CATEGORY_ID);
        )
        {
            stmt1.setString(1, category);
            try(ResultSet res = stmt1.executeQuery()){
                if(res.next()){
                    return res.getInt("id");
                }
                else{
                    throw new SQLException("No Such Category");
                }
            }
        }
    }

    public int createExpense(int amt,String category,String description) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_EXPENSE);
            )
            {
                stmt.setInt(1,amt);
                int cateId = getCategoryId(category);
                stmt.setString(2,description);
                stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(4,cateId);

                return stmt.executeUpdate();
        }
    }

    public int updateExpense(int id, int amount, String description, String category) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_EXPENSE);
        )
        {
            stmt.setInt(1,amount);
            stmt.setString(2,description);

            int cateId = getCategoryId(category);
            stmt.setInt(3,cateId);
            stmt.setInt(4,id);

            return stmt.executeUpdate();
        }


    }

    //------------------------------------------------------------------------------



    public List<Category> getAllCategories() throws SQLException{
        List<Category> categories = new ArrayList<>();

        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet res = stmt.executeQuery();
        )
        {
            while(res.next()){
                categories.add(new Category(res.getInt("id"), res.getString("name")));
            }
        }
        return categories;
    }

    public int createCategory(String name) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_CATEGORY);
        )
        {
            stmt.setString(1,name);
            return stmt.executeUpdate();
        }
    }

    public int updateCategory(int id,String categoryName) throws SQLException{
        try(
            Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_CATEGORY);
        )
        {
            stmt.setString(1,categoryName);
            stmt.setInt(2,id);
            return stmt.executeUpdate();
        }
    }


}
