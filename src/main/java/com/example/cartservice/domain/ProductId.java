package com.example.cartservice.domain;

public record ProductId(long value) {
    private ProductId(Builder builder) {
        this(builder.value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long value;

        public Builder value(long value) {
            this.value = value;
            return this;
        }

        public ProductId build() {
            return new ProductId(this);
        }
    }
}
