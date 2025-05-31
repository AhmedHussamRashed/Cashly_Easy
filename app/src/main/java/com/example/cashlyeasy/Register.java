package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

class Register extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLoginLink;
    private ImageButton ibGoogle, ibFacebook, ibTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);
        ibGoogle = findViewById(R.id.ibGoogle);
        ibFacebook = findViewById(R.id.ibFacebook);
        ibTwitter = findViewById(R.id.ibTwitter);

        // زر إنشاء حساب
        btnRegister.setOnClickListener(v -> registerUser());

        // عند الضغط على "لديك حساب؟ تسجيل الدخول" ينتقل إلى LoginActivity
        tvLoginLink.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, LoginActivity.class);
            startActivity(intent);
            finish(); // لمنع العودة لواجهة التسجيل بالضغط على زر الرجوع
        });

        // تسجيل دخول بواسطة جوجل
        ibGoogle.setOnClickListener(v -> {
            Toast.makeText(this, "جاري تنفيذ تسجيل الدخول بجوجل...", Toast.LENGTH_SHORT).show();
            // اكتب الكود الخاص بـ Google Sign-In هنا
        });

        // تسجيل دخول بواسطة فيسبوك
        ibFacebook.setOnClickListener(v -> {
            Toast.makeText(this, "جاري تنفيذ تسجيل الدخول بفيسبوك...", Toast.LENGTH_SHORT).show();
            // اكتب الكود الخاص بـ Facebook Sign-In هنا
        });

        // تسجيل دخول بواسطة تويتر
        ibTwitter.setOnClickListener(v -> {
            Toast.makeText(this, "جاري تنفيذ تسجيل الدخول بتويتر...", Toast.LENGTH_SHORT).show();
            // اكتب الكود الخاص بـ Twitter Sign-In هنا
        });
    }

    private void registerUser() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(fullName)) {
            etFullName.setError("الرجاء إدخال الاسم الكامل");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("الرجاء إدخال البريد الإلكتروني");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("الرجاء إدخال كلمة المرور");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("كلمتا المرور غير متطابقتين");
            return;
        }

        // هنا يمكنك استدعاء Firebase أو API لتسجيل المستخدم

        Toast.makeText(this, "تم إنشاء الحساب بنجاح!", Toast.LENGTH_LONG).show();

        // بعد التسجيل الناجح، الانتقال إلى شاشة تسجيل الدخول
        Intent intent = new Intent(Register.this, LoginActivity.class);
        startActivity(intent);
        finish(); // لمنع العودة لشاشة التسجيل بالضغط على زر الرجوع
    }
}
