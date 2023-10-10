package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Model.Item.Item;
import com.example.Dist_sys_lab1_webshop.Model.Item.ItemHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserHandler {

	public static User authenticateUser(String username, String password){
		return User.getUserFromDB(username, password);
	}

	/**
	 * Sets the privilege in the database. Used for initialization
	 */
	public static void setInitUser(){
		User.setLowestDbPrivilege();
	}


	/**
	 * Fetches all users from the model (who fetches it from the database)
	 * Used for admin purposes.
	 * @return
	 */
	public static List<User> getAllUsers(){
		List<User> users = User.getAllUsersFromDB();
		List<User> userCopy = new ArrayList<>();
		for (User user : users) {
			userCopy.add(User.getCopy(user));
		}

		return userCopy;
	}

	/**
	 * Gets the values for a new user from a hashmap and passes it to the user model.
	 * All values need to be present
	 * @param values the hashmap containing the user values.
	 */
	public static void addUser(HashMap<String, String> values) {
		for (String value : values.values()) {
			if(value.isEmpty()) return;
		}

		User.addUserToDB(
				values.get("username"),
				values.get("password"),
				values.get("userPrivilege"),
				values.get("userEmail"),
				values.get("userAddress"));

	}


	public static void removeItemFromShoppingCart(String stringId, User user) {
		if (stringId == null) return;
		int itemId = Integer.parseInt(stringId);
		Item item = ItemHandler.getItemByID(itemId);
		user.getShoppingcart().removeItems(item, 1);
	}

	/**
	 * Updates the user from the values passed in the hashmap. All values need to be present.
	 * @param values the hashmap containing the user values.
	 */

	public static void updateUser(HashMap<String, String> values){
		for (String s : values.values()) {
			if (s.isEmpty()) return;
		}


		int userId = Integer.parseInt(values.get("userId"));
		String privilege = values.get("userPrivilege");
		String email = values.get("userEmail");
		String address = values.get("userAddress");
		User.updateUserInDB(userId, privilege, email, address);
	}

	/**
	 * Deletes a user
	 * @param id the user id in the database
	 */

	public static void deleteUser(String id) {
		if (id != null) {
			User.deleteUserFromDB(Integer.parseInt(id));
		}
	}

}
