package org.luedtke.dirk.vereinsVerwaltung;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/mitglieder")
public class MitgliedService {

	MitgliedDAO dao = new MitgliedDAO();

	@GET
	@Produces(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response getAllMembers() {
		//Response response=new Response();
		//response.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		Iterator<Mitglied> daoIterator = dao.findAll().iterator();
		assertTrue(daoIterator.hasNext());
		while (daoIterator.hasNext()) {
			Mitglied m = daoIterator.next();
			jsonObject.append("mitglieder", new JSONObject(m));
		}
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON+";charset=utf-8")
	public Response createNewMember(InputStream incomingData) {
		JSONObject jsonObject = new JSONObject();
		JSONObject returnJSON = new JSONObject();
		StringBuilder mitgliedBuilder = new StringBuilder();
		Status returnStatus = Status.OK;
		// try {
		BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				mitgliedBuilder.append(line);
			}

			String jsonString;
			jsonString = java.net.URLDecoder.decode(mitgliedBuilder.toString(), "UTF-8");
			System.out.println("Data Received: " + jsonString);
			jsonObject = new JSONObject(jsonString);
			Mitglied newMitglied = new Mitglied(jsonObject);
			dao.save(newMitglied);
			returnJSON.put("message",
					"Neues Mitglied " + newMitglied.getFirstName() + " " + newMitglied.getLastName() + " angelegt.");
			returnStatus = Status.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnJSON.put("message", e.getMessage());
			returnStatus = Status.BAD_REQUEST;
			// return
			// Response.status(Status.BAD_REQUEST).entity(returnJSON.toString()).build();
		}
		System.out.println("JSON Received: " + jsonObject.toString());
		return Response.status(returnStatus).entity(returnJSON.toString()).build();
	}

}
