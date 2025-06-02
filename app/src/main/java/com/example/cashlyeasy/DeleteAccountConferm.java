package com.example.cashlyeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteAccountConferm extends AppCompatActivity {

    Button btnCancel, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_account);

        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // أغلق الصفحة
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // تنفيذ حذف الحساب
                Toast.makeText(DeleteAccountConferm.this, "Account deleted permanently", Toast.LENGTH_LONG).show();
                // هنا يمكنك إضافة كود لحذف الحساب نهائيًا
                finish();
            }
        });
    }
}
