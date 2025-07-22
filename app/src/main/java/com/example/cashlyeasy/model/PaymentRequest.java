package com.example.cashlyeasy.model;

public class PaymentRequest {
    private int userId;
    private double amount;
    private String description;

    public PaymentRequest() {
        // Default constructor for serialization
    }

    public PaymentRequest(int userId, double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
