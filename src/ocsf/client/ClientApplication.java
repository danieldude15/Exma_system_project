package ocsf.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Globals;
import logic.iMessage;


public class ClientApplication extends Application {
	
	public static void main(String args[]) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(ClientGlobals.ClientConnectionScreenPath));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client Connection");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				if(ClientGlobals.client!=null) {
					ClientGlobals.client.sendToServer(new iMessage("logout",null));
					ClientGlobals.client.closeConnection();
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
	    	System.exit(0);
	    });
		primaryStage.show();	
	}
	
	public static void startme(String[] stg) throws Exception{
		launch(stg);
	}

}
