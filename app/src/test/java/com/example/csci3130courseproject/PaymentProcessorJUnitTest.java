package com.example.csci3130courseproject;

import org.junit.Test;
import static org.junit.Assert.*;

import android.app.Activity;

import com.example.csci3130courseproject.Utils.PaymentProcessor;
import com.example.csci3130courseproject.Utils.UserObject;


public class PaymentProcessorJUnitTest {
    private UserObject provider = new UserObject();
    private UserObject receiver = new UserObject();

    @Test
    public void invalidAmount() {
        PaymentProcessor processor = new PaymentProcessor(null);

        processor.setAmount(0.0);
        assertEquals(0, (int)processor.getAmount());

        processor.setAmount(-500.0);
        assertEquals(0, (int)processor.getAmount());
    }

    @Test
    public void validAmount() {
        PaymentProcessor processor = new PaymentProcessor(null);

        processor.setAmount(500.00);
        assertEquals(500, (int)processor.getAmount());
    }

    @Test
    public void payNoProvider() {
        PaymentProcessor processor = new PaymentProcessor(null);

        processor.setAmount(500.00);
        processor.addReceiver(receiver);

        assertFalse(processor.payReceivers());
    }

    @Test
    public void payNoReceivers() {
        PaymentProcessor processor = new PaymentProcessor(null);

        processor.setAmount(500.00);
        processor.setProvider(provider);

        assertFalse(processor.payReceivers());
    }

    @Test
    public void payNoAmount() {
        PaymentProcessor processor = new PaymentProcessor(null);

        processor.setProvider(provider);
        processor.addReceiver(receiver);

        assertFalse(processor.payReceivers());
    }
}
