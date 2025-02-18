package com.huudan.ProductService.service;

import com.huudan.ProductService.model.ProductRequest;
import com.huudan.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
