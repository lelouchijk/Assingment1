package com.sell.service;

import com.sell.model.Role;
import com.sell.model.User;
import com.sell.repository.RoleRepository;
import com.sell.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder passEncode;

    public Boolean saveUser(User user){
        User existingUser = userRepo.findByEmail(user.getEmail());
        if(existingUser == null){
            user.setPassword(passEncode.encode(user.getPassword()));

//            Role adminRole = roleRepo.findByRoleName("Admin");
//            user.setRole(adminRole);

            Role userRole = roleRepo.findByRoleName("Customer");
            user.setRole(userRole);

            userRepo.save(user);
            return true;
        }
        return false;
    }

//    public String checkLogin(String email,String pw){
//        User user = userRepo.findByEmail(email);
//        if(user != null){
//            if(passEncode.matches(pw,user.getPassword()))
//                pw = user.getPassword();
//            user = userRepo.findByEmailAndPassword(email,pw);
//            if(user==null)
//                return "login";
//            else if (user.getRole().equals("Admin")) {
//                return "AdminPage";
//            } else if (user.getRole().equals("ShopAdmin")) {
//                return "ShopAdminPage";
//            } else if (user.getRole().equals("Delievery")) {
//                return "DelieveryPage";
//            } else
//                return "userPage";
//        }
//
//        return "home";
//    }

    public User checkLogIn(String email,String pw){
        User user = userRepo.findByEmail(email);
        if(user != null){
            if(passEncode.matches(pw,user.getPassword())){
                return user;
            }

        }
        return null;
    }

    @PostConstruct
    public void initRoles() {
        createRoleIfNotFound("Admin");
        createRoleIfNotFound("ShopAdmin");
        createRoleIfNotFound("Customer");
        createRoleIfNotFound("Delivery");
    }

    private void createRoleIfNotFound(String roleName) {
        if (roleRepo.findByRoleName(roleName) == null) {
            Role role = new Role(roleName);
            roleRepo.save(role);
        }
    }

}
