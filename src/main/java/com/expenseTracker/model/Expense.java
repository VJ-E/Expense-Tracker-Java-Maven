package com.expenseTracker.model;

import java.time.LocalDateTime;

public class Expense {
    private int expId;
    private int amount;
    private LocalDateTime createdAt;
    private int cateId;
    private String description;
    private String category;



    public Expense(int expId, int amount, String description,LocalDateTime createdAt, int cateId,String category) {
        this.expId = expId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.description = description;
        this.cateId = cateId;
        this.category = category;
    }


    public int getExpId() {
        return expId;
    }
    public void setExpId(int expId) {
        this.expId = expId;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public int getCateId() {
        return cateId;
    }
    public void setCateId(int cateId) {
        this.cateId = cateId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    

}
