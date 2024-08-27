package com.example.cartservice.infrastructure.persistence;

import jakarta.persistence.*;

@Entity(name = "CartItem")
@Table(name = "cart_items")
public class CartItemEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;
    @Column
    private long productId;
    @Column
    private int quantity;

    public CartItemEntity() {
    }

    private CartItemEntity(Builder builder) {
        this.id = builder.id;
        this.cart = builder.cart;
        this.productId = builder.productId;
        this.quantity = builder.quantity;
    }

    public Long getId() {
        return id;
    }

    public CartEntity getCart() {
        return cart;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private CartEntity cart;
        private long productId;
        private int quantity;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder cart(CartEntity cart) {
            this.cart = cart;
            return this;
        }

        public Builder productId(long productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public CartItemEntity build() {
            return new CartItemEntity(this);
        }
    }
}
