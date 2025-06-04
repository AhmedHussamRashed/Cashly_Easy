package com.example.cashlyeasy;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class Send extends AppCompatActivity {

    EditText recipientEditText, amountEditText;
    Spinner currencySpinner;
    Button reviewButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        recipientEditText = findViewById(R.id.recipientEditText);
        amountEditText = findViewById(R.id.amountEditText);
        currencySpinner = findViewById(R.id.currencySpinner);
        reviewButton = findViewById(R.id.reviewButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        reviewButton.setOnClickListener(v -> {
            String recipient = recipientEditText.getText().toString().trim();
            String amount = amountEditText.getText().toString().trim();

            boolean hasError = false;

            if (recipient.isEmpty()) {
                recipientEditText.setError("هذا الحقل مطلوب");
                hasError = true;
            }

            if (amount.isEmpty()) {
                amountEditText.setError("هذا الحقل مطلوب");
                hasError = true;
            }

            if (!hasError) {
                String currency = currencySpinner.getSelectedItem().toString();
                Toast.makeText(this, "مراجعة التحويل إلى " + recipient, Toast.LENGTH_SHORT).show();
                // يمكنك الانتقال إلى شاشة المراجعة هنا
            }
        });
    }
}
