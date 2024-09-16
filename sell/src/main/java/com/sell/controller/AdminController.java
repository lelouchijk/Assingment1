package com.sell.controller;

import com.sell.model.Category;
import com.sell.model.Item;
import com.sell.model.Shop;
import com.sell.model.User;
import com.sell.service.AdminService;
import com.sell.service.ShopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/adminSystem")
public class AdminController {

    private final AdminService adminSer;
    private final ShopService shopSer;



    @Autowired
    public AdminController(AdminService adminSer,ShopService shopSer) {
        this.adminSer = adminSer;
        this.shopSer = shopSer;
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
        return "redirect:/adminSystem/categoryindex";
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
        return "showCategory";
    }

    @GetMapping("/itemindex")
    public String itemStartPage() {
        return "itemindex";
    }

    @GetMapping("/itemInsert")
    public String itemInsertPage(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("categoryList", adminSer.getAllCategory());
        return "newItem";

    }

    @PostMapping("/addItem")
    public String addNewItem(Model model,@ModelAttribute("item") Item i) {
        adminSer.saveItem(i);
        return "redirect:/adminSystem/itemindex";
    }

    @GetMapping("updateItem/{id}")
    public String itemUpdatePage(@PathVariable("id") long id,Model model) {
        model.addAttribute("item",adminSer.getItem(id));
        model.addAttribute("categoryList",adminSer.getAllCategory());
        return "updateItem";
    }

    @PostMapping("/updateItemProcess/{id}")
    public String processUpdate(@PathVariable("id")long id,@ModelAttribute("item")Item item,Model model) {

        adminSer.updateItem1(id, item);
        return "redirect:/adminSystem/showItem";

    }



    @GetMapping("/deleteItem/{id}")
    public String itemDeletePage(@PathVariable("id")long id,Model model) {
        adminSer.deleteItem(id);
        return "redirect:/adminSystem/itemindex";

    }

    @GetMapping("/showItem")
    public String itemShowPage(Model model) {
        model.addAttribute("itemList",adminSer.showAllItem());
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

       // shop.setStatus("Approved");
     //   shop.setVerify(true);
//        shop.setShopOwner();
        shopSer.updateShop(shopId);

        return "redirect:/adminSystem/pendingShops";
    }

    @PostMapping("/rejectShop/{shopId}")
    public String rejectShop(@PathVariable("shopId") long shopId,Model model) {
        shopSer.deleteShop(shopId);

        return "redirect:/adminSystem/pendingShops";
    }






}
