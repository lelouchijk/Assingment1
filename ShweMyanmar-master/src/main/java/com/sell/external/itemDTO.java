package com.sell.external;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class itemDTO {

    private long itemId;
    private String itemName;
    private double price;
    private int quantity;
    private MultipartFile itemImage;  // This will hold the uploaded file

    private String base64Image;

    private LocalDate date;

    private long categoryId;

    public itemDTO(){
        super();
    }

    public itemDTO(String itemName, double price, int quantity, MultipartFile itemImage, String base64Image, LocalDate date, long categoryId) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.itemImage = itemImage;
        this.base64Image = base64Image;
        this.date = date;
        this.categoryId = categoryId;
    }


    // Getters and Setters

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public MultipartFile getItemImage() {
        return itemImage;
    }
    public void setItemImage(MultipartFile itemImage) {
        this.itemImage = itemImage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
