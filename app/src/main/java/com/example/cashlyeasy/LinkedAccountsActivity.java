package com.example.cashlyeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LinkedAccountsActivity extends AppCompatActivity {

    ImageView ivBackLinkedAccounts, ivAddAccount;
    RecyclerView rvLinkedAccounts;
    LinearLayout llEmptyState;
    Button btnLinkAccountNow;
    LinkedAccountAdapter linkedAccountAdapter;
    List<LinkedAccount> linkedAccountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_accounts);

        // Bind Views
        ivBackLinkedAccounts = findViewById(R.id.ivBackLinkedAccounts);
        ivAddAccount = findViewById(R.id.ivAddAccount);
        rvLinkedAccounts = findViewById(R.id.rvLinkedAccounts);
        llEmptyState = findViewById(R.id.llEmptyState);
        btnLinkAccountNow = findViewById(R.id.btnLinkAccountNow);

        // Setup RecyclerView
        setupRecyclerView();

        // Load Data (Example)
        loadLinkedAccounts();

        // Setup Click Listeners
        setupClickListeners();

        // Check if list is empty to show/hide empty state
        checkEmptyState();

    }

    private void setupRecyclerView() {
        rvLinkedAccounts.setLayoutManager(new LinearLayoutManager(this));
        linkedAccountList = new ArrayList<>();
        linkedAccountAdapter = new LinkedAccountAdapter(this, linkedAccountList);
        rvLinkedAccounts.setAdapter(linkedAccountAdapter);
    }

    private void loadLinkedAccounts() {
        // Replace this with your actual data fetching logic
        linkedAccountList.clear(); // Clear previous data

        // Add sample data based on user request
        linkedAccountList.add(new LinkedAccount("Bank of Palestine", "Current Account", R.drawable.bank)); // Replace with actual drawable
        linkedAccountList.add(new LinkedAccount("Quds Bank", "Savings Account", R.drawable.bank)); // Replace with actual drawable
        linkedAccountList.add(new LinkedAccount("Jawwal Pay", "Mobile Wallet", R.drawable.jawwalpay)); // Create this drawable
        linkedAccountList.add(new LinkedAccount("PayPal", "PayPal Balance", R.drawable.paypal)); // Create this drawable

        // Notify adapter about data changes
        linkedAccountAdapter.notifyDataSetChanged();
    }

    private void checkEmptyState() {
        if (linkedAccountList.isEmpty()) {
            rvLinkedAccounts.setVisibility(View.GONE);
            llEmptyState.setVisibility(View.VISIBLE);
        } else {
            rvLinkedAccounts.setVisibility(View.VISIBLE);
            llEmptyState.setVisibility(View.GONE);
        }
    }

    private void setupClickListeners() {
        ivBackLinkedAccounts.setOnClickListener(v -> finish()); // Go back

        ivAddAccount.setOnClickListener(v -> {
            // Logic to navigate to Add Account screen
            Toast.makeText(this, "Add Account Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, AddAccountActivity.class));
        });

        btnLinkAccountNow.setOnClickListener(v -> {
            // Logic to navigate to Add Account screen from empty state
            Toast.makeText(this, "Link Account Now Clicked", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, AddAccountActivity.class));
        });
    }

    // --- Inner Classes for Model and Adapter ---

    // 1. LinkedAccount Model Class
    public static class LinkedAccount {
        String accountName;
        String accountType;
        int logoResId; // Drawable resource ID for the logo

        public LinkedAccount(String accountName, String accountType, int logoResId) {
            this.accountName = accountName;
            this.accountType = accountType;
            this.logoResId = logoResId;
        }

        // Getters
        public String getAccountName() { return accountName; }
        public String getAccountType() { return accountType; }
        public int getLogoResId() { return logoResId; }
    }

    // 2. LinkedAccountAdapter Class
    public static class LinkedAccountAdapter extends RecyclerView.Adapter<LinkedAccountAdapter.ViewHolder> {

        private Context context;
        private List<LinkedAccount> accountList;

        public LinkedAccountAdapter(Context context, List<LinkedAccount> accountList) {
            this.context = context;
            this.accountList = accountList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_linked_account, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            LinkedAccount account = accountList.get(position);

            holder.tvAccountName.setText(account.getAccountName());
            holder.tvAccountType.setText(account.getAccountType());
            holder.ivAccountLogo.setImageResource(account.getLogoResId());

            // Set click listener for options icon (e.g., show a menu)
            holder.ivAccountOptions.setOnClickListener(v -> {
                Toast.makeText(context, "Options for " + account.getAccountName(), Toast.LENGTH_SHORT).show();
                // Implement options menu logic here (e.g., PopupMenu)
            });

            // Set click listener for the whole item if needed
            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, account.getAccountName() + " clicked", Toast.LENGTH_SHORT).show();
                // Navigate to account details screen, etc.
            });
        }

        @Override
        public int getItemCount() {
            return accountList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivAccountOptions, ivAccountLogo;
            TextView tvAccountName, tvAccountType;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ivAccountOptions = itemView.findViewById(R.id.ivAccountOptions);
                ivAccountLogo = itemView.findViewById(R.id.ivAccountLogo);
                tvAccountName = itemView.findViewById(R.id.tvAccountName);
                tvAccountType = itemView.findViewById(R.id.tvAccountType);
            }
        }
    }
}
