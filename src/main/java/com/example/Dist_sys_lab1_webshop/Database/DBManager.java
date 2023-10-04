package com.example.Dist_sys_lab1_webshop.Database;


import com.example.Dist_sys_lab1_webshop.Model.User.Privilege;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static String  url = "jdbc:mysql://localhost:3306/dist1";
	private static String currentUser = "distlab1user";
	private static String passw = "1234";
	private static Connection connection;
	public static Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		try {
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
		currentUser = "distlab1user";
	}

	protected static void setUserPrivilege(Privilege privilege)  {
		String loginUser;
		switch (privilege){
			case ADMIN: loginUser = "distlab1admin"; break;
			case STAFF: loginUser = "distlab1user"; break;
			case CUSTOMER: loginUser = "distlab1user"; break; //TODO: Fixa en till privilege
			default: loginUser = "distlab1user"; break; // TODO: Customer privilege
		}
		System.out.println(privilege + " " + loginUser);
		currentUser = loginUser;
		try {
			connection.close();
			connection = DriverManager.getConnection(url, currentUser, passw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
