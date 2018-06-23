package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.Course;
import logic.Exam;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class ExamController {

	public static ArrayList<Exam> getTeachersExams(Teacher t){
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersExam",t);
			try {
				client.sendToServer(msg);
				return (ArrayList<Exam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static ArrayList<Exam> getAllExams(){
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getAllExams",null);
			try {
				client.sendToServer(msg);
				return (ArrayList<Exam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int deleteExam(Exam exam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("DeleteExam",exam);
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

	public static void  addExam(Exam exam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("addExam",exam);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
	
	}

	public static ArrayList<Exam> getcourseExams(Course C){
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getcourseExams",C);
			try {
				client.sendToServer(msg);
				return (ArrayList<Exam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	
	}

}
