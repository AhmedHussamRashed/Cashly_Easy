package com.example.cashlyeasy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Send extends AppCompatActivity {

    EditText recipientEditText, amountEditText, descriptionEditText;
    Spinner currencySpinner;
    Button reviewButton;
    ImageView backButton;

    private static final String API_URL = "http://192.168.1.10/api/payments"; // Laravel API

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        // ربط العناصر من XML
        recipientEditText = findViewById(R.id.recipientEditText);
        amountEditText = findViewById(R.id.amountEditText);
        currencySpinner = findViewById(R.id.currencySpinner);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        reviewButton = findViewById(R.id.reviewButton);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        reviewButton.setOnClickListener(v -> {
            String recipient = recipientEditText.getText().toString().trim();
            String amountStr = amountEditText.getText().toString().trim();
            String currency = currencySpinner.getSelectedItem().toString();
            String description = descriptionEditText.getText().toString().trim();

            boolean hasError = false;

            if (recipient.isEmpty()) {
                recipientEditText.setError("This field is required");
                hasError = true;
            }

            if (amountStr.isEmpty()) {
                amountEditText.setError("This field is required");
                hasError = true;
            }

            if (description.isEmpty()) {
                descriptionEditText.setError("Enter a description of the operation.");
                hasError = true;
            }

            if (!hasError) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    sendTransactionToApi(recipient, -Math.abs(amount), currency, description);
                } catch (NumberFormatException e) {
                    showErrorDialog("Invalid amount");
                }
            }
        });
    }

    private void sendTransactionToApi(String recipient, double amount, String currency, String description) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending the operation...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API_URL,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean status = jsonResponse.optBoolean("status", false);

                        if (status) {
                            JSONObject paymentData = jsonResponse.optJSONObject("data");
                            String createdAt = (paymentData != null) ? paymentData.optString("created_at", "Now") : "Now";

                            showSuccessDialog(description, amount, createdAt);
                        } else {
                            String message = jsonResponse.optString("message", "Sending failed");
                            showErrorDialog(message);
                        }
                    } catch (JSONException e) {
                        showErrorDialog("Response parsing error");
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    showErrorDialog("Response parsing error");
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("description", description);
                params.put("amount", String.valueOf(amount));
                params.put("currency", currency);
                params.put("user_id", "1");
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

    private void showSuccessDialog(String description, double amount, String createdAt) {
        Dialog dialog = new Dialog(Send.this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(Send.this, HomeActivity.class);
            intent.putExtra("description", description);
            intent.putExtra("amount", amount);
            intent.putExtra("created_at", createdAt);
            startActivity(intent);
            finish();
        });
        dialog.show();
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(Send.this);
        errorDialog.setContentView(R.layout.dialog_error);
        errorDialog.setCancelable(true);
        TextView errorMsg = errorDialog.findViewById(R.id.errorMessage);
        if (errorMsg != null) {
            errorMsg.setText(message);
        }
        errorDialog.findViewById(R.id.buttonRetry).setOnClickListener(view -> errorDialog.dismiss());
        errorDialog.show();
    }
}
