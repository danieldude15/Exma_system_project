package pGUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pSQLTools.client.PrototypeClient;


public class pType extends Application {
	
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
			gui_globals.client = new PrototypeClient("localhost", 12345);
			gui_globals.client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (!pType.mainContainer.loadScreen(pType.UpdateAnswerScreenID, pType.UpdateAnswerScreenFilePath)) {
        	System.out.println("failed to load "+ pType.UpdateAnswerScreenID);
        }
        if(!mainContainer.loadScreen(pType.SelectQuestionScreenID, pType.SelectQuestionScreenFilePath)) {
        	System.out.println("failed to load "+ SelectQuestionScreenID);
        }
        
        mainContainer.setScreen(pType.SelectQuestionScreenID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				gui_globals.client.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.exit(0);
	    });
        primaryStage.show();
	}


}
