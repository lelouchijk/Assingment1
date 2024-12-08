package com.sell.repository;

import com.sell.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order>findOrderByOrderId(long id);
    List<Item>findItemByOrderId(long id);
    @Query("select o from Order o join o.shop s where s.shopId = :shopId and o.status ='Pending'")
    List<Order> findByShopAndStatus(@Param("shopId") Long shopId);

    List<Order> findOrdersByDeliveryAndStatus(Delivery delivery,String status);

    List<Order> findOrdersByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.shop.shopOwner = :user")
    List<Order> findOrdersByUser(@Param("user") User user);
}

