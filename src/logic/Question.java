package logic;

import java.util.ArrayList;
import java.util.Vector;

public class Question{
	private int ID;
	private int AutherID;
	private String question;
	private String[] Answers;
	private int CorrectAnswerIndex;
	private ArrayList<Note> notes;
	
	public Question(int id, int autherid, String question, String[] answers,int correctindex,ArrayList<Note> notes) {
		ID=id;
		AutherID=autherid;
		this.question = new String(question);
		Answers = answers;
		setCorrectAnswerIndex(correctindex);
		notes =new ArrayList<Note>();
		
	}
	
	public Question(Question question) {
		ID = question.ID;
		AutherID = question.AutherID;
		this.question = new String(question.question);
		Answers = question.Answers.clone();
		CorrectAnswerIndex = question.CorrectAnswerIndex;
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

	public void setCorrectAnswerIndex(int correctAnswerIndex) {
		CorrectAnswerIndex = correctAnswerIndex;
	}

	public int getAutherID() {
		return AutherID;
	}
}
