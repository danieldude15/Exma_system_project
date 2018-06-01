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
				Object o = client.getResponseFromServer().getObj();
				//Object o = client.getMsg().getObj();
				ArrayList<Question> TeacherQuestions = null;
				if(o instanceof ArrayList) {
					TeacherQuestions = (ArrayList<Question>) ((ArrayList<Question>) o).clone();
				}
				questions = TeacherQuestions;
				return questions;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;

	}

	public static ArrayList<Course> getQuestionCourses(Question question) {
		AESClient client = ClientGlobals.client;
		ArrayList<Course> courses;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getQuestionCourses",question);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				ArrayList<Course> QuestionCourses = null;
				if(o instanceof ArrayList) {
					QuestionCourses = (ArrayList<Course>) ((ArrayList<Course>) o).clone();
				}
				courses = QuestionCourses;
				return courses;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static ArrayList<QuestionInExam> getQuestionsInExam(String examid) {
		AESClient client = ClientGlobals.client;
		ArrayList<QuestionInExam> questions = null;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getQuestionInExam",examid);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				if(o instanceof ArrayList) {
					questions = (ArrayList<QuestionInExam>) ((ArrayList<QuestionInExam>) o).clone();
				}
				return questions;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	public static int deleteQuestion(Question q) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("deleteQuestion",q);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				if(o instanceof Integer) {
					return (Integer) o;
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return 0;
	}
}
