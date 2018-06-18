package logic;

import GUI.ScreensController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * this class holds global variables that are used globally in the application for easy access and maintanace
 * @author Group-12
 *
 */
public class Globals {
		
	public static final String ProgressIndicatorID = "progressIndicator";
	public static final String ProgressIndicatorPath = "/resources/fxml/progressIndicator.fxml";
	/**
	 * if im a client or a server
	 */
	public static String application = "";
	/**
	 * this enum will hold visible or hidden types
	 */
	public enum Type {VISIBLE,HIDDEN;}
	/**
	 * this is the primaryStage of the users application used to change size of windows and other adjustments
	 */
	public static Stage primaryStage = null;
	/**
	 * this is the mainContainer that holds all the nodes of contents of all screens and used to pass by different screens information
	 */
	public static ScreensController mainContainer;
	/**
	 * handle a core exception when the program cannot continue to function after this type of exception
	 * this function should show and alert with exception details and than System.exit(1) at the end of it.
	 * @param e the exception thrown
	 */
	public static void handleException(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}
	
	public static void PopUp_INFORMATION(String title,String contentText)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.show();
	}
}
