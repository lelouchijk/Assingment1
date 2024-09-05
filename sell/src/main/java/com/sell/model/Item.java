package com.sell.model;

import java.util.List;

import jakarta.persistence.*;


@Entity
public class Item{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long itemId;

	private double price;
	private int quantity;
	private String ItemImage;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToMany(mappedBy = "items", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Company> company;

	public Item(double price, int quantity, Category category, String ItemImage) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.category = category;
		this.ItemImage = ItemImage;
	}
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}
	
	
	
	
	

