package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    ImageView ivBackArrowEditProfile;
    EditText etEditName, etEditEmail, etEditPhone;
    Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // ربط عناصر الواجهة
        ivBackArrowEditProfile = findViewById(R.id.ivBackArrowEditProfile);
        etEditName = findViewById(R.id.etEditName);
        etEditEmail = findViewById(R.id.etEditEmail);
        etEditPhone = findViewById(R.id.etEditPhone);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // --- تحميل بيانات المستخدم الحالية (مثال) ---
        loadCurrentUserData();

        // --- تعيين مستمعي النقر ---

        // زر الرجوع
        ivBackArrowEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // العودة إلى الشاشة السابقة (الملف الشخصي)
            }
        });

        // زر حفظ التغييرات
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges();
            }
        });
    }

    // دالة مثال لتحميل بيانات المستخدم الحالية في الحقول
    private void loadCurrentUserData() {
        // هنا يمكنك جلب بيانات المستخدم الحالية من مصدر البيانات
        String currentName = "Jennifer Lee"; // مثال
        String currentEmail = "jennifer.lee@example.com"; // مثال
        String currentPhone = "+1 234 567 3901"; // مثال

        etEditName.setText(currentName);
        etEditEmail.setText(currentEmail);
        etEditPhone.setText(currentPhone);
    }

    // دالة لحفظ التغييرات
    private void saveProfileChanges() {
        String name = etEditName.getText().toString().trim();
        String email = etEditEmail.getText().toString().trim();
        String phone = etEditPhone.getText().toString().trim();

        // التحقق من صحة المدخلات
        if (TextUtils.isEmpty(name)) {
            etEditName.setError("Name cannot be empty");
            etEditName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEditEmail.setError("Email cannot be empty");
            etEditEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEditEmail.setError("Please enter a valid email address");
            etEditEmail.requestFocus();
            return;
        }

        // يمكنك إضافة تحقق لرقم الهاتف إذا لزم الأمر
        if (TextUtils.isEmpty(phone)) {
            etEditPhone.setError("Phone number cannot be empty");
            etEditPhone.requestFocus();
            return;
        }

        // هنا يمكنك إضافة منطق حفظ البيانات المحدثة (إلى قاعدة بيانات، SharedPreferences، أو API)
        Toast.makeText(this, "Saving changes...", Toast.LENGTH_SHORT).show();

        // بعد الحفظ بنجاح، يمكنك إغلاق الشاشة
        // finish();
    }
}
