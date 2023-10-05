package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;


public class ItemDB extends Item {

	ItemDB(int id, String name, String description, double price, int quantity, String imagesrc) {
		super(id, name, description, price, quantity, imagesrc);
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
				String imagesrc = resultSet.getString("imagesrc");
				itemCollection.add(new ItemDB(id, name, description, price, quantity, imagesrc));
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
					String imagesrc = resultSet.getString("imagesrc");
					item = new ItemDB(id, name, description, price, quantity, imagesrc);
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
			con.setAutoCommit(false);  // Start transaction
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


			con.commit();  // Commit the transaction
		}
		finally {
			con.setAutoCommit(true);  // End the transaction and set the connection back to default mode
		}
		return true; //Kolla om detta returnerar true endast vid success.
	}

//TODO: denna behöver kanske skrivas om
	public static void updateItemById(int id, HashMap<String, String> values)  {
		Connection con = DBManager.getConnection();
		String baseSQL = "UPDATE item SET";
		try {
			con.setAutoCommit(false);
			Statement statement = con.createStatement();

			if (values.containsKey("itemName")) {
				statement.executeUpdate(baseSQL + " name = '" +
						values.get("itemName") + "' WHERE id = " + id);
			}
			if (values.containsKey("descriptionName")) {
				statement.executeUpdate(baseSQL + " description = '" +
						values.get("descriptionName") + "' WHERE id = " + id);
			}
			if(values.containsKey("itemPrice")) {
				statement.executeUpdate(baseSQL + " price = " +
						values.get("itemPrice") + " WHERE id = " + id);
			}
			if (values.containsKey("itemQuantity")) {
				statement.executeUpdate(baseSQL + " quantity = " +
						values.get("itemQuantity") + " WHERE id = " + id);
			}
			if (values.containsKey("itemIMG")) {
				statement.executeUpdate(baseSQL + " imagesrc = '" +
						values.get("itemIMG") + "' WHERE id = " + id);
			}

			con.commit();
		} catch (SQLException e){
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void addItemToDB(Item item) {
		Connection connection = DBManager.getConnection();

		String sql = "INSERT INTO item (name, description, price, quantity, imagesrc) " +
				"VALUES (?, ?, ?, ?, ?)";

		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setDouble(3, item.getPrice());
			statement.setInt(4, item.getQuantity());
			statement.setString(5, item.getImagesrc());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public static void removeItemById(int id) {
		Connection connection = DBManager.getConnection();
		String sql = "DELETE from item where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
