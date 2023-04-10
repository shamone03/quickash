package com.example.csci3130courseproject.Utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;


import com.example.csci3130courseproject.MainActivity;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaymentProcessor {
    private static final String clientId = "AU9R_njJM40MRDc860_k_wte3x0Qlav8eEbQhizkFMjNYJcWnUfCs3edGMuANzE_9imOX8P4EZwsv9FN";
    private PayPalConfiguration paypalConfig;
    private UserObject provider;
    private ArrayList<UserObject> receivers = new ArrayList<>();
    private Double amountToPay = 0.0;
    private Activity activity;

    //for using Paypal related methods
    private PayPalConfiguration payPalConfig;

    //UI Elements
    private EditText enterAmtET;
    private Button payNowBtn;
    private TextView paymentStatusTV;


    public PaymentProcessor(Activity activity) {
        this.activity = activity;

        paypalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);
    }


    private void processPayment() {
        //getting the amount from the user
        final String amount = enterAmtET.getText().toString();

        //setting the parameters for payment i.e the amount, the currency, intent of the sale
        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                amount), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);
    }

    public void setAmount(Double amountToPay) {
        if (amountToPay > 0) {
            this.amountToPay = amountToPay;
        }
    }

    public double getAmount() {
        return this.amountToPay;
    }

    public UserObject getProvider() {
        return this.provider;
    }

    public void setProvider(UserObject provider) {
        this.provider = provider;
    }

    public ArrayList<UserObject> getReceivers() {
        return this.receivers;
    }

    public void addReceiver(UserObject receiver) {
        if (receiver != null) {
            receivers.add(receiver);
        }
    }

    public Boolean payReceivers(ActivityResultLauncher<Intent> activityResultLauncher) {
        if (provider == null) {
            return false;
        } else if (receivers.isEmpty()) {
            return false;
        } else if (amountToPay == 0) {
            return false;
        } else if (activity == null) {
            return false;
        }

        final PayPalPayment payPalPayment = new PayPalPayment(
                new BigDecimal(amountToPay * receivers.size()),
                "CAD", "Pay Employees", PayPalPayment.PAYMENT_INTENT_SALE);

        final Intent intent = new Intent(activity, PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);

        activityResultLauncher.launch(intent);

        return true;
    }
}
