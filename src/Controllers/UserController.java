package Controllers;

import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			ClientGlobals.client.waitForResponse();
			String usertype = ClientGlobals.client.getMsg().getCommand();
			u = (User)ClientGlobals.client.getMsg().getObj();
			Alert alert;
			switch (usertype) {
			case "none":
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Failed");
				alert.setHeaderText(null);
				alert.setContentText("Username or Password was incorrect\nPlease try again");
				alert.showAndWait();
				break;
			case "Student":
				System.out.println("Student!!!");
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
			}
			ClientGlobals.client.cleanMsg();
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		
	}

}
