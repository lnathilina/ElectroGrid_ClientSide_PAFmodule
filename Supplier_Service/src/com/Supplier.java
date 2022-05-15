package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


public class Supplier {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/billsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertSupplier(String AccNo, String UnitPrice, String StartDate, String LastDate, String NoOfUnits)  
	{   
		String output = ""; 	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into billings(`Sup_ID`,`AccNo`,`UnitPrice`,`StartDate`,`LastDate`,`NoOfUnits`)" + " values (?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setInt(1, 0);
			 preparedStmt.setString(2, AccNo);
			 preparedStmt.setString(3, UnitPrice);
			 preparedStmt.setString(4, StartDate);
			 preparedStmt.setString(5, LastDate);
			 preparedStmt.setString(6, NoOfUnits);
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newSupplier = readSupplier(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newSupplier + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Suppliers.\"}";  
			System.err.println(e.getMessage());   
		} 		
	  return output;  
	} 	
	
	public String readSupplier()  
	{   
		String output = ""; 
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border=\'1\'><tr><th>Account No</th><th>Unit Price </th><th>Start Date</th><th>Last Date</th><th>No Of Units</th><th>Update</th><th>Remove</th></tr>";
	 
			String query = "select * from suppliers";    
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				 String Sup_ID = Integer.toString(rs.getInt("Sup_ID"));
				 String AccNo = rs.getString("AccNo");
				 String UnitPrice = rs.getString("UnitPrice");
				 String StartDate = rs.getString("StartDate");
				 String LastDate = rs.getString("LastDate");
				 String NoOfUnits = rs.getString("NoOfUnits");
				 
				// Add into the html table 
				output += "<tr><td><input id=\'hidSupplierIDUpdate\' name=\'hidSupplierIDUpdate\' type=\'hidden\' value=\'" + Sup_ID + "'>" 
							+ AccNo + "</td>"; 
				output += "<td>" + UnitPrice + "</td>";
				output += "<td>" + StartDate + "</td>";
				output += "<td>" + LastDate + "</td>";
				output += "<td>" + NoOfUnits + "</td>";
	 
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-supplierid='" + Sup_ID + "'>" + "</td></tr>"; 
			
			}
			con.close(); 
	   
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the suppliers.";    
			System.err.println(e.getMessage());   
		} 	 
		return output;  
	}
	
	public String updateSupplier(String Sup_ID, String AccNo, String UnitPrice, String StartDate, String LastDate, String NoOfUnits)  
	{   
		String output = "";  
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			String query = "UPDATE suppliers SET AccountNo=?,UnitPrice=?,SupplierStartDate=?,SupplierEndDate=?,NoOfUnits=?"  + "WHERE Sup_ID=?";  	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setString(1, AccNo);
			 preparedStmt.setString(2, UnitPrice);
			 preparedStmt.setString(3, StartDate);
			 preparedStmt.setString(4, LastDate);
			 preparedStmt.setString(5, NoOfUnits);
			 preparedStmt.setInt(6, Integer.parseInt(Sup_ID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newBilling = readSupplier();    
			output = "{\"status\":\"sudccess\", \"data\": \"" + newBilling + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the billing.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 
	
	public String deleteSupplier(String Sup_ID)   
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
			String query = "delete from suppliers where Sup_ID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(Sup_ID)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newSupplier = readSupplier();    
			output = "{\"status\":\"success\", \"data\": \"" +  newSupplier + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the supplier.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
