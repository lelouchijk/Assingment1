package com.sell.controller;

import com.sell.model.*;
import com.sell.repository.RoleRepository;
import com.sell.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/userSystem")
public class UserController {
    private final UserService userSer;
    private final ShopService shopSer;
    private final LoginService loginSer;

    private final AdminService adminSer;

    private final DeliveryService deliSer;

//    private final userSer orderSer;

    private final HttpSession session;

    private final RoleRepository roleRepo;

    @Autowired
    public UserController(UserService userSer, ShopService shopSer, HttpSession session,
                          LoginService loginSer, AdminService adminSer, DeliveryService deliSer, RoleRepository roleRepo) {
        this.userSer = userSer;
        this.shopSer = shopSer;
        this.session = session;
        this.loginSer = loginSer;
        this.adminSer = adminSer;
        this.deliSer = deliSer;
        this.roleRepo = roleRepo;
    }

    @GetMapping("/index")
    public String showMainPage(HttpSession session,Model model){
        User user = (User) session.getAttribute("loggedUser");
        System.out.println(user.getUserId());
        model.addAttribute("user",user);
        return "main";
    }

    @GetMapping("/createShop")
    public String createShopPage(HttpSession session,Model model){
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("shop", new Shop());
        return "createShop";
    }



    @PostMapping("/createShopProcess")
    public String createShopProcess(@ModelAttribute("shop") Shop shop,HttpSession session,Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");

        shop.setShopOwner(loggedInUser);
        shop.setVerify(false);
        shop.setStatus("Pending");
        shopSer.createShop(shop);

        if(loggedInUser.getRole().getRoleName()=="Customer"){
            Role userRole = roleRepo.findByRoleName("ShopAdmin");
            loggedInUser.setRole(userRole);
        }
        return "redirect:/userSystem/createShop";
    }

    @GetMapping("/createDelivery")
    public String createDeliveryPage(HttpSession session,Model model){
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("delivery", new Delivery());
        return "createDelivery";
    }



    @PostMapping("/createDeliveryProcess")
    public String createDeliveryProcess(@ModelAttribute("delivery") Delivery delivery,HttpSession session,Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");

        delivery.setStatus("Pending");
        delivery.setDeliveryPerson(loggedInUser);
        deliSer.registerDelivery(delivery);


        if(loggedInUser.getRole().getRoleName()=="Customer"){
            Role userRole = roleRepo.findByRoleName("Delivery");
            loggedInUser.setRole(userRole);
        }
        return "redirect:/userSystem/createDelivery";
    }



    @GetMapping("/itemInsert")
    public String itemInsert(Model model,HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        model.addAttribute("item",new Item());
        return "newItemCustomer";
    }

    @PostMapping("/addItem")
    public String addNewItem(Model model,@ModelAttribute("item") Item item,HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        item.setUser(loggedInUser);
        userSer.saveItem(item);
        return "redirect:/userSystem/index";
    }

    @GetMapping("/updateItem/{id}")
    public String updateItem(@PathVariable("id") long id,HttpSession session,Model model){
        User loggedUser = (User) session.getAttribute("loggedUser");
        Optional<Item> existingItem = userSer.getItem(id);
            model.addAttribute("item",userSer.getItem(id));
            model.addAttribute("categoryList",userSer.getAllCategory());
            return "updateItemCustomer";

    }



    @PostMapping("/updateItemProcess/{id}")
    public String updateItemProcess(@PathVariable("id")long id, @ModelAttribute("item") Item item,
                                    @ModelAttribute("category")Category category,HttpSession session,Model model){
        User loggedUser = (User) session.getAttribute("loggedUser");
        Optional<Item> existingItem = userSer.getItem(id);
            userSer.updateItem(id, item, category,loggedUser);
            model.addAttribute("itemList",userSer.getItemsByUser(loggedUser));
            return "showItemCustomer";

    }

    @GetMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable("id") long id, Model model, HttpSession session){
        User loggedUser = (User) session.getAttribute("loggedUser");
        Optional<Item> existingItem = userSer.getItem(id);
            userSer.deleteItem(id);
        return "redirect:/userSystem/index";
    }

    @GetMapping("/showItem")
    public String showItemPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        List<Item> itemList = userSer.getItemsByUser(loggedInUser);
        model.addAttribute("itemList",itemList);
        return "showItemCustomer";
    }

    @GetMapping("/updateUser")
    public String updateUserInfo( Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        long id =  loggedInUser.getUserId();
        User existingUser = userSer.getUser(id);
        if(existingUser!=null && existingUser.getUserId() == loggedInUser.getUserId()){
            model.addAttribute("user",userSer.getUser(id));
            return "updateUser";
        }else{
            return "redirect:/userSystem/index";
        }
    }

    // passing with model
    @PostMapping("/updateUserProcess/{id}")
    public String updateUserProcess(@PathVariable("id")long id,@ModelAttribute("user")User user,HttpSession session,Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        if (loggedInUser != null && loggedInUser.getUserId() == id) {
            if (user != null) {
                userSer.updateUser(id, user);
            } else {
                model.addAttribute("errorMessage", "Form submission error.");
                return "updateUser";
            }
            return "redirect:/userSystem/index";
        } else {
            return "redirect:/userSystem/index";
        }

    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")long id,Model model,HttpSession session){
        User loggedUser = (User) session.getAttribute("loggedUser");
//        User existingUser = userSer.getUser(id);

        if(loggedUser !=null && loggedUser.getUserId()== id){
            userSer.deleteUser(id);
            return "redirect:/LogIn/index";
        }else{
            return "redirect:/userSystem/index";
        }
    }

    @GetMapping("/showAllItem")
    public String showAllItemPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
            List<Item> itemList = userSer.showAllItem();
            model.addAttribute("itemList", itemList);
            return "showAllItem";

    }


    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("itemId") long itemId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session, Model model) {
        User user = (User)  session.getAttribute("loggedUser");
        userSer.addToCart(user, itemId, quantity);
        model.addAttribute("message", "Item added to cart!");
        return "redirect:/userSystem/showAllItem";
        }

    @PostMapping("/buyAll")
    public String buyAll(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        userSer.setOrder(loggedInUser);
        userSer.clearCartItem(loggedInUser);
        userSer.clearCart(loggedInUser);


        return "redirect:/userSystem/showAllItem";
    }


    @GetMapping("/viewCart")
    public String viewCart(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        List<Cart> cart = userSer.getAllCartsByUser(loggedInUser);
        model.addAttribute("cart", cart);
        return "viewCart";
    }

    @GetMapping("/viewCartItem")
    public String viewCartItem(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        List<CartItem> cartItems = userSer.getAllCartItemsByUser(loggedInUser);
        model.addAttribute("cartItems",cartItems);
        return "viewCartItem";
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("itemId") long itemId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        userSer.deleteCart(loggedInUser.getUserId());
        return "redirect:/userSystem/viewCart";
    }

    @GetMapping("/viewHistory")
    public String OrderHistory(HttpSession session,Model model){
        User loggedUser = (User) session.getAttribute("loggedUser");
        List<Order> orders = userSer.getAllOrdersLoggedUser(loggedUser);
        model.addAttribute("orders",orders);
        return "viewOrders";
    }

}
