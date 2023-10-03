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

}
