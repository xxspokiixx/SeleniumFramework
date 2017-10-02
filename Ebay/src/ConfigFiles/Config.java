package ConfigFiles;

import TestCases.Items_Test_TC;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.MarionetteDriver;

import javax.imageio.ImageIO;



public class Config {
	
	public static int waitForElement = 10;
	public static WebDriver driver;
	public static Properties LocatorProps;
	public static String s9Space="         ";
	public static String Ebay_LocatorRep_Multi = "C:\\workspace\\Ebay\\Locators\\MultiBrowser";
	public static File srcFile;
	public static String executionResultsPath = "C:\\workspace\\Ebay\\ExecutionResults\\";
	public static List<String> attachments = new ArrayList<String>();
	public static final int size=1024*3;
	private static final int IMG_WIDTH =850;
	private static final int IMG_HEIGHT =6944;
	private static JavascriptExecutor jse = (JavascriptExecutor)driver;
	private static WebElement domestic;
	private static Actions actions;
	private static WebDriverWait wait;
	public static Logger APP_LOGS;
	
	
	public static void createLogFile(String fName){	
		String filenameWithTimestamp = fName+getCurrentTime();
		executionResultsPath = executionResultsPath + filenameWithTimestamp;
		System.setProperty("filename", filenameWithTimestamp);		
		APP_LOGS = Logger.getLogger("AutomationLog");
		org.apache.log4j.BasicConfigurator.configure(new NullAppender());
		
	}
	
 	public static void openDriver(String browser) throws InterruptedException{
 		if (browser.equals("Firefox"))
 		{
 			System.setProperty("webdriver.gecko.driver", "C://geckodriver.exe");
 			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
 			capabilities.setCapability("marionette", true);
 			WebDriver driver = new FirefoxDriver(capabilities);
 			driver.get("http://ebay.com/");	
 			driver.manage().window().maximize();
 			 Thread.sleep(1000);
 		}
 		else if(browser.equals("Chrome")){
		System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://ebay.com/");	
		driver.manage().window().maximize();
 		}else {
 			System.out.println("Browser not supported");
 		}
 		
	}
 	
 	public static void dbInsert(String product, String id, String precio, String vendedor ) throws SQLException{
 		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
 			  	"databaseName=ebay;integratedSecurity=true;";
 		String producto = product;
		String idp = id;
		String preciop = precio;
		String vend = vendedor;
		
 			Connection con = null;
 			Statement stmt = null;
 			
 			
 			try {
 				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 					con = DriverManager.getConnection(connectionUrl);
 				System.out.println(" Connection Established");	
 				System.out.println(" Inserting info to DB!");
 				
 				String SQL = "INSERT INTO Productos (Producto, id_Producto,Precio_Producto,Vendedor)VALUES ('"+ producto + "','" + idp + "','" + preciop + "','" + vend + "');" ;
 				stmt = con.createStatement();
 				stmt.executeUpdate(SQL);
 				con.close();
 				
 					}
 			
 			
 			catch(Exception e){
 				con.close();
 				e.printStackTrace();
 			}
 		}
 	

	
 	public static void dbRead()  throws SQLException{
 		String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
 			  	"databaseName=ebay;integratedSecurity=true;";
 			//String dato = columna;
 			
 			
 			Connection con = null;
 			Statement stmt = null;
 			ResultSet rs = null;
 			
 			try {
 				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 					con = DriverManager.getConnection(connectionUrl);
 				System.out.println(" Connection Established!!");			
 				System.out.println(" Reading info from DB!");
 				
 				String SQL = "SELECT * from Productos " ;
 				//String SQL = "WITH Base AS (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) RN FROM Items)SELECT *FROM Base WHERE RN IN ("+ dato + ")";
 				stmt = con.createStatement();
 				rs = stmt.executeQuery(SQL);
 				int i=0;
 				
 				while (rs.next()){ 			
 					System.out.println(rs.getString(1) + "  " + rs.getString(2) );
 					Array ItemNumber = rs.getArray(i++);
 					
 				}
 				
 				con.close();
 			}
 			
 			catch(Exception e){
 				con.close();
 				e.printStackTrace();
 			}
 		}
 	
 	
	
	  public static WebElement waitForLocator(String ObjectIdentifier) throws Exception {
			
			WebElement element = null;
			String objectIdentifierType="";
			String objectIdentifierValue="";
			String objectArray[]=null;
			WebDriverWait wait = new WebDriverWait(driver, waitForElement);
			String printApplog="";
			String errorArray[]=null;
			
			try {
				String object = LocatorProps.getProperty(ObjectIdentifier);
				objectArray = object.split("__");
				objectIdentifierType=objectArray[0].trim();
				objectIdentifierValue=objectArray[1].trim();

	  			System.out.println(s9Space+"Executing waitForLocator method for "+ObjectIdentifier);
				
				switch (objectIdentifierType) {
				case "id":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.id(objectIdentifierValue)));
					Thread.sleep(3000);
					break;
				case "className":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.id(objectIdentifierValue)));
					Thread.sleep(3000);
					break;
				case "cssSelector":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(objectIdentifierValue)));
					Thread.sleep(3000);		
					break;
				case "linkText":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(objectIdentifierValue)));
					Thread.sleep(3000);
					break;
				case "xpath":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(objectIdentifierValue)));
					Thread.sleep(3000);
					break;
				case "name":
					element=wait.until(ExpectedConditions.presenceOfElementLocated(By.name(objectIdentifierValue)));
					Thread.sleep(3000);
					break;
				}
			} catch (Exception e) {
				System.out.println("WaitForLocator is not happening!");
				captureScreenShot("Ebay_");
				Assert.fail();

				return null;
			}
			return element;
		}

	  public static WebElement returnLocator(String ObjectIdentifier) throws Exception {

	 		WebElement elements = null;
	 		String objectIdentifierType="";
	 		String objectIdentifierValue="";
	 		String objectArray[]=null;
	 		String printApplog="";
			String errorArray[]=null;
	 				
	 		try {
	 			String object = LocatorProps.getProperty(ObjectIdentifier);
	 			objectArray= object.split("__");
	 			objectIdentifierType=objectArray[0].trim();
	 			objectIdentifierValue=objectArray[1].trim();
	 			
	  			System.out.println(s9Space+"Executing returnLocator method for "+ObjectIdentifier);
	  			
	 			switch (objectIdentifierType) {
	 			case "id":
	 				elements=driver.findElement(By.id(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;
	 			case "cssSelector":
	 				elements=driver.findElement(By.cssSelector(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;
	 			case "linkText":
	 				elements=driver.findElement(By.linkText(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;
	 			case "xpath":
	 				elements=driver.findElement(By.xpath(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;
	 			case "name":
	 				elements=driver.findElement(By.name(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;
	 			case "className":
	 				elements=driver.findElement(By.className(objectIdentifierValue));
	 				Thread.sleep(250);
	 				break;	
	 			}

	 		} catch (Exception e) {
	 			System.out.println("return Locator is not happening!");
	 			captureScreenShot("Ebay_");
				Assert.fail();
				
	 			return null;
	 		}
	 		return elements;
	 	}

	  
	  public static void waitPop()
	  {	
		wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='hiddenLogo']/img")));
		System.out.println("--> Waiting completed!");	
	  } 
	  
	  public static boolean isElementPresent(String ObjectIdentifier) throws Exception{
			String objectIdentifierType="";
			String objectIdentifierValue="";
			String objectArray[]=null;
			WebDriverWait wait = new WebDriverWait(driver, waitForElement);
	 		String printApplog="";
			String errorArray[];
			
			try {
				String object = LocatorProps.getProperty(ObjectIdentifier);
				objectArray = object.split("__");
				objectIdentifierType=objectArray[0].trim();
				objectIdentifierValue=objectArray[1].trim();

	  			System.out.println(s9Space+"Executing isElementPresent method for "+ObjectIdentifier);
				
				switch (objectIdentifierType) {
				case "id":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(objectIdentifierValue)));
					Thread.sleep(250);
					break;
				case "cssSelector":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(objectIdentifierValue)));
					Thread.sleep(250);		
					break;
				case "linkText":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(objectIdentifierValue)));
					Thread.sleep(250);
					break;
				case "xpath":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objectIdentifierValue)));
					Thread.sleep(250);
					break;
				case "name":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(objectIdentifierValue)));
					Thread.sleep(250);
					break;
				case "className":
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(objectIdentifierValue)));
	 				Thread.sleep(250);
	 				break;	
				}
			} catch (Exception e) {
				System.out.println("WaitForLocator is not happening!");
				captureScreenShot("Ebay_");
				Assert.fail();
				return false;
			}
			
			return true;
		}
	  
	  private static BufferedImage resizeImage(BufferedImage originalImage, int type)
	    {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	    }
	  
	  public static String getCurrentTime(){
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss_aaa(zzz)");
			java.util.Date curDate = new java.util.Date();
			String strDate = sdf.format(curDate);
			String strActDate = strDate.toString();
			return strActDate;
		}
	  
	  public static void captureScreenShot(String fName) throws Exception{
	  		System.out.println("-> Capturing Screenshot");	  		
			srcFile=((TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(executionResultsPath+"\\"+fName+" - "+getCurrentTime()+".png"));
			attachments.add(srcFile.getAbsolutePath());
			File f=new File(executionResultsPath+"\\"+fName+" - "+getCurrentTime()+ ".png");
			String originalf = executionResultsPath+"\\"+fName+" - "+getCurrentTime()+ ".png";
			String compressf=executionResultsPath+"\\"+fName+" - "+getCurrentTime()+ ".png";
			if(f.exists() )
			{
			    double bytes = f.length();
				double kb=(bytes/1024);
				if(kb>size)
				{
					System.out.println("kilobytes : " + kb);
			try
			{
			BufferedImage originalImage = ImageIO.read(new File(originalf));
			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImagePng = resizeImage(originalImage, type);
			ImageIO.write(resizeImagePng, "png", new File(compressf)); 
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
				}
			}
		}
	  
	  public static void Login() throws Exception {
		  LocatorProps = new Properties();
	      LocatorProps.load(new FileInputStream(Ebay_LocatorRep_Multi + ".properties"));
	    	if(isElementPresent("LOGIN_URL") == true){	    		
		    	System.out.println("-->Loging in!");
		    	returnLocator("LOGIN_URL").click();
		    	waitForLocator("USER");
		    	returnLocator("USER").sendKeys("dsolisgarcia@uabc.edu.mx");
		    	waitForLocator("PASSWORD");
		    	returnLocator("PASSWORD").sendKeys("lolitadeinfralar2510#");
		    	waitForLocator("LOGIN_BUTTON");
		    	returnLocator("LOGIN_BUTTON").click();
		    	waitForLocator("MAYBE_LATER");
		    	returnLocator("MAYBE_LATER").click();
		    	waitForLocator("LOGED");
		    	System.out.println("-->Login successful!");
		    	
			}
		}

	  public static void LogOut() throws Exception {
		  if(isElementPresent("USER_NAME") == true){			
			  returnLocator("USER_NAME").click();
			  waitForLocator("SIGN_OUT");
		      returnLocator("SIGN_OUT").click();
				System.out.println("--> Closing browser!");	
			}
		  driver.quit();
		  System.out.println("--> Bye bye!");	
	  }
	  
	  	  
	  public static void ScrollUp() throws Exception{
		  ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,0)", "");
		  System.out.println("--> Scrolling up!");	
		   
	  }
	  
	  public static void hideElement(String xpath)
	  {
	      WebElement element = driver.findElement(By.xpath(xpath));       
	      Boolean isPresent = driver.findElements(By.xpath(xpath)).size() > 0;
	   if(isPresent == true){
	      ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", element);
	   }
	  }
	  
	  public static void waitSpot()
	  {	
		wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='hiddenLogo']/img")));
		System.out.println("--> Waiting completed!");	
	  }
}


