package org.luedtke.dirk.vereinsVerwaltung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVMitgliedUploader {

	private String csvFile = "/temp/mitgliederlist.csv";
	private static int startDeletedID = 10000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public CSVMitgliedUploader(String fileName) {
		csvFile = fileName;
	}

	public List<Mitglied> getMitgleiderList() {

		List<Mitglied> mitgliederList = new ArrayList<Mitglied>();

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// Nr.;Name;Vorname;Beitrag;Monat;Beginn;Mandat;Strasse;PLZ;Stadt;sonstiges
				String[] column = line.split(cvsSplitBy);
				System.out.println("Array Length: " + column.length);
				System.out.println(column.toString());
				Mitglied m = new Mitglied();
				int i = 1;
				System.out.println("got: " + column[i]);
				m.setLastName(column[i]);
				i++;
				System.out.println("got: " + column[i]);
				m.setFirstName(column[i]);
				i++;
				System.out.println("got: " + column[i]);
				m.setPayment(Float.parseFloat(column[i]));
				i++;
				System.out.println("got: " + column[i]);
				m.setPaymentMonth(Integer.parseInt(column[i]));
				i++;
				System.out.println("got: " + column[i]);
				m.setEntryYear(Integer.parseInt(column[i]));
				i++;
				System.out.println("got: " + column[i]);
				if (Integer.parseInt(column[i]) == 9999) {
					m.setStatus("ausgetreten");
					m.setMandat(startDeletedID++);
				}
				else m.setMandat(Integer.parseInt(column[i]));
				i++;
				System.out.println("got: " + column[i]);
				m.setStreet(column[i]);
				i++;
				System.out.println("got: " + column[i]);
				m.setPLZ(column[i]);
				i++;
				System.out.println("got: " + column[i]);
				m.setCity(column[i]);
				i++;
				System.out.println("i: " + i);
				if (column.length >i ) {
					System.out.println("got: " + column[i]);
					m.setRemark(column[i]);
				}
				
				mitgliederList.add(m);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return mitgliederList;
	}

}
