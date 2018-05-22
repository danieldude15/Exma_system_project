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
	
	@SuppressWarnings("unchecked")
	public Teacher(Teacher t) {
		super(t.getUserName(),t.getPassword(),t.getName());
		fields = (ArrayList<Field>) t.getFiels().clone();
		questions = (ArrayList<Question>) t.getQuestions().clone();
		exams = (ArrayList<Exam>) t.getExams().clone();
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

	public boolean examExists(Exam e) {
		int examID = e.getID();
		for (Exam exam:exams) {
			if (exam.getID()==examID) {
				return true;
			}
		}
		return false;
	}
	
	public boolean fieldExists(Field f) {
		String fieldName = f.getName();
		for(Field field:fields) {
			if(fieldName.equals(field.getName())) return true;
		}
		return false;
	}
	
	public boolean questionExists(Question q) {
		int QuestionID = q.getID();
		for(Question question:questions) {
			if (QuestionID==question.getID()) return true;
		}
		return false;
	}
}
