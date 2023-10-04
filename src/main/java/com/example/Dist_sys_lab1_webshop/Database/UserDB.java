package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB extends User {

	private static Connection con;

	private UserDB(String userName, String password, String email, Privilege privilege, int id) {
		super(userName, password, email, privilege, id);
	}

	public UserDB(String username, String email, String privilege, int id) {
		super(username, email, privilege, id);
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
				users.add(new UserDB(username, email, privilege, id));
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
					user.setUserName(resultSet.getString("username"));
					user.setEmail(resultSet.getString("email"));
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


	public static void addUserToDB(String username, String password, String privilege, String email){
		con = DBManager.getConnection();
		String sql = "INSERT INTO user (username, password, email, privilege) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, email);
			statement.setString(4, privilege);

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

	public static void updateUserInDB(int userId, String privilege, String email) {
		String sql;
		con = DBManager.getConnection();

		try  {
			PreparedStatement statement = null;
			if (email.compareTo("") != 0 && privilege.compareTo("NOVALUE") != 0) {
				sql = "UPDATE user SET email = ?, privilege = ? WHERE user_id = ?";
				statement = con.prepareStatement(sql);
				statement.setString(1, email);
				statement.setString(2, privilege);
				statement.setInt(3, userId);
			} else if (email.compareTo("") != 0) {
				sql = "UPDATE user SET email = ? WHERE user_id = ?";
				statement = con.prepareStatement(sql);
				statement.setString(1, email);
				statement.setInt(2, userId);
			} else if (privilege.compareTo("NOVALUE") != 0) {
				sql = "UPDATE user SET privilege = ? WHERE user_id = ?";
				statement = con.prepareStatement(sql);
				statement.setString(1, privilege);
				statement.setInt(2, userId);
			}

			if (statement != null)
				statement.executeUpdate();



		} catch (SQLException e) {
			e.printStackTrace();
		}

	}



}
