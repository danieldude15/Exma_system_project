package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.iMessage;
import ocsf.server.AESServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.ServerGlobals;

public class ServerFrame implements ControlledScreen,Initializable {

	@FXML Button closeConnectionBotton;
	@FXML Button StartListenBotton;
	@FXML Label statusLabel;
	@FXML TextField portnum;


	@Override
	public void runOnScreenChange() {
	}
	
	@FXML
	public void StartListening(ActionEvent event) {
		ServerGlobals.server = new AESServer(Integer.parseInt(portnum.getText()));
		portnum.setDisable(true);
		try {
			ServerGlobals.server.listen();
			statusLabel.setText("Listening");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#00FF00"));
		} catch (Exception e) {
			e.printStackTrace();
			statusLabel.setText("Failed To Listen Try Again");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		}
	}
	
	@FXML
	public void closeConnectionsBotton(ActionEvent event) {
		try {
			ServerGlobals.server.sendToAllClients(new iMessage("closing Connection",null));
			ServerGlobals.server.close();
			ServerGlobals.server.stopListening();
			statusLabel.setText("Server Down");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			portnum.setDisable(false);			
		} catch (IOException e) {
			e.printStackTrace();
			statusLabel.setText("Failed To close connection");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusLabel.setText("<Status>");
		statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
	}
	
	public void addClient(ConnectionToClient client) {

	}

	public void removeClient(ConnectionToClient client) {
		// TODO Auto-generated method stub
		
	}
	
}
