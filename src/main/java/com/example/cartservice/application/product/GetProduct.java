package com.example.cartservice.application.product;

import com.example.cartservice.domain.Product;
import com.example.cartservice.domain.ProductId;

import java.util.List;
import java.util.Optional;

public interface GetProduct {
    Optional<Product> byId(ProductId id);
    List<Product> byIdsIn(List<ProductId> ids);
}
