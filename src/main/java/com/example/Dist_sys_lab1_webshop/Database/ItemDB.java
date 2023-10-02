package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ItemDB extends Item {


	private ItemDB(int id, String name, String description, double price, int quantity) {
		super(id, name, description, price, quantity);
	}

	public static Collection<Item> getItemsFromDb() throws SQLException {
		DBManager manager = DBManager.getInstance();
		manager.getConnection().createStatement();

		
	}


}
