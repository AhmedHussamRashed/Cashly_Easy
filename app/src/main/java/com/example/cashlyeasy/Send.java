package com.example.cashlyeasy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



import java.util.HashMap;
import java.util.Map;

public class Send extends AppCompatActivity {

    EditText recipientEditText, amountEditText;
    Spinner currencySpinner;
    Button reviewButton;
    ImageView backButton;

    //  رابط Laravel API
    private static final String API_URL = "http://192.168.1.10/api/payments";


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
            String amountStr = amountEditText.getText().toString().trim();
            String currency = currencySpinner.getSelectedItem().toString();

            boolean hasError = false;

            if (recipient.isEmpty()) {
                recipientEditText.setError("هذا الحقل مطلوب");
                hasError = true;
            }

            if (amountStr.isEmpty()) {
                amountEditText.setError("هذا الحقل مطلوب");
                hasError = true;
            }

            if (!hasError) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    sendTransactionToApi(recipient, -Math.abs(amount), currency);
                } catch (NumberFormatException e) {
                    showErrorDialog();
                }
            }
        });
    }

    private void sendTransactionToApi(String recipient, double amount, String currency) {
        StringRequest request = new StringRequest(Request.Method.POST, API_URL,
                response -> {
                    //  نجاح الإرسال
                    showSuccessDialog(recipient, amount);
                },
                error -> {
                    error.printStackTrace();
                    showErrorDialog();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("description", "Send to: " + recipient);
                params.put("amount", String.valueOf(amount));
                params.put("currency", currency);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void showSuccessDialog(String recipient, double amount) {
        Dialog dialog = new Dialog(Send.this);
        dialog.setContentView(R.layout.dialog_success); // للنجاح
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(view -> {
            dialog.dismiss();

            Intent intent = new Intent(Send.this, HomeActivity.class);
            intent.putExtra("description", "Send to: " + recipient);
            intent.putExtra("amount", amount);
            intent.putExtra("created_at", "الآن");
            startActivity(intent);
            finish();
        });
        dialog.show();
    }

    private void showErrorDialog() {
        Dialog errorDialog = new Dialog(Send.this);
        errorDialog.setContentView(R.layout.dialog_error); // للفشل
        errorDialog.setCancelable(true);
        errorDialog.findViewById(R.id.buttonRetry).setOnClickListener(view -> errorDialog.dismiss());
        errorDialog.show();
    }
}
