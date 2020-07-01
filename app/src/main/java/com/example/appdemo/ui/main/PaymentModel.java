package com.example.appdemo.ui.main;

import java.security.PublicKey;

public class PaymentModel {
    public String PaymentType;
    public int Amount;

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
