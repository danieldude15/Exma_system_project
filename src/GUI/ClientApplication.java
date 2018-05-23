package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Globals;
import ocsf.client.ClientGlobals;


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
	        System.exit(0);
	    });
		primaryStage.show();	
	}
	
	public static void startme(String[] stg) throws Exception{
		launch(stg);
	}

}
