package com.myexample.demofullstack.model;

public enum ProductCategory {
    ELECTRONICS("Electronics"),
    GAMES("Games"),
    FASHION("Fasion"),
    BOOKS("Books"),
    SPORTS("Sports"),
    BEVERAGE("Beverage"),
    LUXURY("Luxury"),
    COSMETICS("Cosmetics");

    private String category;

    ProductCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
