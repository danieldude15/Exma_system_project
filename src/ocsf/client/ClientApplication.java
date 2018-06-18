package ocsf.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import GUI.ClientFrame;
import GUI.ScreensController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Globals;
import logic.iMessage;


public class ClientApplication extends Application {
	
	public static void main(String args[]) {
		Globals.application = "client";
		ClientGlobals.initialArgs = args;
		launch(args);
	}
	
	@Override public void start(Stage primaryStage) throws Exception {
		boolean connected = false;
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
	    			connected = true;
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
		Globals.mainContainer = new ScreensController();
		Globals.primaryStage = primaryStage;
		
		
		leadAllScreens();
		
		
		if(connected) {
			Globals.mainContainer.setScreen(ClientGlobals.LogInID);
		} else {
			Globals.mainContainer.setScreen(ClientGlobals.ClientConnectionScreenID);
		}
		
		AnchorPane root = new AnchorPane();
		root.getChildren().addAll(Globals.mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				if(ClientGlobals.client!=null) {
					ClientGlobals.kill=true;
					ClientGlobals.client.sendToServer(new iMessage("disconnect",ClientGlobals.client.me));
					System.err.println("Notified Server to Disconnect me!");
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
	    	System.exit(0);
	    });
		primaryStage.setScene(scene);
		primaryStage.show();	
	}
	
	private void leadAllScreens() {
		ScreensController mainContainer = Globals.mainContainer;
		if (!mainContainer.loadScreen(ClientGlobals.LogInID, ClientGlobals.LogInPath)) {
        	System.out.println("failed to load "+ ClientGlobals.LogInID);
        	return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.TeacherBuildNewExamID, ClientGlobals.TeacherBuildNewExamPath)) {
        	System.out.println("failed to load "+ ClientGlobals.TeacherBuildNewExamID);
        	return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.TeacherMainID, ClientGlobals.TeacherMainPath)) {
        	System.out.println("failed to load "+ ClientGlobals.TeacherMainID);
        	return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.TeacherManageQuestionsID, ClientGlobals.TeacherManageQuestionsPath)) {
        	System.out.println("failed to load "+ ClientGlobals.TeacherManageQuestionsID);
        	return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.TeacherEditAddQuestionID, ClientGlobals.TeacherEditAddQuestionPath)) {
        	System.out.println("failed to load "+ ClientGlobals.TeacherEditAddQuestionID);
        	return;
        }
        if(!mainContainer.loadScreen(ClientGlobals.PrincipalMainID,ClientGlobals.PrincipalMainPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalMainID);
			return;
		}
        if(!mainContainer.loadScreen(ClientGlobals.PrincipalViewReportID,ClientGlobals.PrincipalViewReportPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalViewReportID);
			return;
		}
        if(!mainContainer.loadScreen(Globals.ProgressIndicatorID,Globals.ProgressIndicatorPath)) {
			System.out.println("failed to load " + Globals.ProgressIndicatorID);
			return;
		}
		if(!mainContainer.loadScreen(ClientGlobals.PrincipalReportsID,ClientGlobals.PrincipalReportsPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalReportsID);
			return;
		}
		if(!mainContainer.loadScreen(ClientGlobals.PrincipalViewDataID,ClientGlobals.PrincipalViewDataPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalViewDataID);
			return;
		}
		if(!mainContainer.loadScreen(ClientGlobals.PrincipalViewQuestionID,ClientGlobals.PrincipalViewQuestionPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalViewQuestionID);
			return;
		}
		if(!mainContainer.loadScreen(ClientGlobals.PrincipalViewExamID,ClientGlobals.PrincipalViewExamPath)) {
			System.out.println("failed to load " + ClientGlobals.PrincipalViewExamID);
			return;
		}
        if(!mainContainer.loadScreen(ClientGlobals.PrincipalViewFieldID,ClientGlobals.PrincipalViewFieldPath)) {
            System.out.println("failed to load " + ClientGlobals.PrincipalViewFieldID);
            return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.StudentMainID, ClientGlobals.StudentMainPath)) {
			System.out.println("failed to load "+ ClientGlobals.StudentMainID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.TeacherCheckExamID, ClientGlobals.TeacherManageExamPath)) {
			System.out.println("failed to load "+ ClientGlobals.TeacherCheckExamID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.TeacherManageExamsID, ClientGlobals.TeacherManageExamsPath)) {
			System.out.println("failed to load "+ ClientGlobals.TeacherManageExamsID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.TeacherTimeChangeRequestID, ClientGlobals.TeacherTimeChangeRequestPath)) {
			System.out.println("failed to load "+ ClientGlobals.TeacherTimeChangeRequestID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.StudentStartExamID, ClientGlobals.StudentStartExamPath)) {
			System.out.println("failed to load "+ ClientGlobals.StudentStartExamID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.StudentViewExamID, ClientGlobals.StudentViewExamPath)) {
				System.out.println("failed to load "+ ClientGlobals.StudentViewExamID);
				return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.StudentSolvesExamID, ClientGlobals.StudentSolvesExamPath)) {
				System.out.println("failed to load "+ ClientGlobals.StudentSolvesExamID);
				return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.InitializeExamID, ClientGlobals.InitializeExamPath)) {
			System.out.println("failed to load "+ ClientGlobals.InitializeExamID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.ActiveExamID, ClientGlobals.ActiveExamPath)) {
			System.out.println("failed to load "+ ClientGlobals.ActiveExamID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.TeacherViewExamID, ClientGlobals.TeacherViewExamPath)) {
			System.out.println("failed to load "+ ClientGlobals.TeacherViewExamID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.TeacherCheckExamsID, ClientGlobals.TeacherCheckExamsPath)) {
			System.out.println("failed to load "+ ClientGlobals.TeacherCheckExamsID);
			return;
		}
		if (!mainContainer.loadScreen(ClientGlobals.ClientConnectionScreenID, ClientGlobals.ClientConnectionScreenPath)) {
			System.out.println("failed to load "+ ClientGlobals.ClientConnectionScreenID);
			return;
		}
	}

}
