package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.User.User;

import java.util.HashMap;
import java.util.List;


public class UserHandler {



	public static User authenticateUser(String username, String password){
		User user = User.getUserFromDB(username, password);
		if (user == null) return null;
		return user;
	}


	public static List<User> getAllUsers(){
		return User.getAllUsersFromDB(); //TODO: ska kopia göras?
	}


	public static void updateUser(HashMap<String, String> values){
		String userStringId = values.get("userId");
		if (userStringId == null) {
			return;
		}
		int userId = Integer.parseInt(userStringId);

		if (values.containsKey("userDelete")) {
			User.deleteUserFromDB(userId);
		} else if (values.containsKey("userSubmit")){
			String privilege = values.get("userPrivilege");
			String email = values.get("userEmail");
			User.updateUserInDB(userId, privilege, email);
		}
	}

}
