package com.myexample.demofullstack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String identifier;
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    @NotBlank(message = "Product description cannot be blank")
    @Size(min = 10, max = 100, message = "Description must be within 10 - 100 chars")
    private String description;
    @NotNull(message = "Please set a price")
    @Min(value = 0, message = "Price at least 0")
    private int price;
    private String imageUrl;
    private String category;
    @Transient
    private String ownerName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private AppUser owner;
    private boolean ownerActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_buyer",
            joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<AppUser> buyer;

    public Product() {
    }

    public Product(@NotBlank(message = "Product name cannot be blank") String name,
                   @NotBlank(message = "Product description cannot be blank") @Size(min = 10, max = 50, message = "Description must be within 10 - 100 chars") String description,
                   @NotNull(message = "Please set a price") @Min(value = 0, message = "Price at least 0") int price,
                   String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    // TODO pre persist owner


    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    public List<AppUser> getBuyer() {
        return buyer;
    }

    public void setBuyer(List<AppUser> buyer) {
        this.buyer = buyer;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isOwnerActive() {
        return ownerActive;
    }

    public void setOwnerActive(boolean ownerActive) {
        this.ownerActive = ownerActive;
    }

    public void addBuyer(AppUser user) {
        buyer.add(user);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", owner=" + owner +
                ", ownerActive=" + ownerActive +
                ", buyer=" + buyer +
                '}';
    }
}
