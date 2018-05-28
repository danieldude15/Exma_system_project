package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.*;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class ActiveExamController {

	public static void getTeachersActiveExams(User user) {
		if (user instanceof Teacher) {
			iMessage msg = new iMessage("getTeachersActiveExams",user);
			try {
				ClientGlobals.client.sendToServer(msg);
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
			}
		} else {
			//show error alert
		}
		
	}

	public static ArrayList<ActiveExam> GetAllActiveExams() {
		// TODO Auto-generated method stub
		AESClient client = ClientGlobals.client;
		ArrayList<ActiveExam> allActiveExams;
		iMessage msg;
		if(client.isConnected()) {
			msg= new iMessage("getAllActiveExams",null);
			msg= new iMessage("getAllActiveExams",null);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = msg.getObj();
				allActiveExams = new ArrayList<ActiveExam>();
				if(o instanceof ArrayList) {
					ArrayList<ActiveExam> activeExams = (ArrayList<ActiveExam>) o;
					for (ActiveExam e: allActiveExams) {
						ActiveExam eExam = new ActiveExam(e);
						allActiveExams.add(eExam);
					}
				}
				client.cleanMsg();
				return allActiveExams;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}

	}

}
