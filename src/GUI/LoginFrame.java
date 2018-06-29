package GUI;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.Globals;
import logic.iMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFrame implements ControlledScreen,Initializable {
	
	@FXML TextField userfield;
	@FXML PasswordField passwordfield;
	@FXML Button loginB;
	@FXML Label error;

	
	@Override public void initialize(URL location, ResourceBundle resources) {}
	
	@Override public void runOnScreenChange() {
		error.setText("");

	}
	
	@FXML public void enterKeyPressed(ActionEvent event) {
		logInClicked(event);
	}
	
	@FXML public void logInClicked(ActionEvent event) {
		if (userfield.getText().equals("") || passwordfield.getText().equals("")) {
			error.setText("* You Must Fill In Both UserName And Password");
		} else {
			iMessage message = UserController.login(userfield.getText(),passwordfield.getText());
			if(message.getCommand().equals("failedAuth"))
				Globals.popUp(Alert.AlertType.INFORMATION,"Failed to Log-In","UserName or Password are incorrect.");
			if(message.getCommand().equals("AlreadyLoggedIn"))
				Globals.popUp(Alert.AlertType.INFORMATION, "Failed to Log-In", "You Are Already Logged In From Another Computer!\n Please LogOut And Then Try Again.");
		}
	}

}
