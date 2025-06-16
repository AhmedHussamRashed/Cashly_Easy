package com.example.cashlyeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendMoneyActivity extends AppCompatActivity {

    private EditText etRecipient, etAmount;
    private Spinner spinnerCurrency, spinnerFundingSource;
    private Button btnReviewTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        etRecipient = findViewById(R.id.etRecipient);
        etAmount = findViewById(R.id.etAmount);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        spinnerFundingSource = findViewById(R.id.spinnerFundingSource);
        btnReviewTransfer = findViewById(R.id.btnReviewTransfer);

        btnReviewTransfer.setOnClickListener(v -> {
            String recipient = etRecipient.getText().toString();
            String amount = etAmount.getText().toString();
            String currency = spinnerCurrency.getSelectedItem().toString();
            String fundingSource = spinnerFundingSource.getSelectedItem().toString();

            if (recipient.isEmpty() || amount.isEmpty()) {
                Toast.makeText(SendMoneyActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SendMoneyActivity.this, "Review Transfer: " + amount + " " + currency + " to " + recipient + " from " + fundingSource, Toast.LENGTH_LONG).show();
            }
        });
    }
}
