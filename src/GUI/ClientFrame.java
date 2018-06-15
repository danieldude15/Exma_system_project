package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Globals;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFrame implements Initializable {
	
	@FXML TextField hostval;
	@FXML TextField portval;
	@FXML Label clientStatus;
	@FXML Button connectB;
	@FXML Button disconnectB;
	@FXML Button launchapp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ClientGlobals.ClientConnectionController = this; 
	}

	@FXML
	public void DisconnectFromServer(ActionEvent event) {
		try {
			if (event!=null)ClientGlobals.client.closeConnection();
			ClientGlobals.client=null;
			clientStatus.setText("<Disconnected>");
			clientStatus.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			launchapp.setDisable(true);
		} catch (IOException e) {
			clientStatus.setText("<ERROR>");
			clientStatus.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			launchapp.setDisable(true);
			e.printStackTrace();
			e.printStackTrace();
		}
	}
	
	@FXML
	public void ConnectToServer(ActionEvent event) {
		ClientGlobals.client = new AESClient(hostval.getText(), Integer.parseInt(portval.getText()));
		try {
			ClientGlobals.client.openConnection();
			clientStatus.setText("<Connected>");
			clientStatus.setTextFill(javafx.scene.paint.Paint.valueOf("#00FF00"));
			launchapp.setDisable(false);
			writeConfigFile();
		} catch (IOException e) {
			clientStatus.setText("<ERROR>");
			clientStatus.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			e.printStackTrace();
		}
	}
	
	@FXML
	public void LaunchApp(ActionEvent event) {
		Stage primaryStage = new Stage();
		Globals.mainContainer = new ScreensController();
		Globals.primaryStage = primaryStage;
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
		
		mainContainer.setScreen(ClientGlobals.LogInID);
		
		AnchorPane root = new AnchorPane();
		root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				if(ClientGlobals.client!=null) {
					ClientGlobals.kill=true;
					if (ClientGlobals.client.getUser()!=null)
						ClientGlobals.client.sendToServer(new iMessage("disconnect",ClientGlobals.client.getUser()));
					System.exit(0);
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
	    });
        primaryStage.show();
	}
	
	/**
	 * writing config file to reconnect to same Server as before
	 * for later to read and connect immidiatlly instead of setting up every time
	 */
	private void writeConfigFile() {
		// The name of the file to open.

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(ClientGlobals.ConfigfileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(hostval.getText());
            bufferedWriter.newLine();
            bufferedWriter.write(portval.getText());
            
            // Always close files.
            bufferedWriter.close();
            System.out.println("Finished Writting a config file");
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + ClientGlobals.ConfigfileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
	}
}
