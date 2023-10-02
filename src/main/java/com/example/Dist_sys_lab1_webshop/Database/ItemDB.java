package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class ItemDB extends Item {


	private ItemDB(int id, String name, String description, double price, int quantity) {
		super(id, name, description, price, quantity);
	}

	public static Collection<Item> getItemsFromDb() throws SQLException {
		DBManager manager = DBManager.getInstance();
		Connection connection = manager.getConnection();

		// Create a statement to execute SQL queries
		Statement statement = connection.createStatement();

		// Define your SQL query
		String query = "SELECT id, name, description FROM Item"; // Assuming the table name is 'Item'

		// Execute the query
		ResultSet rs = statement.executeQuery(query);

		// Process the results and create Item objects
		List<Item> items = new ArrayList<>();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String description = rs.getString("description");
			items.add(new Item(id, name, description));
		}

		// Clean up resources
		rs.close();
		statement.close();

		return items;
	}


}
