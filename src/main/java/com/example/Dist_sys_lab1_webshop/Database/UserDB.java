package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Order.OrderStatus;
import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.ShoppingCart;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends User {

	private static Connection con;

	private UserDB(String userName, String password, String email, Privilege privilege, int id, String address) {
		super(userName, password, email, privilege, id, address);
	}
	private UserDB(String userName, String email, Privilege privilege, int id, String address) {
		super(userName, email, privilege, id, address);
	}

	private UserDB(){

	}


	public static List<User> getAllUsersFromDB() {
		con = DBManager.getConnection();
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM user";
		try (Statement statement = con.createStatement()) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("user_id");
				String username = resultSet.getString("username");
				String email = resultSet.getString("email");
				String privilege = resultSet.getString("privilege");
				String address = resultSet.getString("address");
				users.add(new UserDB(username, email, Privilege.valueOf(privilege), id, address));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static User getUserFromDB(String username, String password) {
		con = DBManager.getConnection();
		UserDB user = null;
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setString(1, username);
			statement.setString(2, password);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					user = new UserDB();
					user.setId(resultSet.getInt("user_id"));
					user.setUserName(resultSet.getString("username"));
					user.setEmail(resultSet.getString("email"));
					user.setAddress(resultSet.getString("address"));
					user.setPrivilege(resultSet.getString("privilege"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (user != null) {
			DBManager.setUserPrivilege(user.getPrivilege());
		}
		return user;
	}


	public static void addUserToDB(String username, String password, String privilege, String email, String address){
		con = DBManager.getConnection();
		String sql = "INSERT INTO user (username, password, email, privilege, address) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, email);
			statement.setString(4, privilege);
			statement.setString(5, address);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}



	}

	public static void deleteUserFromDB(int userId){
		con = DBManager.getConnection();
		String sql = "DELETE FROM user where user_id = ?";
		try (PreparedStatement statement = con.prepareStatement(sql)){
			statement.setInt(1, userId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateUserInDB(int userId, String privilege, String email, String address) {
		con = DBManager.getConnection();
		System.out.println(address);
		String sql = "UPDATE user SET privilege = ?, email = ?, address = ? where user_id = ?";
		try {
			con.setAutoCommit(true);
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, privilege);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, address);
			preparedStatement.setInt(4, userId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean addUserOrder(User user) {
		ShoppingCart shoppingcart = user.getShoppingcart();
		Connection con = DBManager.getConnection();

		// Updates the item quantity
		String itemSql = "UPDATE item SET quantity = quantity - ? WHERE id = ?;";
		// Inserts the order into the order table
		String ordersSql = "INSERT INTO orders (customer_name, order_date, status, shipping_address, user_id) VALUES (?, ?, ?, ?, ?)";
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
			orderStatement.setString(4, user.getAddress());
			orderStatement.setInt(5, user.getId());
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
}
