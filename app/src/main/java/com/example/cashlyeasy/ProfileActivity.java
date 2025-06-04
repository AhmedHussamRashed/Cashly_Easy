package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivCloseProfile;
    CircleImageView ivProfileAvatar;
    TextView tvProfileName, tvProfileEmail;
    LinearLayout llPersonalInfo, llPasswordSecurity, llNotification, llHelpCenter;
    BottomNavigationView bottomNavigationViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivCloseProfile = findViewById(R.id.ivCloseProfile);
        ivProfileAvatar = findViewById(R.id.ivProfileAvatar);
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        llPersonalInfo = findViewById(R.id.llPersonalInfo);
        llPasswordSecurity = findViewById(R.id.llPasswordSecurity);
        llNotification = findViewById(R.id.llNotification);
        llHelpCenter = findViewById(R.id.llHelpCenter);
        bottomNavigationViewProfile = findViewById(R.id.bottomNavigationViewProfile);

        loadUserData(); // تحميل بيانات المستخدم
        setupCardViewListeners();
        setupBottomNavigation();
    }

    private void loadUserData() {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String name = prefs.getString("name", "اسم المستخدم");
        String email = prefs.getString("email", "example@email.com");
        String imageUri = prefs.getString("imageUri", null); // تخزن كرابط أو مسار محلي

        tvProfileName.setText(name);
        tvProfileEmail.setText(email);

        // تحميل الصورة الرمزية إذا كانت موجودة
        if (imageUri != null) {
            try {
                ivProfileAvatar.setImageURI(android.net.Uri.parse(imageUri));
            } catch (Exception e) {
                e.printStackTrace();
                // صورة افتراضية
                ivProfileAvatar.setImageResource(R.drawable.ic_person_outline);
            }
        } else {
            ivProfileAvatar.setImageResource(R.drawable.ic_person_outline);
        }
    }

    private void setupCardViewListeners() {
        ivCloseProfile.setOnClickListener(v -> finish());

        llPersonalInfo.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class)));

        llPasswordSecurity.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, SecuritySettingsActivity.class)));

        llNotification.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, NotificationsActivity.class)));

        llHelpCenter.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, HelpActivity.class)));
    }

    private void setupBottomNavigation() {
        bottomNavigationViewProfile.setSelectedItemId(R.id.navigation_account);
        bottomNavigationViewProfile.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_exchange) {
                startActivity(new Intent(ProfileActivity.this, Exchange.class));
                return true;
            } else if (itemId == R.id.navigation_reports) {
                startActivity(new Intent(ProfileActivity.this, ReportsActivity.class));
                return true;
            } else if (itemId == R.id.navigation_bills) {
                startActivity(new Intent(ProfileActivity.this, Bill.class));
                return true;
            } else if (itemId == R.id.navigation_account) {
                return true;
            }
            return false;
        });
    }
}
