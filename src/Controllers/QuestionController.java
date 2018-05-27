package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class QuestionController {
	
	public static ArrayList<Question> getTeachersQuestions(Teacher t) {
		AESClient client = ClientGlobals.client;
		ArrayList<Question> questions;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersQuestions",t);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = client.getMsg().getObj();
				ArrayList<Question> TeacherQuestions = null;
				if(o instanceof ArrayList) {
					TeacherQuestions = (ArrayList<Question>) ((ArrayList<Question>) o).clone();
				}
				questions = TeacherQuestions;
				client.cleanMsg();
				return questions;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static ArrayList<Course> getQuestionCourses(Question question) {
		AESClient client = ClientGlobals.client;
		ArrayList<Course> courses;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getQuestionCourses",question);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = client.getMsg().getObj();
				ArrayList<Course> QuestionCourses = null;
				if(o instanceof ArrayList) {
					QuestionCourses = (ArrayList<Course>) ((ArrayList<Course>) o).clone();
				}
				courses = QuestionCourses;
				client.cleanMsg();
				return courses;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
}
