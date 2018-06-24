package ocsf.server;

import java.io.IOException;

import GUI.ScreensController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Globals;

public class ServerApplication extends Application {

	public static void main (String args[]) {
		Globals.application = "server";
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Globals.mainContainer = new ScreensController();
		Globals.primaryStage = primaryStage;
        if(!Globals.mainContainer.loadScreen(ServerGlobals.ServerGuiID, ServerGlobals.ServerGuiPath)) {
        	System.out.println("failed to load "+ ServerGlobals.ServerGuiID);
        }
        
        Globals.mainContainer.setScreen(ServerGlobals.ServerGuiID);
        
        Group root = new Group();
        root.getChildren().addAll(Globals.mainContainer);
        Scene scene = new Scene(root);
        Image icon = new Image(this.getClass().getResourceAsStream("/resources/images/icon.png"));
		primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
	        	if (ServerGlobals.server!=null) {
	        		ServerGlobals.server.close();
	        	}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
	        
	    });
        primaryStage.show();

	}

}
