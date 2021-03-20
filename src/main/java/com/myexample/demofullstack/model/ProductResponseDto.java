package com.myexample.demofullstack.model;


import java.util.List;

public class ProductResponseDto {

    private Long id;
    private String identifier;
    private String name;
    private String description;
    private int price;
    private String imageUrl;
    private String category;
    private AppUser owner;
    private boolean ownerActive;
    private List<AppUser> buyer;

    public ProductResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public boolean isOwnerActive() {
        return ownerActive;
    }

    public void setOwnerActive(boolean ownerActive) {
        this.ownerActive = ownerActive;
    }

    public List<AppUser> getBuyer() {
        return buyer;
    }

    public void setBuyer(List<AppUser> buyer) {
        this.buyer = buyer;
    }
}
