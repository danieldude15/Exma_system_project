package Controllers;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class QuestionController { 
	
	public static ArrayList<Question> getTeachersQuestions(Teacher t) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersQuestions",t);
			try {
				client.sendToServer(msg);
				return (ArrayList<Question>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;

	}

	/**
	 * method retrieves all questions in database
	 * @return ArrayList<Question> array list of all the questions in database
	 */
	public static ArrayList<Question> getAllQuestions(){
		AESClient client = ClientGlobals.client;
		if (client.isConnected()){
			iMessage msg = new iMessage("getAllQuestions",null);
			try{
				client.sendToServer(msg);
				return (ArrayList<Question>) client.getResponseFromServer().getObj();
			}catch (IOException e){
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ArrayList<Course> getQuestionCourses(Question question) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getQuestionCourses",question);
			try {
				client.sendToServer(msg);
				return (ArrayList<Course>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static ArrayList<QuestionInExam> getQuestionsInExam(String examid) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getQuestionInExam",examid);
			try {
				client.sendToServer(msg);
				return (ArrayList<QuestionInExam>) client.getResponseFromServer().getObj();
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
				return (Integer) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return 0;
	}
/**
 * A function that returns all the questions of this course
 * @param c-Course
 * @return ArrayList<Question>
 */
	public static ArrayList<Question> getCourseQuestions(Course c) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("CourseQuestions",c);
			try {
				client.sendToServer(msg);
				return (ArrayList<Question>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;

	}

	public static int addQuestion(Question q) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("addQuestion",q);
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

	public static int editQuestion(Question q) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("editQuestion",q);
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
