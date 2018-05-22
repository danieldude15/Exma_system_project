package logic;

import java.util.ArrayList;
import java.util.Vector;

public class Question{
	private int ID;
	private Teacher Auther;
	private String question;
	private String[] Answers;
	private int CorrectAnswerIndex;
	private ArrayList<Note> Notes;
	
	public Question(int id, Teacher a, String q, String[] answers,int correctindex,ArrayList<Note> notes) {
		ID=id;
		Auther = a;
		question = q;
		Answers = answers;
		CorrectAnswerIndex = correctindex;
		Notes = notes;		
	}
	
	public Question(Question q) {
		ID = q.getID();
		Auther = new Teacher(q.getAuther());
		question = new String(q.getQuestionString());
		Answers = q.Answers.clone();
		CorrectAnswerIndex = q.getCorrectAnswerIndex();
	}
	
	public static Vector<Question> clone(Vector<Question> questions) {
		Vector<Question> questionsRes = new Vector<Question>();
		for (Question q:questions) {
			questionsRes.add(new Question(q));
		}
		return questionsRes;
	}

	public String getQuestionString() {
		// TODO Auto-generated method stub
		return question;
	}

	public int getID() {
		return ID;
	}

	public String getAnswer(int i) {
		return Answers[i];
	}
	
	public String[] getAnswers() {
		return Answers;
	}

	public int getCorrectAnswerIndex() {
		return CorrectAnswerIndex;
	}


	public ArrayList<Note> getNotes() {
		return Notes;
	}

	public Teacher getAuther() {
		return Auther;
	}
}
