package pClient;

import java.io.IOException;

import Controllers.pScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PrototypeClientApp extends Application {
	
	public static void main( String args[] ) throws Exception{ 
		launch(args);		
	} // end main
	
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Connecting to server");
		try {
			pClientGlobals.client = new PrototypeClient("127.0.0.1", 12345);
			pClientGlobals.client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		pClientGlobals.mainContainer = new pScreensController();
		if (!pClientGlobals.mainContainer.loadScreen(pClientGlobals.UpdateAnswerScreenID, pClientGlobals.UpdateAnswerScreenFilePath)) {
        	System.out.println("failed to load "+ pClientGlobals.UpdateAnswerScreenID);
        }
        if(!pClientGlobals.mainContainer.loadScreen(pClientGlobals.SelectQuestionScreenID, pClientGlobals.SelectQuestionScreenFilePath)) {
        	System.out.println("failed to load "+ pClientGlobals.SelectQuestionScreenID);
        }
        
        pClientGlobals.mainContainer.setScreen(pClientGlobals.SelectQuestionScreenID);
        
        Group root = new Group();
        root.getChildren().addAll(pClientGlobals.mainContainer);
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
