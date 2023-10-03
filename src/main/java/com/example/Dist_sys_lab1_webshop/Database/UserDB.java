package com.example.Dist_sys_lab1_webshop.Database;

import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;
import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.sql.*;

public class UserDB extends User {
	private UserDB(String userName, String password, String email, Privilege privilege) {
		super(userName, password, email, privilege);
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
