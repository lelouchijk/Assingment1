package com.sell.service;

import com.sell.model.Item;
import com.sell.model.User;
import com.sell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(String firstName, String lastName, User user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }


}




