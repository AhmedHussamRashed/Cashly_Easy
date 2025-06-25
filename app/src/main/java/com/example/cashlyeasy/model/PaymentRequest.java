package com.example.cashlyeasy.model;

public class PaymentRequest {
    private int userId;
    private double amount;
    private String description;

    public PaymentRequest(int userId, double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
