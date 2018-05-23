package logic;

import ocsf.client.AESClient;

public class Globals {
	public enum Type {
		VISIBLE,HIDDEN;
	}
	
	/**
	 * handle a core exception when the program cannot continue to function after this type of exception
	 * this function should show and alert with exception details and than System.exit(1) at the end of it.
	 * @param e the exception thrown
	 */
	public static void handleException(Exception e) {
		
		e.printStackTrace();
		System.exit(1);
	}
}
