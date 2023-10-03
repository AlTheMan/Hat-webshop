package com.example.Dist_sys_lab1_webshop.Model.User;

import com.example.Dist_sys_lab1_webshop.Database.UserDB;

public class User {


	private String userName;
	private String password;
	private String email;
	private Privilege privilege;

	public User(String userName, String password, String email, Privilege privilege) {
		this.password = password;
		this.userName = userName;
		this.email = email;
		this.privilege = privilege;
	}
	public User(){

	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = Privilege.valueOf(privilege);
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
