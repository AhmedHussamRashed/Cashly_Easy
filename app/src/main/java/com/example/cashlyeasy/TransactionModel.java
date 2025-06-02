package com.example.cashlyeasy;

public class TransactionModel {
    private final String title;
    private final  String date;
    private final String amount;
    private final String category;

    public TransactionModel(String title, String date, String amount, String category) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
