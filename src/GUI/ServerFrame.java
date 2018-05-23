package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.ScreensController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ocsf.server.AESServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.ServerGlobals;

public class ServerFrame implements ControlledScreen,Initializable {

	ScreensController myController;
	@FXML Button closeConnectionBotton;
	@FXML Button StartListenBotton;
	@FXML Label statusLabel;
	@FXML TextField portnum;

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@Override
	public void runOnScreenChange() {
		statusLabel.setText("<Status>");
	}
	
	@FXML
	public void StartListening(ActionEvent event) {
		ServerGlobals.server = new AESServer(Integer.parseInt(portnum.getText()));
		portnum.setDisable(true);
		try {
			ServerGlobals.server.listen();
			statusLabel.setText("Listening");
		} catch (Exception e) {
			e.printStackTrace();
			statusLabel.setText("Failed To Listen Try Again");
		}
	}
	
	@FXML
	public void closeConnectionsBotton(ActionEvent event) {
		try {
			ServerGlobals.server.close();
			ServerGlobals.server.stopListening();
			statusLabel.setText("Server Down");
			portnum.setDisable(false);			
		} catch (IOException e) {
			e.printStackTrace();
			statusLabel.setText("Failed To close connection");
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
