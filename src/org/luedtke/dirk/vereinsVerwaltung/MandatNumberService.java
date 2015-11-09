package org.luedtke.dirk.vereinsVerwaltung;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/mandatnr")
public class MandatNumberService {
	
	MandatNumberDAO dao = new MandatNumberDAO();
	
	@GET
	@Produces("application/json")
	public Response findAll() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mandatNr", new Integer(dao.getNewMandatNumber()+1).toString());
		return Response.status(200).entity(jsonObject.toString()).build();
	}
}
