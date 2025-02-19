package com.huudan.PaymentService.service;

import com.huudan.PaymentService.model.PaymentRequest;
import com.huudan.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
