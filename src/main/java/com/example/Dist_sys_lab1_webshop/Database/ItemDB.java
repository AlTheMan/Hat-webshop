package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Category;
import com.example.Dist_sys_lab1_webshop.Model.Item.Item;


import java.sql.*;
import java.util.*;


public class ItemDB extends Item {

	ItemDB(int id, String name, String description, double price, int quantity, String imageSrc, Category category) {
		super(id, name, description, price, quantity, imageSrc, category);
	}



	public static ArrayList<Item> getDBItemsAll() {
		Connection con = DBManager.getConnection();
		ArrayList<Item> itemCollection = new ArrayList<>();
		try {
			Statement statement = con.createStatement();
			String query = "SELECT item.*, categories.category AS category FROM item JOIN categories ON item.categoryId = categories.id;";
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				double price = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				String imageSrc = resultSet.getString("imagesrc");
				int categoryId = resultSet.getInt("categoryid");
				String categoryName = resultSet.getString("category");
				Category category = new Category(categoryName, categoryId);
				itemCollection.add(new ItemDB(id, name, description, price, quantity, imageSrc, category));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return itemCollection;
	}
	public static Item getDBItemByID(int idInput) {
		Connection con = DBManager.getConnection();
		Item item = null;
		String sql = "SELECT item.*, categories.category AS category FROM item JOIN categories ON item.categoryId = categories.id WHERE item.id = ?;";
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setInt(1, idInput);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String description = resultSet.getString("description");
					double price = resultSet.getDouble("price");
					int quantity = resultSet.getInt("quantity");
					String imageSrc = resultSet.getString("imagesrc");
					int categoryId = resultSet.getInt("categoryid");
					String categoryName = resultSet.getString("category");
					Category category = new Category(categoryName, categoryId);
					item = new ItemDB(id, name, description, price, quantity, imageSrc, category);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}


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
			if (item.getImageSrc() != null) {
				updateFields.add("imagesrc = ?");
				parameters.add(item.getImageSrc());
			}
			if (item.getCategoryId() > -1) {
				updateFields.add("categoryid = ?");
				parameters.add(item.getCategoryId());
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

	public static boolean addItemToDB(Item item) {
		Connection connection = DBManager.getConnection();

		String sql = "INSERT INTO item (name, description, price, quantity, imagesrc, categoryid) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, item.getName());
			statement.setString(2, item.getDescription());
			statement.setDouble(3, item.getPrice());
			statement.setInt(4, item.getQuantity());
			statement.setString(5, item.getImageSrc());
			statement.setInt(6, item.getCategoryId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
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
