package com.sell;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import com.sell.model.Cart;
import com.sell.model.CartItem;
import com.sell.model.Category;
import com.sell.model.Delivery;
import com.sell.model.Item;
import com.sell.model.Order;
import com.sell.model.Role;
import com.sell.model.Shop;
import com.sell.model.User;
import com.sell.repository.CartItemRepository;
import com.sell.repository.CartRepository;
import com.sell.repository.CategoryRepository;
import com.sell.repository.DeliveryRepository;
import com.sell.repository.ItemRepository;
import com.sell.repository.OrderRepository;
import com.sell.repository.RoleRepository;
import com.sell.repository.ShopRepository;
import com.sell.repository.UserRepository;
import com.sell.service.AdminService;
import com.sell.service.DeliveryService;
import com.sell.service.ShopService;
import com.sell.service.UserService;

import java.util.*;
import java.util.stream.IntStream;

class ServiceTest {

    @InjectMocks
    private AdminService adminSer; 
    
    @InjectMocks
    private DeliveryService deliverySer;
    
    @InjectMocks
    private UserService userSer;
    
    @InjectMocks
    private ShopService shopSer;
    

    @Mock
    private ItemRepository itemRepo;
    @Mock
    private CategoryRepository categoryRepo;
    @Mock
    private DeliveryRepository deliveryRepo;
    @Mock
    private OrderRepository orderRepo;
    @Mock
    private ShopRepository shopRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private CartRepository cartRepo;
    @Mock
    private CartItemRepository cartItemRepo;
    @Mock
    private RoleRepository roleRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test saveItem
    @Test
    void testSaveItem() {
        Item item = new Item();
        shopSer.saveItem(item);
        verify(itemRepo, times(1)).save(item);
    }

    // Test updateItem
    @Test
    void testUpdateItem() {
        Item item = new Item();
        shopSer.updateItem(1L, item);
        assertEquals(1L, item.getItemId());
        verify(itemRepo, times(1)).save(item);
    }

    // Test deleteItem
    @Test
    void testDeleteItem() {
        shopSer.deleteItem(1L);
        verify(itemRepo, times(1)).deleteById(1L);
    }

    // Test showAllItem
    @Test
    void testShowAllItem() {
        List<Item> items = List.of(new Item(), new Item());
        when(itemRepo.findAll()).thenReturn(items);

        List<Item> result = adminSer.showAllItem();
        assertEquals(2, result.size());
        verify(itemRepo, times(1)).findAll();
    }

    // Test saveCategory
    @Test
    void testSaveCategory() {
        Category category = new Category();
        adminSer.saveCategory(category);
        verify(categoryRepo, times(1)).save(category);
    }

    // Test updateCategory
    @Test
    void testUpdateCategory() {
        Category existingCategory = new Category();
        when(categoryRepo.findById(1L)).thenReturn(Optional.of(existingCategory));

        Category newCategory = new Category();
        adminSer.updateCategory(1L, newCategory);

        assertEquals(1L, newCategory.getCategoryId());
        assertSame(existingCategory.getShop(), newCategory.getShop());
        verify(categoryRepo, times(1)).save(newCategory);
    }

    // Test deleteCategory
    @Test
    void testDeleteCategory() {
        adminSer.deleteCategory(1L);
        verify(categoryRepo, times(1)).deleteById(1L);
    }

    // Test registerDelivery
    @Test
    void testRegisterDelivery() {
        Delivery delivery = new Delivery();
        deliverySer.registerDelivery(delivery);
//        assertFalse(delivery.isApproval());
        verify(deliveryRepo, times(1)).save(delivery);
    }

    // Test markAsDelivered
    @Test
    void testMarkAsDelivered() {
        Order order = new Order();
        Delivery delivery = new Delivery();

        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        when(deliveryRepo.findById(2L)).thenReturn(Optional.of(delivery));

        deliverySer.markAsDelivered(1L, 2L);

        assertEquals("Delivered", order.getStatus());
        assertEquals("Free", delivery.getStatus());
        verify(orderRepo, times(1)).save(order);
        verify(deliveryRepo, times(1)).save(delivery);
    }

    // Test findDeliveryByStatus
    @Test
    void testFindDeliveryByStatus() {
        List<Delivery> deliveries = List.of(new Delivery(), new Delivery());
        when(deliveryRepo.findByStatus("Free")).thenReturn(deliveries);

        List<Delivery> result = deliverySer.findDeliveryByStatus("Free");
        assertEquals(2, result.size());
        verify(deliveryRepo, times(1)).findByStatus("Free");
    }

    // Test registerToShop
    @Test
    void testRegisterToShop() {
        Delivery delivery = new Delivery();
        Shop shop = new Shop();

        when(deliveryRepo.findById(1L)).thenReturn(Optional.of(delivery));

        deliverySer.registerToShop(1L, shop);

        assertEquals(shop, delivery.getShop());
        verify(deliveryRepo, times(1)).save(delivery);
    }


    // Test createShop
    @Test
    void testCreateShop() {
        Shop shop = new Shop();
        shopSer.createShop(shop);

        assertFalse(shop.isVerify());
        verify(shopRepo, times(1)).save(shop);
    }

    // Test deleteShop
    @Test
    void testDeleteShop() {
        Shop shop = new Shop();
        Role customerRole = new Role();
        User shopOwner = new User();

        shop.setShopOwner(shopOwner);

        when(shopRepo.findById(1L)).thenReturn(Optional.of(shop));
        when(roleRepo.findByRoleName("Customer")).thenReturn(customerRole);

        shopSer.deleteShop(1L);

        assertEquals(customerRole, shopOwner.getRole());
        verify(shopRepo, times(1)).deleteById(1L);
    }

    
    @Test
    void testSetOrder() {
        User user = new User();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        Item item = new Item();
        Shop shop = new Shop();

        cartItem.setItem(item);
        cart.setCartItems(Collections.singletonList(cartItem));
        item.setShop(shop);

        when(cartRepo.findByUser(user)).thenReturn(Collections.singletonList(cart));

        userSer.setOrder(user);

        verify(orderRepo, times(1)).save(any(Order.class));
    }
    
    

    // Test getAllOrdersLoggedUser
    @Test
    void testGetAllOrdersLoggedUser() {
        User user = new User();
        List<Order> orders = List.of(new Order(), new Order());
        when(orderRepo.findOrdersByUser(user)).thenReturn(orders);

        List<Order> result = userSer.getAllOrdersLoggedUser(user);
        assertEquals(2, result.size());
        verify(orderRepo, times(1)).findOrdersByUser(user);
    }
    
    // Functional Testing
    @Test
    @Transactional
    void functionalTestAddToCart() {
        // Setup: Create a user and an item
        User user = new User();
        user.setUserId(1L);

        Item item = new Item();
        item.setItemId(1L);
        item.setQuantity(10);
        item.setPrice(100.0);
        itemRepo.save(item);

        // Execute: Add the item to the user's cart
        userSer.addToCart(user, item.getItemId(), 2);

        // Retrieve the updated cart
        Cart cart = cartRepo.findByUser(user).get(0);

        // Assert: Verify item is added, quantity is updated, and totals are calculated
        assertEquals(1, cart.getCartItems().size());
        CartItem cartItem = cart.getCartItems().get(0);
        assertEquals(item.getItemId(), cartItem.getItem().getItemId());
        assertEquals(2, cartItem.getQuantity());
        assertEquals(200.0, cart.getTotal());

        // Edge Case: Attempt to add more than the available stock
        assertThrows(IllegalArgumentException.class, () -> userSer.addToCart(user, item.getItemId(), 10));
    }
    
    @Test
    @Transactional
    void testAssignDeliveryToOrder() {
        
        Order order = new Order();
        order.setStatus("Pending");
        order = orderRepo.save(order);

        Delivery delivery = new Delivery();
        delivery.setStatus("Free");
        delivery = deliveryRepo.save(delivery);

        deliverySer.assignDelivery(delivery.getDeliveryId(), order.getOrderId());
        
        Order updatedOrder = orderRepo.findById(order.getOrderId()).orElseThrow();
        Delivery updatedDelivery = deliveryRepo.findById(delivery.getDeliveryId()).orElseThrow();

   
        assertEquals("Assigned", updatedDelivery.getStatus());
        assertEquals("Delivered", updatedOrder.getStatus());
        assertEquals(updatedDelivery, updatedOrder.getDelivery());
    }
    
    @Test
    void testFindShopsByStatusPerformance() {
        
        IntStream.range(0, 1000).forEach(i -> {
            Shop shop = new Shop();
            shop.setShopName("Shop " + i);
            shop.setStatus("Approved");
            shopRepo.save(shop);
        });

        
        long startTime = System.currentTimeMillis();
        List<Shop> shops = shopSer.findShopsByStatus("Approved");
        long endTime = System.currentTimeMillis();

       
        assertEquals(1000, shops.size());
        long elapsedTime = endTime - startTime;
        System.out.println("Query execution time: " + elapsedTime + " ms");

        
        assertTrue(elapsedTime < 500, "Query took too long to execute");
    }

    
}
