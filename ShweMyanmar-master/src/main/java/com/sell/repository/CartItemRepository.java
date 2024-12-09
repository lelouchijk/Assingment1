package com.sell.repository;

import com.sell.model.Cart;
import com.sell.model.CartItem;
import com.sell.model.Item;
import com.sell.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByCartAndItem(Cart cart, Item item);
   List<CartItem> findByUser(User user);

    @Transactional
    void deleteByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.cartItemId = :cartItemId")
    void deleteById(@Param("cartItemId") Long cartItemId);



}
