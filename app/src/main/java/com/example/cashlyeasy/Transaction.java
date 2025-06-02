package com.example.cashlyeasy;

public class Transaction {
    private final String title;
    private final String date;
    private final String amount;
    private final int iconResId;
    private final boolean isIncome;

    public Transaction(String title, String date, String amount, int iconResId, boolean isIncome) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.iconResId = iconResId;
        this.isIncome = isIncome;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isIncome() {
        return isIncome;
    }
}
