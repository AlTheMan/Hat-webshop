package com.example.Dist_sys_lab1_webshop.Database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static String  url = "jdbc:mysql://localhost:3306/dist_labb1";
	private static String rootUser = "distlab1admin";
	private static String passw = "1234";

	private Connection connection;

	private static DBManager manager;

	private DBManager(){

	}

	public static DBManager getInstance() throws SQLException {
		if (manager != null) {
			return manager;
		}
		manager = new DBManager();
		manager.connection = DriverManager.getConnection(url, rootUser, passw);

		return manager;
	}

	public Connection getConnection(){
		return manager.connection;
	}







}
