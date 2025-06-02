package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        EditText passwordInput = findViewById(R.id.passwordInput);
        Button deleteAccountButton = findViewById(R.id.deleteAccountButton);
        ImageView backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(v -> finish());

        deleteAccountButton.setOnClickListener(v -> {
            String password = passwordInput.getText().toString().trim();
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            deleteAccount();
        });
    }

    private void deleteAccount() {

        Toast.makeText(this, "Account deleted", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(DeleteAccountActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
