package com.example.cartservice.infrastructure.http;

import com.example.cartservice.application.cart.*;
import com.example.cartservice.domain.CartId;
import com.example.cartservice.domain.ProductId;
import com.example.cartservice.domain.UserId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "All endpoints for the cart")
public class CartController {
    private final GetCartUseCase getCartUseCase;
    private final CreateCartForUserUseCase createCartForUserUseCase;
    private final UpdateCartUseCase updateCartUseCase;
    private final ResetCartUseCase resetCartUseCase;

    public CartController(GetCartUseCase getCartUseCase,
                          CreateCartForUserUseCase createCartForUserUseCase,
                          UpdateCartUseCase updateCartUseCase,
                          ResetCartUseCase resetCartUseCase) {
        this.getCartUseCase = getCartUseCase;
        this.createCartForUserUseCase = createCartForUserUseCase;
        this.updateCartUseCase = updateCartUseCase;
        this.resetCartUseCase = resetCartUseCase;
    }

    @GetMapping
    public CartReadModel getCart(@AuthenticationPrincipal UserId userId) {
        final var cart = getCartUseCase.handle(userId);

        return CartReadModel.builder()
                .id((int) cart.getId().value())
                .items(cart.getItems().stream()
                        .map(item -> CartItemReadModel.builder()
                                .productId((int) item.getProduct().id().value())
                                .name(item.getProduct().title())
                                .brand(item.getProduct().brand())
                                .category(item.getProduct().category())
                                .thumbnail(item.getProduct().thumbnail())
                                .price(item.getProduct().price())
                                .quantity(item.getQuantity())
                                .build())
                        .toList())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    @PostMapping("/create")
    public AddCartResponse create(@Valid @RequestBody AddCartModel request) {
        final var userId = UserId.builder()
                .value((long) request.userId())
                .build();

        final var cartId = createCartForUserUseCase.handle(userId);

        return AddCartResponse.builder()
                .id((int) cartId.value())
                .build();
    }

    @PostMapping("/{productId}")
    public void update(@AuthenticationPrincipal UserId userId,
                       @PathVariable @NotNull @Positive int productId,
                       @Valid @RequestBody UpdateCartModel request) {
        final var addCartItem = UpdateCart.builder()
                .userId(userId)
                .productId(ProductId.builder()
                        .value(productId)
                        .build())
                .quantity(request.quantity())
                .build();

        updateCartUseCase.handle(addCartItem);
    }

    @PostMapping("/reset")
    public void reset(@AuthenticationPrincipal UserId userId) {
        resetCartUseCase.handle(userId);
    }
}
