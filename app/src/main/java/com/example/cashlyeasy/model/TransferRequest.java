package com.example.cashlyeasy.model;

public class TransferRequest {
    private int fromUserId;
    private int toUserId;
    private double amount;

    public TransferRequest(int fromUserId, int toUserId, double amount) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public double getAmount() {
        return amount;
    }
}
