package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Database.ItemDB;
import com.example.Dist_sys_lab1_webshop.Database.UserDB;

import java.util.ArrayList;
import java.util.List;

public class User {

	private int id;
	private String userName;
	private String password;
	private String email;
	private Privilege privilege;
	private String address;
	private ShoppingCart shoppingcart;

	public User(String username, String password, String email, Privilege privilege, int id, ShoppingCart shoppingcart, String address) {
		this.password = password;
		this.userName = username;
		this.email = email;
		this.privilege = privilege;
		this.id = id;
		this.shoppingcart=shoppingcart;
		this.address = address;
	}
	public User(String username, String password, String email, Privilege privilege, int id, String address){
		this(username,password,email,privilege,id,new ShoppingCart(), address);
	}

	public static User getCopy(User user) {
		User copy = new User();
		copy.id = user.id;
		copy.password = user.password;
		copy.userName = user.userName;
		copy.address = user.address;
		copy.email = user.email;
		copy.privilege = user.privilege;
		copy.shoppingcart = ShoppingCart.getCopy(user.shoppingcart);
		return copy;
	}


	public User(String username, String email, Privilege privilege, int id, String address){
		this.userName = username;
		this.email = email;
		this.privilege = privilege;
		this.id = id;
		this.password = "******";
		this.shoppingcart = new ShoppingCart();
		this.address = address;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static User getUserFromId(ArrayList<User> users, int id) {
		for (User u : users) {
			if (u.getId() == id){
				return u;
			}
		}
		return null;
	}

	public User(){
		this.shoppingcart = new ShoppingCart();
	}

	public int getId() {
		return id;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = Privilege.valueOf(privilege);
	}

	protected static List<User> getAllUsersFromDB(){
		return UserDB.getAllUsersFromDB();
	}

	protected static void updateUserInDB(int userId, String privilege, String email, String address) {
		UserDB.updateUserInDB(userId, privilege, email, address);
	}

	protected static void addUserToDB(String username, String password, String privilege, String email, String address){

		UserDB.addUserToDB(username, password, privilege, email, address);
	}

	protected static void deleteUserFromDB(int userId) {
		UserDB.deleteUserFromDB(userId);
	}





	public static User getUserFromDB(String userName, String password){
		User dbUser = UserDB.getUserFromDB(userName, password);

		if (dbUser == null) {
			return null;
		}

		User user = getCopy(dbUser);
		user.password = "********";
		return user;
	}


	public static void setLowestDbPrivilege() {
		UserDB.setLowestDbPrivilege();
	}

	public ShoppingCart getShoppingcart(){
		return shoppingcart;
	}
	public static boolean buyItems(User user) {
		return UserDB.addUserOrder(user);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
