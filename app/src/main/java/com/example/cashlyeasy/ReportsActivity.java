package com.example.cashlyeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// لاستخدام MaterialButton، تأكد من إضافة الاعتمادية: implementation 'com.google.android.material:material:1.x.x'
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    MaterialButton btnThisWeek, btnThisMonth, btnThisYear;
    TextView tvTotalIncome, tvTotalExpenses;
    RecyclerView rvReportCategories;
    ReportCategoryAdapter categoryAdapter;
    List<ReportCategory> categoryList;
    BottomNavigationView bottomNavigationView;  // أضفت هذا لتشغيل Navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // ربط العناصر
        btnThisWeek = findViewById(R.id.btnThisWeek);
        btnThisMonth = findViewById(R.id.btnThisMonth);
        btnThisYear = findViewById(R.id.btnThisYear);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpenses = findViewById(R.id.tvTotalExpenses);
        rvReportCategories = findViewById(R.id.rvReportCategories);
        bottomNavigationView = findViewById(R.id.bottomNavigationView); // تأكد من وجوده في xml

        rvReportCategories.setLayoutManager(new LinearLayoutManager(this));
        rvReportCategories.setNestedScrollingEnabled(false); // مهم داخل NestedScrollView

        // إعداد مستمعي الأزرار
        setupTimePeriodButtons();

        // تحميل بيانات التقارير (مثال)
        loadReportData("week"); // تحميل بيانات الأسبوع افتراضياً

        // إعداد الـ Adapter
        categoryAdapter = new ReportCategoryAdapter(this, categoryList);
        rvReportCategories.setAdapter(categoryAdapter);

        // إعداد Bottom Navigation
        setupBottomNavigation();

        // --- إعداد الرسم البياني (مثال باستخدام عنصر نائب) ---
        Toast.makeText(this, "Bar chart placeholder shown. Integrate a library like MPAndroidChart.", Toast.LENGTH_LONG).show();
    }

    private void setupTimePeriodButtons() {
        View.OnClickListener listener = v -> {
            // إعادة تعيين جميع الأزرار إلى الحالة غير النشطة
            setButtonState(btnThisWeek, false);
            setButtonState(btnThisMonth, false);
            setButtonState(btnThisYear, false);

            // تعيين الزر المضغوط إلى الحالة النشطة وتحميل البيانات
            if (v.getId() == R.id.btnThisWeek) {
                setButtonState(btnThisWeek, true);
                loadReportData("week");
            } else if (v.getId() == R.id.btnThisMonth) {
                setButtonState(btnThisMonth, true);
                loadReportData("month");
            } else if (v.getId() == R.id.btnThisYear) {
                setButtonState(btnThisYear, true);
                loadReportData("year");
            }
            // تحديث الـ Adapter والرسم البياني
            categoryAdapter.updateData(categoryList);
            // مثال: updateBarChart(categoryList);
        };

        btnThisWeek.setOnClickListener(listener);
        btnThisMonth.setOnClickListener(listener);
        btnThisYear.setOnClickListener(listener);

        // تعيين الحالة الأولية (الأسبوع نشط)
        setButtonState(btnThisWeek, true);
    }

    // دالة لتغيير مظهر الزر (نشط/غير نشط)
    private void setButtonState(MaterialButton button, boolean isActive) {
        if (isActive) {
            button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.button_blue)));
            button.setTextColor(ContextCompat.getColor(this, R.color.button_blue));
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setIcon(null);
        } else {
            button.setStrokeWidth(0);
            button.setTextColor(ContextCompat.getColor(this, R.color.grey_text));
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setIcon(null);
        }
    }

    // دالة مثال لتحميل بيانات التقارير بناءً على الفترة
    private void loadReportData(String period) {
        categoryList = new ArrayList<>();
        String income = "$0";
        String expenses = "$0";

        if (period.equals("week")) {
            income = "$500";
            expenses = "$350";
            categoryList.add(new ReportCategory(R.drawable.ic_category_groceries, R.drawable.circle_background_green, "Groceries", "Apr 20", "$150", true));
            categoryList.add(new ReportCategory(R.drawable.ic_category_transport, R.drawable.circle_background_blue, "Transport", "Apr 18", "$40", true));
        } else if (period.equals("month")) {
            income = "$2,500";
            expenses = "$1,900";
            categoryList.add(new ReportCategory(R.drawable.ic_category_groceries, R.drawable.circle_background_green, "Groceries", "Apr 20", "$250", true));
            categoryList.add(new ReportCategory(R.drawable.ic_category_salary, R.drawable.circle_background_blue, "Salary", "Apr 20", "$2,500", false));
            categoryList.add(new ReportCategory(R.drawable.ic_category_transport, R.drawable.circle_background_blue, "Transport", "Apr 18", "$40", true));
            categoryList.add(new ReportCategory(R.drawable.ic_category_shopping, R.drawable.circle_background_purple, "Shopping", "Apr 18", "$120", true));
        } else {
            income = "$30,000";
            expenses = "$22,000";
            categoryList.add(new ReportCategory(R.drawable.ic_category_salary, R.drawable.circle_background_blue, "Salary", "Year", "$30,000", false));
            categoryList.add(new ReportCategory(R.drawable.ic_category_rent, R.drawable.circle_background_red, "Rent", "Year", "$12,000", true));
            categoryList.add(new ReportCategory(R.drawable.ic_category_groceries, R.drawable.circle_background_green, "Groceries", "Year", "$5,000", true));
        }

        tvTotalIncome.setText(income);
        tvTotalExpenses.setText(expenses);
    }

    // إعداد BottomNavigationView
    private void setupBottomNavigation() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_reports);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(ReportsActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_exchange) {
                startActivity(new Intent(ReportsActivity.this, Exchange.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_reports) {
                return true; // نحن هنا الآن
            } else if (itemId == R.id.navigation_bills) {
                startActivity(new Intent(ReportsActivity.this, Bill.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (itemId == R.id.navigation_account) {
                startActivity(new Intent(ReportsActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }

    // --- Inner Class: ReportCategory Model ---
    public static class ReportCategory {
        int iconResId;
        int backgroundResId;
        String categoryName;
        String date;
        String amount;
        boolean isExpense;

        public ReportCategory(int iconResId, int backgroundResId, String categoryName, String date, String amount, boolean isExpense) {
            this.iconResId = iconResId;
            this.backgroundResId = backgroundResId;
            this.categoryName = categoryName;
            this.date = date;
            this.amount = amount;
            this.isExpense = isExpense;
        }

        public int getIconResId() { return iconResId; }
        public int getBackgroundResId() { return backgroundResId; }
        public String getCategoryName() { return categoryName; }
        public String getDate() { return date; }
        public String getAmount() { return amount; }
        public boolean isExpense() { return isExpense; }
    }

    // --- Inner Class: ReportCategory Adapter ---
    public class ReportCategoryAdapter extends RecyclerView.Adapter<ReportCategoryAdapter.CategoryViewHolder> {

        private Context context;
        private List<ReportCategory> categoryList;

        public ReportCategoryAdapter(Context context, List<ReportCategory> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_item_report_category, parent, false);
            return new CategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            ReportCategory category = categoryList.get(position);

            holder.ivCategoryIcon.setImageResource(category.getIconResId());
            holder.flCategoryIconBg.setBackgroundResource(category.getBackgroundResId());
            holder.tvCategoryName.setText(category.getCategoryName());
            holder.tvCategoryDate.setText(category.getDate());
            holder.tvCategoryAmount.setText(category.getAmount());

            int amountColor = ContextCompat.getColor(context, category.isExpense() ? R.color.transaction_red : R.color.transaction_green);
            holder.tvCategoryAmount.setTextColor(amountColor);

            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(context, "Clicked on: " + category.getCategoryName(), Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        public void updateData(List<ReportCategory> newCategoryList) {
            this.categoryList.clear();
            this.categoryList.addAll(newCategoryList);
            notifyDataSetChanged();
        }

        public class CategoryViewHolder extends RecyclerView.ViewHolder {
            FrameLayout flCategoryIconBg;
            ImageView ivCategoryIcon;
            TextView tvCategoryName, tvCategoryDate, tvCategoryAmount;

            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                flCategoryIconBg = itemView.findViewById(R.id.flCategoryIconBg);
                ivCategoryIcon = itemView.findViewById(R.id.ivCategoryIcon);
                tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
                tvCategoryDate = itemView.findViewById(R.id.tvCategoryDate);
                tvCategoryAmount = itemView.findViewById(R.id.tvCategoryAmount);
            }
        }
    }
}
