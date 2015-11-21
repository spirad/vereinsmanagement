package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MitgliedDAO {
	
	public List<Mitglied> findAll(String whereClause) {
		List<Mitglied> list = new ArrayList<Mitglied>();
		Connection c = null;

		String sql = "SELECT m.mandat, m.name, m.vorname, m.beitrag, m.monat, m.eintritt, m.mandat, m.strasse, "
				+ "m.plz, m.stadt, m.bemerkung, m.status, m.geschlecht, m.titel FROM MITGLIEDER as m ";// + "ORDER BY m.name, m.vorname ";
		sql = sql + whereClause;
		
		System.out.println(sql);

		try {
			c = DBConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processSummaryRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnectionHelper.close(c);
		}
		return list;
	}

	public List<Mitglied> findAll( ) {
		return findAll("");
	}
	
	public List<Mitglied> findAllActive( ) {
		return findAll(" WHERE m.status = '"+Mitglied.STATUS_ACTIVE+"'");
	}
		
	
	public Mitglied findByMandatID(int mandatID) {
		//List<Mitglied> list = new ArrayList<Mitglied>();
		Mitglied mitglied = new Mitglied();
		Connection c = null;

		String sql = "SELECT m.mandat, m.name, m.vorname, m.beitrag, m.monat, m.eintritt, m.mandat, m.strasse, m.PLZ, m.stadt, m.bemerkung, m.status, m.geschlecht, m.titel FROM MITGLIEDER as m " + "WHERE m.mandat="+String.valueOf(mandatID);

		try {
			c = DBConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				mitglied = processSummaryRow(rs);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBConnectionHelper.close(c);
		}
		return mitglied;
	}
	
	private Mitglied processSummaryRow(ResultSet rs) {
		Mitglied mitglied = new Mitglied();
		try {
			mitglied.setMandat(rs.getInt("mandat"));
			mitglied.setLastName(rs.getString("name"));
			mitglied.setFirstName(rs.getString("vorname"));
			mitglied.setPayment(rs.getFloat("beitrag"));
			mitglied.setPaymentMonth(rs.getInt("monat"));
			mitglied.setEntryYear(rs.getInt("eintritt"));
			mitglied.setMandat(rs.getInt("mandat"));
			mitglied.setStreet(rs.getString("strasse"));
			mitglied.setPLZ(rs.getString("PLZ"));
			mitglied.setCity(rs.getString("stadt"));
			mitglied.setRemark(rs.getString("bemerkung"));
			mitglied.setGender(rs.getString("geschlecht"));
			mitglied.setTitle(rs.getString("titel"));
			mitglied.setStatus(rs.getString("status"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mitglied;
	}

	public Mitglied save(Mitglied mitglied) throws DataBaseException {
		if (mitglied.getMandat() == Mitglied.NO_MANDAT_GIVEN) return createMandateSet(mitglied); 
		if (mitglied.getMandat() == Mitglied.GENERATE_MANDAT) return create(mitglied);
		return update(mitglied);
	}


	public Mitglied create(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare create statement");
			ps = c.prepareStatement(
					"INSERT INTO MITGLIEDER (NAME, VORNAME, BEITRAG, MONAT, EINTRITT, STRASSE, PLZ, STADT, BEMERKUNG, TITEL, GESCHLECHT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setString(6, mitglied.getStreet());
			ps.setString(7, mitglied.getPLZ());
			ps.setString(8, mitglied.getCity());
			ps.setString(9, mitglied.getRemark());
			ps.setString(10, mitglied.getTitle());
			ps.setString(11, mitglied.getGender());
			ps.setString(12, mitglied.getStatus());
			//System.out.println("before excecute");
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Mitglied konnte nicht gesichert werden");
		} finally {
			DBConnectionHelper.close(c);
		}
		return mitglied;
	}

	public Mitglied createMandateSet(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare create statement");
			ps = c.prepareStatement(
					"INSERT INTO MITGLIEDER (NAME, VORNAME, BEITRAG, MONAT, EINTRITT, MANDAT, STRASSE, PLZ, STADT, BEMERKUNG, TITEL, GESCHLECHT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setInt(6, mitglied.getMandat());
			ps.setString(7, mitglied.getStreet());
			ps.setString(8, mitglied.getPLZ());
			ps.setString(9, mitglied.getCity());
			ps.setString(10, mitglied.getRemark());
			ps.setString(11, mitglied.getTitle());
			ps.setString(12, mitglied.getGender());
			ps.setString(13, mitglied.getStatus());
			System.out.println("before excecute " + ps.toString());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Mitglied konnte nicht gesichert werden");
		} finally {
			DBConnectionHelper.close(c);
		}
		return mitglied;
	}

	public Mitglied update(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare update statement");
			PreparedStatement ps = c.prepareStatement(
					"UPDATE MITGLIEDER SET NAME=?, VORNAME=?, BEITRAG=?, MONAT=?, EINTRITT=?, STRASSE=?, PLZ=?, STADT=?, BEMERKUNG=?, TITEL=?, GESCHLECHT=?, STATUS=? WHERE mandat=?");
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setString(6, mitglied.getStreet());
			ps.setString(7, mitglied.getPLZ());
			ps.setString(8, mitglied.getCity());
			ps.setString(9, mitglied.getRemark());
			ps.setString(10, mitglied.getTitle());
			ps.setString(11, mitglied.getGender());
			ps.setString(12, mitglied.getStatus());
			ps.setInt(13, mitglied.getMandat());
			System.out.println(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Mitglied konnte nicht geaendert werden");
		} finally {
			DBConnectionHelper.close(c);
		}
		return mitglied;
	}


}
