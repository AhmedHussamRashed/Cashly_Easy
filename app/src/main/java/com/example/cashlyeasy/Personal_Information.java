package com.example.cashlyeasy;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Personal_Information extends AppCompatActivity {

    CircleImageView ivProfileAvatar;
    TextView tvName, tvEmail, tvPassword, tvLanguage, tvAddress, tvBalance;
    Button btnEdit, btnDeleteAccount, btnLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        // ربط العناصر من XML
        ivProfileAvatar = findViewById(R.id.ivProfileAvatar);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvAddress = findViewById(R.id.tvAddress);
        tvBalance = findViewById(R.id.tvBalance);

        btnEdit = findViewById(R.id.btnEdit);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        btnLogout = findViewById(R.id.btnLogout);

        loadUserData();

        btnEdit.setOnClickListener(v -> {
            // فتح واجهة التعديل
            Intent intent = new Intent(Personal_Information.this, EditProfileActivity.class);
            startActivity(intent);
        });

        btnDeleteAccount.setOnClickListener(v -> {
            // فتح واجهة حذف الحساب
            Intent intent = new Intent(Personal_Information.this, DeleteAccountActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> showLogoutDialog());
    }

    private void loadUserData() {
        tvName.setText("Ahmed Rashed");
        tvEmail.setText("ahmedrashed@example.com");
        tvPassword.setText("Password: ********");
        tvLanguage.setText("Language: Arabic");
        tvAddress.setText("Address: Gaza");
        tvBalance.setText("Bank balance: $10,000");

        ivProfileAvatar.setImageResource(R.drawable.ic_user_avatar_large);
    }

    private void showLogoutDialog() {
        // تخصيص دايلوج تسجيل الخروج
        View dialogView = LayoutInflater.from(this).inflate(R.layout.logout_dialog, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnConfirmLogout = dialogView.findViewById(R.id.btn_logout);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirmLogout.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}
