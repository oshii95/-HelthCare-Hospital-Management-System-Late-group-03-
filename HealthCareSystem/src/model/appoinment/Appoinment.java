package model.appoinment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class Appoinment {

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
	
	
	public String GetAllHospitals() {
		String text;
		String url = "http://localhost:8081/Hospital/HospitalService/Hospital/Hospitals";
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
	
	public String GetTreatmentDetails(int HospitalID) {
		String text;
		String url = "http://localhost:8081/Hospital/HospitalService/Hospital/Treatments?HospitalID="+HospitalID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
	
	public String GetDoctorDetails(int HospitalID, int TreatmentID) {
		String text;
		String url = "http://localhost:8081/Hospital/HospitalService/Hospital/Doctors?HospitalID="+HospitalID+"&TreatmentID="+TreatmentID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
	
	public HashMap GetFreeTimeSlots(Date Date) {
		HashMap<Integer, String> Times = new HashMap <Integer, String> ();
		Times.put(1,"8.00am - 9.00am");
		Times.put(2,"9.00am - 10.00am");
		Times.put(3,"10.00am - 11.00am");
		Times.put(4,"11.00am - 12.00pm");
		Times.put(5,"12.00pm - 1.00pm");
		Times.put(6,"1.00pm - 2.00pm");
		Times.put(7,"2.00pm - 3.00pm");
		Times.put(8,"3.00pm - 4.00pm");
		Times.put(8,"4.00pm - 5.00pm");
		Times.put(9,"5.00pm - 6.00pm");
		Times.put(10,"6.00pm - 7.00pm");
		
		int[] LocalTime = new int[9];
		int count = 0, count2;
		
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet RHospitals = stmt.executeQuery("SELECT time FROM appoinment WHERE date = '"+Date+"'");

			while(RHospitals.next()) {
				LocalTime[count] = RHospitals.getInt("time");
				count++;
			}
			
			count = 0;
			count2 = 0;
			while(count2 < 9 ) {
				if(LocalTime[count2] == 1) {
					Times.remove(1);
				} else if(LocalTime[count2] == 2) {
					Times.remove(2);
				} else if(LocalTime[count2] == 3) {
					Times.remove(3);
				} else if(LocalTime[count2] == 4) {
					Times.remove(4);
				} else if(LocalTime[count2] == 5) {
					Times.remove(5);
				} else if(LocalTime[count2] == 6) {
					Times.remove(6);
				} else if(LocalTime[count2] == 7) {
					Times.remove(7);
				} else if(LocalTime[count2] == 8) {
					Times.remove(8);
				} else if(LocalTime[count2] == 8) {
					Times.remove(9);
				} else if(LocalTime[count2] == 9) {
					Times.put(9,"5.00pm - 6.00pm");
				} else if(LocalTime[count2] == 10) {
					Times.remove(10);
				}
				count2++;
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		
		return Times;
	}
	
	public String CreateAppoinment(int patientID, int doctorID, int hospitalID, int treatmentID, int time) {	
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO appoinment(p_id, d_id, h_id, treatment_id, time) VALUES ('"+patientID+"','"+doctorID+"','"+hospitalID+"','"+treatmentID+"','"+time+"')");
			
		} catch(Exception E) {
			System.out.println(E);
		}
		return UpdateCreateNotification();
	}
	
	public int GetLastUpdatedAppoinmentID() {
		int AppoinmentID = 0;
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet ID = stmt.executeQuery("SELECT MAX(apt_id) AS ID FROM appoinment");

			while(ID.next()) {
				AppoinmentID = ID.getInt("ID");
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		return AppoinmentID;
	}
	
	public String UpdateCreateNotification() {
		String text = "Innitial";
		String url = "http://localhost:8081/Notification/NotificationService/Notification/CreateAppoinment?AppoinmentID="+GetLastUpdatedAppoinmentID();
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("text/html").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
	
	public String DeleteNotifiication(int AppoinmentID) {
		String text = "Innitial";

		String url = "http://localhost:8081/Notification/NotificationService/Notification/DeleteAppoinment?AppoinmentID="+AppoinmentID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("text/html").get(ClientResponse.class);
        text = resp.getEntity(String.class);

        return text;
	}
	
	public String DeleteAppoinment(int AppoinmentID) {
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM appoinment WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		return DeleteNotifiication(AppoinmentID);
	}
	
	public String ConfirmAppoinment(int AppoinmentID) {
		String text = "Innitial";

		String url = "http://localhost:8081/Notification/NotificationService/Notification/ConfirmAppoinment?AppoinmentID="+AppoinmentID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("text/html").get(ClientResponse.class);
        text = resp.getEntity(String.class);

        return text;
	}
	
	public String RejectAppoinment(int AppoinmentID) {
		String text = "Innitial";

		String url = "http://localhost:8081/Notification/NotificationService/Notification/RejectAppoinment?AppoinmentID="+AppoinmentID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("text/html").get(ClientResponse.class);
        text = resp.getEntity(String.class);

        return text;
	}
	public HashMap ViewConfirmedAppoinments(int UserID, int Type) {
		String[] PatientDetails = new String[3]; 
		String[] DoctorDetails = new String[3]; 
		
		HashMap<Integer, String[]> Patient = new HashMap <Integer, String[]> ();
		HashMap<Integer, String[]> Doctor = new HashMap <Integer, String[]> ();
		HashMap<Integer, String[]> Result = new HashMap <Integer, String[]> ();
		

		String Status = "Approved";
		if(Type == 1) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				ResultSet RPatient = stmt.executeQuery("SELECT A.apt_id AS ID, (SELECT first_name FROM doctor WHERE d_id = A.d_id) AS DOCTOR, (SELECT DISTINCT h_name FROM hospital WHERE h_id = A.h_id) AS HOSPITAL, (SELECT treatment_name FROM treatment WHERE treatment_id = A.treatment_id) AS TREATMENT FROM appoinment A WHERE A.p_id = '"+UserID+"' AND A.apt_id IN (SELECT apt_id FROM notification WHERE status = '"+Status+"')");

				while(RPatient.next()) {
					PatientDetails[0] = RPatient.getString("DOCTOR");
					PatientDetails[1] = RPatient.getString("HOSPITAL");
					PatientDetails[2] = RPatient.getString("TREATMENT");
					Patient.put(RPatient.getInt("ID"), PatientDetails);
				}
			} catch(Exception E) {
				System.out.println(E);
			}
			Result = Patient;
		} else if (Type == 2) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				ResultSet RDoctor = stmt.executeQuery("SELECT A.apt_id AS ID, (SELECT first_name FROM patient WHERE p_id = A.p_id) AS PATIENT, (SELECT DISTINCT h_name FROM hospital WHERE h_id = A.h_id) AS HOSPITAL, (SELECT treatment_name FROM treatment WHERE treatment_id = A.treatment_id) AS TREATMENT FROM appoinment A WHERE A.p_id = '"+UserID+"' AND A.apt_id IN (SELECT apt_id FROM notification WHERE status = Approved)");

				while(RDoctor.next()) {
					DoctorDetails[0] = RDoctor.getString("PATIENT");
					DoctorDetails[1] = RDoctor.getString("HOSPITAL");
					DoctorDetails[2] = RDoctor.getString("TREATMENT");
					Doctor.put(RDoctor.getInt("ID"), DoctorDetails);
				}
			} catch(Exception E) {
				System.out.println(E);
			}
			Result = Doctor;
		}
		return Result;
	}
	
	public HashMap ViewPendingAppoinments(int UserID, int Type) {
		String[] PatientDetails = new String[3]; 
		String[] DoctorDetails = new String[3]; 
		
		HashMap<Integer, String[]> Patient = new HashMap <Integer, String[]> ();
		HashMap<Integer, String[]> Doctor = new HashMap <Integer, String[]> ();
		HashMap<Integer, String[]> Result = new HashMap <Integer, String[]> ();
		

		String Status = "Pending";
		if(Type == 1) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				ResultSet RPatient = stmt.executeQuery("SELECT A.apt_id AS ID, (SELECT first_name FROM doctor WHERE d_id = A.d_id) AS DOCTOR, (SELECT DISTINCT h_name FROM hospital WHERE h_id = A.h_id) AS HOSPITAL, (SELECT treatment_name FROM treatment WHERE treatment_id = A.treatment_id) AS TREATMENT FROM appoinment A WHERE A.p_id = '"+UserID+"' AND A.apt_id IN (SELECT apt_id FROM notification WHERE status = '"+Status+"')");

				while(RPatient.next()) {
					PatientDetails[0] = RPatient.getString("DOCTOR");
					PatientDetails[1] = RPatient.getString("HOSPITAL");
					PatientDetails[2] = RPatient.getString("TREATMENT");
					Patient.put(RPatient.getInt("ID"), PatientDetails);
				}
			} catch(Exception E) {
				System.out.println(E);
			}
			Result = Patient;
		} else if (Type == 2) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				ResultSet RDoctor = stmt.executeQuery("SELECT A.apt_id AS ID, (SELECT first_name FROM patient WHERE p_id = A.p_id) AS PATIENT, (SELECT DISTINCT h_name FROM hospital WHERE h_id = A.h_id) AS HOSPITAL, (SELECT treatment_name FROM treatment WHERE treatment_id = A.treatment_id) AS TREATMENT FROM appoinment A WHERE A.p_id = '"+UserID+"' AND A.apt_id IN (SELECT apt_id FROM notification WHERE status = Approved)");

				while(RDoctor.next()) {
					DoctorDetails[0] = RDoctor.getString("PATIENT");
					DoctorDetails[1] = RDoctor.getString("HOSPITAL");
					DoctorDetails[2] = RDoctor.getString("TREATMENT");
					Doctor.put(RDoctor.getInt("ID"), DoctorDetails);
				}
			} catch(Exception E) {
				System.out.println(E);
			}
			Result = Doctor;
		}
		return Result;
	}
	
	public Boolean GetSession(int UserID) {
		String text;
		String url = "http://localhost:8081/User/UserService/User/GetSession?UserID="+UserID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("text/html").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        
        if(text.contains("True")) {
        	return true;
        } else {
        	return false;
        }
	}
}