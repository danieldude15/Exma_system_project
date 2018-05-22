package logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;
import ocsf.client.iMessage;

public class Student extends User{
	
	private ArrayList<SolvedExam> solved;
	
	public Student(String userName, String Password, String Name) {
		super(userName, Password, Name);
		solved = getMySolvedExams();
	}
	
	private ArrayList<SolvedExam> getMySolvedExams() {
		/* THIS GOES IN SOLVED EXAM CONTROLLER!!!
		AESClient client = ClientGlobals.client;
		
		if(client.isConnected()) {
			iMessage msg= new iMessage("getStudentsSolvedExams",this);
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
		*/
		return null;
	}

	public Student(Student s) {
		super(new String(s.getName()),new String(s.getPassword()),new String(s.getName()));
	}

}
