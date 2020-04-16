package com.appoinment;

import model.appoinment.Appoinment;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.websocket.server.PathParam;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Appoinments")
public class AppoinmentService {
	Appoinment APP = new Appoinment();
	
	@GET
	@Path("/Hospitals")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllHospitalDetails(@QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.GetAllHospitals();
		} else {
			return "Invalid session";
		}
	}
	
	@GET
	@Path("/Treatments")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTreatmentDetailsByHospital(@QueryParam("HospitalID") int HospitalID, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.GetTreatmentDetails(HospitalID).toString();
		} else {
			return "Invalid session";
		}
	}
	
	@GET
	@Path("/Doctors")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetDoctorDetailsByTreatment(@QueryParam("HospitalID") int HospitalID, @QueryParam("TreatmentID") int TreatmentID, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.GetDoctorDetails(HospitalID, TreatmentID);
		} else {
			return "Invalid session";
		}
	}
	
	@GET
	@Path("/Time")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetFreeTimeSlots(@QueryParam("Date") Date date, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.GetFreeTimeSlots(date).toString();
		} else {
			return "Invalid session";
		}
	}
	
	
	@POST
	@Path("/Appoinment")
	@Produces(MediaType.APPLICATION_JSON)
	public String CreateAppoinment(@QueryParam("patientID") int patientID, @QueryParam("doctorID") int doctorID, @QueryParam("hospitalID") int hospitalID, @QueryParam("treatmentID") int treatmentID, @QueryParam("time") int time) {
		if(APP.GetSession(patientID)) {
			return APP.CreateAppoinment(patientID, doctorID, hospitalID, treatmentID, time);
		} else {
			return "Invalid session";
		}
	}
	
	@DELETE
	@Path("/DeleteAppoinment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String DeleteAppoinment(@QueryParam("AppoinmentID") int AppoinmentID, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.DeleteAppoinment(AppoinmentID);
		} else {
			return "Invalid session";
		}
	}
	
	@PUT
	@Path("/ConfirmAppoinment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String ConfirmAppoinment(@QueryParam("AppoinmentID") int AppoinmentID, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.ConfirmAppoinment(AppoinmentID);
		} else {
			return "Invalid session";
		}
	}
	
	@PUT
	@Path("/RejectAppoinment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String RejectAppoinment(@QueryParam("AppoinmentID") int AppoinmentID, @QueryParam("UserID") int UserID) {
		if(APP.GetSession(UserID)) {
			return APP.RejectAppoinment(AppoinmentID);
		} else {
			return "Invalid session";
		}
	}
	
	@GET
	@Path("/ViewConfirmedAppoinments")
	@Produces(MediaType.APPLICATION_JSON)
	public String ViewConfirmedAppoinments(@QueryParam("UserID") int UserID, @QueryParam("Type") int Type) {
		if(APP.GetSession(UserID)) {
			return APP.ViewConfirmedAppoinments(UserID, Type).toString();
		} else {
			return "Invalid session";
		}
	}
	
	@GET
	@Path("/ViewPendingAppoinments")
	@Produces(MediaType.APPLICATION_JSON)
	public String ViewPendingAppoinments(@QueryParam("UserID") int UserID, @QueryParam("Type") int Type) {
		if(APP.GetSession(UserID)) {
			return APP.ViewPendingAppoinments(UserID, Type).toString();
		} else {
			return "Invalid session";
		}
	}
}
