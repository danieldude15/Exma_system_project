package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Exam;
import logic.Question;
import logic.QuestionInExam;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class ExamController {

	public static ArrayList<Exam> getTeachersExams(Teacher t){
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersExam",t);
			try {
				client.sendToServer(msg);
				return (ArrayList<Exam>) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return null;
	}

	public static int deleteExam(Exam exam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("DeleteExam",exam);
			try {
				client.sendToServer(msg);
				return (Integer) client.getResponseFromServer().getObj();
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		} 
		return 0;
	}

	public static int addExam(Exam exam) {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("addExam",exam);
			try {
				client.sendToServer(msg);
				Object o = client.getResponseFromServer().getObj();
				if(o instanceof Integer) {
					return (Integer) o;
				}
			} catch (IOException e) {
				ClientGlobals.handleIOException(e);
				e.printStackTrace();
			}
		}
		return 0;
	}
}
