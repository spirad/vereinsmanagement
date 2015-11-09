package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MitgliedDAO {
	public List<Mitglied> findAll() {
		List<Mitglied> list = new ArrayList<Mitglied>();
		Connection c = null;
		
		String sql = "SELECT m.mandat, m.name, m.vorname, m.beitrag, m.monat, m.eintritt, m.mandat, m.strasse, m.stadt, m.bemerkung, m.status, m.geschlecht, m.titel FROM MITGLIEDER as m " + "ORDER BY m.name, m.vorname";

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
		return mitglied.getMandat() > 0 ? update(mitglied) : create(mitglied);
	}

	
	public Mitglied create(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare create statement");
			ps = c.prepareStatement(
					"INSERT INTO MITGLIEDER (NAME, VORNAME, BEITRAG, MONAT, EINTRITT, STRASSE, STADT, BEMERKUNG, TITEL, GESCHLECHT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setString(6, mitglied.getStreet());
			ps.setString(7, mitglied.getCity());
			ps.setString(8, mitglied.getRemark());
			ps.setString(9, mitglied.getTitle());
			ps.setString(10, mitglied.getGender());
			ps.setString(11, mitglied.getStatus());
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
	
	public Mitglied createBatch(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare create statement");
			ps = c.prepareStatement(
					"INSERT INTO MITGLIEDER (NAME, VORNAME, BEITRAG, MONAT, EINTRITT, MANDAT, STRASSE, STADT, BEMERKUNG, TITEL, GESCHLECHT, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setInt(6, mitglied.getMandat());
			ps.setString(7, mitglied.getStreet());
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

	public Mitglied update(Mitglied mitglied) throws DataBaseException {
		Connection c = null;
		try {
			c = DBConnectionHelper.getConnection();
			System.out.println("prepare update statement");
			PreparedStatement ps = c.prepareStatement(
					"UPDATE MITGLIEDER SET NAME=?, VORNAME=?, BEITRAG=?, MONAT=?, EINTRITT=?, STRASSE=?, STADT=?, BEMERKUNG=?, TITEL=?, GESCHLECHT=?, STATUS=? WHERE mandat=?");
			ps.setString(1, mitglied.getLastName());
			ps.setString(2, mitglied.getFirstName());
			ps.setFloat(3, mitglied.getPayment());
			ps.setInt(4, mitglied.getPaymentMonth());
			ps.setInt(5, mitglied.getEntryYear());
			ps.setString(6, mitglied.getStreet());
			ps.setString(7, mitglied.getCity());
			ps.setString(8, mitglied.getRemark());
			ps.setString(9, mitglied.getTitle());
			ps.setString(10, mitglied.getGender());
			ps.setString(11, mitglied.getStatus());
			ps.setInt(12, mitglied.getMandat());
			System.out.println(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataBaseException("Mitglied konnte nicht geändert werden");
		} finally {
			DBConnectionHelper.close(c);
		}
		return mitglied;
	}
	//
	// public boolean remove(Employee employee) {
	// Connection c = null;
	// try {
	// c = ConnectionHelper.getConnection();
	// PreparedStatement ps = c.prepareStatement("DELETE FROM employee WHERE
	// id=?");
	// ps.setInt(1, employee.getId());
	// int count = ps.executeUpdate();
	// return count == 1;
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new RuntimeException(e);
	// } finally {
	// ConnectionHelper.close(c);
	// }
	// }
	//
	// protected Mitglied processRow(ResultSet rs) throws SQLException {
	// Mitglied mitglied = new Mitglied();
	// employee.setId(rs.getInt("id"));
	// employee.setFirstName(rs.getString("firstName"));
	// employee.setLastName(rs.getString("lastName"));
	// employee.setTitle(rs.getString("title"));
	// employee.setDepartment(rs.getString("department"));
	// employee.setCity(rs.getString("city"));
	// employee.setOfficePhone(rs.getString("officePhone"));
	// employee.setCellPhone(rs.getString("cellPhone"));
	// employee.setEmail(rs.getString("email"));
	// employee.setPicture(rs.getString("picture"));
	// int managerId = rs.getInt("managerId");
	// if (managerId > 0) {
	// Employee manager = new Employee();
	// manager.setId(managerId);
	// manager.setFirstName(rs.getString("managerFirstName"));
	// manager.setLastName(rs.getString("managerLastName"));
	// employee.setManager(manager);
	// }
	// employee.setReportCount(rs.getInt("reportCount"));
	// return employee;
	// }
	//

}
