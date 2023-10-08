package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;
import com.example.Dist_sys_lab1_webshop.Model.User.ShoppingCart;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class ItemDB extends Item {

	ItemDB(int id, String name, String description, double price, int quantity, String imagesrc, String category) {
		super(id, name, description, price, quantity, imagesrc, category);
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
				String category= resultSet.getString("category");
				itemCollection.add(new ItemDB(id, name, description, price, quantity, imagesrc, category));
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
					String category = resultSet.getString("category");
					item = new ItemDB(id, name, description, price, quantity, imagesrc, category);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
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
			if (item.getCategory() != null) {
				updateFields.add("category = ?");
				parameters.add(item.getCategory());
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
