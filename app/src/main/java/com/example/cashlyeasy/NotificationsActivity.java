package com.example.cashlyeasy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationAdapter adapter;
    ArrayList<String> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.recyclerViewNotifications);
        notifications = new ArrayList<>();

        // إشعارات تجريبية
        notifications.add("✅ Deposit has been successfully added.");
        notifications.add("👤 Your profile has been updated.");
        notifications.add("🎉 Welcome to the Cashly app!");
        notifications.add("🌐 The language has been changed to Arabic.");
        notifications.add("📊 Reminder: Check your monthly report now.");

        adapter = new NotificationAdapter(notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
