package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.Exam;
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
			switch (usertype) {
			case "none":
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Failed");
				alert.setHeaderText(null);
				alert.setContentText("Username or Password was incorrect\nPlease try again");
				alert.showAndWait();
				break;
			case "Student":
				System.out.println("Student!!!");
				break;
			case "Teacher":
				ClientGlobals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
				break;
			case "Principle":
				System.out.println("Principle!!!");
				break;
			}
			ClientGlobals.client.cleanMsg();
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		
	}

}
