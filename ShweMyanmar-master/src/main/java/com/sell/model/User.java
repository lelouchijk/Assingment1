package com.sell.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
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

    private String profileImage;

    private LocalDate date;

//    @OneToMany(mappedBy = "itemOwner",cascade = CascadeType.ALL)
//    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "shopOwner", fetch = FetchType.EAGER)
    private List<Shop> shops;



//    cant create this because i got error at creating delivery account
//    @OneToMany(mappedBy = "user")
//    private List<Delivery> deliveries;

    public User() {
        super();
    }


    public User(String firstName, String lastName, String email, String password, String profileImage, LocalDate date, Role role, List<Shop> shops) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.date = date;
        this.role = role;
        this.shops = shops;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    //    public List<Delivery> getDeliveries() {
//        return deliveries;
//    }
//
//    public void setDeliveries(List<Delivery> deliveries) {
//        this.deliveries = deliveries;
//    }




    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }


}
