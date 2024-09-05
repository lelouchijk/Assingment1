package com.sell.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Company {

    @Id
    private long companyId;
    private String companyName;
    private boolean verify=false;
    private String location;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "company_item",
            joinColumns = @JoinColumn(name = "companyId"),
            inverseJoinColumns = @JoinColumn(name = "itemId")
    )
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;


    public Company(String companyName, boolean verify, String location) {
        super();
        this.companyName = companyName;
        this.verify = verify;
        this.location = location;
    }

    public Company() {
        super();
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
