package ocsf.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import GUI.ClientFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.iMessage;


public class ClientApplication extends Application {
	
	public static void main(String args[]) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		File f = new File(ClientGlobals.ConfigfileName);
		if(f.exists() && !f.isDirectory()) { 
			try {
				String hostIP = null;
				String hostPort = null;
	            // FileReader reads text files in the default encoding.
	            FileReader fileReader = 
	                new FileReader(ClientGlobals.ConfigfileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            hostIP = bufferedReader.readLine();
	            hostPort = bufferedReader.readLine();

	            // Always close files.
	            bufferedReader.close();     
	            
	            ClientGlobals.client = new AESClient(hostIP,Integer.parseInt(hostPort));
	    		try {
	    			ClientGlobals.client.openConnection();
	    			ClientFrame cFrame = new ClientFrame();
	    			cFrame.LaunchApp(null);
	    			return;
	    		} catch (Exception e) {
	    			System.out.println("Failed To Connect");
				}
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                		ClientGlobals.ConfigfileName + "'");                
	        }
		}
		
		Parent root = FXMLLoader.load(getClass().getResource(ClientGlobals.ClientConnectionScreenPath));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Client Connection");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				if(ClientGlobals.client!=null) {
					ClientGlobals.kill=true;
					ClientGlobals.client.sendToServer(new iMessage("disconnect",ClientGlobals.client.me));
					System.out.println("Notified Server to Disconnect me!");
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
