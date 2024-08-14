package com.stocker.api.service;

import com.stocker.api.domain.dto.product.ProductRequest;
import com.stocker.api.domain.dto.product.ProductResponse;
import com.stocker.api.domain.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    void createProduct(Product product);
    ProductResponse getProductById(UUID id);
    List<ProductResponse> getProducts();
    void updateProduct(UUID id, ProductRequest product);
    void deleteProduct(UUID id);
}
