package com.expenseTracker.model;

import java.time.LocalDateTime;

public class Expense {
    private int expId;
    private int amount;
    private LocalDateTime createdAt;
    private int cateId;


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

    

}
