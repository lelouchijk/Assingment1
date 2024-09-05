package com.sell.controller;

import com.sell.model.Category;
import com.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorySystem")
public class CategoryController {

    @Autowired
    private CategoryService categorySer;

    @GetMapping("/index")
    public String startPage(){
        return "categoryindex";
    }

    @GetMapping("/categoryInsert")
    public String categoryInsertPage(Model model){
        model.addAttribute("category",new Category());
        return "newCategory";
    }

    @PostMapping("/addCategory")
    public String addNewCategory(@ModelAttribute("category")Category c){
        categorySer.saveCategory(c);
        return "redirect:/categorySystem/index";
    }

    @GetMapping("/updateCategory/{id}")
    public String categoryUpdatePage(@PathVariable("id")long id,Model model){
        model.addAttribute("category",categorySer.getCategory(id));
        return "updateCategory";
    }

    @PostMapping("/updateProcess/{id}")
    public String categoryDeletePage(@PathVariable("id")long id){
        categorySer.deleteCategory(id);
        return "redirect:/categorySystem/showCategory";
    }

    @GetMapping("/deleteCategory/{id}")
    public String categoryDeletePage(@PathVariable("id") long id,Model model){
        categorySer.deleteCategory(id);
        return "redirect:/categorySystem/showCategory";
    }

    @GetMapping("/showCategory")
    public String categoryShowPage(Model model){
        model.addAttribute("categoryList",categorySer.showAll());
        return "showCategory";
    }
}
