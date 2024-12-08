package com.sell.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shopId;

    private String shopName;
    private boolean verify = false;
    private String location;

    private String status;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User shopOwner;

    @OneToMany(mappedBy = "shop")
//    @JoinColumn(name = "categoryId")
    List<Category> categories;

    @OneToMany(mappedBy = "shop")
    List<Item> items;
//cant write this method cant create delivery account
//    @ManyToOne
//    @JoinColumn(name = "deliverId")
//    private Delivery delivery;



    public Shop() {
        super();
    }

    public Shop(String shopName, boolean verify, String location, String status, LocalDate date, User shopOwner, List<Category> categories, List<Item> items) {
        this.shopName = shopName;
        this.verify = verify;
        this.location = location;
        this.status = status;
        this.date = date;
        this.shopOwner = shopOwner;
        this.categories = categories;
        this.items = items;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(User shopOwner) {
        this.shopOwner = shopOwner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }





}
