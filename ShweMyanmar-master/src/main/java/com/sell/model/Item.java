package com.sell.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;


@Entity
public class Item{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long itemId;

	private String ItemName;

	private double price;
	private int quantity;
	@Lob
	@Column(name = "item_image", columnDefinition = "MEDIUMBLOB")
	private byte[] ItemImage;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "shopId")
	private Shop shop;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User itemOwner;

	// Didn't need this.
//	@ManyToOne
//	@JoinColumn(name = "cartItemId")
//	private CartItem cartItem;

//	@ManyToMany
//	private List<Order> orders;

	public Item() {
		super();
	}

	public Item(String itemName, double price, int quantity, byte[] itemImage, LocalDate date, Category category, Shop shop, User itemOwner) {
		ItemName = itemName;
		this.price = price;
		this.quantity = quantity;
		ItemImage = itemImage;
		this.date = date;
		this.category = category;
		this.shop = shop;
		this.itemOwner = itemOwner;
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

	public byte[] getItemImage() {
		return ItemImage;
	}

	public void setItemImage(byte[] itemImage) {
		ItemImage = itemImage;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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


//	public CartItem getCartItem() {
//		return cartItem;
//	}
//
//	public void setCartItem(CartItem cartItem) {
//		this.cartItem = cartItem;
//	}

	//	public List<Order> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
}
	
	
	
	
	

