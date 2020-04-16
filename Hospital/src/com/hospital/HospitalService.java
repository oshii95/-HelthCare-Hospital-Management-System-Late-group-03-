package com.hospital;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import model.hospital.Hospital;


@Path("/Hospital")
public class HospitalService {
	Hospital H = new Hospital();
	
	@GET
	@Path("/Hospitals")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetAllHospitalDetails() {
			return H.GetAllHospitals().toString();
	}
	
	@GET
	@Path("/Doctors")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetDoctorDetailsByTreatment(@QueryParam("HospitalID") int HospitalID, @QueryParam("TreatmentID") int TreatmentID) {
			return H.GetDoctorDetails(HospitalID, TreatmentID).toString();
	}
	
	@GET
	@Path("/Treatments")
	@Produces(MediaType.APPLICATION_JSON)
	public String GetTreatmentDetailsByHospital(@QueryParam("HospitalID") int HospitalID) {
			return H.GetTreatmentDetails(HospitalID).toString();
	}
}
