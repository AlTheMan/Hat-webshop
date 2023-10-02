package com.example.Dist_sys_lab1_webshop.Model.Item;

import com.example.Dist_sys_lab1_webshop.Database.ItemDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ItemHandler {



	public static ArrayList<Item> getItems()  {

		Collection<Item> itemCollection = ItemDB.getItemsFromDb();
		return new ArrayList<>(itemCollection);

	}


}
