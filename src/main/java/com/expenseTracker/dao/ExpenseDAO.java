package com.expenseTracker.dao;

import com.expenseTracker.util.*;
import com.expenseTracker.model.*;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import java.time.LocalDateTime;

import java.sql.*;

public class ExpenseDAO {

    private static final String SELECT_ALL_EXPENSE = "SELECT * FROM Expense ORDER BY created_at DESC";
    private static final String SELECT_CATEGORY_NAME = "SELECT name FROM Category WHERE id = ?";

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM Category";

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



}
