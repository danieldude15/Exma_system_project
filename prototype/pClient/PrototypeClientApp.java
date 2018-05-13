package pClient;

import java.io.IOException;

import Controllers.pScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pGUI.pClientGlobals;


public class PrototypeClientApp extends Application {
	
	public static String SelectQuestionScreenID = "SelectQuestionScreen";
	public static String SelectQuestionScreenFilePath = "SelectQuestion.fxml";
	public static String UpdateAnswerScreenID = "UpdateAnswerScreen";
	public static String UpdateAnswerScreenFilePath = "UpdateAnswer.fxml";
	public static pScreensController mainContainer = new pScreensController();
	
	public static void main( String args[] ) throws Exception
	   { 
     launch(args);		
	  } // end main
	
	@Override
	public void start(Stage primaryStage) {
		try {
			pClientGlobals.client = new PrototypeClient("localhost", 12345);
			pClientGlobals.client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (!PrototypeClientApp.mainContainer.loadScreen(PrototypeClientApp.UpdateAnswerScreenID, PrototypeClientApp.UpdateAnswerScreenFilePath)) {
        	System.out.println("failed to load "+ PrototypeClientApp.UpdateAnswerScreenID);
        }
        if(!mainContainer.loadScreen(PrototypeClientApp.SelectQuestionScreenID, PrototypeClientApp.SelectQuestionScreenFilePath)) {
        	System.out.println("failed to load "+ SelectQuestionScreenID);
        }
        
        mainContainer.setScreen(PrototypeClientApp.SelectQuestionScreenID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				pClientGlobals.client.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.exit(0);
	    });
        primaryStage.show();
	}


}
