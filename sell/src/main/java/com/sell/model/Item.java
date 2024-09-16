package com.sell.model;

import java.util.List;

import jakarta.persistence.*;


@Entity
public class Item{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long itemId;

	private String ItemName;

	private double price;
	private int quantity;
	private String ItemImage;
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User itemOwner;

	@ManyToMany
	private List<Order> orders;

	public Item() {
		super();
	}

	public Item(String itemName, double price, int quantity, String itemImage, Category category, Shop shop, User itemOwner, List<Order> orders) {
		ItemName = itemName;
		this.price = price;
		this.quantity = quantity;
		ItemImage = itemImage;
		this.category = category;
		this.shop = shop;
		this.itemOwner = itemOwner;
		this.orders = orders;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
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

	public String getItemImage() {
		return ItemImage;
	}

	public void setItemImage(String itemImage) {
		ItemImage = itemImage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public User getUser() {
		return itemOwner;
	}

	public void setUser(User itemOwner) {
		this.itemOwner = itemOwner;
	}

	public User getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(User itemOwner) {
		this.itemOwner = itemOwner;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
	
	
	
	
	

