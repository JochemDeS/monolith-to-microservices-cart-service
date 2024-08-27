package com.example.cartservice.infrastructure.http;

public record AddCartResponse(int id) {
    public AddCartResponse(Builder builder) {
        this(builder.id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public AddCartResponse build() {
            return new AddCartResponse(id);
        }
    }
}
