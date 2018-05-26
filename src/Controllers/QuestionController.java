package Controllers;

import java.io.IOException;
import java.util.ArrayList;

import logic.Question;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

@SuppressWarnings("unchecked")
public class QuestionController {
	
	public static ArrayList<Question> getTeachersQuestions(Teacher t) {
		AESClient client = ClientGlobals.client;
		ArrayList<Question> questions;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersQuestions",t);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = client.getMsg().getObj();
				questions = new ArrayList<Question>();
				if(o instanceof ArrayList) {
					ArrayList<Question> TeacherQuestions = (ArrayList<Question>) o;
					for (Question q: TeacherQuestions) {
						Question question = new Question(q);
						questions.add(question);
					}
				}
				client.cleanMsg();
				return questions;
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
