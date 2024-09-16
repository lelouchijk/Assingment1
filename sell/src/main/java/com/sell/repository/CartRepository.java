package com.sell.repository;

import com.sell.model.Cart;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
//    List<User>findByUserId(long userId);
    List<Cart> findByUser_UserId(long userId);

    void deleteByUser_UserId(long userId);
}
