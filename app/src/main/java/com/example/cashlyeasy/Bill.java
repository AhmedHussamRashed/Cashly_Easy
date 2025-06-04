package com.example.cashlyeasy;

public class Bill {
    public String title;
    public String dueDate;
    public String amount;
    public boolean isUpcoming;

    public Bill(String title, String dueDate, String amount, boolean isUpcoming) {
        this.title = title;
        this.dueDate = dueDate;
        this.amount = amount;
        this.isUpcoming = isUpcoming;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isUpcoming() {
        return isUpcoming;
    }

    public void setUpcoming(boolean upcoming) {
        isUpcoming = upcoming;
    }
}
