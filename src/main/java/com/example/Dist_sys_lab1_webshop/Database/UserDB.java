package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDB extends User {
	private UserDB(String userName, String password, String email) {
		super(userName, password, email);
	}

	private UserDB(){

	}

	public static User getUserFromDB(String username, String password) {
		Connection con = DBManager.getConnection();
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
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

}
