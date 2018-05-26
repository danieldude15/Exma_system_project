package ocsf.server;

import java.sql.SQLException;

public class ServerGlobals {
	public static final String ServerGuiID = "ServerGui";
	public static final String ServerGuiPath = "/fxml/ServerGui.fxml";
	public static AESServer server = null;
	public static final String dbHost = "84.108.116.235/aes";
	public static final String dbuser="aesUser";
	public static final String dbpass="QxU&v&HMm0t&";
	
	public static void handleSQLException(SQLException e) {
		e.printStackTrace();
		System.exit(1);
	}
	
	
	/*
	#####################   NIV's Globals!    #########################
	*/
	
	
	
	
	/*
	#####################  END OF NIV's Globals!    #########################
	*/
	
	
	
	
	
	/*
	#####################   ITZIKS's Globals!    #########################
	*/




	/*
	#####################  END OF ITZIKS's Globals!    #########################
	*/
	
	
	
	
	
	/*
	#####################   NATHAN's Globals!    #########################
	*/




	/*
	#####################  END OF NATHAN's Globals!    #########################
	*/
	
	
	
	
	
	
	/*
	#####################   DANIEL's Globals!    #########################
	*/




	/*
	#####################  END OF DANIEL's Globals!    #########################
	*/
	

}
