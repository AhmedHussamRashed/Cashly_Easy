package com.example.cashlyeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class BillsActivity extends AppCompatActivity {

    private TextView textViewScreenTitle;
    private TabLayout tabLayout;
    private RecyclerView recyclerViewBills;
    private BottomNavigationView bottomNavigationViewBills;

    private List<Bill> allBills = new ArrayList<>();
    private List<Bill> displayedBills = new ArrayList<>();

    private BillAdapter billAdapter; // تأكد من وجود هذا الكلاس

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        // Initialize Views
        textViewScreenTitle = findViewById(R.id.textViewScreenTitle);
        tabLayout = findViewById(R.id.tabLayout);
        recyclerViewBills = findViewById(R.id.recyclerViewBills);
        bottomNavigationViewBills = findViewById(R.id.bottomNavigationViewBills);

        setupRecyclerView();
        setupTabs();
        setupBottomNavigation();
        loadBillData(); // Load initial data
    }

    private void setupRecyclerView() {
        recyclerViewBills.setLayoutManager(new LinearLayoutManager(this));
        billAdapter = new BillAdapter(displayedBills, this); // تأكد من أن BillAdapter يأخذ السياق
        recyclerViewBills.setAdapter(billAdapter);
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Paid"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterBills(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationViewBills.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(BillsActivity.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_bills) {
                    return true; // You are already in BillsActivity
                } else if (itemId == R.id.navigation_account) {
                    startActivity(new Intent(BillsActivity.this, ProfileActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_exchange) {
                    startActivity(new Intent(BillsActivity.this, Exchange.class));
                    return true;
                } else if (itemId == R.id.navigation_reports) {
                    startActivity(new Intent(BillsActivity.this, ReportsActivity.class));
                    return true;
                }

                return false;
            }
        });
    }

    private void loadBillData() {
        allBills.clear();
        allBills.add(new Bill("Electric Plus", "Due Jun 15", "$120.00", true));
        allBills.add(new Bill("Internet Co.", "Due Jun 15", "$60.00", true));
        allBills.add(new Bill("Water Works", "Due Jun 15", "$45.00", true));
        allBills.add(new Bill("Mobile Plan", "Due Jun 15", "$90.00", true));
        allBills.add(new Bill("Credit Card", "Paid May 20", "$500.00", false));
        allBills.add(new Bill("Rent", "Paid May 01", "$1200.00", false));

        filterBills(0); // Show Upcoming by default
    }

    private void filterBills(int tabPosition) {
        displayedBills.clear();
        boolean showUpcoming = (tabPosition == 0);

        for (Bill bill : allBills) {
            if (bill.isUpcoming == showUpcoming) {
                displayedBills.add(bill);
            }
        }

        if (billAdapter != null) {
            billAdapter.notifyDataSetChanged();
        }
    }
}
