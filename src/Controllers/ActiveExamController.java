package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

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

}
