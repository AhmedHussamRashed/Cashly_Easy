package com.example.cashlyeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditPersonalInfoActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhoneNumber;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        btnSaveChanges.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();

            if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(EditPersonalInfoActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditPersonalInfoActivity.this, "Changes saved!", Toast.LENGTH_SHORT).show();
                // Here, you can implement saving the data (e.g., to a database or shared preferences)
            }
        });
    }
}
