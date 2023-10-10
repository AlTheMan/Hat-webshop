package com.example.Dist_sys_lab1_webshop.Database;


import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBManager {

	private static String  url = "jdbc:mysql://localhost:3306/dist1";
	private static String currentUser = DbUsers.Customer;
	private static String passw = DbUsers.Password;
	private static Connection connection;





	protected static Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		try {
			setInitUser();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, currentUser, passw);
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public static String getCurrentUser(){
		return currentUser;
	}

	public static void setInitUser() {
		currentUser = DbUsers.Customer;
	}

	protected static void setUserPrivilege(Privilege privilege)  {
		String loginUser;
		switch (privilege){
			case ADMIN: loginUser = DbUsers.Admin; break;
			case STAFF: loginUser = DbUsers.Staff; break;
			default: loginUser = DbUsers.Customer; break;
		}
		currentUser = loginUser;
		try {
			connection.close();
			connection = DriverManager.getConnection(url, currentUser, passw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
