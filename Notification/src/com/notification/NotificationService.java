package com.notification;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.notification.Notification;

@Path("/Notification")
public class NotificationService {
	Notification NOT = new Notification();
	
	@GET
	@Path("/CreateAppoinment")
	@Produces(MediaType.TEXT_HTML)
	public String CreateAppoinment(@QueryParam("AppoinmentID") int AppoinmentID) {
		return NOT.CreateAppoinment(AppoinmentID);
	}
	
	@GET
	@Path("/DeleteAppoinment")
	@Produces(MediaType.TEXT_HTML)
	public String DeleteAppoinment(@QueryParam("AppoinmentID") int AppoinmentID) {
		return NOT.DeleteAppoinment(AppoinmentID);
	}
	
	@GET
	@Path("/ConfirmAppoinment")
	@Produces(MediaType.TEXT_HTML)
	public String ConfirmAppoinment(@QueryParam("AppoinmentID") int AppoinmentID) {
		return NOT.ConfirmAppoinment(AppoinmentID);
	}
	
	@GET
	@Path("/RejectAppoinment")
	@Produces(MediaType.TEXT_HTML)
	public String RejectAppoinment(@QueryParam("AppoinmentID") int AppoinmentID) {
		return NOT.RejectAppoinment(AppoinmentID);
	}
}
