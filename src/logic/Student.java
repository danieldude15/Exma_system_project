package logic;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Student extends User implements Serializable{
	
	public Student(int id,String userName, String Password, String Name) {
		super(id,userName, Password, Name);
	}
	
	/**
	 * copy constructor
	 * @param student
	 */
	public Student(Student s) {
		super(s.getID(),new String(s.getName()),new String(s.getPassword()),new String(s.getName()));
	}
	
	/**
	 * this function gets all solved exams completed by this student using solved exam controller
	 * @return currently null needs implementation
	 */
	private ArrayList<SolvedExam> getAllSolvedExams() {
		return null;
	}
	/**
	 * this function will get the solved exam made by this student 
	 * @param an Exam that we wish to get its solved exam of
	 * @return the solved exam of exam
	 */
	public SolvedExam getSolvedExam(Exam e) {
		/*int examID = e.getID();
		for (SolvedExam solvedExam:solved) {
			if (solvedExam.getID()==examID) {
				return solvedExam;
			}
		}*/
		return null;
	}

}
