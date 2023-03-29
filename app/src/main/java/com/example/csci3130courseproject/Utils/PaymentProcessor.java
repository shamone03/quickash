package com.example.csci3130courseproject.Utils;

import java.util.ArrayList;

public class PaymentProcessor {
    private UserObject provider;
    private ArrayList<UserObject> receivers = new ArrayList<>();
    private Double amountToPay;

    public PaymentProcessor() {

    }

    public void setAmount(Double amountToPay) {

    }

    public double getAmount() {
        return this.amountToPay;
    }

    public UserObject getProvider() {
        return this.provider;
    }

    public void setProvider(UserObject provider) {

    }

    public ArrayList<UserObject> getReceivers() {
        return this.receivers;
    }

    public void addReceiver(UserObject receiver) {

    }

    public Boolean payReceivers() {
        return false;
    }
}
