package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.*;


//import org.apache.catalina.*;
public class ApplicationInfo {

	public String getVersion() {
		String returnVersion = "0.0.0";

		try {
			Connection c = DBConnectionHelper.getConnection();
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery("select * from APPINFO");
			
			while (rs.next()) {
				returnVersion = rs.getString("VERSION");
			}
			
			statement.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return returnVersion;
	}
}
