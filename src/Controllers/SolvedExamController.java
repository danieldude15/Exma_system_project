package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.SolvedExam;
import logic.Student;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
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
				solved = new ArrayList<SolvedExam>();
				if(o instanceof ArrayList) {
					ArrayList<SolvedExam> StudentsSolvedExams = (ArrayList<SolvedExam>) o;
					for (SolvedExam se: StudentsSolvedExams) {
						SolvedExam sExam = new SolvedExam(se);
						solved.add(sExam);
					}
				}
				client.cleanMsg();
				return solved;
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
