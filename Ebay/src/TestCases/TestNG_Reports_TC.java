package TestCases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ConfigFiles.Config;
import Logger.Logger;

public class TestNG_Reports_TC  extends Config{
	
	public static  void reportTestNG() throws Exception {
	 	 
		Logger log = new Logger("");
		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
	 			  	"databaseName=ebay;integratedSecurity=true;";

	 			Connection con = null;
	 			Statement stmt = null;
	 			ResultSet rs = null;
	 			String result ="";
	 			try {
	 				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	 					con = DriverManager.getConnection(connectionUrl);
	 				System.out.println(" Connection Established!!");			
	 				System.out.println(" Reading info from DB!");
	 				
	 				String SQL = "SELECT Items.ItemName,Costs.Price,Costs.Shipping,Costs.Total FROM Items INNER JOIN Costs ON Items.ItemNumber=Costs.ItemNumber;" ;	 		
	 				stmt = con.createStatement();
	 				rs = stmt.executeQuery(SQL);
	 				
	 				while (rs.next()){ 			
	 					System.out.println(rs.getString(1) + " --  " + rs.getString(2)+ " --  " + rs.getString(3)+ " --  " + rs.getString(4) );
	 					result=result+ "\n"+rs.getString(1) + " --  " + rs.getString(2)+ " --  " + rs.getString(3)+ " --  " + rs.getString(4) + "\n";
	 				}
	 				con.close();
	 				System.out.println("Connection Closed");
	 				log.createFile(result);
	 				System.out.println("Connection Closed");
	 			}
	 			
	 			catch(Exception e){
	 				con.close();
	 				e.printStackTrace();
	 			}
	 		}
	}

