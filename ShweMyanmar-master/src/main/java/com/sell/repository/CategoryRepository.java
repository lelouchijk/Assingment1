package com.sell.repository;

import java.util.List;
import java.util.Optional;

import com.sell.model.Shop;
import com.sell.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sell.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category>findByCategoryName(String categoryName);
	Optional<Category> findByCategoryId(long id);

	List<Category> findByShop(Shop shop);

}
