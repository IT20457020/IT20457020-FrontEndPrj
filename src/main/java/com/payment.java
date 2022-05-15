package com;

import java.sql.*;

public class payment
{
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
 
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/payment_management_system", "root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	public String readPayments()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
 
			// Prepare HTML table to display
			output = "<table border='1'><tr><th>Payment ID</th><th>Customer Name</th><th>Email</th><th>Bill ID</th><th>Area Office</th> <th> Card Number</th> Pay amount<th> </th><th>Update</th><th>Remove</th></tr>";
 
			String query = "select * from payments";
			
			Statement stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
 
			// iterate through the rows in the result set
			while (rs.next())
			{
				String paymentID = Integer.toString(rs.getInt("paymentID"));
				String cusName = rs.getString("cusName"); 
				String email = rs.getString("email");
				String billID = Integer.toString(rs.getInt("billID"));
				String areaOffice = rs.getString("areaOffice");
				String cardNum = rs.getString("cardNum");
				String payAmount = Double.toString(rs.getDouble("payAmount"));
 
				// Add data into the HTML table
				output += "<tr><td><input id='hidPaymentIDUpdate'name='hidpaymentIDUpdate'type='hidden' value='" + paymentID+ "'>" + cusName + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + billID + "</td>";
				output += "<td>" + areaOffice + "</td>";
				output += "<td>" + cardNum + "</td>";
				output += "<td>" + payAmount + "</td>";
 
				// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"+ "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-itemid='"+ paymentID + "'>" + "</td></tr>";
			}
 
			con.close();
 
			// Complete the HTML table
			output += "</table>";
		}
 
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
 
		return output;
	}
	
	public String insertPayment(String cusName, String email,String billID, String areaOffice, String cardNum, String payAmount)

    {
		String output = "";

		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}
 
			// create a prepared statement
			String query = " insert into payments(`paymentID`,`cusName`,`email`,`billID`, `areaOffice`,`cardNum`,`payAmount`)"+ " values (?, ?, ?, ?, ?,?,?)";
		 
			PreparedStatement preparedStmt = con.prepareStatement(query);
		 
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, cusName);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, billID);
			preparedStmt.setString(5, areaOffice);
			preparedStmt.setString(6, cardNum);
			preparedStmt.setString(7, payAmount);
		 
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" +newPayments + "\"}";
		 }
		
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment details.\"}";
			 System.err.println(e.getMessage());
		 }
		
		 return output;
		 
		 }
	
		 public String updatePayment(String paymentID, String cusName, String email, String billID, String areaOffice, String cardNum, String payAmount)
		 {
			 String output = "";
			 try
			 {
				 Connection con = connect();
				 if (con == null)
				 {
					 return "Error while connecting to the database for updating.";
				 }
				 
				 // create a prepared statement
				 String query = "UPDATE payments SET cusName=?,email=?,billID=?, areaOffice=?,cardNum=?, payAmount=? WHERE paymentID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
				 // binding values
				 preparedStmt.setString(1, cusName);
				 preparedStmt.setString(2, email);
				 preparedStmt.setString(3, billID);
				 preparedStmt.setString(4, areaOffice);
				 preparedStmt.setString(5, cardNum);
				 preparedStmt.setString(6, payAmount); 
		
				 // execute the statement
				 preparedStmt.execute();
				 con.close();
		
				 String newPayment = readPayments();
				 output = "{\"status\":\"success\", \"data\": \"" +
				 newPayment + "\"}";
		 }
			 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while updating the Payment.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		return output;
	}
		
	public String deletePayment(String paymentID)
	{
		 String output = "";
		 try
		 {
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database for deleting.";
			 }
		 
			 // create a prepared statement
			 String query = "delete from items where itemID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
		 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(paymentID));
		 
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 String newPayment = readPayments();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayment + "\"}";
		 }
		 
		 catch (Exception e)
		 {
			 output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment history.\"}";
			 System.err.println(e.getMessage());
		 }
		 
		 return output;
		 
		 }
	}
		 