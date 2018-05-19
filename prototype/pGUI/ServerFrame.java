package pGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.pControlledScreen;
import Controllers.pScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ocsf.server.ConnectionToClient;
import pServer.pServerGlobals;

public class ServerFrame implements pControlledScreen,Initializable {

	pScreensController myController;
	@FXML Button closeConnectionBotton;
	@FXML Button StartListenBotton;
	@FXML Label statusLabel;

	@Override
	public void setScreenParent(pScreensController screenParent) {
		myController = screenParent;
	}

	@Override
	public void runOnScreenChange() {
		statusLabel.setText("Not Listening");
		StartListening(null);
		//statusLabel.getGraphic().setStyle("-fx-text-fill: #AAAAAA;");
	}
	
	@FXML
	public void StartListening(ActionEvent event) {
		try {
			pServerGlobals.server.listen();
			statusLabel.setText("Listening");
			//statusLabel.getGraphic().setStyle("-fx-text-fill: #c4d8de;");
		} catch (Exception e) {
			e.printStackTrace();
			statusLabel.setText("Failed To Listen Try Again");
			//statusLabel.getGraphic().setStyle("-fx-text-fill: #505050;");
		}
	}
	
	@FXML
	public void closeConnectionsBotton(ActionEvent event) {
		try {
			pServerGlobals.server.close();
			pServerGlobals.server.stopListening();
			statusLabel.setText("Stopped Listening and closed Connections");
		} catch (IOException e) {
			e.printStackTrace();
			statusLabel.setText("Failed To close connection");
			//statusLabel.getGraphic().setStyle("-fx-text-fill: #505050;");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void addClient(ConnectionToClient client) {

	}

	public void removeClient(ConnectionToClient client) {
		// TODO Auto-generated method stub
		
	}
	
}
