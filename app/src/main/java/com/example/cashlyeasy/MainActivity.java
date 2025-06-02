package com.example.cashlyeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] categories = {"الطعام", "التسوق", "الترفيه"};
    int[] currentSpending = {1200, 400, 350};
    int[] limits = {1500, 500, 400};
    int[] icons = {R.drawable.ic_food, R.drawable.ic_shop, R.drawable.ic_fun};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = (LinearLayout) ((LinearLayout) findViewById(android.R.id.content)).getChildAt(0);

        // تعيين مجموع الإنفاق
        TextView totalView = findViewById(R.id.tv_total);
        totalView.setText("1,750 رس");

        // إدخال البيانات إلى العناصر المكررة
        for (int i = 0; i < categories.length; i++) {
            View item = container.getChildAt(i + 1); // +1 لتخطي ملخص الإنفاق
            ((TextView) item.findViewById(R.id.tv_category)).setText(categories[i]);
            ((TextView) item.findViewById(R.id.tv_amount)).setText(currentSpending[i] + " / " + limits[i] + " رس");
            ((ImageView) item.findViewById(R.id.img_icon)).setImageResource(icons[i]);

            ProgressBar progressBar = item.findViewById(R.id.progress_bar);
            int percentage = (int) ((currentSpending[i] * 1.0 / limits[i]) * 100);
            progressBar.setProgress(percentage);
        }
    }
}
