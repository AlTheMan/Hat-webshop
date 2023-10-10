package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends User {

	private static Connection con;

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

	public static void setLowestDbPrivilege() {
		DBManager.setInitUser();
	}



	public static void updateUserInDB(int userId, String privilege, String email, String address) {
		con = DBManager.getConnection();
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



}
