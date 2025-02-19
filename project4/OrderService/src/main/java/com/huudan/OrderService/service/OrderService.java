package com.huudan.OrderService.service;

import com.huudan.OrderService.model.OrderRequest;
import com.huudan.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
