package com.example.cashlyeasy;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private List<Bill> billList;
    private Context context;
    private OnPayClickListener onPayClickListener;

    public BillAdapter(List<Bill> billList, Context context, OnPayClickListener listener) {
        this.billList = billList;
        this.context = context;
        this.onPayClickListener = listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.textViewBillerName.setText(bill.title);
        holder.textViewDueDate.setText(bill.dueDate);
        holder.textViewAmount.setText(bill.amount);

        holder.buttonPayNow.setOnClickListener(v -> showPaymentDialog(bill));
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    private void showPaymentDialog(Bill bill) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_payment, null);

        TextView dialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
        TextView dialogDescription = dialogView.findViewById(R.id.textViewDialogDescription);
        Button buttonPayWithPaypal = dialogView.findViewById(R.id.buttonPayWithPaypal);
        Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

        dialogTitle.setText("Pay " + bill.title);
        dialogDescription.setText("You can pay this bill securely using PayPal.");

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        buttonPayWithPaypal.setOnClickListener(view -> {
            dialog.dismiss();
            if (onPayClickListener != null) {
                onPayClickListener.onPayClick(bill);
            }
        });

        buttonCancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBillerName, textViewDueDate, textViewAmount;
        Button buttonPayNow;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBillerName = itemView.findViewById(R.id.textViewBillerName);
            textViewDueDate = itemView.findViewById(R.id.textViewDueDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            buttonPayNow = itemView.findViewById(R.id.buttonPayNow);
        }
    }

    public interface OnPayClickListener {
        void onPayClick(Bill bill);
    }
}
