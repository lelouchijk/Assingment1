package com.sell.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    private String roleName;

//    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<User> users;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
        super();
    }

    public Role(String roleName) {
        super();
        this.roleName = roleName;

    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }


}
