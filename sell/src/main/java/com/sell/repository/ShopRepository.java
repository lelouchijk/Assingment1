package com.sell.repository;

import com.sell.model.Shop;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<Shop> findByShopName(String shopName);
    List<Shop> findByVerifyFalse();
    List<Shop> findByShopOwner(User shopOwner);

    List<Shop> findByStatus(String status);

    List<Shop>findShopByShopId(long id);
}
