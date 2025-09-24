package com.expenseTracker.gui;

import javax.swing.*;

import java.awt.*;

public class MainGui extends JFrame{
    private JButton expenseButton;
    private JButton categoryButton;
    
    public MainGui(){   
        initializeComponents();
        setupLayout();

    }

    private void initializeComponents(){
        setTitle("Main UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,1000);
        setLocationRelativeTo(null);

        expenseButton = new JButton("Expense");
        categoryButton = new JButton("Category");
        expenseButton.setPreferredSize(new Dimension(200,200));
        categoryButton.setPreferredSize(new Dimension(200,200));

    }

    private void setupLayout(){
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        
        panel.add(expenseButton,CENTER_ALIGNMENT);       
        panel.add(categoryButton,CENTER_ALIGNMENT);

        add(panel,BorderLayout.CENTER);
    }

    // private void setupEventListeners(){
    //     expenseButton.addActionListener((e)->{
    //         new ExpenseGui().setVisible(true);
    //     });
    //     categoryButton.addActionListener((e)->{
    //         new CategoryGui().setVisible(true);
    //     });
    // }
}
