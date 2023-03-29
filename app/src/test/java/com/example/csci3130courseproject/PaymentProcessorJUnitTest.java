package com.example.csci3130courseproject;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.csci3130courseproject.Utils.PaymentProcessor;
import com.example.csci3130courseproject.Utils.UserObject;


public class PaymentProcessorJUnitTest {
    private UserObject provider = new UserObject();
    private UserObject receiver = new UserObject();

    @Test
    public void invalidAmount() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setAmount(0.0);
        assertNull(processor.getAmount());

        processor.setAmount(-500.0);
        assertNull(processor.getAmount());
    }

    @Test
    public void validAmount() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setAmount(500.00);
        assertEquals(500.00, processor.getAmount());
    }

    @Test
    public void validPayment() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setAmount(500.00);
        processor.setProvider(provider);
        processor.addReceiver(receiver);

        assertTrue(processor.payReceivers());
    }

    @Test
    public void payNoProvider() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setAmount(500.00);
        processor.addReceiver(receiver);

        assertFalse(processor.payReceivers());
    }

    @Test
    public void payNoReceivers() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setAmount(500.00);
        processor.setProvider(provider);

        assertFalse(processor.payReceivers());
    }

    @Test
    public void payNoAmount() {
        PaymentProcessor processor = new PaymentProcessor();

        processor.setProvider(provider);
        processor.addReceiver(receiver);

        assertFalse(processor.payReceivers());
    }
}
