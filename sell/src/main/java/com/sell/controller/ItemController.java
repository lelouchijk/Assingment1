package com.sell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sell.model.Item;
import com.sell.service.CategoryService;
import com.sell.service.ItemService;

@Controller
@RequestMapping("/itemSystem")
public class ItemController {
	@Autowired
	private ItemService itemSer;
	
	@Autowired
	private CategoryService categorySer;
	
	@GetMapping("/index")
	public String startPage() {
		return "index";
	}
	
	@GetMapping("/itemInsert")
	public String itemInsertPage(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("categoryList", categorySer.getAllCategory());
		return "newItem";
		
	}
	
	@PostMapping("/addItem")
	public String addNewItem(Model model,@ModelAttribute("item") Item i) {
		itemSer.saveItem(i);
		return "redirect:/itemSystem/index";
	}
	
	@GetMapping("updateItem/{id}")
	public String itemUpdatePage(@PathVariable("id") long id,Model model) {
		model.addAttribute("item",itemSer.getItem(id));
		return "updateItem";
	}
	@PostMapping("/updateprocess/{id}")
	public String processUpdate(@PathVariable("id")long id,@ModelAttribute("item")Item item,Model model) {
		itemSer.updateItem(id, item);
		return "redirect:/itemSystem/showItem";
		
	}
	
	@GetMapping("/deleteItem/{id}")
	public String itemDeletePage(@PathVariable("id")long id,Model model) {
		itemSer.deleteItem(id);
		return "redirect:/itemSystem/showItem";
		
	}
	
	@GetMapping("/showItem")
	public String itemShowPage(Model model) {
		model.addAttribute("itemList",itemSer.showAll());
		return "showItem";
		
	}
}
