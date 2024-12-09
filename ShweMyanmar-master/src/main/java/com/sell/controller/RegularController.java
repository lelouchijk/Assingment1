package com.sell.controller;

import com.sell.external.itemDTO;
import com.sell.external.itemMapper;
import com.sell.model.Item;
import com.sell.service.AdminService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ecommerce")
public class RegularController {

    private final AdminService adminSer;

    public RegularController(AdminService adminSer) {
        this.adminSer = adminSer;
    }

    @GetMapping("/main")
    public String showMainPage(Model model){
        List<Item> itemList = adminSer.showAllItem();
        List<Item> limitedItems = itemList.stream().limit(6).collect(Collectors.toList());
        model.addAttribute("itemList",limitedItems);
        return "ecommerce";
    }

    @GetMapping("/item")
    public String showItemPage(Model model){
        List<Item> itemList = adminSer.showAllItem();
        model.addAttribute("itemList",itemList);
        return "ecommerceItem";
    }

    @GetMapping("/viewItem/{id}")
    public String viewItem(@PathVariable("id") long id,Model model){
        Optional<Item> thisItem = adminSer.getItem(id);
        model.addAttribute("itemDTO", itemMapper.convertToDTO(thisItem.get()));
        return "itemDetailPage";
    }

    @GetMapping("/image/{itemId}")
    @ResponseBody
    public ResponseEntity<byte[]> getItemImage(@PathVariable Long itemId) {
        Optional<Item> item = adminSer.getItemById(itemId);
        byte[] image = item.get().getItemImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }


}
