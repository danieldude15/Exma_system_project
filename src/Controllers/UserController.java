package Controllers;

import logic.*;
import ocsf.client.ClientGlobals;
import ocsf.client.IAESClient;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class UserController {

	public static iMessage login(String username, String password, IAESClient client) {
		User u = new User(0,username,password,null);
		iMessage msg = new iMessage("login",u);
		try {
			client.sendToServer(msg);
			iMessage message = client.getResponseFromServer();
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
			}
			return message;
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		return null;
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
