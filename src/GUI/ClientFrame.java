package GUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
import logic.Globals;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class ClientFrame implements ControlledScreen ,Initializable {
	
	@FXML TextField hostval;
	@FXML TextField portval;
	@FXML Label clientStatus;
	@FXML Button connectB;
	@FXML Button disconnectB;
	@FXML Button launchapp;

	@Override public void initialize(URL location, ResourceBundle resources) {
		ClientGlobals.ClientConnectionController = this; 
	}

	@FXML public void DisconnectFromServer(ActionEvent event) {
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
	
	@FXML public void ConnectToServer(ActionEvent event) {
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
	
	@FXML public void LaunchApp(ActionEvent event) {
		if(ClientGlobals.client!=null && ClientGlobals.client.isConnected())
			Globals.mainContainer.setScreen(ClientGlobals.LogInID);
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

	@Override
	public void runOnScreenChange() {
		portval.setText(Globals.port);
	}
}
