package com.sell.controller;

import com.sell.external.itemDTO;
import com.sell.external.itemMapper;
import com.sell.model.*;
import com.sell.repository.CategoryRepository;
import com.sell.repository.ItemRepository;
import com.sell.service.AdminService;
import com.sell.service.DeliveryService;
import com.sell.service.ShopService;
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
@RequestMapping("/adminSystem")
public class AdminController {

    private final AdminService adminSer;
    private final ShopService shopSer;

    private final DeliveryService deliSer;
    private final ItemRepository itemRepo;
    private final CategoryRepository categoryRepo;


    @Autowired
    public AdminController(AdminService adminSer, ShopService shopSer, DeliveryService deliSer, ItemRepository itemRepo, CategoryRepository categoryRepo) {
        this.adminSer = adminSer;
        this.shopSer = shopSer;
        this.deliSer = deliSer;
        this.itemRepo = itemRepo;
        this.categoryRepo = categoryRepo;
    }

    @GetMapping("/index")
    public String startPage(){
        return "index";
    }

    @GetMapping("/categoryindex")
    public String categorystartPage(){
        return "categoryindex";
    }

    @GetMapping("/categoryInsert")
    public String categoryInsertPage(Model model){
        model.addAttribute("category",new Category());
        return "newCategory";
    }

    @PostMapping("/addCategory")
    public String addNewCategory(@ModelAttribute("category")Category c){
        adminSer.saveCategory(c);
        return "redirect:/adminSystem/showCategory";
    }

    @GetMapping("/updateCategory/{id}")
    public String categoryUpdatePage(@PathVariable("id")long id, Model model){
        model.addAttribute("category",adminSer.getCategory(id));
        return "updateCategory";
    }

    @PostMapping("/updateCategoryProcess/{id}")
    public String categoryUpdatePage(@PathVariable("id")long id,@ModelAttribute("category")Category c,Model model){

        adminSer.updateCategory(id,c);
        return "redirect:/adminSystem/showCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String categoryDeletePage(@PathVariable("id") long id,Model model){
        adminSer.deleteCategory(id);
        return "redirect:/adminSystem/showCategory";
    }

    @GetMapping("/showCategory")
    public String categoryShowPage(Model model){
        model.addAttribute("categoryList",adminSer.showAllCategory());
        model.addAttribute("categories",new Category());
        return "showCategory";
    }

    @GetMapping("/itemindex")
    public String itemStartPage() {
        return "itemindex";
    }

    @GetMapping("/itemInsert")
    public String itemInsertPage(Model model) {
        model.addAttribute("item", new itemDTO());
        model.addAttribute("categoryList", adminSer.getAllCategory());
        return "newItem";

    }

    @GetMapping("/image/{itemId}")
    @ResponseBody
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long itemId) {
        Optional<Item> item = adminSer.getItemById(itemId);
        byte[] image = item.get().getItemImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }


    @PostMapping("/addItem")
    public String addNewItem(Model model, @ModelAttribute itemDTO itemDTO, HttpSession session) throws IOException  {
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
        Category selectedCategory = adminSer.findCategoryById(itemDTO.getCategoryId());
        i.setCategory(selectedCategory);
        adminSer.saveItem(i);
        return "redirect:/adminSystem/showItem";
    }

    @GetMapping("updateItem/{id}")
    public String itemUpdatePage(@PathVariable("id") long id,Model model) {
        Optional<Item> existingItem = adminSer.getItem(id);
        model.addAttribute("itemDTO", itemMapper.convertToDTO(existingItem.get()));
        model.addAttribute("categoryList", adminSer.getAllCategory());
        return "updateItem";
    }


    @PostMapping("/updateItemProcess/{id}")
    public String processUpdate(@PathVariable("id") long id, @ModelAttribute("itemDTO") itemDTO dto,
                                @RequestParam("categoryId") long categoryId, HttpSession session,Model model) throws IOException {
        User loggedUser = (User) session.getAttribute("loggedUser");
        Category category = adminSer.getCategoryById(categoryId); // Fetch category by ID
        if (category == null) {
            return "redirect:/adminSystem/showItem";
        }

        Optional<Item> existingItemOpt = adminSer.getItemById(id);

        Item item = itemMapper.convertToEntity(dto, category, null, loggedUser);
        adminSer.updateItem(id, item);

        return "redirect:/adminSystem/showItem";

    }



    @GetMapping("/deleteItem/{id}")
    public String itemDeletePage(@PathVariable("id")long id,Model model) {
        adminSer.deleteItem(id);
        return "redirect:/adminSystem/showItem";

    }


    @GetMapping("/showItem")
    public String itemShowPage(Model model) {
        List<Item> items = adminSer.showAllItem();
        model.addAttribute("itemList", items);
        return "showItem";
    }

    @GetMapping("/pendingShops")
    public String viewPendingShops(Model model) {
        List<Shop> pendingShops = shopSer.findShopsByStatus("Pending");
        model.addAttribute("pendingShops", pendingShops);

        return "adminPendingShops";
    }

    @PostMapping("/approveShop/{shopId}")
    public String approveShop(@PathVariable("shopId") long shopId,Model model) {

         shopSer.updateShop(shopId);

        return "redirect:/adminSystem/pendingShops";
    }

    @PostMapping("/rejectShop/{shopId}")
    public String rejectShop(@PathVariable("shopId") long shopId,Model model) {
        shopSer.deleteShop(shopId);

        return "redirect:/adminSystem/pendingShops";
    }

    @GetMapping("/pendingDelivery")
    public String viewPendingDelivery(Model model) {
//        List<Shop> pendingShops = shopSer.findShopsByStatus("Pending");
//        model.addAttribute("pendingShops", pendingShops);
        List<Delivery> pendingDeilvery = deliSer.findDeliveryByStatus("Pending");
        model.addAttribute("pendingDelivery",pendingDeilvery);
        return "adminPendingDelivery";
    }

    @PostMapping("/approveDelivery/{deliveryId}")
    public String approveDelivery(@PathVariable("deliveryId") long deliveryId,Model model) {
        adminSer.updateDeliveryByAdmin(deliveryId);
        return "redirect:/adminSystem/pendingDelivery";
    }

    @PostMapping("/rejectDelivery/{deliveryId}")
    public String rejectDelivery(@PathVariable("deliveryId") long deliveryId,Model model) {
        deliSer.deleteDelivery(deliveryId);

        return "redirect:/adminSystem/pendingDelivery";
    }




}
