package com.example.cashlyeasy;

public class TransferRequest {
    private int fromUserId;
    private int toUserId;
    private double amount;

    public TransferRequest(int fromUserId, int toUserId, double amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }
}

