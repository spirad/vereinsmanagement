package org.luedtke.dirk.vereinsVerwaltung;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/version")
public class VersionService {
	@GET
	@Produces("application/json")
	public Response convertFtoC() throws JSONException {

		JSONObject jsonObject = new JSONObject();
		System.out.println("creating ApplicationInfo");
		ApplicationInfo info = new ApplicationInfo();
		String version = info.getVersion();
		jsonObject.put("The Version of this application is ", version);
		String result = "@Produces(\"application/json\") Version: \n\n"
				+ jsonObject;
		return Response.status(200).entity(result).build();
	}
}
