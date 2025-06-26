package com.example.cashlyeasy;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

        TextView totalView = findViewById(R.id.tv_total);
        totalView.setText("1,750 رس");

        View item1 = findViewById(R.id.item1);
        View item2 = findViewById(R.id.item2);
        View item3 = findViewById(R.id.item3);

        View[] items = {item1, item2, item3};

        for (int i = 0; i < categories.length; i++) {
            View item = items[i];

            if (item != null) {
                TextView tvCategory = item.findViewById(R.id.tv_category);
                TextView tvAmount = item.findViewById(R.id.tv_amount);
                ImageView imgIcon = item.findViewById(R.id.img_icon);
                ProgressBar progressBar = item.findViewById(R.id.progress_bar);

                tvCategory.setText(categories[i]);
                tvAmount.setText(currentSpending[i] + " / " + limits[i] + " رس");
                imgIcon.setImageResource(icons[i]);

                int percentage = (int) ((currentSpending[i] * 100.0f) / limits[i]);
                progressBar.setProgress(percentage);

                if (percentage < 50) {
                    progressBar.getProgressDrawable().setColorFilter(
                            getResources().getColor(android.R.color.holo_green_light),
                            PorterDuff.Mode.SRC_IN);
                } else if (percentage < 80) {
                    progressBar.getProgressDrawable().setColorFilter(
                            getResources().getColor(android.R.color.holo_orange_light),
                            PorterDuff.Mode.SRC_IN);
                } else {
                    progressBar.getProgressDrawable().setColorFilter(
                            getResources().getColor(android.R.color.holo_red_light),
                            PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }
}
