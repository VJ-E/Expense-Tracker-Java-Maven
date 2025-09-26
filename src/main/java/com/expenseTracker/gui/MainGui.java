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
    private ExpenseDAO expenseDao;

    //MAIN
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton expenseButton;
    private JButton categoryButton;

    //Expense

    private JLabel expenseHeading;
    private JTextField amountField;
    private JTextArea descriptoinArea;
    private JButton expenseAddButton;
    private JButton expenseRefreshButton;
    private JButton expenseDeleteButton;
    private JButton expenseUpdateButton;
    private JComboBox<String> expenseCategoryComboBox;

    private JTable expenseTable;
    private DefaultTableModel expenseTableModel;

    //Category
    private JLabel categoryHeading;
    private JTextField categoryNameField;
    private JButton categoryAddButton;
    private JButton categoryRefreshButton;
    private JButton categoryDeleteButton;
    private JButton categoryUpdateButton;
    private JTable categoryTable;
    private DefaultTableModel categoryTableModel;

    

    public MainGui(){
        cardLayout = new CardLayout();
        expenseDao = new ExpenseDAO();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadCategory();
        loadExpense();
    }

    private void initializeComponents(){
        setTitle("Expense Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,1000);
        setLocationRelativeTo(null);

        // MAIN
        expenseButton = new JButton("Expense");
        categoryButton = new JButton("Category");

        //Expense

        expenseHeading = new JLabel("Expense");
        expenseHeading.setFont(new Font("Arial",Font.BOLD,36));
        amountField = new JTextField(20);
        descriptoinArea = new JTextArea(5,20);
        expenseAddButton = new JButton("Add");
        expenseRefreshButton = new JButton("Refresh");
        expenseDeleteButton = new JButton("Delete");
        expenseUpdateButton = new JButton("Update");
        List<String> categories = new ArrayList<>(); 
        try{
            List<Category> cate = expenseDao.getAllCategories();
            for(Category c: cate){
                categories.add(c.getName());
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Databse Failed : "+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }

        String[] categoriesArray = categories.toArray(new String[0]);


        expenseCategoryComboBox = new JComboBox<>(categoriesArray);

        String[] expenseColumnNames = {"Id","Amount","Description","Category","Created At"};

        expenseTableModel = new DefaultTableModel(expenseColumnNames,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        expenseTable = new JTable(expenseTableModel);

        expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expenseTable.getSelectionModel().addListSelectionListener( 
            e->{
            if(!e.getValueIsAdjusting()){
                loadSelectedExpense();
            }
        });

        //Category

        categoryHeading = new JLabel("Category");
        categoryHeading.setFont(new Font("Arial",Font.BOLD,36));
        categoryNameField = new JTextField(20);
        categoryAddButton = new JButton("Add");
        categoryRefreshButton = new JButton("Refresh");
        categoryDeleteButton = new JButton("Delete");
        categoryUpdateButton = new JButton("Update");
        String[] categoryColumnNames = {"Id","Title"};

        categoryTableModel = new DefaultTableModel(categoryColumnNames,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        categoryTable = new JTable(categoryTableModel);

        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryTable.getSelectionModel().addListSelectionListener( 
            e->{
            if(!e.getValueIsAdjusting()){
                loadSelectedCategory();
            }
        });

    }

    private void setupLayout(){
        setLayout(new BorderLayout());

        //MAIN
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.weightx = 0.5;  
        gbc1.weighty = 1.0; 
        gbc1.insets = new Insets(5, 5, 5, 5); 
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        
        GridBagConstraints gbc2 = (GridBagConstraints) gbc1.clone();
        gbc2.gridx = 1;
        expenseButton.setPreferredSize(new Dimension(0, 0)); 
        categoryButton.setPreferredSize(new Dimension(0, 0));
        buttonPanel.add(expenseButton, gbc1);
        buttonPanel.add(categoryButton, gbc2);
        buttonPanel.setPreferredSize(new Dimension(getWidth(), 80)); 

        mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);

        //Expense

        JPanel expensePanel = new JPanel(new BorderLayout());

        JPanel norhtJPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(expenseHeading,gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        
        inputPanel.add(new JLabel("Amount"),gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        inputPanel.add(amountField,gbc);     

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Description"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;

        inputPanel.add(new JScrollPane(descriptoinArea),gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
   
        inputPanel.add(new JLabel("Category"),gbc);

        gbc.gridx = 1;
   
        inputPanel.add(expenseCategoryComboBox,gbc);


        JPanel buttonsPanel = new JPanel(new FlowLayout());

        buttonsPanel.add(expenseAddButton);
        buttonsPanel.add(expenseDeleteButton);
        buttonsPanel.add(expenseUpdateButton);
        buttonsPanel.add(expenseRefreshButton);

        gbc.gridx = 0;
        gbc.gridy = 4;

        
        norhtJPanel.add(buttonsPanel,BorderLayout.CENTER);     
        norhtJPanel.add(inputPanel,BorderLayout.NORTH);

        expensePanel.add(norhtJPanel,BorderLayout.NORTH);
        expensePanel.add(new JScrollPane(expenseTable),BorderLayout.CENTER);

        mainPanel.add(expensePanel,"Expense");


        //Category
        
        JPanel categoryPanel = new JPanel(new BorderLayout());
        JPanel categoryInputPanel = new JPanel(new GridBagLayout());
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        categoryInputPanel.add(categoryHeading,gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;

        categoryInputPanel.add(new JLabel("Name"),gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        categoryInputPanel.add(categoryNameField,gbc);     

        JPanel categoryButtonsPanel = new JPanel(new FlowLayout());

        categoryButtonsPanel.add(categoryAddButton);
        categoryButtonsPanel.add(categoryDeleteButton);
        categoryButtonsPanel.add(categoryUpdateButton);
        categoryButtonsPanel.add(categoryRefreshButton);

        gbc.gridx = 1;
        gbc.gridy = 2;
        categoryInputPanel.add(categoryButtonsPanel,gbc);
        

        categoryPanel.add(categoryInputPanel,BorderLayout.NORTH);
        categoryPanel.add(new JScrollPane(categoryTable),BorderLayout.CENTER);

        mainPanel.add(categoryPanel,"Category");
        
        //--------
        add(buttonPanel, BorderLayout.NORTH);
        add(mainPanel,BorderLayout.CENTER);
    }

    private void setupEventListeners(){

        //MAIN
        categoryButton.addActionListener( e ->{
            cardLayout.show(mainPanel,"Category");
            loadCategory();
        }
        );
        expenseButton.addActionListener(e ->
            cardLayout.show(mainPanel,"Expense")
        );

        //Expense

        expenseAddButton.addActionListener((e)->{
            addExpense();
        });
        expenseUpdateButton.addActionListener((e)->{
            updateExpense();
        });
        expenseDeleteButton.addActionListener((e)->{
            deleteExpense();
        });
        expenseRefreshButton.addActionListener((e)->{
            refreshExpense();
        });

        //Category

        categoryAddButton.addActionListener((e)->{
            addCategory();
        });
        categoryUpdateButton.addActionListener((e)->{
            updateCategory();
        });
        categoryDeleteButton.addActionListener((e)->{
            deleteCategory();
        });
        categoryRefreshButton.addActionListener((e)->{
            refreshCategory();
        });

    }

    //Expense

    private void expensepdateTable(List<Expense> expense){
        expenseTableModel.setRowCount(0);
        for(Expense exp: expense){
            Object row[] = {
                exp.getExpId(),
                exp.getAmount(),
                exp.getDescription(),
                exp.getCategory(),
                exp.getCreatedAt()
            };
            expenseTableModel.addRow(row);
        }
    }

    private void loadExpense(){
        try{
            List<Expense> expense = expenseDao.getAllExpenses();
            expensepdateTable(expense);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage(),"DataBase Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addExpense(){
        String amount = amountField.getText();
        String category = (expenseCategoryComboBox.getSelectedItem().toString()).trim();
        String description = descriptoinArea.getText().trim();
        int amt = 0;

        if(amount.equals("")){
            JOptionPane.showMessageDialog(this, "Enter a Amount","Invaild field",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            amt = Integer.parseInt(amount);
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Enter a Number in Amount","Invaild Input",JOptionPane.WARNING_MESSAGE);
            return;
        }

        try{
            if(expenseDao.createExpense(amt,category,description)>0){
                JOptionPane.showMessageDialog(this,"Expense Added Successfully", "Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,"Failed to add expense", "Failed",JOptionPane.ERROR_MESSAGE);
            }
            loadExpense();
            expenseClearTable();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Database Failed "+e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }
        

    }

    private void updateExpense(){
        int row = expenseTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a Category to update..","Invalid Update",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int)expenseTable.getValueAt(row,0);
        String amount = amountField.getText();
        String description = descriptoinArea.getText();
        String category = expenseCategoryComboBox.getSelectedItem().toString();
        int amt = 0;
    
        if(amount == ""){
            JOptionPane.showMessageDialog(this,"Expense Amount is emty!","Invaild Category Name",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            amt = Integer.parseInt(amount);
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Enter a Number in Amount","Invaild Input",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            if(expenseDao.updateExpense(id,amt, description, category) > 0){
                JOptionPane.showMessageDialog(this,"Expense updated Successfully","Update Success",JOptionPane.INFORMATION_MESSAGE);
                loadExpense();
                expenseClearTable();
            }
            else{
                JOptionPane.showMessageDialog(this, "Expense Update Failed","Update Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Databse Failed while Updating - "+e.getMessage(),"Databse failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteExpense(){
        int row = expenseTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a Expense to update..","Invalid Update",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int)expenseTable.getValueAt(row,0);
        try{
            if(expenseDao.deleteExpense(id) > 0){
                JOptionPane.showMessageDialog(this,"Expense deleted Successfully","Delete Success",JOptionPane.INFORMATION_MESSAGE);
                loadExpense();
                expenseClearTable();
            }
            else{
                JOptionPane.showMessageDialog(this, "Expense Delete Failed","Delete Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Databse Failed while deleting - "+e.getMessage(),"Databse failed",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void refreshExpense(){
        loadExpense();
        expenseClearTable();
    }

    private void loadSelectedExpense(){
        int row = expenseTable.getSelectedRow();
        if(row != -1){
            String amount = expenseTable.getValueAt(row,1).toString();
            String description = expenseTable.getValueAt(row, 2).toString();
            String category = expenseTable.getValueAt(row, 3).toString();

            amountField.setText(amount);
            descriptoinArea.setText(description);
            expenseCategoryComboBox.setSelectedItem(category);
        }
    }
    
    private void expenseClearTable(){
        amountField.setText("");
        descriptoinArea.setText("");
    }

    //Category
    private void CategoryUpdateTable(List<Category> category){
        categoryTableModel.setRowCount(0);
        for(Category cate: category){
            Object row[] = {
                cate.getId(),
                cate.getName()
            };
            categoryTableModel.addRow(row);
        }
    }

    private void loadCategory(){
        try{
            List<Category> categories = expenseDao.getAllCategories();
            CategoryUpdateTable(categories);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Database Error: "+e.getMessage(),"DataBase Error",JOptionPane.ERROR_MESSAGE);
        }
    }    

    private void addCategory(){
        String name = categoryNameField.getText().trim();
        if(name.equals("")){
            JOptionPane.showMessageDialog(this,"Name field is Empty","Invaild Name",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            int rowsAffected = expenseDao.createCategory(name);
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(this,"Category Added Successfully", "Success",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,"Failed to add category", "Failed",JOptionPane.ERROR_MESSAGE);
            }
            loadCategory();
            categoryClearTable();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Database Failed","Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCategory(){
        int row = categoryTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a Category to update..","Invalid Update",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int)categoryTable.getValueAt(row,0);
        String categoryName = categoryNameField.getText();

        if(categoryName == ""){
            JOptionPane.showMessageDialog(this,"Category name is emty!","Invaild Category Name",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            if(expenseDao.updateCategory(id,categoryName) > 0){
                JOptionPane.showMessageDialog(this,"Category updated Successfully","Update Success",JOptionPane.INFORMATION_MESSAGE);
                loadCategory();
                categoryClearTable();
            }
            else{
                JOptionPane.showMessageDialog(this, "Category Update Failed","Update Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Databse Failed while Updating - "+e.getMessage(),"Databse failed",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void deleteCategory(){
        int row = categoryTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a Category to update..","Invalid Update",JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int)categoryTable.getValueAt(row,0);
        try{
            if(expenseDao.deleteCategory(id) > 0){
                JOptionPane.showMessageDialog(this,"Category deleted Successfully","Delete Success",JOptionPane.INFORMATION_MESSAGE);
                loadCategory();
                categoryClearTable();
            }
            else{
                JOptionPane.showMessageDialog(this, "Category Delete Failed","Delete Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(this,"Databse Failed while deleting - "+e.getMessage(),"Databse failed",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshCategory(){
        loadCategory();
        categoryClearTable();
    }

    private void loadSelectedCategory(){
        int row = categoryTable.getSelectedRow();
        if(row != -1){
            String categoryName =  categoryTable.getValueAt(row, 1).toString();
            categoryNameField.setText(categoryName);
        }
    }

    private  void categoryClearTable(){
        categoryNameField.setText("");
    }


}
