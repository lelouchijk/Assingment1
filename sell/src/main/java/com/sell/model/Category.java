package com.sell.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long categoryId;
	private String categoryName;
	
//	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Item> items = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;
	
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
	
	public Category() {
		super();
		
	}

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

//	public List<Item> getItems() {
//		return items;
//	}
//
//	public void setItems(List<Item> items) {
//		this.items = items;
//	}


	
}
