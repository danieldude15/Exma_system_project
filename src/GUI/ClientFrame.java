package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Globals;
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
		// TODO Auto-generated method stub

	}

	@FXML
	public void DisconnectFromServer(ActionEvent event) {
		try {
			ClientGlobals.client.closeConnection();
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
		ClientGlobals.mainContainer = new ScreensController();
		Globals.primaryStage = primaryStage;
		ScreensController mainContainer = ClientGlobals.mainContainer;
		if (!mainContainer.loadScreen(ClientGlobals.LogInID, ClientGlobals.LogInPath)) {
        	System.out.println("failed to load "+ ClientGlobals.LogInID);
        	return;
        }
		if (!mainContainer.loadScreen(ClientGlobals.TeacherMainID, ClientGlobals.TeacherMainPath)) {
        	System.out.println("failed to load "+ ClientGlobals.TeacherMainID);
        	return;
        }
		mainContainer.setScreen(ClientGlobals.LogInID);
		
		AnchorPane root = new AnchorPane();
		root.getChildren().addAll(mainContainer);
		root.setTopAnchor(mainContainer, 0.0);
		root.setRightAnchor(mainContainer, 0.0);
		root.setBottomAnchor(mainContainer, 0.0);
		root.setLeftAnchor(mainContainer, 0.0);
        //Group root = new Group();
        //root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(closeUpdate ->
	    {
	        System.exit(0);
	    });
        primaryStage.show();
	}
}