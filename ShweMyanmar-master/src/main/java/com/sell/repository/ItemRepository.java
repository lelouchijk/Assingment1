package com.sell.repository;

import java.util.List;
import java.util.Optional;

import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sell.model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

    List<Item>findByItemOwner(User itemOwner);
    Optional<Item> findById(long itemId);

}