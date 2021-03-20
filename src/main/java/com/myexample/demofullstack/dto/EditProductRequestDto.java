package com.myexample.demofullstack.dto;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class EditProductRequestDto {

    private String category;
    private MultipartFile image;
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    @NotBlank(message = "Product description cannot be blank")
    @Size(min = 10, max = 50, message = "Description must be within 10 - 100 chars")
    private String description;
    @NotNull(message = "Please set a price")
    @Min(value = 0, message = "Price at least 0")
    private int price;

    public EditProductRequestDto() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "EditProductRequestDto{" +
                "category='" + category + '\'' +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
