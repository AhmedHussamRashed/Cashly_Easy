package com.example.cashlyeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    EditText etQuestion;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        etQuestion = findViewById(R.id.etQuestion);
        btnSend = findViewById(R.id.btnSendQuestion);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = etQuestion.getText().toString().trim();

                if (question.isEmpty()) {
                    etQuestion.setError("الرجاء كتابة سؤالك");
                } else {
                    // هنا يمكنك إرسال السؤال إلى السيرفر أو حفظه
                    Toast.makeText(HelpActivity.this, "تم إرسال سؤالك: " + question, Toast.LENGTH_SHORT).show();
                    etQuestion.setText(""); // إعادة تعيين الحقل
                }
            }
        });
    }
}
