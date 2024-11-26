package com.sell.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordervouncher")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;

    private int quantity;

    private int total;

    private String status;

    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;



//    @ManyToMany
//    @JoinTable(name = "itemOrder",joinColumns = @JoinColumn(name = "orderId"),inverseJoinColumns = @JoinColumn(name = "itemId"))
//    private List<Item> item;
    public Order() {
        super();
    }

    public Order(int quantity, int total, String status, LocalDate date, Shop shop, User user, Delivery delivery) {
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.date = date;
        this.shop = shop;
        this.user = user;
        this.delivery = delivery;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}
