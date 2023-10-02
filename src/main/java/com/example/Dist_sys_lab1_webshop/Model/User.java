package com.example.Dist_sys_lab1_webshop.Model;

public class User {


	private String userName;
	private String password;

	public User(String userName, String password) {
		this.password = password;
		this.userName = userName;
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
