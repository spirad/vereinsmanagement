package org.luedtke.dirk.vereinsVerwaltung;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.json.JSONObject;
import org.junit.Test;

public class AllTestsMembersAsJson {

	MitgliedDAO dao = new MitgliedDAO();
	
	@Test
	public void test() {
		JSONObject jsonObject = new JSONObject();
		Iterator<Mitglied> daoIterator = dao.findAll().iterator();
		assertTrue(daoIterator.hasNext());
		while (daoIterator.hasNext()) {
			Mitglied m = daoIterator.next();
			System.out.println(m.getLastName());
			jsonObject.append("mitglieder", new JSONObject(m));
			System.out.println(jsonObject.toString());
		}
		
	}

}
