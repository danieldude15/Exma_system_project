package ocsf.client;

import java.io.IOException;

import GUI.ScreensController;
import javafx.stage.Stage;

public class ClientGlobals {
	public static AESClient client = null;
	public static ScreensController mainContainer;
	public static Stage primaryStage;
	
	/*
	 * Screen Controllers ID's and Paths!
	 */
	public static final String TeacherMainID = "TeacherMain";
	public static final String TeacherMainPath = "TeacherMain.fxml";
	public static final String ClientConnectionScreenPath = "/GUI/ClientGui.fxml";
	public static final String LogInID = "LogIn";
	public static final String LogInPath = "Login.fxml";

	public static void handleIOException(IOException e) {
		e.printStackTrace();
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
