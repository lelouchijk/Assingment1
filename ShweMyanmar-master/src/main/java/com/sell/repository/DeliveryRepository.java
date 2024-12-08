package com.sell.repository;

import com.sell.model.Delivery;
import com.sell.model.Shop;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByStatus(String status);
    Optional<Delivery> findByDeliveryPerson(User user);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryPerson.userId = :userId")
    Optional<Delivery> findByUserId(@Param("userId") Long userId);

    @Query("SELECT d FROM Delivery d JOIN d.shop s WHERE s.shopId = :shopId AND d.verifyByShop = false")
    List<Delivery> findByShopsAndVerifyByShopFalse(@Param("shopId") Long shopId);

    List<Delivery> findByShop(Shop shop);

}
