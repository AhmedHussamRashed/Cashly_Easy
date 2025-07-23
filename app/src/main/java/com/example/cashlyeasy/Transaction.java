package com.example.cashlyeasy;

public class Transaction {

    // بيانات قاعدة البيانات أو API
    private int id;
    private String type;
    private double amount;
    private String description;
    private String createdAt;

    // بيانات العرض في الواجهة
    private int iconResId;
    private boolean isIncome;

    // ✅ Constructor لواجهة المستخدم
    public Transaction(String title, String date, double amount, int iconResId, boolean isIncome) {
        this.description = title;
        this.createdAt = date;
        this.amount = amount;
        this.iconResId = iconResId;
        this.isIncome = isIncome;
    }

    // ✅ Constructor لقاعدة البيانات / API
    public Transaction(int id, String type, double amount, String description, String createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
    }

    // ✅ Getters للمستخدم في الواجهة
    public boolean isIncome() {
        return isIncome;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return description;
    }

    public String getDate() {
        return createdAt;
    }

    public double getAmount() {
        return amount;
    }

    // ✅ Getters لقاعدة البيانات
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // ✅ Setters (اختياري)
    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
