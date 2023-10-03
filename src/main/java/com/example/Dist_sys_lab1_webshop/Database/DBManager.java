package com.example.Dist_sys_lab1_webshop.Database;


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

	public static void setCurrentUser(String username) {
		if (username.compareTo("distlab1admin") == 0 || username.compareTo("distlab1user") == 0)
			currentUser = username;
	}


}
