package com.example.cashlyeasy;

public class TransactionRequest {
    private int userId;
    private double amount;

    public TransactionRequest(int userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}

