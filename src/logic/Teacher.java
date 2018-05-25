package logic;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Teacher extends User implements Serializable{
	
	private ArrayList<Field> fields;
	private ArrayList<Question> questions;
	private ArrayList<Exam> exams;
	
	public Teacher(int id,String userName, String Password, String Name, ArrayList<Field> fs, ArrayList<Question> qs, ArrayList<Exam> es) {
		super(id,userName, Password, Name);
		fields = fs;
		questions = qs;
		exams = es;
	}
	
	public Teacher(Teacher t) {
		super(t.getID(),new String(t.getUserName()),new String(t.getPassword()),new String(t.getName()));
	}

	public Teacher(User o) {
		super(o);
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
