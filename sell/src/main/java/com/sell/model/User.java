package com.sell.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

//    @OneToMany(mappedBy = "itemOwner",cascade = CascadeType.ALL)
//    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

//    @OneToMany(mappedBy = "shopOwner", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Shop> shop;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String password, List<Item> items, Role role, List<Shop> shop) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public List<Shop> getShop() {
//        return shop;
//    }
//
//    public void setShop(List<Shop> shop) {
//        this.shop = shop;
//    }



}
