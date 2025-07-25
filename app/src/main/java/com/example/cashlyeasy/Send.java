package com.example.cashlyeasy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Send extends AppCompatActivity {

    EditText recipientEditText, amountEditText, descriptionEditText;
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
                    showSuccessDialog(description, -Math.abs(amount), "now");
                } catch (NumberFormatException e) {
                    showErrorDialog("Invalid amount");
                }
            }
        });
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
