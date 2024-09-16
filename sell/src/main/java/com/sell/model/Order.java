package com.sell.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ordervouncher")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    private String itemName;
    private int quantity;
    private int price;

    private int total;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @ManyToMany
    @JoinTable(name = "itemOrder",joinColumns = @JoinColumn(name = "orderId"),inverseJoinColumns = @JoinColumn(name = "itemId"))
    private List<Item> item;
    public Order() {
        super();
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Order(String itemName, int quantity, int price, int total, Shop shop, List<Item> item, User user) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.shop = shop;
        this.item = item;
        this.user = user;
    }

    public Order(int quantity, int price, Shop shop, List<Item> item, User user) {
        this.quantity = quantity;
        this.price = price;
        this.shop = shop;
        this.item = item;
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
