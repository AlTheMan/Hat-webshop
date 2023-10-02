package com.example.Dist_sys_lab1_webshop.Model.Item;

import java.util.ArrayList;
import java.util.Collection;

public class ItemHandler {



	public static ArrayList<Item> getAllItems()  {
		Collection<Item> itemCollection = Item.getDBItemsAll();
		return new ArrayList<>(itemCollection);

	}


}
