package com.example.cashlyeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;



public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.tvTransactionName.setText(transaction.getName());
        holder.tvTransactionDate.setText(transaction.getDate());
        holder.tvTransactionTimeAgo.setText(transaction.getTimeAgo());

        // عرض المبلغ مع علامة + أو - حسب نوع العملية (دخل أو مصروف)
        String amountText = (transaction.isIncome() ? "+ " : "- ") + String.format("%.2f $", transaction.getAmount());
        holder.tvTransactionAmount.setText(amountText);

        // لون النص بناءً على نوع العملية
        int colorRes = transaction.isIncome() ? R.color.transaction_green : R.color.transaction_red;
        holder.tvTransactionAmount.setTextColor(context.getResources().getColor(colorRes));

        // أيقونة المعاملة
        holder.ivTransactionIcon.setImageResource(transaction.getIconResId());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        ImageView ivTransactionIcon;
        TextView tvTransactionName, tvTransactionDate, tvTransactionAmount, tvTransactionTimeAgo;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTransactionIcon = itemView.findViewById(R.id.ivTransactionIcon);
            tvTransactionName = itemView.findViewById(R.id.tvTransactionName);
            tvTransactionDate = itemView.findViewById(R.id.tvTransactionDate);
            tvTransactionAmount = itemView.findViewById(R.id.tvTransactionAmount);
            tvTransactionTimeAgo = itemView.findViewById(R.id.tvTransactionTimeAgo);
        }
    }
}
