package org.luedtke.dirk.vereinsVerwaltung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

public class DBContentSetup {

	public static final String VERSION = "0.0.2";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//createAppInfoTable();
		createMitgliederTable();
	}

	private static void createAppInfoTable() {

		try {
			Connection c = DBConnectionHelper.getConnection();
			Statement stmt = null;
			System.out.println("Opened database successfully");

			String sql;
			try {
				sql = "DROP TABLE APPINFO";
				stmt = c.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				System.out.println("APPINFO TABLE successfully dropped ...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			sql = "CREATE TABLE APPINFO " + " (VERSION        TEXT    NOT NULL, " + " INFO           TEXT    NOT NULL, "
					+ " DATE           TEXT    NOT NULL, " + " COMMENT         TEXT)";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("APPINFO TABLE successfully created ...");

			Statement sqlInsert = c.createStatement();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			sql = "INSERT INTO APPINFO (VERSION,INFO,DATE,COMMENT) " + "VALUES ('" + VERSION
					+ "', 'InitialVersion','"+dateFormat.format(date)+"','auto create' );";
			System.out.println("sql: "+sql);
			sqlInsert.executeUpdate(sql);
			System.out.println("Initial Version inserted ...");
			sqlInsert.close();

			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

	private static void createMitgliederTable() {

		try {
			Connection c = DBConnectionHelper.getConnection();
			Statement stmt = null;
			System.out.println("Opened database successfully");

			String sql;
			try {
				sql = "DROP TABLE IF EXISTS MITGLIEDER";
				stmt = c.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				System.out.println("MITGLIEDER TABLE successfully dropped ...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "DROP SEQUENCE if EXISTS mandatid;";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Sequence MandatId dropped...");

			sql = "CREATE SEQUENCE mandatid START 109;";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Sequence MandatId created...");

			// Nr.;Name;Vorname;Beitrag;Monat;Beginn;Mandat;Strasse;PLZ;Stadt;sonstiges
			sql = "CREATE TABLE MITGLIEDER "
			+ " (MANDAT        integer PRIMARY KEY DEFAULT nextval('mandatid'), "
			+ " NAME       varchar(40) NOT NULL CHECK (name <> ''), "
			+ " VORNAME    varchar(40) , "
			+ " TITEL      varchar(40) , "
			+ " GESCHLECHT varchar(10) , "
			+ " BEITRAG    DECIMAL(6,2)  NOT NULL, "
			+ " MONAT      integer     , "
			+ " EINTRITT   integer     NOT NULL, "
			//+ " MANDAT     integer     NOT NULL, "
			+ " STRASSE    varchar(40) , "
			+ " STADT      varchar(40) , "
			+ " STATUS      varchar(40) , "
			+ " BEMERKUNG  varchar(100) , "
			+ "CONSTRAINT uc_PersonID UNIQUE (NAME,VORNAME)"
			+ " );";
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("MITGLIEDER TABLE successfully created ...");


			MitgliedDAO mitgliedDAO = new MitgliedDAO();
			CSVMitgliedUploader csvUploader = new CSVMitgliedUploader("c:/temp/mitglieder.csv");
			ArrayList<Mitglied> mitglieder = (ArrayList<Mitglied>) csvUploader.getMitgleiderList();
			Iterator<Mitglied> iterator = mitglieder.iterator();
			while (iterator.hasNext()) {
				mitgliedDAO.createMandateSet((Mitglied)iterator.next());
			}

			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Table created successfully");
	}

}
