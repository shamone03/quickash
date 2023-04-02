package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;

public class PaymentProcessor {
    private UserObject provider;
    private ArrayList<UserObject> receivers = new ArrayList<>();
    private Double amountToPay = 0.0;

    public PaymentProcessor() {

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
        }



        return true;
    }
}
