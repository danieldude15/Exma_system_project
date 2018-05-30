package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class ActiveExamController {

	/**
	 * this function will get the Teachers Active Exams from database to display in main window of teacher
	 * @param Teacher
	 * @return
	 */
	public static ArrayList<ActiveExam> getTeachersActiveExams(Teacher t) {
		AESClient client = ClientGlobals.client;
		ActiveExam activeExam;	
		iMessage msg = new iMessage("getTeachersActiveExams",t);
		try {
			client.sendToServer(msg);
			Object o = client.getResponseFromServer().getObj();
			if(o instanceof ArrayList) {
				return (ArrayList<ActiveExam>) o;
			}
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
		
	

	@SuppressWarnings("unchecked")
	public static ArrayList<ActiveExam> GetAllActiveExams() {
		AESClient client = ClientGlobals.client;
		ArrayList<ActiveExam> allActiveExams;
		iMessage msg;
		if(client.isConnected()) {
			try {
				msg= new iMessage("getAllActiveExams",null);
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				allActiveExams = new ArrayList<ActiveExam>();
				if(o instanceof ArrayList) {
					ArrayList<ActiveExam> activeExams = (ArrayList<ActiveExam>) o;
					for (ActiveExam e: allActiveExams) {
						ActiveExam eExam = new ActiveExam(e);
						allActiveExams.add(eExam);
					}
					return allActiveExams;
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return null;
	}

}
