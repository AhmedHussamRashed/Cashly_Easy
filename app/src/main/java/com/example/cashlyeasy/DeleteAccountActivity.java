package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DeleteAccountActivity extends AppCompatActivity {

    ImageView ivBackArrowDeleteAccount;
    EditText etConfirmPasswordDelete;
    Button btnProceedDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        // ربط عناصر الواجهة
        ivBackArrowDeleteAccount = findViewById(R.id.ivBackArrowDeleteAccount);
        etConfirmPasswordDelete = findViewById(R.id.etConfirmPasswordDelete);
        btnProceedDelete = findViewById(R.id.btnProceedDelete);

        // زر الرجوع
        ivBackArrowDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // العودة إلى الشاشة السابقة
            }
        });

        // زر المتابعة لحذف الحساب
        btnProceedDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etConfirmPasswordDelete.getText().toString().trim();

                // التحقق من إدخال كلمة المرور
                if (TextUtils.isEmpty(password)) {
                    etConfirmPasswordDelete.setError("Password is required for confirmation");
                    etConfirmPasswordDelete.requestFocus();
                    return;
                }

                // مثال: التحقق من كلمة المرور (يمكنك تعديل المنطق حسب التطبيق الفعلي)
                // هنا سنفترض أنها صحيحة ونقوم بالحذف

                Toast.makeText(DeleteAccountActivity.this, "Account deleted successfully.", Toast.LENGTH_SHORT).show();

                // إغلاق جميع الأنشطة والخروج من التطبيق
                finishAffinity(); // ينهي جميع الأنشطة الحالية
                System.exit(0);   // إنهاء العملية بشكل كامل (اختياري)
            }
        });
    }
}
