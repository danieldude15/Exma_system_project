package logic;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Student extends User implements Serializable{
	
	private ArrayList<SolvedExam> solved;
	
	public Student(int id,String userName, String Password, String Name,ArrayList<SolvedExam> s) {
		super(id,userName, Password, Name);
		solved = s;
	}
	
	private ArrayList<SolvedExam> getAllSolvedExams() {
		return solved;
	}

	@SuppressWarnings("unchecked")
	public Student(Student s) {
		super(s.getID(),new String(s.getName()),new String(s.getPassword()),new String(s.getName()));
		solved = (ArrayList<SolvedExam>) s.getAllSolvedExams().clone();
	}
	
	public Student(User o) {
		super(o);
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
