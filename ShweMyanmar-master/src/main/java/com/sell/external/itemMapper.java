package com.sell.external;

import com.sell.model.Category;
import com.sell.model.Item;
import com.sell.model.Shop;
import com.sell.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

public class itemMapper {

    //Below method are Factory Design
    public static itemDTO convertToDTO(Item item) {
        itemDTO dto = new itemDTO();
        dto.setItemId(item.getItemId());
        dto.setItemName(item.getItemName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());

        if (item.getItemImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(item.getItemImage());
            dto.setBase64Image(base64Image);
        }
        return dto;
    }

    public static Item convertToEntity(itemDTO dto, Category category, Shop shop, User user) throws IOException {
        Item item = new Item();
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        if (dto.getItemImage() != null && !dto.getItemImage().isEmpty()) {
            item.setItemImage(dto.getItemImage().getBytes());
        }

        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        item.setDate(dto.getDate());
        item.setCategory(category);
        item.setShop(shop);
        item.setUser(user);
        return item;
    }
}
