package org.luedtke.dirk.vereinsVerwaltung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class CSVMitgliedUploader {

	private static String csvFile = "./mitgliederlist.csv";
	private static int startDeletedID = 10000;

	public static void main(String[] args) {
		String paramCSVFile = "-f";
		for (int i=0; i <args.length; i++) {
			if (paramCSVFile.equals(args[i])) {
					csvFile = args[i+1];
					System.out.println("Uploading file " +csvFile);
			}
		}
		MitgliedDAO mitgliedDAO = new MitgliedDAO();
		CSVMitgliedUploader csvUploader = new CSVMitgliedUploader(csvFile);
		ArrayList<Mitglied> mitglieder = (ArrayList<Mitglied>) csvUploader.getMitgleiderList();
		Iterator<Mitglied> iterator = mitglieder.iterator();
		while (iterator.hasNext()) {
			try {
				mitgliedDAO.createMandateSet((Mitglied)iterator.next());
			} catch (DataBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//mitgliedDAO.createMandateSet((Mitglied)iterator.next());
		}


	}

	public CSVMitgliedUploader(String fileName) {
		System.out.println("Uploading file " +csvFile);
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
				//title
				System.out.println("got: " + column[i]);
				m.setTitle(column[i]);
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
					m.setStatus(Mitglied.STATUS_INACTIVE);
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

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(2);
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
