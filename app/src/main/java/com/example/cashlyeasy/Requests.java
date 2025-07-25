package com.example.cashlyeasy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Requests extends AppCompatActivity {

    private TextInputEditText editTextTo;
    private TextInputEditText editTextAmount;
    private TextInputEditText editTextNote;
    private Button buttonSendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        editTextTo = findViewById(R.id.editTextTo);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextNote = findViewById(R.id.editTextNote);
        buttonSendRequest = findViewById(R.id.buttonSendRequest);

        buttonSendRequest.setOnClickListener(v -> sendRequest());
    }

    private void sendRequest() {
        String recipient = editTextTo.getText().toString().trim();
        String amountStr = editTextAmount.getText().toString().trim();
        String note = editTextNote.getText().toString().trim();

        if (TextUtils.isEmpty(recipient)) {
            showErrorDialog("Please enter the recipient's name.");
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            showErrorDialog("Please enter the amount");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showErrorDialog("The amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("The amount format is incorrect.");
            return;
        }

        // إنشاء الوصف
        String description = "Request from: " + recipient;
        if (!note.isEmpty()) {
            description += " (" + note + ")";
        }

        // عرض Dialog النجاح
        showSuccessDialog(description, amount, "now");
    }

    private void showSuccessDialog(String description, double amount, String createdAt) {
        Dialog dialog = new Dialog(Requests.this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(Requests.this, HomeActivity.class);
            intent.putExtra("description", description);
            intent.putExtra("amount", amount);
            intent.putExtra("created_at", createdAt);
            startActivity(intent);
            finish();
        });
        dialog.show();
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(Requests.this);
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
