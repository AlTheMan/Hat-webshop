package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.User.User;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserHandler {



	public static User authenticateUser(String username, String password){
		return User.getUserFromDB(username, password);
	}

	public static boolean buyItems(User user) {
		return User.buyItems(user);
	}

	public static void setInitUser(){
		User.setLowestDbPrivilege();
	}


	public static List<User> getAllUsers(){
		List<User> users = User.getAllUsersFromDB();
		List<User> userCopy = new ArrayList<>();
		for (User user : users) {
			userCopy.add(User.getCopy(user));
		}

		return userCopy;
	}

	public static void addUser(HashMap<String, String> values) {
		User.addUserToDB(
				values.get("username"),
				values.get("password"),
				values.get("userPrivilege"),
				values.get("userEmail"),
				values.get("userAddress"));

	}

	public static void updateUser(HashMap<String, String> values){
		int userId = Integer.parseInt(values.get("userId"));
		String privilege = values.get("userPrivilege");
		String email = values.get("userEmail");
		String address = values.get("userAddress");
		User.updateUserInDB(userId, privilege, email, address);
	}

	public static void deleteUser(int id) {
		User.deleteUserFromDB(id);
	}

}
