package com.example.cashlyeasy;

public class Transaction {

    private String name;
    private String date;  // مثلاً "April 24"
    private String timeAgo; // مثلاً "3.2 Lag"
    private double amount;
    private boolean isIncome; // true لو دخل، false لو مصروف
    private int iconResId; // مثلا R.drawable.ic_store

    public Transaction(String name, String date, String timeAgo, double amount, boolean isIncome, int iconResId) {
        this.name = name;
        this.date = date;
        this.timeAgo = timeAgo;
        this.amount = amount;
        this.isIncome = isIncome;
        this.iconResId = iconResId;
    }

    // getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTimeAgo() { return timeAgo; }
    public double getAmount() { return amount; }
    public boolean isIncome() { return isIncome; }
    public int getIconResId() { return iconResId; }


}
