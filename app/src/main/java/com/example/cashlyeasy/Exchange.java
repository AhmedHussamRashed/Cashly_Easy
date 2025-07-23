package com.example.cashlyeasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class Exchange extends AppCompatActivity {

    private EditText editAmount;
    private Spinner fromCurrencySpinner, toCurrencySpinner;
    private TextView exchangeRateValue, receiveAmount;
    private Button exchangeButton;
    private BottomNavigationView bottomNavigationView;

    private final HashMap<String, Double> currencyRates = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        editAmount = findViewById(R.id.editAmount);
        fromCurrencySpinner = findViewById(R.id.fromCurrencySpinner);
        toCurrencySpinner = findViewById(R.id.toCurrencySpinner);
        exchangeRateValue = findViewById(R.id.exchangeRateValue);
        receiveAmount = findViewById(R.id.receiveAmount);
        exchangeButton = findViewById(R.id.exchangeButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewExchange);

        currencyRates.put("USD", 1.0);
        currencyRates.put("EUR", 0.92);
        currencyRates.put("GBP", 0.78);
        currencyRates.put("JPY", 155.70);
        currencyRates.put("SAR", 3.75);
        currencyRates.put("AED", 3.67);
        currencyRates.put("EGP", 48.00);
        currencyRates.put("TRY", 32.50);

        String[] currencies = currencyRates.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        exchangeButton.setOnClickListener(v -> {
            String amountText = editAmount.getText().toString().trim();
            if (amountText.isEmpty()) {
                editAmount.setError("Please enter an amount");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
                String toCurrency = toCurrencySpinner.getSelectedItem().toString();

                double fromRate = currencyRates.get(fromCurrency);
                double toRate = currencyRates.get(toCurrency);

                double rate = toRate / fromRate;
                double result = amount * rate;

                exchangeRateValue.setText("    1 " + fromCurrency + " = " + String.format("%.4f", rate) + " " + toCurrency);
                receiveAmount.setText("   " + toCurrency + " " + String.format("%.2f", result));

                Toast.makeText(Exchange.this, "Converted successfully!", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                editAmount.setError("Invalid number");
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.navigation_exchange);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) startActivity(new Intent(this, HomeActivity.class));
            else if (itemId == R.id.navigation_reports) startActivity(new Intent(this, ReportsActivity.class));
            else if (itemId == R.id.navigation_bills) startActivity(new Intent(this, BillsActivity.class));
            else if (itemId == R.id.navigation_account) startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        });
    }
}
