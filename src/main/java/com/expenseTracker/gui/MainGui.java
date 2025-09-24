package com.expenseTracker.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.expenseTracker.dao.ExpenseDAO;
import com.expenseTracker.model.*;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


import java.awt.*;

public class MainGui extends JFrame{
    private JButton expenseButton;
    private JButton categoryButton;

    
    public MainGui(){   
        initializeComponents();
        setupLayout();
        setupEventListeners();

    }

    private void initializeComponents(){
        setTitle("Main UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,1000);
        setLocationRelativeTo(null);

        expenseButton = new JButton("Expense");
        categoryButton = new JButton("Category");
        expenseButton.setPreferredSize(new Dimension(500,500));
        categoryButton.setPreferredSize(new Dimension(500,500));

    }

    private void setupLayout(){
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(expenseButton);       
        buttonPanel.add(categoryButton);

        panel.add(buttonPanel,gbc);
        add(panel,BorderLayout.CENTER);
    }

    private void setupEventListeners(){
        expenseButton.addActionListener((e)->{
            new ExpenseGui().setVisible(true);
        });
        categoryButton.addActionListener((e)->{
            new CategoryGui().setVisible(true);
        });
    }

}

class CategoryGui extends JFrame {

    private JTextField titleField;
    private JButton addButton;
    private JButton removeButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTable categoryTable;
    private DefaultTableModel tableModel;
    private ExpenseDAO expenseDao;

    public CategoryGui(){
        initializeComponents();
        setupLayout();
        setupEventListeners();
        expenseDao = new ExpenseDAO();
        loadCategory();

    }

    public void initializeComponents(){

        titleField = new JTextField(20);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        String[] columnNames = {"Id","Title"};

        tableModel = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        categoryTable = new JTable(tableModel);

    }

    public void setupLayout(){
        setTitle("Category UI");

        setSize(1000,1000);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Name"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(titleField,gbc);     

        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);

        northPanel.add(inputPanel,BorderLayout.NORTH);
        northPanel.add(buttonsPanel,BorderLayout.CENTER);
        
        add(northPanel,BorderLayout.NORTH);
        add(new JScrollPane(categoryTable),BorderLayout.CENTER);
    }

    public void setupEventListeners(){

    }

    private void updateTable(List<Category> category){
        tableModel.setRowCount(0);
        for(Category cate: category){
            Object row[] = {
                cate.getId(),
                cate.getName()
            };
            tableModel.addRow(row);
        }
    }

    private void loadCategory(){
        try{
            List<Category> categories = expenseDao.getAllCategories();
            updateTable(categories);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage(),"DataBase Error",JOptionPane.ERROR_MESSAGE);
        }
    }    

}


class ExpenseGui extends JFrame {

    private JTextField titleField;
    private JButton addButton;
    private JButton removeButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JComboBox<String> categoryComboBox;

    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private ExpenseDAO expenseDao;

    public ExpenseGui(){
        expenseDao = new ExpenseDAO();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadExpense();

    }

    public void initializeComponents(){

        titleField = new JTextField(20);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        String[] categories = {"House","Petrol"};
        // String[] categories = getCategories();

        categoryComboBox = new JComboBox<>(categories);

        String[] columnNames = {"Id","Amount","Description","Category","Created At"};

        tableModel = new DefaultTableModel(columnNames,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        expenseTable = new JTable(tableModel);

    }

    public void setupLayout(){
        setTitle("Expenses");
        setSize(1000,1200);

        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        inputPanel.add(new JLabel("Amount"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(titleField,gbc);     

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(categoryComboBox,gbc);


        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(updateButton);

        northPanel.add(inputPanel,BorderLayout.NORTH);
        northPanel.add(buttonsPanel,BorderLayout.CENTER);

        
        add(northPanel,BorderLayout.NORTH);
        add(new JScrollPane(expenseTable),BorderLayout.CENTER);


    }

    public void setupEventListeners(){

    }

    private void updateTable(List<Expense> expense){
        tableModel.setRowCount(0);
        for(Expense exp: expense){
            Object row[] = {
                exp.getExpId(),
                exp.getAmount(),
                exp.getDescription(),
                exp.getCategory(),
                exp.getCreatedAt()
            };
            tableModel.addRow(row);
        }
    }

    private void loadExpense(){
        try{
            List<Expense> expense = expenseDao.getAllExpenses();
            updateTable(expense);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage(),"DataBase Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    
}
