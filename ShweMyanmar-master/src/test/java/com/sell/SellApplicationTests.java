package com.sell;


import com.sell.model.*;
import com.sell.repository.*;
import com.sell.service.ShopService;
import com.sell.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.EntityNotFoundException;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class SellApplicationTests {



    @Autowired
    private ShopService shopService;
    
    @Autowired
    private UserService userService;

    @MockBean
    private CategoryRepository categoryRepo;
    @MockBean
    private ShopRepository shopRepo;
    @MockBean
    private ItemRepository itemRepo;
    @MockBean
    private CartRepository cartRepo;
    @MockBean
    private CartItemRepository cartItemRepo;
    @MockBean
    private RoleRepository roleRepo;
    @MockBean
    private OrderRepository orderRepo;
    @MockBean
    private DeliveryRepository deliveryRepo;

    private Shop shop;
    private Category category;
    private User user;
    private Item item;
    private Cart cart;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        category = new Category();
        user = new User();
        item = new Item();
        cart = new Cart();
        cart.setUser(user);
    }


    @Test
    void testGetAllCategory() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepo.findAll()).thenReturn(categories);

        List<Category> result = shopService.getAllCategory();

        assertEquals(2, result.size());
        verify(categoryRepo, times(1)).findAll();
    }


    @Test
    void testUpdateShop() {
        Role shopAdminRole = new Role();
        shop.setShopOwner(user);

        when(shopRepo.findById(1L)).thenReturn(Optional.of(shop));
        when(roleRepo.findByRoleName("ShopAdmin")).thenReturn(shopAdminRole);

        shopService.updateShop(1L);

        assertEquals("Approved", shop.getStatus());
        assertTrue(shop.isVerify());
        assertEquals(shopAdminRole, shop.getShopOwner().getRole());
        verify(shopRepo, times(1)).save(shop);
    }


    @Test
    void testGetAllShop() {
        List<Shop> shops = Arrays.asList(new Shop(), new Shop());
        when(shopRepo.findAll()).thenReturn(shops);

        List<Shop> result = shopService.getAllShop();

        assertEquals(2, result.size());
        verify(shopRepo, times(1)).findAll();
    }


    @Test
    void testFindShopsByStatus() {
        List<Shop> shops = Arrays.asList(new Shop(), new Shop());
        when(shopRepo.findByStatus("Approved")).thenReturn(shops);

        List<Shop> result = shopService.findShopsByStatus("Approved");

        assertEquals(2, result.size());
        verify(shopRepo, times(1)).findByStatus("Approved");
    }


    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setItemId(1L);

        when(itemRepo.findById(1L)).thenReturn(Optional.of(existingItem));

        shopService.updateItem(1L, existingItem);

        assertEquals(1L, existingItem.getItemId());
        verify(itemRepo, times(1)).save(existingItem);
    }

    @Test
    void testSaveCategory() {
        shopService.saveCategory(category);

        verify(categoryRepo, times(1)).save(category);
    }


    @Test
    void testGetCategoryByShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        user.setShops(Collections.singletonList(shop));
        List<Category> categories = Arrays.asList(new Category(), new Category());

        when(categoryRepo.findByShop(shop)).thenReturn(categories);

        List<Category> result = shopService.getCategoryByShop(user);

        assertEquals(2, result.size());
        verify(categoryRepo, times(1)).findByShop(shop);
    }


    @Test
    void testFindWithItem() {
        when(shopRepo.findShopByItems(item)).thenReturn(Optional.of(shop));

        Optional<Shop> result = shopService.findWithItem(item);

        assertTrue(result.isPresent());
        assertEquals(shop, result.get());
        verify(shopRepo, times(1)).findShopByItems(item);
    }


    @Test
    void testAddToCart() {
        long itemId = 1L;
        int quantity = 2;
        Cart cart = new Cart();
        cart.setUser(user);
        Item item = new Item();
        item.setItemId(itemId);
        item.setPrice(100);
        when(itemRepo.findById(itemId)).thenReturn(Optional.of(item));
        when(cartRepo.findByUser(user)).thenReturn(Collections.singletonList(cart));
        userService.addToCart(user, itemId, quantity);
        verify(cartRepo, times(1)).save(cart);
        verify(cartItemRepo, times(1)).save(any(CartItem.class));
    }
    




    @Test
    void testAddToCartWithExistingItem() {
        long itemId = 1L;
        int quantity = 3;
        Cart cart = new Cart();
        cart.setUser(user);
        Item item = new Item();
        item.setItemId(itemId);
        item.setPrice(100);
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(2);
        when(itemRepo.findById(itemId)).thenReturn(Optional.of(item));
        when(cartRepo.findByUser(user)).thenReturn(Collections.singletonList(cart));
        when(cartItemRepo.findByCartAndItem(cart, item)).thenReturn(Optional.of(cartItem));
        userService.addToCart(user, itemId, quantity);
        assertEquals(5, cartItem.getQuantity()); // updated quantity
        assertEquals(500, cartItem.getTotal()); // updated total
        verify(cartItemRepo, times(1)).save(cartItem);
    }
}
