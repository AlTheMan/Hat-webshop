package com.example.Dist_sys_lab1_webshop.Model.Item;

import java.util.*;

public class ItemHandler {


	/**
	 * Fetches a list of all items from the database and returns a deep copy of the item objects inside.
	 * @return
	 */

	public static ArrayList<Item> getAllItemsFromDb()  {
		ArrayList<Item> itemCollection = Item.getDBItemsAll();
		ArrayList<Item> copy = new ArrayList<>();
		for (Item item : itemCollection) {
			Item copyItem = Item.getCopy(item);
			copy.add(copyItem);
		}
		return copy;

	}

	/**
	 *
	 * @param values Only contains values that is going to be updated.
	 *               Values that are not in this map will be set to null.
	 */
	public static void updateItem(HashMap<String, String> values) {
		Item item = createItemFromHashmap(values);
		int id = Integer.parseInt(values.get("itemId"));
		item.setId(id);
		Item.updateItemById(item);
	}


	/**
	 *
	 * @param values for an item stored in a hashmap
	 * @return an item with members that may have null values (or -1 if not nullable)
	 */
	private static Item createItemFromHashmap(HashMap<String, String> values) {
		Item item = new Item();

		item.setName(values.get("itemName"));
		item.setDescription(values.get("descriptionName"));
		item.setImageSrc(values.get("itemIMG"));

		//TODO: uppdatera item.setCategory() med värdet från item.getCategoryID(), som man gör en lookup på i databasen vilken kategori det ID:t motsvarar.

		String categoryIDString = values.get("itemCategoryId");
		String quantityString = values.get("itemQuantity");
		String priceString = values.get("itemPrice");

		double price = -1;
		int quantity = -1;
		int categoryID = -1;
		if (priceString != null) {
			price = Double.parseDouble(priceString);
		}
		if (quantityString != null) {
			quantity = Integer.parseInt(quantityString);
		}
		if(categoryIDString!=null){
			categoryID=Integer.parseInt(categoryIDString);
		}

		item.setPrice(price);
		item.setQuantity(quantity);
		item.setCategoryId(categoryID);

		return item;
	}

	/**
	 * Add one item to the database
	 * @param values a hashmap containing name and parameter of the item.
	 */
	public static boolean addItemToDb(HashMap<String, String> values) {
		for (String value : values.values()) {
			if (value.isEmpty()) return false;
		}
		return Item.addItemToDB(createItemFromHashmap(values));
	}

	/**
	 * Removes one item from the database
	 * @param id the id of the item to be removed.
	 */
	public static void removeItem(int id) {
		Item.removeItemById(id);
	}

	/**
	 * Gets an item from the database
	 * @param id the id of the item to get.
	 * @return the item of that id (if it exists)
	 */

	public static Item getItemByID(int id){
		return Item.getDBItemByID(id);
	}


}
