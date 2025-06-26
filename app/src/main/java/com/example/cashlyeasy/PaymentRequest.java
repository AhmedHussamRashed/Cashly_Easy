package com.example.cashlyeasy;

public class PaymentRequest {
    private int userId;
    private double amount;
    private String description;

    public PaymentRequest(int userId, double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }
}

