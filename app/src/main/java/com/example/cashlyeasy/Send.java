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

    EditText recipientEditText, amountEditText;
    Spinner currencySpinner;
    Button reviewButton;
    ImageView backButton;

    private static final String API_URL = "http://192.168.1.10/api/payments"; // Laravel API

    ProgressDialog progressDialog;

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
                    showErrorDialog("المبلغ غير صالح");
                }
            }
        });
    }

    private void sendTransactionToApi(String recipient, double amount, String currency) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("جارٍ إرسال العملية...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, API_URL,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean status = jsonResponse.optBoolean("status", false);

                        if (status) {
                            // العملية نجحت
                            JSONObject paymentData = jsonResponse.optJSONObject("data");
                            String createdAt = (paymentData != null) ? paymentData.optString("created_at", "الآن") : "الآن";

                            showSuccessDialog(recipient, amount, createdAt);
                        } else {
                            // فشل العملية مع رسالة من السيرفر
                            String message = jsonResponse.optString("message", "فشل الإرسال");
                            showErrorDialog(message);
                        }
                    } catch (JSONException e) {
                        showErrorDialog("خطأ في تحليل الاستجابة");
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    showErrorDialog("فشل الاتصال بالخادم");
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("description", "Send to: " + recipient);
                params.put("amount", String.valueOf(amount));
                params.put("currency", currency);
                // ✅ يمكننا إضافة user_id ثابت إذا أردنا (مثلاً 1)
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

    private void showSuccessDialog(String recipient, double amount, String createdAt) {
        Dialog dialog = new Dialog(Send.this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(Send.this, HomeActivity.class);
            intent.putExtra("description", "Send to: " + recipient);
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
