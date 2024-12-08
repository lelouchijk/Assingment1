package com.sell.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartItemId;

    private String itemName;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    private int quantity;

    private int price;
    private int total;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public CartItem() {
        super();
    }

//    public CartItem(String itemName, List<Item> item, int quantity, int price, int total, Cart cart, User user) {
//        this.itemName = itemName;
//        this.item = item;
//        this.quantity = quantity;
//        this.price = price;
//        this.total = total;
//        this.cart = cart;
//        this.user = user;
//    }


    public CartItem(String itemName, Item item, int quantity, int price, int total, Cart cart, User user) {
        this.itemName = itemName;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.cart = cart;
        this.user = user;
    }

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

//    public List<Item> getItem() {
//        return item;
//    }
//
//    public void setItem(List<Item> item) {
//        this.item = item;
//    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
