package com.fuzzy.fuzzy_backend.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Product {
    // UUID is used to generate a unique id for each product
    private String id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String category;

    @NotNull
    @NotEmpty
    private String description;

    @Positive
    private double price;

    @NotNull
    @NotEmpty
    private String imageUrl;

    public Product(String name, String category, String description, double price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
