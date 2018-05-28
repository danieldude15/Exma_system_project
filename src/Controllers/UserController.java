package Controllers;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ConstraintsBase;
import logic.Globals;
import logic.User;
import logic.iMessage;
import ocsf.client.ClientGlobals;

public class UserController {

	public static void login(String username, String password) {
		User u = new User(0,username,password,null);
		iMessage msg = new iMessage("login",u);
		try {
			ClientGlobals.client.sendToServer(msg);
			iMessage message = ClientGlobals.client.getResponseFromServer();
			String usertype = message.getCommand();
			u = (User)message.getObj();
			Alert alert;
			switch (usertype) {
			case "Student":
				Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
				break;
			case "Teacher":
				Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
				break;
			case "Principle":
				Globals.mainContainer.setScreen(ClientGlobals.PrincipalMainID);
				break;
			case "AlreadyLoggedIn":
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed to Log-In");
				alert.setHeaderText(null);
				alert.setContentText("You Are Already Logged In From Another Computer!\n Please LogOut And Then Try Again.");
				alert.showAndWait();
				break;
			case "failedAuth":
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Failed to Log-In");
				alert.setHeaderText(null);
				alert.setContentText("UserName or Password are incorrect.");
				alert.showAndWait();
				break;
			}
			ClientGlobals.client.cleanMsg();
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		
	}

	public static void logout() {
		try {
			if (ClientGlobals.client!=null)
				ClientGlobals.client.sendToServer(new iMessage("logout",ClientGlobals.client.getUser()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globals.mainContainer.setScreen(ClientGlobals.LogInID);
	}

}
