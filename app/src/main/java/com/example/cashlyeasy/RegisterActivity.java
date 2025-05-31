package com.example.cashlyeasy;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    EditText etFullName, etEmailRegister, etPasswordRegister, etConfirmPassword;
    Button btnRegister;
    TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // ربط عناصر الواجهة بالكود
        etFullName = findViewById(R.id.etFullName);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // تعيين مستمع النقر لزر إنشاء الحساب
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String email = etEmailRegister.getText().toString().trim();
                String password = etPasswordRegister.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                // التحقق من صحة المدخلات
                if (TextUtils.isEmpty(fullName)) {
                    etFullName.setError("الرجاء إدخال الاسم الكامل");
                    etFullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    etEmailRegister.setError("الرجاء إدخال البريد الإلكتروني");
                    etEmailRegister.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmailRegister.setError("الرجاء إدخال بريد إلكتروني صالح");
                    etEmailRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPasswordRegister.setError("الرجاء إدخال كلمة المرور");
                    etPasswordRegister.requestFocus();
                    return;
                }

                if (password.length() < 6) { // مثال: كلمة المرور يجب أن تكون 6 أحرف على الأقل
                    etPasswordRegister.setError("كلمة المرور قصيرة جداً (6 أحرف على الأقل)");
                    etPasswordRegister.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError("الرجاء تأكيد كلمة المرور");
                    etConfirmPassword.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("كلمتا المرور غير متطابقتين");
                    etConfirmPassword.requestFocus();
                    return;
                }

                // هنا يمكنك إضافة منطق إنشاء الحساب الفعلي (مثل إرسال البيانات للخادم)
                Toast.makeText(RegisterActivity.this, "جاري إنشاء الحساب...", Toast.LENGTH_SHORT).show();

                // مثال: بعد التسجيل الناجح، يمكن الانتقال لشاشة تسجيل الدخول أو الشاشة الرئيسية
                // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                // startActivity(intent);
                // finish(); // إنهاء شاشة التسجيل
            }
        });

        // تعيين مستمع النقر لرابط تسجيل الدخول
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // العودة إلى شاشة تسجيل الدخول
                finish(); // يغلق الشاشة الحالية ويعود للشاشة السابقة (التي يفترض أن تكون تسجيل الدخول)
                // أو يمكنك استخدام Intent صريح إذا أردت التأكد:
                // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                // startActivity(intent);
                // finish();
            }
        });
    }
}
