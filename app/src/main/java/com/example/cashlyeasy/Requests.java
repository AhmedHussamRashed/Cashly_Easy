package com.example.cashlyeasy;

import androidx.appcompat.app.AppCompatActivity;
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
        // تأكد من أن اسم الواجهة يطابق اسم ملف XML الخاص بك
        setContentView(R.layout.activity_requests);

        // تهيئة العروض
        editTextTo = findViewById(R.id.editTextTo);
        editTextAmount = findViewById(R.id.editTextAmount);
        editTextNote = findViewById(R.id.editTextNote); // حقل اختياري
        buttonSendRequest = findViewById(R.id.buttonSendRequest);

        // تعيين مستمع النقر للزر
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
        // String note = editTextNote.getText().toString().trim(); // احصل على الملاحظة إذا لزم الأمر

        // تحقق أساسي
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

        // اختياري: تحقق أكثر قوة من المبلغ (مثل التحقق مما إذا كان رقمًا صالحًا)
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

        // --- مكان للمنطق الفعلي لإرسال الطلب ---
        // هنا ستقوم عادةً بإجراء استدعاء API إلى الواجهة الخلفية الخاصة بك
        // لمعالجة طلب الأموال.
        // في هذا المثال، نعرض فقط رسالة Toast للتأكيد.

        Toast.makeText(this, "Request sent to " + recipient, Toast.LENGTH_LONG).show();

        // اختياري: مسح الحقول أو الانتقال بعيدًا بعد الطلب الناجح
        // editTextTo.setText("");
        // editTextAmount.setText("");
        // editTextNote.setText("");
        // finish(); // إغلاق النشاط
    }
}
