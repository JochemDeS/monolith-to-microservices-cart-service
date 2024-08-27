package com.example.cartservice.domain;

public record Product(ProductId id, String title, String description, double price,
                      int stock, String brand, String category, String thumbnail, String image
) {
    private Product(Builder builder) {
        this(builder.id, builder.title, builder.description, builder.price,
             builder.stock, builder.brand, builder.category, builder.thumbnail, builder.image);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ProductId id;
        private String title;
        private String description;
        private double price;
        private int stock;
        private String brand;
        private String category;
        private String thumbnail;
        private String image;

        public Builder id(ProductId id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder stock(int stock) {
            this.stock = stock;
            return this;
        }

        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
