package Scripts;

import TestCases.Items_Test_TC;


public class Items_Test_Script extends Items_Test_TC{
	public static void main(String[] args) throws Exception {
		openDriver("Chrome");
		Login();
		searchProduct();
		
 }
}
