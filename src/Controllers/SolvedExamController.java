package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.User;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class SolvedExamController {

	/**
	 * Send to server a request to pull the object solved exams from database.
	 * @param 
	 * @return SolvedExam
	 */
	public static ArrayList<SolvedExam> getSolvedExams(User u) {
		AESClient client = ClientGlobals.client;
		ArrayList<SolvedExam> solved;
		iMessage msg;
		if(client.isConnected()) {
			if(u instanceof Student)
				msg= new iMessage("getStudentsSolvedExams",(Student)u);
			else
				msg= new iMessage("getTeacherSolvedExams",(Teacher)u);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				solved = new ArrayList<SolvedExam>();
				if(o instanceof ArrayList) {
					ArrayList<SolvedExam> solvedExams = (ArrayList<SolvedExam>) o;
					for (SolvedExam se: solvedExams) {
						SolvedExam sExam = new SolvedExam(se);
						solved.add(sExam);
					}
				}
				return solved;
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	
}
