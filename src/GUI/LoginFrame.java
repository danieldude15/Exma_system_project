package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFrame implements ControlledScreen, Initializable {
	
	@FXML TextField userfield;
	@FXML PasswordField passwordfield;
	@FXML Button loginB;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub

	}

	@FXML
	public void logInClicked(ActionEvent event) {
		loginB.setDisable(true);
		UserController.login(userfield.getText(),passwordfield.getText());
		loginB.setDisable(false);
	}
}
