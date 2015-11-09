package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MandatNumberDAO {
	public int getNewMandatNumber() {
	
		Connection c = null;	
		String sql = "SELECT MAX(mandat) FROM MITGLIEDER";
		int newMandatNr=0;
		
		try {
			c = DBConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			rs.next();
			newMandatNr = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnectionHelper.close(c);
		}
		return newMandatNr;
	}	
}
