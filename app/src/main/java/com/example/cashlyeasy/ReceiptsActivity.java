package com.example.cashlyeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiptsActivity extends AppCompatActivity {

    RecyclerView rvReceipts;
    ReceiptAdapter receiptAdapter;
    List<Receipt> receiptList;
    List<Receipt> receiptListFiltered;
    EditText etSearchReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        // ربط العناصر
        rvReceipts = findViewById(R.id.rvReceipts);
        etSearchReceipts = findViewById(R.id.etSearchReceipts);
        rvReceipts.setLayoutManager(new LinearLayoutManager(this));

        // تحميل بيانات الإيصالات
        loadReceiptsData();

        // إعداد الـ Adapter
        receiptListFiltered = new ArrayList<>(receiptList); // نسخ القائمة للفلترة
        receiptAdapter = new ReceiptAdapter(this, receiptListFiltered);
        rvReceipts.setAdapter(receiptAdapter);

        // إضافة مستمع للبحث (فلترة)
        etSearchReceipts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                receiptAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



    }

    // دالة مثال لتحميل بيانات الإيصالات
    private void loadReceiptsData() {
        receiptList = new ArrayList<>();
        // يجب استبدال الأيقونات بأيقونات حقيقية
        receiptList.add(new Receipt(R.drawable.ic_walmart_logo, "Walmart", "Apr 22", "$52.45"));
        receiptList.add(new Receipt(R.drawable.ic_starbucks_logo, "Starbucks", "Apr 20", "$8.90"));
        receiptList.add(new Receipt(R.drawable.ic_target_logo, "Target", "Apr 16", "$27.16"));
        receiptList.add(new Receipt(R.drawable.ic_electric_plus_logo, "Electric Plus", "Apr 10", "$120.00"));
        // أضف المزيد من الإيصالات حسب الحاجة
    }

    // --- Inner Class: Receipt Model ---
    public static class Receipt {
        int logoResId; // Resource ID for the logo
        String merchantName;
        String date;
        String amount;

        public Receipt(int logoResId, String merchantName, String date, String amount) {
            this.logoResId = logoResId;
            this.merchantName = merchantName;
            this.date = date;
            this.amount = amount;
        }

        // Getters (يمكن إنشاؤها تلقائياً في Android Studio)
        public int getLogoResId() { return logoResId; }
        public String getMerchantName() { return merchantName; }
        public String getDate() { return date; }
        public String getAmount() { return amount; }
    }

    // --- Inner Class: Receipt Adapter ---
    public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ReceiptViewHolder> implements Filterable {

        private Context context;
        private List<Receipt> receiptList;
        private List<Receipt> receiptListFull; // نسخة كاملة للفلترة

        public ReceiptAdapter(Context context, List<Receipt> receiptList) {
            this.context = context;
            this.receiptList = receiptList;
            this.receiptListFull = new ArrayList<>(receiptList); // احتفظ بنسخة كاملة
        }

        @NonNull
        @Override
        public ReceiptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_receipt, parent, false);
            return new ReceiptViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReceiptViewHolder holder, int position) {
            Receipt receipt = receiptList.get(position);
            holder.ivReceiptLogo.setImageResource(receipt.getLogoResId());
            holder.tvReceiptMerchantName.setText(receipt.getMerchantName());
            holder.tvReceiptDate.setText(receipt.getDate());
            holder.tvReceiptAmount.setText(receipt.getAmount());

            // يمكنك إضافة مستمع للنقر على العنصر هنا إذا أردت
            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Clicked on: " + receipt.getMerchantName(), Toast.LENGTH_SHORT).show();
                // يمكنك فتح تفاصيل الإيصال هنا
            });
        }

        @Override
        public int getItemCount() {
            return receiptList.size();
        }

        // ViewHolder Class
        public class ReceiptViewHolder extends RecyclerView.ViewHolder {
            CircleImageView ivReceiptLogo;
            TextView tvReceiptMerchantName, tvReceiptDate, tvReceiptAmount;

            public ReceiptViewHolder(@NonNull View itemView) {
                super(itemView);
                ivReceiptLogo = itemView.findViewById(R.id.ivReceiptLogo);
                tvReceiptMerchantName = itemView.findViewById(R.id.tvReceiptMerchantName);
                tvReceiptDate = itemView.findViewById(R.id.tvReceiptDate);
                tvReceiptAmount = itemView.findViewById(R.id.tvReceiptAmount);
            }
        }

        // Filter Implementation
        @Override
        public Filter getFilter() {
            return receiptFilter;
        }

        private Filter receiptFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Receipt> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(receiptListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim();
                    for (Receipt item : receiptListFull) {
                        if (item.getMerchantName().toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                receiptList.clear();
                receiptList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
