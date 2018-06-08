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
	 * @param 
	 * @return SolvedExam
	 */
	public static ArrayList<SolvedExam> getSolvedExams(Object u) {
		AESClient client = ClientGlobals.client;
		iMessage msg;
		if(client.isConnected()) {
			if(u instanceof Student)
				msg= new iMessage("getStudentsSolvedExams",(Student)u);
			else if (u instanceof Teacher)
				msg= new iMessage("getTeacherSolvedExams",(Teacher)u);
			else if (u instanceof String) {
				msg = new iMessage("getSolvedExamsByID",(String) u );
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

	public static ArrayList<CompletedExam> getCompletedExams(Teacher user) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("getTeacherCompletedExams",user));
				return (ArrayList<CompletedExam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}
	
	//new by itzik
	public static void UploadSolvedExamToDatabase(Object solved) {
		AESClient client = ClientGlobals.client;
		iMessage msg;
		msg= new iMessage("UploadSolvedExamToDatabase",(SolvedExam)solved);
		if(client.isConnected()) {
			try {
				client.sendToServer(msg);
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
	}
	
}
