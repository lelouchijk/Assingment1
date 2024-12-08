package com.sell.repository;

import com.sell.model.Item;
import com.sell.model.Shop;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<Shop> findByShopName(String shopName);
    List<Shop> findByVerifyFalse();
    @Query("SELECT s.shopId FROM Shop s WHERE s.shopOwner.userId = :ownerId")
    List<Long> findShopIdsByOwnerId(@Param("ownerId") Long ownerId);
    List<Shop> findByStatus(String status);
    List<Shop>findShopByShopId(long id);
    Optional<Shop> findByShopOwner(User user);
    Optional<Shop> findShopByItems(Item item);
}
