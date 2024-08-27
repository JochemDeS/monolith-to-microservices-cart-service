package com.example.cartservice.infrastructure.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Cart")
@Table(name = "carts")
public class CartEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long userId;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items;

    public CartEntity() {
    }

    private CartEntity(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.items = builder.items;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<CartItemEntity> getItems() {
        return items;
    }

    public void setItems(List<CartItemEntity> items) {
        this.items = items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private long userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<CartItemEntity> items = new ArrayList<>();

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder items(List<CartItemEntity> items) {
            this.items = items;
            return this;
        }

        public CartEntity build() {
            return new CartEntity(this);
        }
    }
}
