package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.example.Dist_sys_lab1_webshop.Database.DBManager.connection;

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
			e.printStackTrace();
		}
		return itemCollection;
	}
	public static Item getDBItemByID(int idInput) {
		Connection con = DBManager.getConnection();
		Item item = null;
		String sql = "SELECT * from item WHERE ID= ?";
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, idInput);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String description = resultSet.getString("description");
					double price = resultSet.getDouble("price");
					int quantity = resultSet.getInt("quantity");
					item = new ItemDB(id, name, description, price, quantity);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public static boolean removeQuantityDBItemByID(Shoppingcart shoppingcart) throws SQLException{
		Connection con = DBManager.getConnection();
		Item item = null;
		String sql = "UPDATE item SET quantity = quantity - ? WHERE id = ?;";

		try (PreparedStatement statement = con.prepareStatement(sql)) {
			connection.setAutoCommit(false);  // Start transaction
			for(int i=0; i<shoppingcart.getItems().size(); i++){
				//int quantity = shoppingcart.getItems().get(i).getNrOfItems();
				//int id = shoppingcart.getItems().get(i).getItem().getId();
				//System.out.println("index,i: "+ i+", quantity: "+quantity+", id: " + id);
				statement.setInt(1, shoppingcart.getItems().get(i).getNrOfItems());
				statement.setInt(2, shoppingcart.getItems().get(i).getItem().getId());

				statement.executeUpdate();
				//Statement stmt = connection.createStatement();
				//stmt.executeUpdate("UPDATE table1 SET column1 = value1 WHERE condition1");
				//stmt.executeUpdate("UPDATE table2 SET column2 = value2 WHERE condition2");

			}


			connection.commit();  // Commit the transaction
		}
		finally {
			connection.setAutoCommit(true);  // End the transaction and set the connection back to default mode
		}
		return true; //Kolla om detta returnerar true endast vid success.
	}


}
