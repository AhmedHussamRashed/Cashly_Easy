package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvCreateAccount;
    private ImageButton ibFacebook, ibTwitter, ibGoogle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // ربط العناصر من التصميم
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        ibFacebook = findViewById(R.id.ibFacebook);
        ibTwitter = findViewById(R.id.ibTwitter);
        ibGoogle = findViewById(R.id.ibGoogle);

        // زر تسجيل الدخول
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInput(email, password)) {
                // التحقق من صحة البيانات - هنا يمكنك إضافة التحقق الحقيقي (API أو Firebase)

                // مثال على تحقق بسيط: إذا البريد وكلمة المرور صحيحين، انتقل للهوم
                Toast.makeText(LoginActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();

                // الانتقال إلى شاشة HomeActivity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // لمنع العودة لشاشة تسجيل الدخول بالضغط على زر الرجوع
            }
        });

        // باقي الأكواد كما هي
        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "نسيت كلمة المرور", Toast.LENGTH_SHORT).show();
            // كود فتح شاشة استعادة كلمة المرور
        });

        // التعديل هنا: عند الضغط على إنشاء حساب ننتقل إلى Register Activity
        tvCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Register.class);
            startActivity(intent);
        });

        ibFacebook.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "تسجيل الدخول عبر فيسبوك", Toast.LENGTH_SHORT).show();
            // كود تسجيل الدخول عبر فيسبوك
        });

        ibTwitter.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "تسجيل الدخول عبر تويتر", Toast.LENGTH_SHORT).show();
            // كود تسجيل الدخول عبر تويتر
        });

        ibGoogle.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "تسجيل الدخول عبر جوجل", Toast.LENGTH_SHORT).show();
            // كود تسجيل الدخول عبر جوجل
        });
    }

    // دالة التحقق من صحة المدخلات
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("الرجاء إدخال البريد الإلكتروني");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("البريد الإلكتروني غير صالح");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("الرجاء إدخال كلمة المرور");
            return false;
        }
        if (password.length() < 6) {
            etPassword.setError("كلمة المرور يجب أن تكون 6 أحرف على الأقل");
            return false;
        }
        return true;
    }
}
