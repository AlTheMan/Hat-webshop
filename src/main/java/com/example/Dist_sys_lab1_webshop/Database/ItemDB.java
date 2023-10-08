package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;
import com.example.Dist_sys_lab1_webshop.Model.User.Shoppingcart;
import com.example.Dist_sys_lab1_webshop.Model.User.User;
import com.mysql.cj.log.Log;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;


public class ItemDB extends Item {

	ItemDB(int id, String name, String description, double price, int quantity, String imagesrc) {
		super(id, name, description, price, quantity, imagesrc);
	}

	public static ArrayList<Item> getDBItemsAll() {
		Connection con = DBManager.getConnection();
		ArrayList<Item> itemCollection = new ArrayList<>();
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

	public static boolean addUserOrder(User user) {
		Shoppingcart shoppingcart = user.getShoppingcart();
		Connection con = DBManager.getConnection();

		// Updates the item quantity
		String itemSql = "UPDATE item SET quantity = quantity - ? WHERE id = ?;";
		// Inserts the order into the order table
		String ordersSql = "INSERT INTO orders (customer_name, order_date, status, shipping_address) VALUES (?, ?, ?, ?)";
		// Inserts item into the order_items table
		String order_itemsSql = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";
		// Gets the order_id to be inserted into the order_items table
		String getOrderIdSql = "SELECT order_id from orders where customer_name = ?";

		try {
			PreparedStatement itemStatement = con.prepareStatement(itemSql);
			PreparedStatement order_itemsStatement = con.prepareStatement(order_itemsSql);
			PreparedStatement orderStatement = con.prepareStatement(ordersSql);
			PreparedStatement getOrderId = con.prepareStatement(getOrderIdSql);

			con.setAutoCommit(false);  // Start transaction
			// Inserts into the order table
			orderStatement.setString(1, user.getUserName());
			orderStatement.setDate(2, Date.valueOf(LocalDate.now()));
			orderStatement.setString(3, String.valueOf(OrderStatus.PENDING));
			orderStatement.setString(4, "ShippingAddress");
			orderStatement.executeUpdate();

			// Gets the order-id for order_items table insert
			getOrderId.setString(1, user.getUserName());
			ResultSet rs = getOrderId.executeQuery();
			String order_id = null;
			while(rs.next()) {
				order_id = rs.getString(1);
			}

			for(int i=0; i<shoppingcart.getItems().size(); i++){
				int itemId = shoppingcart.getItems().get(i).getItem().getId();
				int itemQuantity = shoppingcart.getItems().get(i).getNrOfItems();

				// Inserts the item in the order_items table
				order_itemsStatement.setInt(1, Integer.parseInt(order_id));
				order_itemsStatement.setInt(2, itemId);
				order_itemsStatement.setInt(3, itemQuantity);
				order_itemsStatement.executeUpdate();

				// Updates the quantity in the item table
				itemStatement.setInt(1, itemQuantity);
				itemStatement.setInt(2, itemId);
				itemStatement.executeUpdate();
			}


		} catch (SQLException e) {
			e.printStackTrace();

			try {
				con.rollback();
				con.setAutoCommit(true);
				System.out.println("Rollback!");
			} catch (SQLException ex) {
				e.printStackTrace();
			}
			return false;
		}

		finally {
			try {
				con.setAutoCommit(true);  // End the transaction and set the connection back to default mode
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true; //Kolla om detta returnerar true endast vid success.
	}

/*
	public static void updateItemById(Item item)  {
		Connection con = DBManager.getConnection();
		String baseSQL = "UPDATE item SET";
		int id = item.getId();
		try {
			con.setAutoCommit(false);
			Statement statement = con.createStatement();

			if (item.getName() != null) {
				statement.executeUpdate(baseSQL + " name = '" +
						item.getName() + "' WHERE id = " + id);
			}
			if (item.getDescription() != null) {
				statement.executeUpdate(baseSQL + " description = '" +
						item.getDescription() + "' WHERE id = " + id);
			}
			if(item.getPrice() > 0) {
				statement.executeUpdate(baseSQL + " price = " +
						item.getPrice() + " WHERE id = " + id);
			}
			if (item.getQuantity() > -1) {
				statement.executeUpdate(baseSQL + " quantity = " +
						item.getQuantity() + " WHERE id = " + id);
			}
			if (item.getImagesrc() != null) {
				statement.executeUpdate(baseSQL + " imagesrc = '" +
						item.getImagesrc() + "' WHERE id = " + id);
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
	}*/

	public static void updateItemByIdPrepared(Item item) {
		Connection con = DBManager.getConnection();

		try {
			con.setAutoCommit(false);

			String updateSQL = "UPDATE item SET ";
			List<String> updateFields = new ArrayList<>();
			List<Object> parameters = new ArrayList<>();

			if (item.getName() != null) {
				updateFields.add("name = ?");
				parameters.add(item.getName());
			}
			if (item.getDescription() != null) {
				updateFields.add("description = ?");
				parameters.add(item.getDescription());
			}
			if (item.getPrice() > 0) {
				updateFields.add("price = ?");
				parameters.add(item.getPrice());
			}
			if (item.getQuantity() > -1) {
				updateFields.add("quantity = ?");
				parameters.add(item.getQuantity());
			}
			if (item.getImagesrc() != null) {
				updateFields.add("imagesrc = ?");
				parameters.add(item.getImagesrc());
			}

			if (updateFields.isEmpty()) {
				con.setAutoCommit(true);
				return;
			}

			updateSQL += String.join(", ", updateFields) + " WHERE id = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(updateSQL)) {
				for (int i = 0; i < parameters.size(); i++) {
					preparedStatement.setObject(i + 1, parameters.get(i));
				}
				preparedStatement.setInt(parameters.size() + 1, item.getId());
				preparedStatement.executeUpdate();

				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
