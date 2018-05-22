package logic;

import java.util.ArrayList;


public class Teacher extends User {
	
	private ArrayList<Field> fields;
	private ArrayList<Question> questions;
	private ArrayList<Exam> exams;
	
	public Teacher(String userName, String Password, String Name, ArrayList<Field> fs, ArrayList<Question> qs, ArrayList<Exam> es) {
		super(userName, Password, Name);
		fields = fs;
		questions = qs;
		exams = es;
	}
	
	public ArrayList<Field> getFiels() {
		return fields;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public ArrayList<Exam> getExams() {
		return exams;
	}

	public Exam getExam(int examID) {
		for (Exam e:exams) {
			if (e.getID()==examID) {
				return e;
			}
		}
		return null;
	}
}
