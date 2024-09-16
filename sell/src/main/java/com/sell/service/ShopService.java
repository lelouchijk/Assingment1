package com.sell.service;

import com.sell.exception.ResourceNotFoundException;
import com.sell.model.Category;
import com.sell.model.Item;
import com.sell.model.Shop;
import com.sell.model.User;
import com.sell.repository.CategoryRepository;
import com.sell.repository.ItemRepository;
import com.sell.repository.ShopRepository;
import com.sell.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    public ShopService(ShopRepository shopRepo, UserRepository userRepo,  CategoryRepository categoryRepo, ItemRepository itemRepo) {
        this.shopRepo = shopRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
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

        existingShop.setStatus("Approved");
        existingShop.setVerify(true);
        shopRepo.save(existingShop);
    }

    public void deleteShop(long id){
        shopRepo.deleteById(id);
    }

//    public void comfirmShop(long shopId){
//        Shop shop = shopRepo.findById(shopId).orElseThrow(()-> new ResourceNotFoundException("Shop not found"));
//        shop.setVerify(true);
//    }

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

    public List<Shop> getShopsByUser(User user) {
        return shopRepo.findByShopOwner(user);
    }

    public void saveItem(Item item){
        itemRepo.save(item);
    }
    public void updateItem(long id, Item item, Category category){
        item.setItemId(id);
        item.setCategory(category);
    }
    public void deleteItem(long id){
        itemRepo.deleteById(id);
    }
    public List<Item> showAllItem(){
        return itemRepo.findAll();
    }
    public Item showByItemId(long id){
        return itemRepo.findById(id).get();
    }



    public Item getItem(long id){
        return itemRepo.findById(id).get();
    }

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

    //teachel method use in userController
    public void updateShopData(long id, Shop shop) {
        shop.setShopId(id);
        shopRepo.save(shop);
    }
}
