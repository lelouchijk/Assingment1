package com.sell.controller;


import com.sell.model.Delivery;
import com.sell.model.Order;
import com.sell.model.Shop;
import com.sell.model.User;
import com.sell.repository.DeliveryRepository;
import com.sell.repository.OrderRepository;
import com.sell.repository.ShopRepository;
import com.sell.repository.UserRepository;
import com.sell.service.DeliveryService;
import com.sell.service.ShopService;
import com.sell.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/deliverySystem")
public class DeliveryController {

    private final DeliveryService deliverySer;

    private final UserService userSer;

    private final ShopService shopSer;

    private final ShopRepository shopRepo;

    private final UserRepository userRepo;

    private final DeliveryRepository deliveryRepo;

    private final OrderRepository orderRepo;

//    private final OrderService

    @Autowired
    public DeliveryController(DeliveryService deliverySer, UserService userSer, ShopService shopSer, ShopRepository shopRepo, UserRepository userRepo, DeliveryRepository deliveryRepo, OrderRepository orderRepo) {
        this.deliverySer = deliverySer;
        this.userSer = userSer;
        this.shopSer = shopSer;
        this.shopRepo = shopRepo;
        this.userRepo = userRepo;
        this.deliveryRepo = deliveryRepo;
        this.orderRepo = orderRepo;
    }

    //this mapping is not use
    @GetMapping("/index")
    public String deliveryHomePage(HttpSession session, Model model){
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("user",user);
        return "deliveryDash";
    }

    @GetMapping("/assignToShop")
    public String assignShop(HttpSession session,Model model){
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("user",user);
        model.addAttribute("shops",shopRepo.findAll());
        return "assignShop";
    }

    @PostMapping("/assignToShopProcess")
    public String assignShopProcess(@RequestParam("shopId")long shopId, HttpSession session, Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Delivery deliveryPerson= deliveryRepo.findByDeliveryPerson(loggedInUser).get();
        Shop shop = shopRepo.findById(shopId).orElseThrow(()->new EntityNotFoundException("Shop not found"));
        deliverySer.registerToShop(deliveryPerson.getDeliveryId(),shop);
        return "redirect:/deliverySystem/viewOrder";

    }

    @GetMapping("/viewOrder")
    public String viewAssignedOrder(HttpSession session,Model model){
        User delivery = (User) session.getAttribute("loggedUser");
        Delivery deliveryPerson= deliveryRepo.findByDeliveryPerson(delivery).get();
        List<Order> pendingOrder = orderRepo.findOrdersByDeliveryAndStatus(deliveryPerson,"Pending");
//        List<Order> pendingOrder = orderRepo.findOrdersByStatus("Pending");
        model.addAttribute("pendingOrder",pendingOrder);
//        model.addAttribute("");
        return "DeliveryPendingOrder";
    }

    @PostMapping("/confirmingOrder/{orderId}")
    public String confirmingOrder(@PathVariable("orderId") long orderId, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Delivery deliveryPerson= deliveryRepo.findByDeliveryPerson(loggedInUser).get();
        deliverySer.comfirmOrder(orderId,deliveryPerson.getDeliveryId());
        return "redirect:/deliverySystem/viewOrder";
    }

    @PostMapping("/declineOrder/{orderId}")
    public String declineOrder(@PathVariable("orderId") long orderId, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Delivery deliveryPerson= deliveryRepo.findByDeliveryPerson(loggedInUser).get();
        deliverySer.declineOrder(orderId,deliveryPerson.getDeliveryId());
        return "redirect:/deliverySystem/viewOrder";
    }

    @GetMapping("/viewDeliveryOrder")
    public String viewDeliveryOrder(HttpSession session, Model model){
        User delivery = (User) session.getAttribute("loggedUser");
        Delivery deliveryPerson = deliveryRepo.findByDeliveryPerson(delivery).get();
        List<Order> omwOrder = orderRepo.findOrdersByDeliveryAndStatus(deliveryPerson,"Confirming");
        model.addAttribute("omwOrder",omwOrder);
        return "deliveryOMWOrder";
    }


    @PostMapping("deliveredOrder/{orderId}")
    public String deliveredOrder(@PathVariable ("orderId") long orderId,HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
//        Delivery deliveryUser = delivery.getUserId()
        Delivery delivery = deliverySer.getDeliveryByUserId(loggedInUser.getUserId());
        Long deliveryId = delivery.getDeliveryId();
        deliverySer.markAsDelivered(orderId,deliveryId);
    return "redirect:/deliverySystem/viewDeliveryOrder";
    }


}
