package com.sell.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long deliveryId;

    private String deliveryName;
    private String status;
    private String phone;

    private Boolean verifyByShop = false;

    private Boolean Approval = false;

    private LocalDate date;

    @OneToMany(mappedBy = "delivery")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User deliveryPerson;

//    teachel logic with shop
//    @OneToMany
//    private List<Shop> shops;


//    cant create like this because of error at creating delivery
//    @OneToMany(mappedBy = "delivery")
//    private List<User> user;


    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;




    public Delivery() {
        super();
    }

    public Delivery(String deliveryName, String status, String phone, Boolean verifyByShop, Boolean approval, LocalDate date, List<Order> orders, User deliveryPerson, Shop shop) {
        this.deliveryName = deliveryName;
        this.status = status;
        this.phone = phone;
        this.verifyByShop = verifyByShop;
        Approval = approval;
        this.date = date;
        this.orders = orders;
        this.deliveryPerson = deliveryPerson;
        this.shop = shop;
    }

    public long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getVerifyByShop() {
        return verifyByShop;
    }

    public void setVerifyByShop(Boolean verifyByShop) {
        this.verifyByShop = verifyByShop;
    }

    public Boolean getApproval() {
        return Approval;
    }

    public void setApproval(Boolean approval) {
        Approval = approval;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public User getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(User deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}

