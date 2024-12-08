package com.sell.service;

import com.sell.model.*;
import com.sell.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService  {
    private final ShopRepository shopRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final ItemRepository itemRepo;
    private final RoleRepository roleRepo;
    private final DeliveryRepository deliveryRepo;
    private final OrderRepository orderRepo;
    private final UserService userSer;


    //Business Logic




    @Autowired
    public ShopService(ShopRepository shopRepo, UserRepository userRepo, CategoryRepository categoryRepo, ItemRepository itemRepo, RoleRepository roleRepo, DeliveryRepository deliveryRepo, OrderRepository orderRepo, UserService userSer) {
        this.shopRepo = shopRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.roleRepo = roleRepo;
        this.deliveryRepo = deliveryRepo;
        this.orderRepo = orderRepo;
        this.userSer = userSer;
    }


    public void createShop(Shop shop){

//        Don't need for right now we can add this fact at Controller
//        shop.setOwner(user);
        shop.setVerify(false);
        shopRepo.save(shop);
    }

    //teachel method use in admin comfirm shop
    public void updateShop(long id){
        Shop existingShop = shopRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Shop not found"));

        existingShop.setShopId(id);
        existingShop.setStatus("Approved");
        existingShop.setVerify(true);
        Role shopAdminRole = roleRepo.findByRoleName("ShopAdmin");
        existingShop.getShopOwner().setRole(shopAdminRole);
        shopRepo.save(existingShop);

       
    }

//    public void updateShopByCustomer(long id,Shop shop){
//        shop.setShopId(id);
//        shopRepo.save(shop);
//    }

    //teachel method use in userController
    public void updateShopData(long id, Shop shop) {
        shop.setShopId(id);
        shopRepo.save(shop);
    }

    public void deleteShop(long id){
        Shop existingShop = shopRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Shop not found"));
        Role customerRole = roleRepo.findByRoleName("Customer");
        existingShop.getShopOwner().setRole(customerRole);
        shopRepo.deleteById(id);
    }



    public List<Shop> getAllShop(){
        return shopRepo.findAll();
    }

    public Shop getShop(long id){
        return shopRepo.findById(id).orElse(null);
    }

    public Shop findShopName(String shopName){
        return (Shop) shopRepo.findByShopName(shopName);
    }

    public List<Shop> findShopsByStatus(String status) {
        return shopRepo.findByStatus(status);
    }

    // block this method because it is already exit in repo
//    public List<Shop> getShopsByUser(User user) {
//        return shopRepo.findByShopOwner(user);
//    }

    public void saveItem(Item item){
        itemRepo.save(item);
    }

    @Transactional
    public Shop getUserShop(User user) {
        User fullUser = userRepo.findById(user.getUserId()).get();
        return fullUser.getShops().get(0);
    }
    @Transactional
    public void updateItem(long id,Item item,Category category,User user,Shop shop){
        user.setUserId(user.getUserId());
        userRepo.save(user);
        item.setUser(user);
        item.setCategory(category);
        item.setShop(shop);
        item.setItemId(id);
        itemRepo.save(item);

    }

    @Transactional
    public void updateItem(long id,Item item){
        item.setItemId(id);
        itemRepo.save(item);
    }

    @Transactional
    public void updateItem1(long id, Item item) {
        Item existingItem = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
//        Item existingItem = itemRepo.findById(id);

        existingItem.setItemId(id);


        Category category = categoryRepo.findById(item.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingItem.setCategory(category);

        itemRepo.save(existingItem);
    }

    public void deleteItem(long id){
        itemRepo.deleteById(id);
    }
    public List<Item> showAllItem(){
        return itemRepo.findAll();
    }
    public Optional<Item> showByItemId(long id){
        return itemRepo.findById(id);
    }



    public Optional<Item> getItem(long id){
        return itemRepo.findById(id);
    }

    public void saveCategory(Category c) {

        categoryRepo.save(c);
    }

    public void saveCategory1(Category c,long shopId){
        Shop shopOwner = shopRepo.findById(shopId).get();
        shopOwner.setShopId(shopId);
        c.setShop(shopOwner);
        categoryRepo.save(c);

    }


    public void updateCategory(Shop shop,Category c,long categoryId) {
        c.setShop(shop);
        c.setCategoryId(categoryId);
        categoryRepo.save(c);
    }

    public void deleteCategory(long id) {
        categoryRepo.deleteById(id);
    }

    public List<Category> showAllCategory(){
        return categoryRepo.findAll();
    }
    public Category getCategory(long id) {
        return categoryRepo.findById(id).get();
    }

    public List<Category> getAllCategory(){
        return categoryRepo.findAll();
    }


    public Shop getShopById(long shopId) {
        return (Shop) shopRepo.findShopByShopId(shopId);
    }


    @Transactional
    public void confirmDeliveryPerson(Long deliveryPersonId) {
        Delivery deliveryPerson = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery person not found"));

//        Shop shop = shopRepo.findById(shopId)
//                .orElseThrow(() -> new EntityNotFoundException("Shop not found"));

        deliveryPerson.setVerifyByShop(true);
//        deliveryPerson.setShops((List<Shop>) shop);

        deliveryRepo.save(deliveryPerson);
    }


    @Transactional
    public List<Category> getCategoryByShop(User user) {
        Hibernate.initialize(user.getShops());
        Shop shop = user.getShops().get(0);
        return categoryRepo.findByShop(shop);
    }

    @Transactional
    public void assignDeliveryToOrder(long orderId,long deliveryId){
        Order order = orderRepo.findById(orderId).get();
        Delivery delivery = deliveryRepo.findById(deliveryId)
                .orElseThrow(()->new EntityNotFoundException("Delivery not found"));
        order.setDelivery(delivery);
        order.setStatus("Confirming");
        delivery.setStatus("Comfirming Order");

    }

    public Category findCategoryById(long categoryId) {
        return categoryRepo.findById(categoryId).orElse(null);
    }

    public Optional<Shop> findWithItem(Item item){
        return Optional.of(shopRepo.findShopByItems(item).get());
    }
}
