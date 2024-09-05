package com.sell.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sell.model.Category;
import com.sell.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepo;
	
	public void saveCategory(Category c) {
		categoryRepo.save(c);
	}
	
	public void updateCategory(long id,Category c) {
		c.setCategoryId(id);
		categoryRepo.save(c);
	}
	
	public void deleteCategory(long id) {
		categoryRepo.deleteById(id);
	}
	
	public List<Category> showAll(){
		return categoryRepo.findAll();
	}
	public Optional<Category> showCategoryById(long id){
		return categoryRepo.findById(id);
	}
	public Category getCategory(long id) {
		return categoryRepo.findById(id).get();
	}
	
	public List<Category> getAllCategory(){
		return categoryRepo.findAll();
	}
}
