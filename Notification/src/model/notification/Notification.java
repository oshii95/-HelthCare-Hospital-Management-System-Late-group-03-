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
	
	public String CreateAppoinment(int AppoinmentID) {
		String Status = "Pending";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO notification(apt_id, status) VALUES ('"+AppoinmentID+"','"+Status+"')");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Created Successfully !";
	}
	
	public String DeleteAppoinment(int AppoinmentID) {
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM notification WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Deleted Successfully !";
	}
	
	public String AcceptAppoinment(int AppoinmentID) {
		String Status = "Accept";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("INSERT INTO notification(apt_id, status) VALUES ('"+AppoinmentID+"','"+Status+"')");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Accepted Successfully !";
	}
	
	public String ConfirmAppoinment(int AppoinmentID) {
		String Status = "Approved";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE notification SET status = '"+Status+"' WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Confirmed Successfully !";
	}
	
	public String RejectAppoinment(int AppoinmentID) {
		String Status = "Cancelled";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE notification SET status = '"+Status+"' WHERE apt_id = '"+AppoinmentID+"'");

		} catch(Exception E) {
			System.out.println(E);
		}
		return "Appoinment Cancelled Successfully !";
	}
}
