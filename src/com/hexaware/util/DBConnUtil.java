package com.hexaware.util;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnUtil {

	static Connection connection;
	
	public static Connection getConnection() {
		try {
			connection = DBPropertyUtil.getConnectionString();
		} 
		catch (SQLException e) {
			System.out.println(e);
		}
		return connection;
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnUtil.getConnection());
	}
	

}
