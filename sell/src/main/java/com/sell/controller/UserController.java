package com.sell.controller;

import com.sell.model.*;
import com.sell.repository.RoleRepository;
import com.sell.service.AdminService;
import com.sell.service.LoginService;
import com.sell.service.ShopService;
import com.sell.service.UserService;
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

    private final HttpSession session;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    public UserController(UserService userSer, ShopService shopSer, HttpSession session, LoginService loginSer, AdminService adminSer) {
        this.userSer = userSer;
        this.shopSer = shopSer;
        this.session = session;
        this.loginSer = loginSer;
        this.adminSer = adminSer;
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

    @GetMapping("/updateShop/{id}")
    public String updateShopPage(@PathVariable("id") long id, HttpSession session, Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop shop = shopSer.getShop(id);
        if(shop!=null &&shop.getStatus()=="" && shop.getShopOwner().getUserId() == loggedInUser.getUserId()) {
            model.addAttribute("shop", shopSer.getShop(id));
            return "updateShop";
        }else {
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdateShop";
        }

    }


    @PostMapping("/updateShopProcess/{id}")
    public String shopUpdateProcess(@PathVariable("id") long id,@ModelAttribute("shop") Shop shop, HttpSession session, Model model){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop existingShop = shopSer.getShop(id);
        if(existingShop!=null && existingShop.getShopOwner().getUserId() == loggedInUser.getUserId()){
            shopSer.updateShopData(id,shop);
            return "redirect:/userSystem/showShop";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }

    }

    @GetMapping("/deleteShopPage/{id}")
    public String shopDeletePage(@PathVariable("id") long id, Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
//        if (loggedInUser == null) {
//            return "redirect:/LogIn/index";
//        }
        Shop shop = shopSer.getShop(id);
        if(shop != null && shop.getShopOwner().getUserId() == loggedInUser.getUserId()){
            shopSer.deleteShop(id);
            return "redirect:/userSystem/index";
        }else {
            //            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }


    }

    @GetMapping("/showShop")
    public String showShopPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");


        model.addAttribute("shopList",shopSer.getShopsByUser(loggedInUser));
        return "showShop";
    }

//    @GetMapping("/userShops")
//    public String getUserShops(@RequestParam("userId") Long userId, Model model) {
//
//        User user = userSer.getUserById(userId);
//
//        List<Shop> shops = shopService.getShopsByUser(user);
//
//        model.addAttribute("shops", shops);
//
//        return "userShops";
//    }

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
        Item existingItem = userSer.getItem(id);
        if( existingItem!=null & existingItem.getUser().getUserId() == loggedUser.getUserId()){
            model.addAttribute("item",userSer.getItem(id));
            return "showItemCustomer";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }
    }

    @PostMapping("/updateItemProcess/{id}")
    public String updateItemProcess(@PathVariable("id")long id, @ModelAttribute("item") Item item, @ModelAttribute("category")Category category,HttpSession session,Model model){
        User loggedUser = (User) session.getAttribute("loggedUser");
        Item existingItem = userSer.getItem(id);
        if( existingItem!=null & existingItem.getUser().getUserId() == loggedUser.getUserId()){
            userSer.updateItem(id, item, category);
            return "redirect:/userSystem/index";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }
    }

    @GetMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable("id") long id, Model model, HttpSession session){
        User loggedUser = (User) session.getAttribute("loggedUser");
        Item existingItem = userSer.getItem(id);
        if( existingItem!=null & existingItem.getUser().getUserId() == loggedUser.getUserId()){
            userSer.deleteItem(id);
            return "redirect:/userSystem/index";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }
    }

    @GetMapping("/showItem")
    public String showItemPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");

        List<Item> itemList = userSer.getItemsByUser(loggedInUser);
        model.addAttribute("itemList",itemList);
        return "showItemCustomer";
    }

    @GetMapping("/updateUser")
    public String updateUserInfo(@PathVariable("id") long id, Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");
        User existingUser = userSer.getUser(id);

        if(existingUser!=null && existingUser.getUserId() == loggedInUser.getUserId()){
            model.addAttribute("user",userSer.getUser(id));
            return "updateUser";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }
    }
    @PostMapping("/updateUserProcess")
    public String updateUserProcess(@PathVariable("id")long id,@ModelAttribute("user")User user,HttpSession session,Model model){
        User loggedInUser = (User) session.getAttribute("loggegUser");
        User existingUser = userSer.getUser(id);

        if(existingUser!=null &&existingUser.getUserId()==loggedInUser.getUserId()){
            userSer.updateUser(id,user);
            return "redirect:/userSystem/updateUser";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
        }
    }
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id")long id,Model model,HttpSession session){
        User loggedUser = (User) session.getAttribute("loggedUser");
        User existingUser = userSer.getUser(id);

        if(existingUser !=null && existingUser.getUserId()==loggedUser.getUserId()){
            userSer.deleteUser(id);
            return "redirect:/userSystem/updateUser";
        }else{
//            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "errorUpdatePage";
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
        System.out.println("user choice item :"+ itemId);
        User user = (User)  session.getAttribute("loggedUser");
        System.out.println(user);
            userSer.addToCart(user, itemId, quantity);
            model.addAttribute("message", "Item added to cart!");
            return "redirect:/userSystem/showAllItem";
        }






    @PostMapping("/buyNow")
    public String buyNow(@RequestParam("itemId") long itemId,
                         @RequestParam("quantity") int quantity,
                         HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Optional<Item> itemOpt = userSer.showByItemId(itemId);

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            int total = (int) (item.getPrice() * quantity);
            Order order = new Order(quantity, total, item.getShop(), List.of(item), loggedInUser);
            userSer.saveOrder(order);
            model.addAttribute("message", "Order placed successfully!");
        } else {
            model.addAttribute("error", "Item not found");
        }

        return "redirect:/userSystem/showAllItem";
    }

    @GetMapping("/viewCart")
    public String viewCart(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        List<Cart> cartItems = userSer.getAllCarts(loggedInUser.getUserId());
        model.addAttribute("cartItems", cartItems);
        return "viewCart";
    }

    @GetMapping("/viewCartProcess")
    public String viewCartProcess(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        List<Cart> cartList = userSer.getAllCarts(loggedInUser.getUserId());

        model.addAttribute("cartList", cartList);

        return "viewCart";
    }

    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("itemId") long itemId, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        userSer.deleteCart(loggedInUser.getUserId());
        return "redirect:/userSystem/viewCart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");

        userSer.checkoutCart(loggedInUser.getUserId());

        return "redirect:/userSystem/orderConfirmation";  // Or another page
    }

    @PostMapping("/placeOrder")
    public String placeOrder(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        userSer.placeOrder(loggedInUser);

        return "redirect:/viewOrders";
    }










}
