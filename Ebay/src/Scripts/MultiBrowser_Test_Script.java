package Scripts;

import TestCases.MultiBrowser_Test_TC;

public class MultiBrowser_Test_Script extends MultiBrowser_Test_TC{
	public static void main(String[] args) throws Exception {
		openDriver("Chrome");
		Login();
		watchList();
		LogOut();
		
 }
}
