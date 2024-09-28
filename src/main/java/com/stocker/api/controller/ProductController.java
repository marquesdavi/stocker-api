package com.stocker.api.controller;

import com.stocker.api.domain.dto.product.ProductRequest;
import com.stocker.api.domain.dto.product.ProductResponse;
import com.stocker.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @CacheEvict(value = "product-find-all", allEntries = true)
    public void createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products")
    })
    @Cacheable(value = "product-find-all")
    public List<ProductResponse> getAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Cacheable(value = "product-find-by-id", key = "#id")
    public ProductResponse getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a product partially")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Caching(evict = {
            @CacheEvict(value = "product-find-by-id", key = "#id"),
            @CacheEvict(value = "product-find-all", allEntries = true)
    })
    public void updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @Caching(evict = {
            @CacheEvict(value = "product-find-by-id", key = "#id"),
            @CacheEvict(value = "product-find-all", allEntries = true)
    })
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
