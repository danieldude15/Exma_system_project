package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.Exam;
import logic.Teacher;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.client.iMessage;

@SuppressWarnings("unchecked")
public class ExamController {

	public static ArrayList<Exam> getTeachersExams(Teacher t){
		AESClient client = ClientGlobals.client;
		ArrayList<Exam> exams;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersExam",t);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = msg.getObj();
				exams = new ArrayList<Exam>();
				if(o instanceof ArrayList) {
					ArrayList<Exam> TeacherExams = (ArrayList<Exam>) o;
					for (Exam e: TeacherExams) {
						Exam exam = new Exam(e);
						exams.add(exam);
					}
				}
				client.cleanMsg();
				return exams;
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
