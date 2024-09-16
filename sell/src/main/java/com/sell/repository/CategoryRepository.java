package com.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sell.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category>findByCategoryName(String categoryName);
	List<Category>findByCategoryId(long id);


	
}
