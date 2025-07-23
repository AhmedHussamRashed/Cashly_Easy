package com.example.cashlyeasy;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PayActivity extends AppCompatActivity {

    private LinearLayout optionBill, optionQR;
    private EditText etBillerId, etBillAmount, etBillDiscription;
    private Button btnProceedToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // ربط المكونات
        optionBill = findViewById(R.id.optionBill);
        optionQR = findViewById(R.id.optionQR);
        etBillerId = findViewById(R.id.etBillerId);
        etBillAmount = findViewById(R.id.etBillAmount);
        etBillDiscription = findViewById(R.id.etBillDiscription);
        btnProceedToPay = findViewById(R.id.btnProceedToPay);

        // عند الضغط على Pay Bill → الانتقال للهوم
        optionBill.setOnClickListener(v -> {
            Intent intent = new Intent(PayActivity.this, HomeActivity.class);
            intent.putExtra("open_from", "pay_bill");
            startActivity(intent);
        });

        // عند الضغط على Scan QR → عرض دايلوج يحتوي على QR أيقونة
        optionQR.setOnClickListener(v -> showQRDialog());

        // عند الضغط على زر الدفع → التحقق من البيانات ثم إرسالها للهوم
        btnProceedToPay.setOnClickListener(v -> {
            String billerId = etBillerId.getText().toString().trim();
            String amount = etBillAmount.getText().toString().trim();
            String description = etBillDiscription.getText().toString().trim();

            if (billerId.isEmpty()) {
                etBillerId.setError("Enter Biller ID");
                return;
            }
            if (amount.isEmpty()) {
                etBillAmount.setError("Enter Amount");
                return;
            }
            if (description.isEmpty()) {
                etBillDiscription.setError("Enter Description");
                return;
            }

            // نقل البيانات للهوم
            Intent intent = new Intent(PayActivity.this, HomeActivity.class);
            intent.putExtra("biller_id", billerId);
            intent.putExtra("amount", amount);
            intent.putExtra("description", description);
            startActivity(intent);
            Toast.makeText(this, "Payment details sent to Home", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // دالة عرض دايلوج QR
    private void showQRDialog() {
        Dialog dialog = new Dialog(PayActivity.this);
        dialog.setContentView(R.layout.dialog_qr);

        ImageView qrIcon = dialog.findViewById(R.id.qrIcon);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
