package com.example.cashlyeasy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private TransactionAdapter adapter;
    private List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionList = new ArrayList<>();

        transactionList.add(new Transaction("بقالة", "23 أبريل", "-99.00", R.drawable.ic_groceries, false));
        transactionList.add(new Transaction("Spotify", "22 أبريل", "-17.99", R.drawable.ic_spotify, false));
        transactionList.add(new Transaction("الراتب", "21 أبريل", "+2500.00", R.drawable.ic_salary, true));
        transactionList.add(new Transaction("مطعم", "19 أبريل", "-45.50", R.drawable.ic_restaurant, false));
        transactionList.add(new Transaction("فريلانسر", "15 أبريل", "+750.00", R.drawable.ic_salary, true));

        adapter = new TransactionAdapter(transactionList);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        transactionRecyclerView.setAdapter(adapter);
    }
}
