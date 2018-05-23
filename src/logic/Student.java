package logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.AESClient;
import ocsf.client.ClientGlobals;

public class Student extends User{
	
	private ArrayList<SolvedExam> solved;
	
	public Student(String userName, String Password, String Name,ArrayList<SolvedExam> s) {
		super(userName, Password, Name);
		solved = s;
	}
	
	private ArrayList<SolvedExam> getAllSolvedExams() {
		return solved;
	}

	@SuppressWarnings("unchecked")
	public Student(Student s) {
		super(new String(s.getName()),new String(s.getPassword()),new String(s.getName()));
		solved = (ArrayList<SolvedExam>) s.getAllSolvedExams().clone();
	}
	
	public SolvedExam getSolvedExam(Exam e) {
		int examID = e.getID();
		for (SolvedExam solvedExam:solved) {
			if (solvedExam.getID()==examID) {
				return solvedExam;
			}
		}
		return null;
	}

}
