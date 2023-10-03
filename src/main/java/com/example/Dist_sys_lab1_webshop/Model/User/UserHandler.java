package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.User.User;

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


}
