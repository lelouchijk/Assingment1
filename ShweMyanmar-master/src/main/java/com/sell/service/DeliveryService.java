package com.sell.service;

import com.sell.model.Delivery;
import com.sell.model.Order;
import com.sell.model.Role;
import com.sell.model.Shop;
import com.sell.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepo;
    private final UserRepository userRepo;
    private final ItemRepository itemRepo;
    private final RoleRepository roleRepo;
    private final OrderRepository orderRepo;
    private final ShopRepository shopRepo;
    public DeliveryService(DeliveryRepository deliveryRepo, UserRepository userRepo, ItemRepository itemRepo,
                           RoleRepository roleRepo, OrderRepository orderRepo, ShopRepository shopRepo) {
        this.deliveryRepo = deliveryRepo;
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
        this.roleRepo = roleRepo;
        this.orderRepo = orderRepo;
        this.shopRepo = shopRepo;
    }
    /// Business Logic

    public void registerDelivery(Delivery delivery){
        delivery.setApproval(false);
        deliveryRepo.save(delivery);
    }


        //Shop can't update Delivery status and other
//    public void updateDeliveryByShop(long deliveryId,List<Shop> shop){
//        Delivery existingDelivery = deliveryRepo.findById(deliveryId).orElseThrow(()->new EntityNotFoundException("Deli is not found"));
//        existingDelivery.setDeliveryId(existingDelivery.getDeliveryId());
//        existingDelivery.setShop((Shop) shop);
//        deliveryRepo.save(existingDelivery);
//    }


    public void updateDeliveryByUser(long id){

    }

    public void updateDelivery(long id){

    }

    public void deleteDelivery(long id){
        deliveryRepo.deleteById(id);
    }

    public List<Delivery> getAllDelivery(){
        return deliveryRepo.findAll();
    }

    public Optional<Delivery> getDelivery(long id){
        return deliveryRepo.findById(id);
    }

    @Transactional
    public void assignDelivery(Long orderId, Long deliveryPersonId) {

        Order order = orderRepo.findById(orderId).orElseThrow(()->new EntityNotFoundException("Not Found"));

        Delivery deliveryPerson = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery person not found"));
        order.setDelivery(deliveryPerson);
        order.setStatus("Assigned");
        orderRepo.save(order);

    }

    @Transactional
    public void registerToShop(long deliveryPersonId,Shop shop) {

        Delivery deliveryPerson = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery person not found"));
        deliveryPerson.setDeliveryId(deliveryPersonId);

      //  Shop shop = shopRepo.findById(delivery.getShop().getShopId()).orElseThrow(()-> new RuntimeException("Shop is not found"));
        deliveryPerson.setShop(shop);

        deliveryRepo.save(deliveryPerson);
    }


    @Transactional
    public void acceptOrder(Long orderId, Long deliveryPersonId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Delivery deliveryPerson = deliveryRepo.findById(deliveryPersonId).get();

        order.setStatus("Accepted");
        orderRepo.save(order);
    }

    @Transactional
    public void markAsDelivered(Long orderId, Long deliveryPersonId) {
        System.out.println("Fetching order with ID: " + orderId);
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found"));
        Delivery deliveryPerson = deliveryRepo.findById(deliveryPersonId).orElseThrow(() -> new IllegalArgumentException("Delivery person with ID " + deliveryPersonId + " not found"));
        order.setStatus("Delivered");
        deliveryPerson.setStatus("Free");

        orderRepo.save(order);
        deliveryRepo.save(deliveryPerson);
    }


    public List<Delivery> findDeliveryByStatus(String status) {
        return deliveryRepo.findByStatus(status);
    }

    @Transactional
    public void comfirmOrder(long orderId, long deliveryId){
        Order order = orderRepo.findById(orderId).get();
        Delivery delivery = deliveryRepo.findById(deliveryId).orElseThrow(()->new EntityNotFoundException("Not Found"));
        order.setStatus("OMW");
        delivery.setStatus("OMW");

    }

    @Transactional
    public void declineOrder(long orderId,long deliveryId){
        Order order = orderRepo.findById(orderId).get();
        Delivery delivery = deliveryRepo.findById(deliveryId).orElseThrow(()->new EntityNotFoundException("Not Found"));
        order.setStatus("Pending");
        order.setDelivery(null);
//        delivery.setStatus("");
    }

    @Transactional
    public void deliverOrder(long orderId, long deliveryId){
        Order order = orderRepo.findById(orderId).get();
        Delivery delivery = deliveryRepo.findById(deliveryId).get();
        order.setStatus("Deilvered");

    }

    public Delivery getDeliveryByUserId(Long userId) {
        return deliveryRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No delivery entry found for user ID: " + userId));
    }



}
