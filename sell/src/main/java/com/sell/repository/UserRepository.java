package com.sell.repository;

import com.sell.model.Cart;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String em,String pass);


}
