package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
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

        buttonSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }

    private void sendRequest() {
        String recipient = editTextTo.getText().toString().trim();
        String amountStr = editTextAmount.getText().toString().trim();
        String note = editTextNote.getText().toString().trim();

        if (TextUtils.isEmpty(recipient)) {
            Toast.makeText(this, "Please enter recipient", Toast.LENGTH_SHORT).show();
            editTextTo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
            editTextAmount.requestFocus();
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                Toast.makeText(this, "Amount must be positive", Toast.LENGTH_SHORT).show();
                editTextAmount.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            editTextAmount.requestFocus();
            return;
        }

        // رجع البيانات للـ HomeActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("description", "Request from: " + recipient + (note.isEmpty() ? "" : " (" + note + ")"));
        resultIntent.putExtra("amount", Double.parseDouble(amountStr));
        resultIntent.putExtra("created_at", "Just Now");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
