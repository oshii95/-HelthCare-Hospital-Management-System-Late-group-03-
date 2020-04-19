package model.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	
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
	
	public String StartSession(int UserID) {
		if(!GetSessionStatus(UserID)) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				stmt.executeUpdate("INSERT INTO session(user) VALUES ('"+UserID+"')");
	
			} catch(Exception E) {
				System.out.println(E);
			}
		}
		return "Session Started !";
	}
	
	public String EndSession(int UserID) {
		if(GetSessionStatus(UserID)) {
			try {
				Connection con = connect();
				Statement stmt = con.createStatement();
				stmt.executeUpdate("DELETE FROM session WHERE user = '"+UserID+"'");
	
			} catch(Exception E) {
				System.out.println(E);
			}
		}
		return "Session Ended !";
	}
	
	public Boolean GetSessionStatus(int UserID) {
		int user = 0;
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet RUser = stmt.executeQuery("SELECT user FROM session WHERE user = '"+UserID+"'");

			while(RUser.next()) {
				user = RUser.getInt("user");
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		if(UserID == user) {
			return true;
		} else {
			return false;
		}
	}
	
	public String GetSession(int UserID) {
		if(GetSessionStatus(UserID)) {
			return "True";
		} else {
			return "False";
		}
	}
	
	public String GetDoctorEmail(int UserID) {
		String Email = "";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet RUser = stmt.executeQuery("SELECT email FROM doctor WHERE d_id = '"+UserID+"'");

			while(RUser.next()) {
				Email = RUser.getString("email");
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		
		return Email;
	}
	
	public String GetPatientEmail(int UserID) {
		String Email = "";
		try {
			Connection con = connect();
			Statement stmt = con.createStatement();
			ResultSet RUser = stmt.executeQuery("SELECT email FROM patient WHERE p_id = '"+UserID+"'");

			while(RUser.next()) {
				Email = RUser.getString("email");
			}
		} catch(Exception E) {
			System.out.println(E);
		}
		
		return Email;
	}
}
