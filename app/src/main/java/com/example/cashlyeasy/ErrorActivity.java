package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Button retryButton = findViewById(R.id.retryButton);

        retryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ErrorActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
