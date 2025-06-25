package com.example.cashlyeasy.model;

public class TransactionRequest {
    private int userId;
    private double amount;

    public TransactionRequest(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }
}
