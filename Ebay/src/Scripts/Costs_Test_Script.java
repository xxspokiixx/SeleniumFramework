package Scripts;

import TestCases.Costs_Test_TC;


public class Costs_Test_Script extends Costs_Test_TC{
	public static void main(String[] args) throws Exception {
		openDriver("Chrome");
		Login();
		costsProduct();
		
 }
}
