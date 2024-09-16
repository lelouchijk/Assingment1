package com.sell.service;

import com.sell.exception.ResourceNotFoundException;
import com.sell.model.*;
import com.sell.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final BCryptPasswordEncoder passEnd;

    private final CategoryRepository categoryRepo;

    private final ItemRepository itemRepo;

    private final RoleRepository roleRepo;

    private final UserRepository userRepo;

    private final ShopRepository shopRepo;


    @Autowired
    public AdminService(BCryptPasswordEncoder passEnd,
                        CategoryRepository categoryRepo, ItemRepository itemRepo,
                        RoleRepository roleRepo, UserRepository userRepo, ShopRepository shopRepo) {
        this.passEnd = passEnd;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.shopRepo = shopRepo;
    }

    public User checkAdminAccount(String email, String pw){

        User admin =userRepo.findByEmail(email);
        if(admin != null){
            if(passEnd.matches(pw,admin.getPassword()))
                pw = admin.getPassword();
            admin = userRepo.findByEmailAndPassword(email, pw);
            return admin;
        }
        return null;
    }


    public void saveItem(Item i) {
        itemRepo.save(i);
    }

//    public void updateItem(long id, Item i) {
//        i.setItemId(id);
//        itemRepo.save(i);
//    }

    @Transactional
    public void updateItem1(long id, Item item) {
        Item existingItem = itemRepo.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));

        existingItem.setItemId(id);

//        existingItem.setItemName(item.getItemName());
//        existingItem.setQuantity(item.getQuantity());
//        existingItem.setPrice(item.getPrice());

        Category category = categoryRepo.findById(item.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingItem.setCategory(category);

        itemRepo.save(existingItem);
    }

    public void deleteItem(long id) {
        itemRepo.deleteById(id);
    }

    public List<Item> showAllItem(){
        return itemRepo.findAll();
    }

    public Optional<Item> showItemById(long id){
        return itemRepo.findById(id);
    }

    public Item getItem(long id) {
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




}
