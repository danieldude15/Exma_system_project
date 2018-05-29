package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import logic.Globals;

public class LoginFrame implements ControlledScreen, Initializable {
	
	@FXML TextField userfield;
	@FXML PasswordField passwordfield;
	@FXML Button loginB;
	@FXML Label error;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(400);
		Globals.primaryStage.setWidth(400);
		error.setText("");

	}
	
	@FXML
	public void enterKeyPressed(ActionEvent event) {
		logInClicked(event);
	}
	@FXML
	public void logInClicked(ActionEvent event) {
		if (userfield.getText().equals("") || passwordfield.getText().equals("")) {
			error.setText("* You Must Fill In Both UserName And Password");
		} else {
			loginB.setDisable(true);
			UserController.login(userfield.getText(),passwordfield.getText());
			loginB.setDisable(false);
		}
	}
}
