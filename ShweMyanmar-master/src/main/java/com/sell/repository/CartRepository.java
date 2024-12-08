package com.sell.repository;

import com.sell.model.Cart;
import com.sell.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_UserId(long userId);
    void deleteByUser_UserId(long userId);
    @Transactional
    void deleteByUser(User user);
    List<Cart> findByUser(User user);

}
