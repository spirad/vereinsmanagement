package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnectionHelper {

		
	private String url;
	private static DBConnectionHelper instance;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private DBConnectionHelper()
	{
    	String driver = null;
		try {
			Class.forName("org.postgresql.Driver");
			url = "jdbc:postgresql:verein?user=verein_manager&password=verein";
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            driver = bundle.getString("jdbc.driver");
            Class.forName(driver);
            url=bundle.getString("jdbc.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new DBConnectionHelper();
		}
		try {
			return DriverManager.getConnection(instance.url);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void close(Connection connection)
	{
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

}
