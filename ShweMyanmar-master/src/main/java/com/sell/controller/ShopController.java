package com.sell.controller;

import com.sell.external.itemDTO;
import com.sell.external.itemMapper;
import com.sell.model.*;
import com.sell.repository.DeliveryRepository;
import com.sell.repository.OrderRepository;
import com.sell.repository.ShopRepository;
import com.sell.service.AdminService;
import com.sell.service.DeliveryService;
import com.sell.service.ShopService;
import com.sell.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shopSystem")
public class ShopController {

    private final ShopService shopSer;

    private final HttpSession session;

    private final UserService userSer;

    private final AdminService adminSer;

    private final DeliveryService deliverySer;

    private final DeliveryRepository deliveryRepo;

    private final ShopRepository shopRepo;

    private final OrderRepository orderRepo;

    @Autowired
    public ShopController(ShopService shopSer, HttpSession session, UserService userSer, AdminService adminSer, DeliveryService deliverySer, DeliveryRepository deliveryRepo, ShopRepository shopRepo, OrderRepository orderRepo) {
        this.shopSer = shopSer;
        this.session = session;
        this.userSer = userSer;
        this.adminSer = adminSer;
        this.deliverySer = deliverySer;
        this.deliveryRepo = deliveryRepo;
        this.shopRepo = shopRepo;
        this.orderRepo = orderRepo;
    }

    @GetMapping("/index")
    public String showMainPage(HttpSession session,Model model){
        User user = (User) session.getAttribute("loggedUser");
        System.out.println(user.getUserId());
        model.addAttribute("user",user);
        return "shopAdminPage";
    }

    @GetMapping("/updateShop/{id}")
    public String updateShopPage(@PathVariable("id") long id, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop shop = shopSer.getShop(id);
        if (shop != null && shop.getStatus() == "Approved" && shop.isVerify() && shop.getShopOwner().getUserId() == loggedInUser.getUserId()) {
            model.addAttribute("shop", shopSer.getShop(id));

        }
        return "updateShop";
    }


    @PostMapping("/updateShopProcess/{id}")
    public String shopUpdateProcess(@PathVariable("id") long id, @ModelAttribute("shop") Shop shop, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop existingShop = shopSer.getShop(id);
        if (shop != null && shop.getStatus() == "Approved" && shop.isVerify() && shop.getShopOwner().getUserId() == loggedInUser.getUserId()) {
            shopSer.updateShopData(id, shop);
            return "redirect:/userSystem/showShop";
        } else {
            return "redirect:/userSystem/index";
        }
    }

    @GetMapping("/deleteShopPage/{id}")
    public String shopDeletePage(@PathVariable("id") long id, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop shop = shopSer.getShop(id);
        if (shop != null && shop.getShopOwner().getUserId() == loggedInUser.getUserId()) {
            shopSer.deleteShop(id);

            return "redirect:/userSystem/index";
        } else {
            //            model.addAttribute("errorMessage", "Unauthorized access or shop does not exist.");
            return "redirect:/userSystem/index";
        }


    }


    @GetMapping("/categoryIndex")
    public String categoryPage() {
        return "categoryPageShop";
    }


    @GetMapping("/categoryInsert")
    public String categoryInsert(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        model.addAttribute("category", new Category());
        return "";
    }
    @PostMapping("/addCategory")
    public String addNewCategory(@ModelAttribute("category") Category c, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedUser");
        Shop shopOwner = shopRepo.findByShopOwner(loggedInUser).get();
        shopSer.saveCategory1(c,shopOwner.getShopId());

        return "redirect:/shopSystem/showCategory";
    }


    @GetMapping("/updateCategory/{id}")
    public String categoryUpdatePage(@PathVariable("id") long id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");

        model.addAttribute("category", shopSer.getCategory(id));
        return "updateCategoryShop";
    }

    @PostMapping("/updateCategoryProcess/{id}")
    public String updateCategoryProcess(@PathVariable("id") long id, Model model, HttpSession session, @ModelAttribute("category") Category category) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Shop shop = loggedUser.getShops().get(0);
        if (loggedUser != null && category != null) {
            shopSer.updateCategory(shop, category, id);

        }
            return "redirect:/shopSystem/showCategory";

    }

    @GetMapping("/deleteCategory/{id}")
    public String categoryDeletePage(@PathVariable("id") long id, Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Category existingCategory = shopSer.getCategory(id);
        if (existingCategory != null && existingCategory.getShop().getShopOwner().getUserId() == loggedUser.getUserId())
            shopSer.deleteCategory(id);
        return "redirect:/shopSystem/showCategory";
    }

    @GetMapping("showCategory")
    public String categoryShopShowPage( Model model, HttpSession session) {
        User loggeduser = (User) session.getAttribute("loggedUser");
//        Shop shop = loggeduser.getShops().get(0);
//        List<Category> categoryList = shopSer.getCategoryByShop(loggeduser);
        model.addAttribute("categoryList",shopSer.getCategoryByShop(loggeduser));
        model.addAttribute("category", new Category());
        return "showShopCategory";
    }

    @GetMapping("itemIndex")
    public String itemStartPage() {
        return "itemIndexShop";
    }

    @GetMapping("itemInsert")
    public String itemInsertPage(Model model, HttpSession session) {
        model.addAttribute("item", new itemDTO());
        model.addAttribute("categoryList", shopSer.getAllCategory());
//        i think i might need addAttribute at shop and user
//        model.addAttribute("")
        return "newItemShop";
    }

    @GetMapping("/image/{itemId}")
    @ResponseBody
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long itemId) {
        Optional<Item> item = adminSer.getItemById(itemId);
        byte[] image = item.get().getItemImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }


    @PostMapping("/addItem")
    public String addNewItem(Model model, @ModelAttribute("item") itemDTO itemDTO, HttpSession session)throws IOException {
        Item i = new Item();
        User loggedInUser = (User) session.getAttribute("loggedUser");
        i.setUser(loggedInUser);
        i.setItemName(itemDTO.getItemName());
        i.setPrice(itemDTO.getPrice());
        i.setQuantity(itemDTO.getQuantity());
        i.setDate(itemDTO.getDate());
        if (itemDTO.getItemImage() != null && !itemDTO.getItemImage().isEmpty()) {
            i.setItemImage(itemDTO.getItemImage().getBytes());
        }
        Category selectedCategory = shopSer.findCategoryById(itemDTO.getCategoryId());
        i.setCategory(selectedCategory);
        i.setShop(loggedInUser.getShops().get(0));
        shopSer.saveItem(i);
        return "redirect:/shopSystem/showItem";
    }


    @GetMapping("/updateItem/{id}")
    public String updateItem(@PathVariable("id") long id, HttpSession session, Model model) {
        Optional<Item> existingItem = shopSer.getItem(id);
        model.addAttribute("itemDTO", itemMapper.convertToDTO(existingItem.get()));
        model.addAttribute("categoryList", shopSer.getAllCategory());
        return "updateItemShop";
    }


    @PostMapping("/updateItemProcess/{id}")
    public String updateItemProcess(@PathVariable("id") long id, @ModelAttribute("itemDTO") itemDTO dto,
                                    @RequestParam("categoryId") long categoryId,
                                    HttpSession session) throws IOException {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Category category = adminSer.getCategoryById(categoryId); // Fetch category by ID
        if (category == null) {
            return "redirect:/shopSystem/showItem";
        }
        Shop eshop = loggedUser.getShops().get(0);

        Item item = itemMapper.convertToEntity(dto, category, eshop, loggedUser);
        shopSer.updateItem(id,item);
        return "redirect:/shopSystem/showItem";
    }

    @GetMapping("deleteItem/{id}")
    public String deleteItem(@PathVariable("id")long id,Model model,HttpSession session){
//        User loggedUser = (User) session.getAttribute("loggedUser");
        shopSer.deleteItem(id);
        return "redirect:/shopSystem/showItem";
    }

    @GetMapping("/showItem")
    public String showItemPage(Model model, HttpSession session){
        User loggedInUser = (User) session.getAttribute("loggedUser");

        List<Item> itemList = userSer.getItemsByUser(loggedInUser);
        model.addAttribute("itemList",itemList);
        return "showItemShop";
    }





    @GetMapping("/pendingDeliveries")
    public String viewPendingDeliveries(HttpSession session, Model model) {
        User shopOwner = (User) session.getAttribute("loggedUser");
        List<Long> shopIds = shopRepo.findShopIdsByOwnerId(shopOwner.getUserId());
        if (!shopIds.isEmpty()) {
            Long shopId = shopIds.get(0);
            List<Delivery> pendingDeliveries = deliveryRepo.findByShopsAndVerifyByShopFalse(shopId);
            model.addAttribute("pendingDeliveries", pendingDeliveries);
            model.addAttribute("shopId", shopId);
        } else {

            model.addAttribute("error", "No shops found for the current user.");
        }
        return "ShopPendingDeliveries";
    }

    @GetMapping("/pendingOrder")
    public String viewOrderByThisShop(HttpSession session,Model model){
        User shopOwner = (User) session.getAttribute("loggedUser");
        List<Long> shopIds = shopRepo.findShopIdsByOwnerId(shopOwner.getUserId());

        if(!shopIds.isEmpty()){
            long shopId = shopIds.get(0);
            List<Order> pendingOrder = orderRepo.findByShopAndStatus(shopId);
//            List<Delivery> delivery = deliveryRepo.findByShop(shopOwner.getShops().get(0)) && deliveryRepo.findByStatus();
            model.addAttribute("pendingOrder",pendingOrder);
            model.addAttribute("shopId",shopId);
            model.addAttribute("deliverys",deliveryRepo.findByShop(shopOwner.getShops().get(0)));
        }
        return "ShopPendingOrder";
    }

    @PostMapping("/assigingOrderToDelivery/{orderId}")
    public String assigingOrderToDelivery(@PathVariable ("orderId") long orderId,HttpSession session){

        User shopOwner = (User) session.getAttribute("loggedUser");

//        List<Delivery> freeDelivery = deliverySer.findDeliveryByStatus("Free");
        List<Delivery> shopDelivery = deliveryRepo.findByShop(shopOwner.getShops().get(0));

        if(!shopDelivery.isEmpty()) {
//            shopSer.assignDeliveryToOrder(orderId, deliveryPeron.getDeliveryId());
            Delivery deliveryPerson = shopDelivery.get(0);
            shopSer.assignDeliveryToOrder(orderId,deliveryPerson.getDeliveryId());

        }
        return "redirect:/shopSystem/pendingOrder";

    }



    @PostMapping("/assigingOrderToShopDelivery/{orderId}")
    public String assigingOrderToShopDelivery(@PathVariable ("orderId") long orderId){


        List<Delivery> freeDelivery = deliverySer.findDeliveryByStatus("Free");
//        List<Delivery> shopDelivery = deliveryRepo.findDeliveryByShop()
        if(!freeDelivery.isEmpty()) {
//            shopSer.assignDeliveryToOrder(orderId, deliveryPeron.getDeliveryId());
            Delivery deliveryPerson = freeDelivery.get(0);
            shopSer.assignDeliveryToOrder(orderId,deliveryPerson.getDeliveryId());

        }
        return "redirect:/shopSystem/pendingOrder";

    }




    @PostMapping("/confirmDeliveryPerson/{deliveryId}")
    public String confirmDeliveryPerson(@PathVariable("deliveryId") long deliveryId) {
        shopSer.confirmDeliveryPerson(deliveryId);
        return "redirect:/shopSystem/pendingDeliveries";
    }

    @PostMapping("/rejectDeliveryPerson/{deliveryId}")
    public String rejectDeliveryPerson(@PathVariable("deliveryId") long deliveryId){
        Delivery deliveryPerson = deliveryRepo.findById(deliveryId).get();
        deliveryPerson.setShop(null);

        return "redirect:/shopSystem/pendingDeliveries";
    }

    @GetMapping("/showHistory")
    public String showHistory(HttpSession session,Model model){
        User shopOwner = (User) session.getAttribute("loggedUser");
        List<Order> history = orderRepo.findOrdersByUser(shopOwner);
        model.addAttribute("orderHistory", history);
        return "shopHistory";
    }


}
