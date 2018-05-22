package logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.client.iMessage;

public class Teacher extends User {
	
	private ArrayList<Field> fields;
	private ArrayList<Question> questions;
	private ArrayList<Exam> exams;
	public Teacher(String userName, String Password, String Name) {
		super(userName, Password, Name);
		fields = getMyFiels();
		questions = getMyQuestions();
		exams = getMyExams();
	}
	@SuppressWarnings("unchecked")
	private ArrayList<Field> getMyFiels() {
		AESClient client = ClientGlobals.client;
		if(client.isConnected()) {
			iMessage msg= new iMessage("getTeachersFields",this);
			try {
				client.sendToServer(msg);
				client.waitForResponse();
				Object o = msg.getObj();
				fields = new ArrayList<Field>();
				if(o instanceof ArrayList) {
					ArrayList<Field> TeacherFields = (ArrayList<Field>) o;
					for (Field f: TeacherFields) {
						Field field = new Field(f);
						fields.add(field);
					}
				}
				client.cleanMsg();
			} catch (IOException e) {
				ClientGlobals.handleIOException();
				e.printStackTrace();
			}
		} else {
			return null;
		}
	}
	private ArrayList<Question> getMyQuestions() {
		// TODO Auto-generated method stub
		return null;
	}
	private ArrayList<Exam> getMyExams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
