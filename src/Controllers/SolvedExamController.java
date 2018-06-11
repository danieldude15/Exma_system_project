package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
/**
 *  This controller is responsible for all information that has to do with solved exams
 *  it has functionality such as adding/editing solved exams also getting solved exams for informational purpeses.
 * @author Group-12
 *
 */
@SuppressWarnings("unchecked")
public class SolvedExamController {

	/**
	 * Send to server a request to pull the object solved exams from database.
	 * 
	 * @param u - is the user object of the student to get the 
	 * students solved exams or teacher to get the teachers solved exams.
	 * also possible to send an integer with the user id to get his solved exams
	 * 
	 * @return SolvedExam
	 */
	public static ArrayList<SolvedExam> getSolvedExamsByUser(Object u) {
		AESClient client = ClientGlobals.client;
		iMessage msg;
		if(client.isConnected()) {
			if(u instanceof Student)
				msg= new iMessage("getStudentsSolvedExams",(Student)u);
			else if (u instanceof Teacher)
				msg= new iMessage("getTeacherSolvedExams",(Teacher)u);
			else if (u instanceof Integer) {
				msg = new iMessage("getSolvedExamsByID",(Integer) u );
			} else return null;
			try {
				client.sendToServer(msg);
				return (ArrayList<SolvedExam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static ArrayList<ExamReport> getCompletedExams(Teacher user) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("getTeacherCompletedExams",user));
				return (ArrayList<ExamReport>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}


	public static int insertSolvedExam(SolvedExam solvedExam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("updateSolvedExam",solvedExam));
				return (Integer) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return 0;
	}

}
