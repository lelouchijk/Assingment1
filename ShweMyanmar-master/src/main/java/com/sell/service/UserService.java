package com.sell.service;

import com.sell.exception.ItemNotFoundException;
import com.sell.model.*;
import com.sell.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final ShopRepository shopRepo;
    private final ItemRepository itemRepo;
    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;
    private final CategoryRepository categoryRepo;
    private final CartItemRepository cartItemRepo;

    @Autowired
    public UserService(UserRepository userRepo, ShopRepository shopRepo, ItemRepository itemRepo,
                       CartRepository cartRepo, OrderRepository orderRepo, CategoryRepository categoryRepo, CartItemRepository cartItemRepo) {
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
        this.itemRepo = itemRepo;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
        this.categoryRepo = categoryRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(long id, User user) {
        User existingUser = userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
//        user.setUserId(id);
        userRepo.save(existingUser);
    }

    public void comfirmUser(User user) {
        userRepo.save(user);
    }

    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }

    public User getUser(long id) {
        return userRepo.findById(id).get();
    }

    public void saveItem(Item item) {
        itemRepo.save(item);
    }

    public void updateItem(long id, Item item, Category category, User user) {

        user.setUserId(user.getUserId());
        userRepo.save(user);
        item.setUser(user);
        item.setItemId(id);
        if (item.getCategory() == null) {
            itemRepo.save(item);
        } else {
            item.setCategory(category);
            //need to add shop like above

            itemRepo.save(item);
        }
    }

    public void deleteItem(long id) {
        itemRepo.deleteById(id);
    }

    public List<Item> showAllItem() {
        return itemRepo.findAll();
    }

    public Optional<Item> showByItemId(long id) {
        return itemRepo.findById(id);
    }

    public Optional<Item> getItem(long id) {
        return itemRepo.findById(id);
    }

    public List<Item> getItemsByUser(User user) {
        return itemRepo.findByItemOwner(user);
    }


    public void saveCart(Cart cart) {
        cartRepo.save(cart);
    }

    public void updateCart(long id, Cart cart) {
        cart.setCartId(id);
        cartRepo.save(cart);
    }

    public void deleteCart(long id) {
        cartRepo.deleteById(id);
    }

    public Cart getCart(long id) {
        return cartRepo.findById(id).get();
    }

    public List<Category> getAllCategory() {
        return categoryRepo.findAll();
    }

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public void deleteOrder(long id) {
        orderRepo.deleteById(id);
    }

    public List<Order> showAllOrder() {
        return orderRepo.findAll();
    }



    public List<CartItem> getAllCartItemsByUser(User loggedInUser) {
        return cartItemRepo.findByUser(loggedInUser);

    }


    @Transactional
    public void addToCart(User user, long itemId, int quantity) {

        List<Cart> userCarts = cartRepo.findByUser(user);
        Cart cart;

        if (userCarts.isEmpty()) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        } else {
            cart = userCarts.get(0);
        }

        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + itemId + " not found"));


        Optional<CartItem> existingCartItem = cartItemRepo.findByCartAndItem(cart, item);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setPrice((int) item.getPrice());
            int newQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(newQuantity);
            cartItem.setTotal((int) (item.getPrice() * newQuantity));
            cartItem.setUser(user);
            cartItem.setItemName(item.getItemName());
            cartItemRepo.save(cartItem);

        } else {
            CartItem newCartItem = new CartItem();


            newCartItem.setItem(item);


            newCartItem.setPrice((int) item.getPrice());
            int newQuantity = newCartItem.getQuantity() + quantity;
            newCartItem.setQuantity(newQuantity);
            newCartItem.setTotal((int) (item.getPrice() * quantity));
            newCartItem.setCart(cart);
            newCartItem.setUser(user);
            newCartItem.setItemName(item.getItemName());
            cartItemRepo.save(newCartItem);

            cart.getCartItems().add(newCartItem);

        }

        int totalQuantity = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        int totalPrice = cart.getCartItems().stream().mapToInt(CartItem::getTotal).sum();

        cart.setQuantity(totalQuantity);
        cart.setTotal(totalPrice);

        cartRepo.save(cart);
    }
//
//    @Transactional
//    public void removingItem(User user,long itemId){
//        List<Cart> userCarts = cartRepo.findByUser(user);
//
//    }
    @Transactional
    public void removingItem(User user,long itemId){
        List<Cart> userCarts = cartRepo.findByUser(user);
        Cart cart;

        if (userCarts.isEmpty()) {
            cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        } else {
            cart = userCarts.get(0);
        }
        cartItemRepo.deleteById(itemId);
        cartRepo.save(cart);

    }




    public List<Cart> getAllCartsByUser(User user) {
        return cartRepo.findByUser(user);
    }




    public void setOrder(User loggedInUser) {
        List<Cart> cartlist = cartRepo.findByUser(loggedInUser);
        Order order = new Order();
        Shop shop = null;
        for(Cart cart:cartlist){
            order.setTotal(cart.getTotal());
            order.setQuantity(cart.getQuantity());
            if (!cart.getCartItems().isEmpty()) {
                CartItem firstCartItem = cart.getCartItems().get(0);
                if (firstCartItem.getItem() != null) {
                    shop = firstCartItem.getItem().getShop();
                }
            }
        }
        if(shop!=null){
            order.setShop(shop);
        }

        order.setUser(loggedInUser);
        order.setStatus("Pending");

        orderRepo.save(order);
    }

    public List<Order> getAllOrdersLoggedUser(User loggedUser) {


        return orderRepo.findOrdersByUser(loggedUser);
    }

    @Transactional
    public void clearCart(User user) {
        List<Cart> cartItems = cartRepo.findByUser(user);
        if (cartItems != null && !cartItems.isEmpty()) {
            cartRepo.deleteByUser(user);  // This needs a transaction
        }
    }

    @Transactional
    public void clearCartItem(User user){
        List<CartItem> cartItemList = cartItemRepo.findByUser(user);
        cartItemRepo.deleteByUser(user);
    }

}








