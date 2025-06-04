package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem; // مهم جداً
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserName, tvBalanceAmount;
    RecyclerView rvTransactions;
    BottomNavigationView bottomNavigationView;

    LinearLayout sendButton, requestButton, payButton, receiptsButton;

    TransactionAdapter transactionAdapter;
    List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvUserName = findViewById(R.id.tvUserName);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        rvTransactions = findViewById(R.id.rvTransactions);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // تأكد أن هذه الـ ids هي LinearLayout في xml
        sendButton = findViewById(R.id.send);
        requestButton = findViewById(R.id.request);
        payButton = findViewById(R.id.pay);
        receiptsButton = findViewById(R.id.receipts);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Send.class));
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Requests.class));
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PayActivity.class));
            }
        });

        receiptsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ReceiptsActivity.class));
            }
        });

        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Electronic Store", "April 24", "3.2 Lag", 120.08, false, R.drawable.ic_store));
        transactionList.add(new Transaction("Salary", "April 23", "1 day ago", 1500.00, true, R.drawable.salary));
        transactionList.add(new Transaction("Coffee Shop", "April 22", "2 days ago", 5.50, false, R.drawable.coffee));

        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        transactionAdapter = new TransactionAdapter(this, transactionList);
        rvTransactions.setAdapter(transactionAdapter);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    return true;
                } else if (itemId == R.id.navigation_exchange) {
                    startActivity(new Intent(HomeActivity.this, Exchange.class));
                } else if (itemId == R.id.navigation_reports) {
                    startActivity(new Intent(HomeActivity.this, ReportsActivity.class));
                } else if (itemId == R.id.navigation_bills) {
                    startActivity(new Intent(HomeActivity.this, BillsActivity.class));
                } else if (itemId == R.id.navigation_account) {
                    startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                }

                overridePendingTransition(0, 0);
                return true;
            }
        });
    }
}
