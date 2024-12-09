package com.sell.service;

import com.sell.exception.ResourceNotFoundException;
import com.sell.model.*;
import com.sell.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final CategoryRepository categoryRepo;
    private final ItemRepository itemRepo;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final ShopRepository shopRepo;
    private final DeliveryRepository deliveryRepo;
    @Autowired
    public AdminService(CategoryRepository categoryRepo, ItemRepository itemRepo,
                        RoleRepository roleRepo, UserRepository userRepo, ShopRepository shopRepo, DeliveryRepository deliveryRepo) {
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
        this.deliveryRepo = deliveryRepo;
    }
    //// Business Logic

    @Transactional
    public void saveItem(Item i) {
            itemRepo.save(i);
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

    @Transactional
    public void updateItem(long id,Item item){
        item.setItemId(id);
        itemRepo.save(item);
    }

    public void deleteItem(long id) {
        itemRepo.deleteById(id);
    }

    public Optional<Item> getItemById(Long itemId) {
        return itemRepo.findById(itemId);

    }



    public List<Item> showAllItem(){
        return itemRepo.findAll();
    }

    public Optional<Item> showItemById(long id){
        return itemRepo.findById(id);
    }

    public Category findCategoryById(Long categoryId) {
        return categoryRepo.findById(categoryId).orElse(null);
    }

    public Optional<Item> getItem(long id) {
        return itemRepo.findById(id);
    }

    public void saveCategory(Category c) {
        categoryRepo.save(c);
    }


    public void updateCategory(long id,Category c) {
        Category existingCategory = categoryRepo.findById(id).get();
        c.setCategoryId(id);
        c.setShop(existingCategory.getShop());
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

    public void saveRole(Role role){
        roleRepo.save(role);
    }

    public void updateRole(long id, Role role){
        role.setRoleId(id);
        roleRepo.save(role);
    }

    public void deleteRole(long id){
        roleRepo.deleteById(id);
    }

    public Role getRole(long id){
        return roleRepo.findById(id).get();
    }

    public Optional<Role> showRoleById(long id){
        return roleRepo.findById(id);
    }


    public List<Role>getAllRole(){
        return roleRepo.findAll();
    }

    public List<Shop>getAllShop(){
        return shopRepo.findAll();
    }

    public void comfirmShop(long shopId){
        Shop shop = shopRepo.findById(shopId).orElseThrow(()-> new ResourceNotFoundException("Shop not found"));
        shop.setVerify(true);
    }

    public  void deleteShop(long shopId){
        shopRepo.deleteById(shopId);
    }


    public User getShopUser(long shopId) {
        return userRepo.findById(shopId).get();
    }

    public void updateDeliveryByAdmin(long id){
        Delivery existingDelivery = deliveryRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Deli not found"));
        existingDelivery.setDeliveryId(existingDelivery.getDeliveryId());
        existingDelivery.setApproval(true);
        existingDelivery.setStatus("Free");
        Role deliveryRole = roleRepo.findByRoleName("Delivery");
//        existingDelivery.getDeliveryPerson().setRole(deliveryRole);
        existingDelivery.getDeliveryPerson().setRole(deliveryRole);
        deliveryRepo.save(existingDelivery);
    }

    public Category getCategoryById(long categoryId) {
//        Optional<Category> category = categoryRepo.findById(categoryId);
        return  categoryRepo.findByCategoryId(categoryId).orElse(null);
    }
}
