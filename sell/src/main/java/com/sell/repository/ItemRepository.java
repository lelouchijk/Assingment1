package com.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sell.model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
//	List<Item>findByCategory(String category);
//	List<Item>findByCondition(String condition);
//	List<Item>findByPrice(double price);
}