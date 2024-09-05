package com.sell.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ordervouncher")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
//
//    @ManyToOne
//    @JoinColumn()
    private int quantity;
    private int price;
    private int discount;

    public Order() {
        super();
    }

    public Order(int quantity, int price, int discount) {
        super();
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
