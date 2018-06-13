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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import logic.iMessage;
import ocsf.server.AESServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.ServerGlobals;

public class ServerFrame implements ControlledScreen,Initializable {

	@FXML Button closeConnectionBotton;
	@FXML Button StartListenBotton;
	@FXML Label statusLabel;
	@FXML TextField portnum;
	@FXML RadioButton DBHostTB;
	@FXML RadioButton DBLocal;
	@FXML TextField DBPass;
	@FXML TextField DBUser;
	@FXML TextField DBPort;


	@Override public void runOnScreenChange() {
		StartListening(null);
	}
	
	@FXML public void StartListening(ActionEvent event) {
		if (DBHostTB.isSelected())
		ServerGlobals.server = new AESServer(
				DBHostTB.getText(),DBUser.getText(),DBPass.getText(),Integer.parseInt(portnum.getText()));
		else 
			ServerGlobals.server = new AESServer(
					DBLocal.getText(),DBUser.getText(),DBPass.getText(),Integer.parseInt(portnum.getText()));
		portnum.setDisable(true);
		try {
			//disconnectUsers(null);
			ServerGlobals.server.listen();
			statusLabel.setText("Listening!!");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#00FF00"));
			StartListenBotton.setDisable(true);
		} catch (Exception e) {
			e.printStackTrace();
			statusLabel.setText("Failed To Listen Try Again");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		}
	}
	
	@FXML public void closeConnectionsBotton(ActionEvent event) {
		try {
			disconnectUsers(null);
			ServerGlobals.server.sendToAllClients(new iMessage("closing Connection",null));
			ServerGlobals.server.close();
			ServerGlobals.server.stopListening();
			statusLabel.setText("Server Down");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			portnum.setDisable(false);			
			StartListenBotton.setDisable(false);
		} catch (IOException e) {
			e.printStackTrace();
			statusLabel.setText("Failed To close connection");
			statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
		}
	}

	@Override public void initialize(URL location, ResourceBundle resources) {
		statusLabel.setText("<Status>");
		statusLabel.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
	}
	
	
	@FXML public void disconnectUsers(ActionEvent event) {
		if (ServerGlobals.server!=null) {
			ServerGlobals.server.clearHashes();
		}
	}
	
}
