package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.User.User;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


public class UserHandler {



	public static User authenticateUser(String username, String password){
		User user = User.getUserFromDB(username, password);
		if (user == null) return null;
		return user;
	}

	public static boolean buyItems(User user) throws SQLException {
		return User.buyItems(user);
	}


	public static List<User> getAllUsers(){
		return User.getAllUsersFromDB(); //TODO: ska kopia göras?
	}

	public static void addUser(HashMap<String, String> values) {
		User.addUserToDB(
				values.get("username"),
				values.get("password"),
				values.get("userPrivilege"),
				values.get("userEmail"));

	}

	public static void updateUser(HashMap<String, String> values){
		int userId = Integer.parseInt(values.get("userId"));
		String privilege = values.get("userPrivilege");
		String email = values.get("userEmail");
		User.updateUserInDB(userId, privilege, email);
	}

	public static void deleteUser(int id) {
		User.deleteUserFromDB(id);
	}

}
