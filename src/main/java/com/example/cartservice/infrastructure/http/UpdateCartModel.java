package com.example.cartservice.infrastructure.http;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartModel(@NotNull @Min(0) Integer quantity) {
}
