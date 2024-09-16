package com.sell.model;

import jakarta.persistence.*;

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



    @ManyToOne
    @JoinColumn(name = "userId")
    private User shopOwner;

    public Shop() {
        super();
    }

    public Shop(String shopName, boolean verify, String location,String status, User shopOwner) {
        this.shopName = shopName;
        this.verify = verify;
        this.location = location;
        this.status = status;
        this.shopOwner = shopOwner;
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

//    public List<Item> getInventory() {
//        return inventory;
//    }
//
//    public void setInventory(List<Item> inventory) {
//        this.inventory = inventory;
//    }

//    public User getOwner() {
//        return shopOwner;
//    }
//
//    public void setOwner(User shopOwner) {
//        this.shopOwner = shopOwner;
//    }
//
//    public List<Category> getInventoryCategory() {
//        return inventoryCategory;
//    }
//
//    public void setInventoryCategory(List<Category> inventoryCategory) {
//        this.inventoryCategory = inventoryCategory;
//    }
}
