package Controllers;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

import java.io.File;
import java.io.FileOutputStream;
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

	/**
	 * Send message to the server to get a specific active exam.
	 * @param examCode
	 * @return
	 */
	public static ActiveExam getActiveExam(String examCode) {
		AESClient client = ClientGlobals.client;
		ActiveExam activeExam;
		iMessage msg;
		if(client.isConnected()) {
			try {
				msg= new iMessage("getActiveExam",examCode);
				client.sendToServer(msg);
				return (ActiveExam) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Send message to the server to get all active exams.
	 * @return
	 */
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
	
	/**
	 * Send message to the server to lock an Active exam.
	 * @param activeExam
	 */
	public static void lockExam(ActiveExam activeExam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("LOCKActiveExam",activeExam);
				client.sendToServer(msg);
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
	}
	
	public static void InitializeActiveExam(ActiveExam AE) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("InitializeActiveExams",AE);
				client.sendToServer(msg);
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return;
	}
	
	
	public static AesWordDoc GetManualExam(ActiveExam activeExam)
	{
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("GetManualExam",activeExam);
				client.sendToServer(msg);
				return (AesWordDoc) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 *  Send message to the server to add the student to the list of the Active exam.
	 * @param s
	 * @param ae
	 * @return
	 */
	public static Boolean StudentCheckedInToActiveExam(Student s,ActiveExam ae)
	{
		Object[] sendObject=new Object[2];
		sendObject[0]=(Student)s;
		sendObject[1]=(ActiveExam)ae;
		AESClient client = ClientGlobals.client;
		Boolean canStart;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("StudentCheckInToExam",sendObject);
				client.sendToServer(msg);
				return (Boolean) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Send message to the server to remove the student from the list of the Active exam.
	 * @param s
	 * @param ae
	 */
	public static void studentCheckedOutFromActiveExam(Student s,ActiveExam ae)
	{
		Object[] sendObject=new Object[2];
		sendObject[0]=(Student)s;
		sendObject[1]=(ActiveExam)ae;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("StudentCheckedOutFromActiveExam",(Object)sendObject);
				client.sendToServer(msg);
				client.getResponseFromServer();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send message to the server to find out if the student is in exam at the moment.
	 * @param s
	 * @param ae
	 * @return
	 */
	public static boolean studentIsInActiveExam(Student s,ActiveExam ae) {
		Object[] sendObject=new Object[2];
		sendObject[0]=(Student)s;
		sendObject[1]=(ActiveExam)ae;
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			try {
				iMessage msg= new iMessage("studentIsInActiveExam",(Object)sendObject);
				client.sendToServer(msg);
				return (boolean) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return false;
	}

	
}
