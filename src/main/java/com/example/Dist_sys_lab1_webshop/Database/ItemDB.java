package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ItemDB extends Item {

	private ItemDB(int id, String name, String description, double price, int quantity) {
		super(id, name, description, price, quantity);
	}

	public static Collection<Item> getDBItemsAll() {
		Connection con = DBManager.getConnection();
		Collection<Item> itemCollection = new ArrayList<>();
		try {

			Statement statement = con.createStatement();
			String query = "SELECT * from item";
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				double price = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				itemCollection.add(new ItemDB(id, name, description, price, quantity));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return itemCollection;
	}


}
