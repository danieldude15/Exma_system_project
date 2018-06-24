package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;
import logic.Course;
import logic.Globals;
import logic.Student;
import logic.User;
import logic.iMessage;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class UserController {

	public static void login(String username, String password) {
		User u = new User(0,username,password,null);
		iMessage msg = new iMessage("login",u);
		try {
			ClientGlobals.client.sendToServer(msg);
			iMessage message = ClientGlobals.client.getResponseFromServer();
			String usertype = message.getCommand();
			u = (User)message.getObj();
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
				Globals.popUp(AlertType.INFORMATION, "Failed to Log-In", "You Are Already Logged In From Another Computer!\n Please LogOut And Then Try Again.");
				break;
			case "failedAuth":
				Globals.popUp(AlertType.INFORMATION,"Failed to Log-In","UserName or Password are incorrect.");
				break;
			}
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		
	}

	/**
	 * Sending to server a request to logout the user from system.
	 */
	public static void logout() {
		try {
			if (ClientGlobals.client!=null)
				ClientGlobals.client.sendToServer(new iMessage("logout",ClientGlobals.client.getUser()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Globals.mainContainer.setScreen(ClientGlobals.LogInID);
	}
	
	public static ArrayList<Student> getStudentsInCourse(Course c) {
		try {
			if (ClientGlobals.client!=null) {
				ClientGlobals.client.sendToServer(new iMessage("studentsInCourse",c));
				return (ArrayList<Student>) ClientGlobals.client.getResponseFromServer().getObj();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sending to server a request to get all students.
	 * @return
	 */
	public static ArrayList<User> getAllStudents() {
		try {
			if (ClientGlobals.client!=null) {
				ClientGlobals.client.sendToServer(new iMessage("getAllStudents",null));
				return (ArrayList<User>) ClientGlobals.client.getResponseFromServer().getObj();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Sending to server request to get all teachers.
	 * @return
	 */
	public static ArrayList<User> getAllTeachers() {
		try {
			if (ClientGlobals.client!=null) {
				ClientGlobals.client.sendToServer(new iMessage("getAllTeachers",null));
				return (ArrayList<User>) ClientGlobals.client.getResponseFromServer().getObj();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
