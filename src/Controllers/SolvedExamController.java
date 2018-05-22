package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.Field;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class SolvedExamController {

	
	public static ArrayList<SolvedExam> getStudentsSolvedExams(Student s) {
		AESClient client = ClientGlobals.client;
		ArrayList<SolvedExam> solved;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getStudentsSolvedExams",s);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = msg.getObj();
				solved = new ArrayList<SolvedExams>();
				if(o instanceof ArrayList) {
					ArrayList<SolvedExams> StudentsSolvedExams = (ArrayList<SolvedExams>) o;
					for (Field se: StudentsSolvedExams) {
						SolvedExams sExam = new SolvedExams(se);
						solved.add(sExam);
					}
				}
				client.cleanMsg();
				return solved;
			} catch (IOException e) {
				ClientGlobals.handleIOException();
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
}
