package model.notification;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Notification {

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
	
	public String CreateAppoinment(int AppoinmentID, int DoctorID) {
		String Status = "Pending";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO notification(apt_id, status) VALUES ('"+AppoinmentID+"','"+Status+"')");

		} catch(Exception E) {
			System.out.println(E);
		}
		//CALL CREATE PAYMENT FUNCTION WHICH IS TO BE IMPLEMENTED IN PAYMENT SERVICE
		return "Appoinment Created Successfully !\nReciept Sent To "+GetDoctorEmail(DoctorID);
	}
	
	public String DeleteAppoinment(int AppoinmentID, int UserID) {
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM notification WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		//CALL DELETE PAYMENT FUNCTION WHICH IS TO BE IMPLEMENTED IN PAYMENT SERVICE
		return "Appoinment Deleted Successfully !\nReciept Sent To "+GetPatientEmail(UserID);
	}
	
	public String ConfirmAppoinment(int AppoinmentID, int UserID) {
		String Status = "Approved";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE notification SET status = '"+Status+"' WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Confirmed Successfully !\nReciept Sent To "+GetPatientEmail(UserID);
	}
	
	public String RejectAppoinment(int AppoinmentID, int UserID) {
		String Status = "Cancelled";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE notification SET status = '"+Status+"' WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		//CALL DELETE PAYMENT FUNCTION WHICH IS TO BE IMPLEMENTED IN PAYMENT SERVICE
		return "Appoinment Cancelled Successfully !\nReciept Sent To "+GetDoctorEmail(UserID);
	}
	
	public String GetDoctorEmail(int UserID) {
		String text;
		String url = "http://localhost:8081/User/UserService/User/GetDoctorEmail?UserID="+UserID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
	
	public String GetPatientEmail(int UserID) {
		String text;
		String url = "http://localhost:8081/User/UserService/User/GetPatientEmail?UserID="+UserID;
        Client restClient = Client.create();
        WebResource webResource = restClient.resource(url);
        ClientResponse resp = webResource.accept("application/json").get(ClientResponse.class);
        text = resp.getEntity(String.class);
        return text;
	}
}
