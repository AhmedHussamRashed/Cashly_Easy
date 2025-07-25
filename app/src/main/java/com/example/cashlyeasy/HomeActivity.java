package com.example.cashlyeasy;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserName, tvBalanceAmount;
    RecyclerView rvTransactions;
    BottomNavigationView bottomNavigationView;

    LinearLayout sendButton, requestButton, payButton, receiptsButton;

    TransactionAdapter transactionAdapter;
    List<Transaction> transactionList = new ArrayList<>();

    String apiUrl = "http://192.168.1.10/api/payments"; // Laravel API endpoint
    SharedPreferences sharedPreferences;

    double currentBalance = 5250.00; // Default balance if no saved data
    DecimalFormat df = new DecimalFormat("#,###.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvUserName = findViewById(R.id.tvUserName);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        rvTransactions = findViewById(R.id.rvTransactions);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        sharedPreferences = getSharedPreferences("CashlyData", MODE_PRIVATE);

        // تحميل البيانات المحفوظة
        loadSavedData();

        ImageView ivUserAvatar = findViewById(R.id.ivUserAvatar);
        ivUserAvatar.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));

        sendButton = findViewById(R.id.send);
        requestButton = findViewById(R.id.request);
        payButton = findViewById(R.id.pay);
        receiptsButton = findViewById(R.id.receipts);

        sendButton.setOnClickListener(view -> startActivityForResult(new Intent(HomeActivity.this, Send.class), 100));
        requestButton.setOnClickListener(view -> startActivityForResult(new Intent(HomeActivity.this, Requests.class), 101));
        payButton.setOnClickListener(view -> startActivityForResult(new Intent(HomeActivity.this, PayActivity.class), 102));
        receiptsButton.setOnClickListener(view -> startActivity(new Intent(HomeActivity.this, ReceiptsActivity.class)));

        transactionAdapter = new TransactionAdapter(this, transactionList);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));
        rvTransactions.setAdapter(transactionAdapter);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) return true;
            else if (itemId == R.id.navigation_exchange)
                startActivity(new Intent(this, Exchange.class));
            else if (itemId == R.id.navigation_reports)
                startActivity(new Intent(this, ReportsActivity.class));
            else if (itemId == R.id.navigation_bills)
                startActivityForResult(new Intent(this, BillsActivity.class), 103);
            else if (itemId == R.id.navigation_account)
                startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        });

        handlePayPalRedirect(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 100 || requestCode == 101 || requestCode == 102 || requestCode == 103)
                && resultCode == RESULT_OK && data != null) {

            String description = data.getStringExtra("description");
            double amount = data.getDoubleExtra("amount", 0);
            String createdAt = data.getStringExtra("created_at");

            String type;
            if (requestCode == 101) {
                type = "income";  // طلب → دخل
                currentBalance += amount; // زيادة الرصيد
            } else {
                type = "expense"; // إرسال أو دفع → مصروف
                if (currentBalance >= amount) {
                    currentBalance -= amount; // خصم من الرصيد
                } else {
                    showErrorDialog("رصيدك غير كافٍ لإتمام العملية");
                    return;
                }
            }

            // تحديث الرصيد مع الأنيميشن
            tvBalanceAmount.setText("$ " + df.format(currentBalance));
            ObjectAnimator animator = ObjectAnimator.ofFloat(tvBalanceAmount, "alpha", 0f, 1f);
            animator.setDuration(500);
            animator.start();

            Transaction newTransaction = new Transaction(0, type, amount, description, createdAt);
            transactionList.add(0, newTransaction);
            transactionAdapter.notifyItemInserted(0);
            rvTransactions.scrollToPosition(0);

            saveData();

            // إرسال المعاملة للسيرفر
            sendTransactionToServer(type, amount, description);
        }
    }

    private void loadSavedData() {
        currentBalance = Double.longBitsToDouble(sharedPreferences.getLong("balance", Double.doubleToLongBits(5250.00)));
        tvBalanceAmount.setText("$ " + df.format(currentBalance));

        String transactionsJson = sharedPreferences.getString("transactions", "[]");
        try {
            JSONArray jsonArray = new JSONArray(transactionsJson);
            transactionList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Transaction t = new Transaction(0, obj.getString("type"), obj.getDouble("amount"), obj.getString("description"), obj.getString("created_at"));
                transactionList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("balance", Double.doubleToLongBits(currentBalance));

        JSONArray jsonArray = new JSONArray();
        for (Transaction t : transactionList) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("type", t.getType());
                obj.put("amount", t.getAmount());
                obj.put("description", t.getDescription());
                obj.put("created_at", t.getCreatedAt());
                jsonArray.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString("transactions", jsonArray.toString());
        editor.apply();
    }

    private void sendTransactionToServer(String type, double amount, String description) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject data = new JSONObject();
        try {
            data.put("type", type);
            data.put("amount", amount);
            data.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, data,
                response -> showSuccessDialog(),
                error -> showErrorDialog("فشل في إرسال البيانات للسيرفر"));
        queue.add(request);
    }

    private void showSuccessDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(true);
        dialog.findViewById(R.id.btn_ok).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void showErrorDialog(String message) {
        Dialog errorDialog = new Dialog(this);
        errorDialog.setContentView(R.layout.dialog_error);
        errorDialog.setCancelable(true);
        TextView errorMsg = errorDialog.findViewById(R.id.errorMessage);
        if (errorMsg != null) {
            errorMsg.setText(message);
        }
        errorDialog.findViewById(R.id.buttonRetry).setOnClickListener(view -> errorDialog.dismiss());
        errorDialog.show();
    }

    private void handlePayPalRedirect(Intent intent) {
        if (intent != null && Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null && "com.example.cashlyeasy".equals(data.getScheme()) && "paypalpay".equals(data.getHost())) {
                String paymentId = data.getQueryParameter("paymentId");
                String token = data.getQueryParameter("token");
                String payerId = data.getQueryParameter("PayerID");

                if (paymentId != null && payerId != null) {
                    showSuccessDialog();
                } else {
                    showErrorDialog("فشل الدفع عبر PayPal");
                }
            }
        }
    }
}
