package com.user;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.user.User;

@Path("/User")
public class UserService {
	User U = new User();
	
	@POST
	@Path("/StartSession")
	@Produces(MediaType.TEXT_HTML)
	public String StartSession(@QueryParam("UserID") int UserID) {
		return U.StartSession(UserID);
	}
	
	@DELETE
	@Path("/EndSession")
	@Produces(MediaType.TEXT_HTML)
	public String EndSession(@QueryParam("UserID") int UserID) {
		return U.EndSession(UserID);
	}
	
	@GET
	@Path("/GetSession")
	@Produces(MediaType.TEXT_HTML)
	public String GetSession(@QueryParam("UserID") int UserID) {
		return U.GetSession(UserID);
	}
}
