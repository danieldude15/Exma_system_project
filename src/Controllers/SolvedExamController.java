package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

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
	
	/**
	 * Send message to the server when the student finished his exam.
	 * @param activeExam
	 * @param solvedExam
	 * @param s
	 */
	public static void SendFinishedSolvedExam(ActiveExam activeExam,SolvedExam solvedExam,Student s,XWPFDocument doc)
	{
		Object[] o=new Object[4];
		o[0]=(ActiveExam)activeExam;
		o[1]=(SolvedExam)solvedExam;
		o[2]=(Student)s;
		o[3]=(XWPFDocument)doc;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				client.sendToServer(new iMessage("FinishedSolvedExam",o));
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
	}

}
