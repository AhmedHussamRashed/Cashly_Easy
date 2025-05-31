package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserName, tvBalanceAmount;
    RecyclerView rvTransactions;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ربط عناصر الواجهة
        tvUserName = findViewById(R.id.tvUserName);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        rvTransactions = findViewById(R.id.rvTransactions);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // إعداد RecyclerView
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(this, "تحتاج لإنشاء TransactionAdapter و Transaction Model", Toast.LENGTH_LONG).show();

        // تعيين العنصر الحالي (Home)
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // التعامل مع عناصر التنقل السفلي
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // نحن بالفعل في الصفحة الرئيسية
                    return true;
                } else if (itemId == R.id.navigation_exchange) {
                    startActivity(new Intent(HomeActivity.this, Exchange.class));
                } else if (itemId == R.id.navigation_reports) {
                    startActivity(new Intent(HomeActivity.this, Reports.class));
                } else if (itemId == R.id.navigation_requests) {
                    startActivity(new Intent(HomeActivity.this, Requests.class));
                } else if (itemId == R.id.navigation_account) {
                    startActivity(new Intent(HomeActivity.this, Account.class));
                }

                overridePendingTransition(0, 0); // منع الأنيميشن عند الانتقال
                return true;
            }
        });
    }
}
