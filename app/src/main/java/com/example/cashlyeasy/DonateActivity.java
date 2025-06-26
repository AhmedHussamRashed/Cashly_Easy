package com.example.cashlyeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DonateActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spinnerFundingSource;
    private Button btnReviewDonation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        etAmount = findViewById(R.id.etAmount);
        spinnerFundingSource = findViewById(R.id.spinnerFundingSource);
        btnReviewDonation = findViewById(R.id.btnReviewDonation);

        btnReviewDonation.setOnClickListener(v -> {
            String amount = etAmount.getText().toString();
            String fundingSource = spinnerFundingSource.getSelectedItem().toString();

            if (amount.isEmpty()) {
                Toast.makeText(DonateActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DonateActivity.this, "Review Donation: " + amount + " from " + fundingSource, Toast.LENGTH_LONG).show();
            }
        });
    }
}
