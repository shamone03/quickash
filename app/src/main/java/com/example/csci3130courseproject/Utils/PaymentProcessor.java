package com.example.csci3130courseproject.Utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaymentProcessor extends AppCompatActivity {
    private static final String clientId = "";
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration paypalConfig;
    private UserObject provider;
    private ArrayList<UserObject> receivers = new ArrayList<>();
    private Double amountToPay = 0.0;
    private Activity activity;

    public PaymentProcessor(Activity activity) {
        this.activity = activity;

        paypalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        initializeActivityLauncher();
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

    public Boolean payReceivers() {
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

    private void initializeActivityLauncher() {
        // Initialize result launcher
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (confirmation != null) {
                        try {
                            // Getting the payment details
                            String paymentDetails = confirmation.toJSONObject().toString(4);
                            // on below line we are extracting json response and displaying it in a text view.
                            JSONObject payObj = new JSONObject(paymentDetails);
                            String payID = payObj.getJSONObject("response").getString("id");
                            String state = payObj.getJSONObject("response").getString("state");
                        } catch (JSONException e) {
                            // handling json exception on below line
                            Log.e("Error", "an extremely unlikely failure occurred: ", e);
                        }
                    }

                    //Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID){
                    Log.d("PayPal","Launcher Result Invalid");
                } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                    Log.d("PayPal", "Launcher Result Cancelled");
                }
            }
        });
    }
}
