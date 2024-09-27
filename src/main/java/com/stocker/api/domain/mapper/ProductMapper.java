package com.stocker.api.domain.mapper;

import com.stocker.api.domain.dto.product.ProductRequest;
import com.stocker.api.domain.dto.product.ProductResponse;
import com.stocker.api.domain.entity.Product;
import com.stocker.api.domain.entity.Category;
import com.stocker.api.domain.shared.DefaultMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper implements DefaultMapper<Product, ProductRequest, ProductResponse> {

    @Override
    public Product toDomain(ProductRequest request) {
        return Product.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .expirationDate(request.expirationDate())
                .productDiscount(request.productDiscount())
                .category(Category.valueOf(request.category().toUpperCase()))
                .build();
    }

    @Override
    public ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .expirationDate(product.getExpirationDate())
                .productDiscount(product.getProductDiscount())
                .category(product.getCategory().name())
                .build();
    }
}
