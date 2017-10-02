package TestCases;

import ConfigFiles.Config;

public class MultiBrowser_Test_TC extends Config{
	
	public static void watchList() throws Exception {
		if(isElementPresent("SEARCHBOX") == true){	    		
	    	System.out.println("-->Lets search!");
	    	returnLocator("SEARCHBOX").sendKeys("222635870847");
	    	waitForLocator("SEARCH_BUTTON");
	    	returnLocator("SEARCH_BUTTON").click();
	    	waitForLocator("SEARCH_COUNT");
	    String bodyText = returnLocator("SEARCH_COUNT").getText();
	    System.out.println("Result items: " + bodyText);
	    waitForLocator("POP");
    	returnLocator("POP").click();
	    waitForLocator("PRODUCT");
    	returnLocator("PRODUCT").click();
    	waitForLocator("PROD_NAME");
    	waitForLocator("WATCH_LIST");
      	returnLocator("WATCH_LIST").click();
      	waitForLocator("MY_EBAY");
      	returnLocator("MY_EBAY").click();
      	waitForLocator("MY_WATCH");
      	returnLocator("MY_WATCH").click();
      	waitForLocator("WATCH_PRODUCT");
     // 	returnLocator("WATCH_PRODUCT").click();
      	waitForLocator("BUY_NOW");
      	returnLocator("BUY_NOW").click();
      	waitForLocator("BUY_IT");
      	returnLocator("BUY_IT").click();
      	waitForLocator("NO_THANKS");
      	returnLocator("NO_THANKS").click();
      	waitForLocator("COMMIT_BUY");
      	//returnLocator("COMMIT_BUY").click();
		}
	}
}
