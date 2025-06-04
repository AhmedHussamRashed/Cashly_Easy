package com.example.cashlyeasy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;
import android.widget.PopupMenu;

public class SecuritySettingsActivity extends AppCompatActivity {

    private Switch twoFactorSwitch;
    private Button setup2faButton;
    private LinearLayout devicesContainer;
    private LinearLayout switchContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);

        // تهيئة العناصر
        initializeViews();

        // إعداد المصادقة الثنائية
        setupTwoFactorAuthentication();

        // إعداد قائمة الأجهزة
        setupDevicesList();

        // إعداد زر العودة
        setupBackButton();
    }

    private void initializeViews() {
        twoFactorSwitch = findViewById(R.id.twoFactorSwitch);
        setup2faButton = findViewById(R.id.setup2faButton);
        devicesContainer = findViewById(R.id.devicesContainer);
        switchContainer = findViewById(R.id.switchContainer);
    }

    private void setupTwoFactorAuthentication() {
        // تفعيل/تعطيل المصادقة الثنائية
        twoFactorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setup2faButton.setVisibility(View.VISIBLE);
                } else {
                    setup2faButton.setVisibility(View.GONE);
                }
            }
        });

        // إعداد زر المصادقة الثنائية
        setup2faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: تنفيذ عملية إعداد المصادقة الثنائية
                startTwoFactorSetup();
            }
        });

        // جعل كامل السويتش قابل للضغط
        switchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoFactorSwitch.setChecked(!twoFactorSwitch.isChecked());
            }
        });
    }

    private void startTwoFactorSetup() {
        // هنا يتم تنفيذ عملية إعداد المصادقة الثنائية
        // يمكنك فتح نشاط جديد أو عرض حوار
    }

    private void setupDevicesList() {
        // بيانات وهمية للأجهزة (في التطبيق الحقيقي سيتم جلبها من الخادم)
        String[] deviceNames = {"هاتف Samsung Galaxy S21", "حاسوب Dell Inspiron"};
        String[] deviceInfos = {"نظام Android - آخر نشاط اليوم 10:30 ص", "نظام Windows - آخر نشاط أمس 3:45 م"};
        int[] deviceIcons = {R.drawable.ic_phone, R.drawable.ic_laptop};

        // مسح أي أجهزة موجودة (في حال إعادة التحميل)
        devicesContainer.removeAllViews();

        for (int i = 0; i < deviceNames.length; i++) {
            // إنشاء عرض الجهاز
            View deviceView = getLayoutInflater().inflate(R.layout.item_device, null);

            TextView deviceName = deviceView.findViewById(R.id.deviceName);
            TextView deviceInfo = deviceView.findViewById(R.id.deviceInfo);
            ImageView deviceIcon = deviceView.findViewById(R.id.deviceIcon);
            ImageView moreOptions = deviceView.findViewById(R.id.moreOptions);

            deviceName.setText(deviceNames[i]);
            deviceInfo.setText(deviceInfos[i]);
            deviceIcon.setImageResource(deviceIcons[i]);

            final int position = i;
            moreOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeviceOptionsMenu(deviceNames[position], position);
                }
            });

            devicesContainer.addView(deviceView);
        }
    }

    private void showDeviceOptionsMenu(String deviceName, int position) {
        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.moreOptions));
        popupMenu.getMenu().add("تسجيل خروج من " + deviceName);
        popupMenu.getMenu().add("إزالة الجهاز");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().contains("تسجيل خروج")) {
                    // تنفيذ تسجيل الخروج من الجهاز
                    logoutFromDevice(position);
                } else {
                    // تنفيذ إزالة الجهاز
                    removeDevice(position);
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void logoutFromDevice(int position) {
        // TODO: تنفيذ تسجيل الخروج من الجهاز
        Toast.makeText(this, "تم تسجيل الخروج من الجهاز", Toast.LENGTH_SHORT).show();
    }

    private void removeDevice(int position) {
        // TODO: تنفيذ إزالة الجهاز
        Toast.makeText(this, "تم إزالة الجهاز", Toast.LENGTH_SHORT).show();
    }

    private void setupBackButton() {
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // يمكنك إضافة أي تنفيذ إضافي هنا قبل العودة
        finish();
    }
}