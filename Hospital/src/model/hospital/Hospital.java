package model.hospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class Hospital {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospitalmanagement", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	
	public HashMap GetAllHospitals() {
		HashMap<Integer, String> Hospitals = new HashMap <Integer, String> ();
		
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet RHospitals = stmt.executeQuery("SELECT h_id, h_name FROM hospital GROUP BY h_id");

			while(RHospitals.next()) {
				Hospitals.put(RHospitals.getInt("h_id"), RHospitals.getString("h_name"));
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		return Hospitals;
	}
	
	public HashMap GetDoctorDetails(int HospitalID, int TreatmentID) {
		HashMap<Integer, String> Doctor = new HashMap <Integer, String> ();
		ResultSet RDoctor;
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			RDoctor = stmt.executeQuery("SELECT d_id, first_name, last_name FROM doctor WHERE d_id IN (SELECT d_id FROM hospital WHERE h_id = '"+HospitalID+"' AND `treatment_id` = '"+TreatmentID+"')");

			while(RDoctor.next()) {
				Doctor.put(RDoctor.getInt("d_id"), RDoctor.getString("first_name")+" "+RDoctor.getString("last_name"));
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		return Doctor;
	}
	
	public HashMap GetTreatmentDetails(int HospitalID) {
		HashMap<Integer, String> Treatment = new HashMap <Integer, String> ();
		ResultSet RTreatment;
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			RTreatment = stmt.executeQuery("SELECT treatment_id, treatment_name FROM treatment WHERE treatment_id IN (SELECT treatment_id FROM hospital WHERE h_id = '"+HospitalID+"')");

			while(RTreatment.next()) {
				Treatment.put(RTreatment.getInt("treatment_id"), RTreatment.getString("treatment_name"));
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		return Treatment;
	}
}
