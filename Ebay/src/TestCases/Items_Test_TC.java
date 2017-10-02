package TestCases;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ConfigFiles.Config;

public class Items_Test_TC extends Config{
	
	public static void searchProduct() throws Exception {
		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
 			  	"databaseName=ebay;integratedSecurity=true;";
 
 			Connection con = null;
 			Statement stmt = null;
 			ResultSet rs = null;
 			
 			try {
 				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 					con = DriverManager.getConnection(connectionUrl);
 				System.out.println(" Connection Established!!");			
 				System.out.println(" Reading info from DB!");
 
 				int i = 1;
 				String item;
 				String SQL2,SQL;
 			
 				
 				while(i <=5){
 				SQL2 = "WITH Base AS (SELECT * , ROW_NUMBER() OVER (ORDER BY ID) RN FROM dbo.Items)SELECT * FROM Base WHERE RN IN ("+ i + ")";
 				System.out.println(SQL2);
 				stmt = con.createStatement();
 				System.out.println(stmt);
 				rs = stmt.executeQuery(SQL2);
 				System.out.println(rs);
 				while (rs.next()){ 			
 					System.out.println(rs.getString(2)); 	
 					item = null;
 					item = rs.getString(2);
 				
 				 
 			   		
 			    System.out.println("-->Lets search!");
 			    returnLocator("SEARCHBOX").clear();
 			    returnLocator("SEARCHBOX").sendKeys(item);
 			    waitForLocator("SEARCH_BUTTON");
 			    returnLocator("SEARCH_BUTTON").click();
 			    waitForLocator("SEARCH_COUNT");
 			    String bodyText = returnLocator("SEARCH_COUNT").getText();
 			    System.out.println("Result items: " + bodyText);
 			    System.out.println("PRODUCT"+ i);	
 			    if(i==1)
 			   	{returnLocator("POP").click();
 			   	}
 		    	waitForLocator("PRODUCT"+ i);
		    	returnLocator("PRODUCT"+i).click();
		    	waitForLocator("PROD_NAME");
 		    	String producto = returnLocator("PROD_NAME").getText();
 		    	SQL = "UPDATE Items SET ID='"+i+"'"+",ItemNumber='"+item+"'"+",ItemName='"+producto+"' WHERE ID = '"+i+"';";
 		    	stmt = con.createStatement();
 		    	stmt.executeUpdate(SQL);
 		    	
 													
 								}
 				i++;
 			}
 			}
 			
 			catch(Exception e){
 				con.close();
 				e.printStackTrace();
 			}
 		}
 	
		
		
	
	}

