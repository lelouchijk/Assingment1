package com.sell.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sell.model.Item;
import com.sell.repository.ItemRepository;
@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepo;


	public void saveItem(Item i) {
		itemRepo.save(i);
	}
	
	public void updateItem(long id, Item i) {
		i.setItemId(id);
		itemRepo.save(i);
	}
	
	public void deleteItem(long id) {
		itemRepo.deleteById(id);
	}
	
	public List<Item> showAll(){
		return itemRepo.findAll();
	}
	
	public Optional<Item> showItemById(long id){
		return itemRepo.findById(id);
	}
	
	public Item getItem(long id) {
		return itemRepo.findById(id).get();
	}
}
