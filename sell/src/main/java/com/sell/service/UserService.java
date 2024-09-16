package com.sell.service;

import com.sell.model.*;
import com.sell.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    private final ShopRepository shopRepo;

    private final ItemRepository itemRepo;

    private final CartRepository cartRepo;

    private final OrderRepository orderRepo;


    @Autowired
    public UserService(UserRepository userRepo, ShopRepository shopRepo, ItemRepository itemRepo, CartRepository cartRepo, OrderRepository orderRepo) {
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
        this.itemRepo = itemRepo;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(long id, User user) {
        user.setUserId(id);
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public User getUser(long id){
        return userRepo.findById(id).get();
    }

    public void saveItem(Item item){
        itemRepo.save(item);
    }
    public void updateItem(long id, Item item, Category category){
        item.setItemId(id);
        item.setCategory(category);
        itemRepo.save(item);
    }
    public void deleteItem(long id){
        itemRepo.deleteById(id);
    }
    public List<Item> showAllItem(){
        return itemRepo.findAll();
    }
//    public Optional<Item> showByItemId(long id){
//        return itemRepo.findById(id);
//    }

    public Item getItem(long id){
        return itemRepo.findById(id);
    }

    public List<Item> getItemsByUser(User user) {
        return itemRepo.findByItemOwner(user);
    }

    public void saveCart(Cart cart){
        cartRepo.save(cart);
    }

    public void updateCart(long id,Cart cart,String itemName){
        cart.setCartId(id);
        cart.setItemName(itemName);
        cartRepo.save(cart);
    }

    public void deleteCart(long id){
        cartRepo.deleteById(id);
    }

    public Cart getCart(long id){
        return cartRepo.findById(id).get();
    }

    public void saveOrder(Order order){
        orderRepo.save(order);
    }

    public void deleteOrder(long id){
        orderRepo.deleteById(id);
    }

    public List<Order>showAllOrder(){
        return orderRepo.findAll();
    }


    public List<Cart> getAllCarts(long userId) {
        return cartRepo.findAll();
    }

    public void checkoutCart(long userId) {

        List<Cart> cartItems = cartRepo.findByUser_UserId(userId);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot proceed with checkout.");
        }

        for (Cart cart : cartItems) {
            Order order = new Order();
            order.setItemName(cart.getItemName());
            order.setQuantity(cart.getQuantity());
            order.setPrice(cart.getTotal());

            order.setUser(cart.getUser());
            orderRepo.save(order);
        }

        cartRepo.deleteByUser_UserId(userId);
    }


    public void addToCart(User user, long itemId, int quantity) {
        Item item = itemRepo.findById(itemId);

            int total = (int) (item.getPrice() * quantity);
            Cart cart = new Cart(item.getItemName(), quantity, (int) item.getPrice(), total,user, item);
            cartRepo.save(cart); // Save cart item

    }



    public List<Cart> getCartItems(User user) {

        return cartRepo.findAll();
    }

    public void placeOrder(User user) {
        List<Cart> cartItems = getCartItems(user);
        for (Cart cart : cartItems) {
            Order order = new Order();
            order.setItemName(cart.getItemName());
            order.setQuantity(cart.getQuantity());
            order.setTotal(cart.getTotal());
            order.setUser(user);
            orderRepo.save(order);
        }
        cartRepo.deleteAll();
    }



}




