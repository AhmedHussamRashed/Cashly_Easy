package com.example.cashlyeasy;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserName, tvBalanceAmount;
    RecyclerView rvTransactions;
    BottomNavigationView bottomNavigationView;

    LinearLayout sendButton, requestButton, payButton, receiptsButton;

    TransactionAdapter transactionAdapter;
    List<Transaction> transactionList = new ArrayList<>();

    String apiUrl = "http://192.168.1.10/api/payments"; // Laravel API endpoint

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvUserName = findViewById(R.id.tvUserName);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        rvTransactions = findViewById(R.id.rvTransactions);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        ImageView ivUserAvatar = findViewById(R.id.ivUserAvatar);
        ivUserAvatar.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        sendButton = findViewById(R.id.send);
        requestButton = findViewById(R.id.request);
        payButton = findViewById(R.id.pay);
        receiptsButton = findViewById(R.id.receipts);

        sendButton.setOnClickListener(view -> startActivityForResult(new Intent(HomeActivity.this, Send.class), 100));
        requestButton.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, Requests.class)));
        payButton.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, PayActivity.class)));
        receiptsButton.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ReceiptsActivity.class)));

        transactionAdapter = new TransactionAdapter(this, transactionList);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(transactionAdapter);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) return true;
            else if (itemId == R.id.navigation_exchange) startActivity(new Intent(this, Exchange.class));
            else if (itemId == R.id.navigation_reports) startActivity(new Intent(this, ReportsActivity.class));
            else if (itemId == R.id.navigation_bills) startActivity(new Intent(this, BillsActivity.class));
            else if (itemId == R.id.navigation_account) startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        });

        handlePayPalRedirect(getIntent());
        fetchTransactions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            String description = data.getStringExtra("description");
            double amount = data.getDoubleExtra("amount", 0);
            String createdAt = data.getStringExtra("created_at");

            boolean isIncome = amount > 0;
            String type = isIncome ? "income" : "expense";

            Transaction newTransaction = new Transaction(0, type, amount, description, createdAt);
            transactionList.add(0, newTransaction);
            transactionAdapter.notifyItemInserted(0);
            rvTransactions.scrollToPosition(0);
        }
    }

    private void handlePayPalRedirect(Intent intent) {
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null && "com.example.cashlyeasy".equals(data.getScheme()) && "paypalpay".equals(data.getHost())) {
                String paymentId = data.getQueryParameter("paymentId");
                String token = data.getQueryParameter("token");
                String payerId = data.getQueryParameter("PayerID");

                if (paymentId != null && payerId != null) {
                    Dialog dialog_success = new Dialog(this);
                    dialog_success.setContentView(R.layout.dialog_success);
                    dialog_success.setCancelable(true);
                    dialog_success.findViewById(R.id.btn_ok).setOnClickListener(v -> dialog_success.dismiss());
                    dialog_success.show();
                } else {
                    Dialog dialog_error = new Dialog(this);
                    dialog_error.setContentView(R.layout.dialog_error);
                    dialog_error.setCancelable(true);
                    dialog_error.findViewById(R.id.buttonRetry).setOnClickListener(v -> dialog_error.dismiss());
                    dialog_error.show();
                }
            }
        }
    }

    private void fetchTransactions() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                response -> {
                    try {
                        transactionList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);

                            int id = obj.getInt("id");
                            String type = obj.getString("type");
                            double amount = obj.getDouble("amount");
                            String description = obj.getString("description");
                            String createdAt = obj.getString("created_at");

                            Transaction transaction = new Transaction(id, type, amount, description, createdAt);
                            transactionList.add(transaction);
                        }
                        transactionAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "خطأ في تحليل البيانات", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "فشل الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }
}