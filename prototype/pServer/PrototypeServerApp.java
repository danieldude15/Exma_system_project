package pServer;

import java.io.IOException;

import Controllers.pScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrototypeServerApp extends Application {

	public static void main(String args[]) throws IOException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		pServerGlobals.server = new PrototypeServer(12345);
		pServerGlobals.mainContainer = new pScreensController();
        if(!pServerGlobals.mainContainer.loadScreen(pServerGlobals.ServerGuiID, pServerGlobals.ServerGuiPath)) {
        	System.out.println("failed to load "+ pServerGlobals.ServerGuiID);
        }
        
        pServerGlobals.mainContainer.setScreen(pServerGlobals.ServerGuiID);
        
        Group root = new Group();
        root.getChildren().addAll(pServerGlobals.mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
	        	pServerGlobals.server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        System.exit(0);
	    });
        primaryStage.show();
	}
}
