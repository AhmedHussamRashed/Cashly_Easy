package com.example.cashlyeasy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.title.setText(transaction.getTitle());
        holder.date.setText(transaction.getDate());
        holder.amount.setText(transaction.getAmount());
        holder.icon.setImageResource(transaction.getIconResId());

        int color = ContextCompat.getColor(holder.itemView.getContext(),
                transaction.isIncome() ? R.color.transaction_green : R.color.transaction_red);
        holder.amount.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, amount;
        ImageView icon;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.transactionTitle);
            date = itemView.findViewById(R.id.transactionDate);
            amount = itemView.findViewById(R.id.transactionAmount);
            icon = itemView.findViewById(R.id.transactionIcon);
        }
    }
}
