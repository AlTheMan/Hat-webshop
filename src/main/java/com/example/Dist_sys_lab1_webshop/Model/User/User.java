package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Database.UserDB;

import java.util.List;

public class User {

	private int id;
	private String userName;
	private String password;
	private String email;
	private Privilege privilege;

	public User(String username, String password, String email, Privilege privilege, int id) {
		this.password = password;
		this.userName = username;
		this.email = email;
		this.privilege = privilege;
		this.id = id;
	}


	public User(String username, String email, String privilege, int id){
		this.userName = username;
		this.email = email;
		this.privilege = Privilege.valueOf(privilege);
		this.id = id;
		this.password = "******";
	}


	public User(){

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

	public static List<User> getAllUsersFromDB(){
		return UserDB.getAllUsersFromDB();
	}

	public static User getUserFromDB(String userName, String password){
		User dbUser = UserDB.getUserFromDB(userName, password);

		if (dbUser == null) {
			return null;
		}

		User user = new User();
		user.userName = dbUser.userName;
		user.email = dbUser.email;
		user.privilege = dbUser.privilege;
		user.password = "********";
		return user;
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
