package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
		} catch (IOException e) {
			clientStatus.setText("<ERROR>");
			clientStatus.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			e.printStackTrace();
		}
	}
	
	public void LaunchApp(ActionEvent event) {
		Stage primaryStage = new Stage();
		Globals.mainContainer = new ScreensController();
		Globals.primaryStage = primaryStage;
		ScreensController mainContainer = Globals.mainContainer;
		if (!mainContainer.loadScreen(ClientGlobals.LogInID, ClientGlobals.LogInPath)) {
        	System.out.println("failed to load "+ ClientGlobals.LogInID);
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
//Itzik710@bitbucket.org/Petachok/automatic_exam_system.git
        /*if (!mainContainer.loadScreen(ClientGlobals.PrincipalMainID,ClientGlobals.PrincipalMainPath)){
			System.out.println("failed to load " + ClientGlobals.PrincipalMainID);
			return;
<<<<<<< HEAD
		}
/*/
		if (!mainContainer.loadScreen(ClientGlobals.StudentMainID, ClientGlobals.StudentMainPath)) {
			System.out.println("failed to load "+ ClientGlobals.StudentMainID);
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

		

//Itzik710@bitbucket.org/Petachok/automatic_exam_system.git
		mainContainer.setScreen(ClientGlobals.LogInID);
		
		AnchorPane root = new AnchorPane();
		root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        try {
				if(ClientGlobals.client!=null) {
					if (ClientGlobals.client.getUser()!=null)
						ClientGlobals.client.sendToServer(new iMessage("logout",ClientGlobals.client.getUser()));
					ClientGlobals.client.closeConnection();
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
	    	System.exit(0);
	    });
        primaryStage.show();
	}
}
