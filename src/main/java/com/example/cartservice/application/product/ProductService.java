package com.example.cartservice.application.product;

import com.example.cartservice.domain.Product;
import com.example.cartservice.domain.ProductId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements GetProduct {
    @Value("${microservices.product.url}")
    private String productServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Product> byId(ProductId id) {
        final var response = restTemplate.getForEntity(
                String.format("%s/products?ids=%d", productServiceUrl, id.value()),
                ProductResponse[].class);

        return Optional.ofNullable(response.getBody())
                .flatMap(products -> {
                    if (products.length > 0) {
                        return Optional.of(products[0]);
                    }
                    return Optional.empty();
                })
                .map(this::toProduct);
    }

    @Override
    public List<Product> byIdsIn(List<ProductId> ids) {
        final var idsQueryParam = ids.stream()
                .map(id -> String.valueOf(id.value()))
                .collect(Collectors.joining(","));

        final var response = restTemplate.getForEntity(
                String.format("%s/products?ids=%s", productServiceUrl, idsQueryParam),
                ProductResponse[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(this::toProduct)
                .collect(Collectors.toList());
    }

    private Product toProduct(ProductResponse productResponse) {
        return Product.builder()
                .id(ProductId.builder()
                        .value(productResponse.id())
                        .build())
                .title(productResponse.title())
                .description(productResponse.description())
                .price(productResponse.price())
                .brand(productResponse.brand())
                .category(productResponse.category())
                .stock(productResponse.stock())
                .image(productResponse.image())
                .thumbnail(productResponse.thumbnail())
                .build();
    }
}
