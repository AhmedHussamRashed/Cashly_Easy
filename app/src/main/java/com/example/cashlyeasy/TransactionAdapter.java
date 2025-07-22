package com.example.cashlyeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    public void setData(List<Transaction> newList) {
        this.transactionList = newList;
        notifyDataSetChanged();
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

        holder.tvTransactionName.setText(transaction.getDescription());

        // تحويل created_at إلى "time ago"
        holder.tvTransactionDate.setText(formatDate(transaction.getCreatedAt()));
        holder.tvTransactionTimeAgo.setText(getTimeAgo(transaction.getCreatedAt()));

        String amountText = (transaction.isIncome() ? "+ " : "- ") + String.format(Locale.US, "%.2f $", transaction.getAmount());
        holder.tvTransactionAmount.setText(amountText);

        int colorRes = transaction.isIncome() ? R.color.transaction_green : R.color.transaction_red;
        holder.tvTransactionAmount.setTextColor(context.getResources().getColor(colorRes));

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

    // تحويل created_at إلى صيغة مبسطة
    private String formatDate(String isoDate) {
        try {
            SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date date = iso.parse(isoDate);
            SimpleDateFormat output = new SimpleDateFormat("MMM dd", Locale.US);
            return output.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    // تحويل إلى "منذ وقت"
    private String getTimeAgo(String isoDate) {
        try {
            SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            Date past = iso.parse(isoDate);
            long seconds = (new Date().getTime() - past.getTime()) / 1000;

            if (seconds < 60) return "الآن";
            else if (seconds < 3600) return (seconds / 60) + " دقيقة";
            else if (seconds < 86400) return (seconds / 3600) + " ساعة";
            else return (seconds / 86400) + " يوم";
        } catch (ParseException e) {
            return "";
        }
    }
}
