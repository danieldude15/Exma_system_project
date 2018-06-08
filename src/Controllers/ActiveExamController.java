package Controllers;

import logic.ActiveExam;
import logic.Student;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ActiveExamController {

	/**
	 * this function will get the Teachers Active Exams from database to display in main window of teacher
	 * @param Teacher
	 * @return
	 */
	public static ArrayList<ActiveExam> getTeachersActiveExams(Teacher t) {
		AESClient client = ClientGlobals.client;
		iMessage msg = new iMessage("getTeachersActiveExams",t);
		try {
			client.sendToServer(msg);
			return (ArrayList<ActiveExam>) client.getResponseFromServer().getObj();
		} catch (IOException e) {
			ClientGlobals.handleIOException(e);
		}
		return null;
	}

	public static ActiveExam getActiveExam(String examCode) {
		AESClient client = ClientGlobals.client;
		ActiveExam activeExam;
		iMessage msg;
		if(client.isConnected()) {
			try {
				msg= new iMessage("getActiveExam",examCode);
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				if(o instanceof ActiveExam) {
					activeExam=(ActiveExam)o;
					return activeExam;
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}
		
	public static ArrayList<ActiveExam> GetAllActiveExams() {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("getAllActiveExams",null);
				client.sendToServer(msg);
				return (ArrayList<ActiveExam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Integer lockExam(ActiveExam activeExam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("LOCKActiveExam",activeExam);
				client.sendToServer(msg);
				return (Integer) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return 0;
	}

	
	
	
	public static XWPFDocument GetManualExam(String activeExamCode)
	{
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("GetManualExam",activeExamCode);
				client.sendToServer(msg);
				return (XWPFDocument) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Send message to the server to add the student to the list of the Active exam.
	 * @param s
	 * @param ae
	 */
	public static void StudentCheckedInToActiveExam(Student s,ActiveExam ae)
	{
		Object[] sendObject=new Object[2];
		sendObject[0]=(Student)s;
		sendObject[1]=(ActiveExam)ae;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("StudentCheckInToExam",sendObject);
				client.sendToServer(msg);
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Send message to the server to remove the student from the list of the Active exam.
	 * @param s
	 * @param ae
	 */
	public static void StudentCheckedOutFromActiveExam(Student s,ActiveExam ae)
	{
		Object[] sendObject=new Object[2];
		sendObject[0]=(Student)s;
		sendObject[1]=(ActiveExam)ae;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("StudentCheckedOutFromActiveExam",sendObject);
				client.sendToServer(msg);
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
	}
	
}
