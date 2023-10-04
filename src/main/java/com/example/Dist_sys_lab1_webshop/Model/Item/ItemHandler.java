package com.example.Dist_sys_lab1_webshop.Model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ItemHandler {



	public static ArrayList<Item> getAllItems()  {
		Collection<Item> itemCollection = Item.getDBItemsAll();
		return new ArrayList<>(itemCollection);

	}

	public static void updateItem(HashMap<String, String> values) {
		String id = values.get("itemId");
		Item.updateItemById(Integer.parseInt(id), values);
	}

	public static void addItem(HashMap<String, String> values) {
		Item item = new Item(
				values.get("itemName"),
				values.get("descriptionName"),
				Double.parseDouble(values.get("itemPrice")),
				Integer.parseInt(values.get("itemQuantity")),
				values.get("itemIMG"));
		Item.addItemToDB(item);
	}

	public static void removeItem(int id) {
		Item.removeItemById(id);
	}


	public static Item getItemByID(int id){
		return Item.getDBItemByID(id);
	}


}
