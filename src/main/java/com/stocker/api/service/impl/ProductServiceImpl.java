package com.stocker.api.service.impl;

import com.stocker.api.domain.dto.product.ProductRequest;
import com.stocker.api.domain.dto.product.ProductResponse;
import com.stocker.api.domain.entity.Category;
import com.stocker.api.domain.entity.Product;
import com.stocker.api.domain.mapper.ProductMapper;
import com.stocker.api.domain.repository.ProductRepository;
import com.stocker.api.exception.exceptions.ResourceNotFoundException;
import com.stocker.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createProduct(ProductRequest request) {
        Product product = productMapper.toDomain(request);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        Product product = findProductByIdOrElseThrow(id);
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(UUID id, ProductRequest request) {
        Product existingProduct = findProductByIdOrElseThrow(id);

        if (request.name() != null) existingProduct.setName(request.name());
        if (request.description() != null) existingProduct.setDescription(request.description());
        if (request.price() != null) existingProduct.setPrice(request.price());
        if (request.stockQuantity() != null) existingProduct.setStockQuantity(request.stockQuantity());
        if (request.expirationDate() != null) existingProduct.setExpirationDate(request.expirationDate());
        if (request.productDiscount() != null) existingProduct.setProductDiscount(request.productDiscount());
        if (request.category() != null) existingProduct.setCategory(Category.valueOf(request.category().toUpperCase()));

        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = findProductByIdOrElseThrow(id);
        productRepository.delete(product);
    }

    @Override
    public void saveMultiple(List<Product> products) {
        productRepository.saveAll(products);
    }

    public Product findProductByIdOrElseThrow(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
