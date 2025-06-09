package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PayActivity extends AppCompatActivity {

    private EditText etBillerId;
    private EditText etBillAmount;
    private Button btnProceedToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        // ربط عناصر الواجهة
        etBillerId = findViewById(R.id.etBillerId);
        etBillAmount = findViewById(R.id.etBillAmount);
        btnProceedToPay = findViewById(R.id.btnProceedToPay);

        // إضافة مستمع النقر لزر الدفع
        btnProceedToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String billerId = etBillerId.getText().toString().trim();
                String amount = etBillAmount.getText().toString().trim();


                if (billerId.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(PayActivity.this, "Please enter Biller ID and Amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                // إظهار مربع حوار تأكيد الدفع عبر باي بال
                showPayPalConfirmationDialog(billerId, amount);
            }
        });
    }

    private void showPayPalConfirmationDialog(String billerId, String amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm PayPal Payment");
        builder.setMessage("Do you want to pay " + amount + " for Biller ID: " + billerId + " using PayPal?");

        // زر "Pay with PayPal"
        builder.setPositiveButton("Pay with PayPal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // هنا تضع الكود الخاص ببدء عملية الدفع الفعلية عبر PayPal SDK
                // حاليًا، سنعرض رسالة توست مؤقتة
                Toast.makeText(PayActivity.this, "Initiating PayPal payment...", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // إغلاق مربع الحوار بعد النقر
            }
        });

        // زر "Cancel"
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // إلغاء العملية وإغلاق مربع الحوار
                dialog.dismiss();
            }
        });

        // إنشاء وإظهار مربع الحوار
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

