package ocsf.server;

import java.sql.SQLException;

public class ServerGlobals {
	public static final String ServerGuiID = "Server Configuration";
	public static final String ServerGuiPath = "/resources/fxml/ServerGui.fxml";
	public static AESServer server = null;
	
	public static void handleSQLException(SQLException e) {
		e.printStackTrace();
		System.out.println("SQLState: "+ e.getSQLState());
		System.out.println("Error Code: "+e.getErrorCode());
		System.out.println("Msg: "+e.getMessage());
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
