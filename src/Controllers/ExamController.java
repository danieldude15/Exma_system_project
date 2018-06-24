package Controllers;

import logic.Course;
import logic.Exam;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ExamController {

	/**
	 * Gets Exams of provided Teacher from the database using an SQL query.
	 * @param t Teacher, exams of which will be fetched.
	 * @return ArrayList of Exams.
	 */
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

	/**
	 * Sending to server a request to get all exams.
	 * @return ArrayList of Exams.
	 */
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

	/**
	 * Sending to server a request to delete exam
	 * @param exam To be deleted from database.
	 * @return confirmation Integer.
	 */
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
/**
 * Sending to server a request to add exam.
 * @param exam To be added to database.
 */
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

	/**
	 * Sending to server a request to get all course exams.
	 * @param C Course, Exams of which will be fetched.
	 * @return ArrayList of Exams.
	 */
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
