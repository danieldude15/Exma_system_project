package pLogic;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class pQuestion implements Serializable  {
	private int ID;
	private int AutherID;
	private String question;
	private String[] Answers;
	private int CorrectAnswerIndex;
	
	public pQuestion(int id, int autherid, String question, String[] answers,int correctindex) {
		ID=id;
		AutherID=autherid;
		this.question = new String(question);
		Answers = answers;
		setCorrectAnswerIndex(correctindex);
	}
	
	public pQuestion(pQuestion question) {
		ID = question.ID;
		AutherID = question.AutherID;
		this.question = new String(question.question);
		Answers = question.Answers.clone();
		CorrectAnswerIndex = question.CorrectAnswerIndex;
	}
	
	public static Vector<pQuestion> clone(Vector<pQuestion> questions) {
		Vector<pQuestion> questionsRes = new Vector<pQuestion>();
		for (pQuestion q:questions) {
			questionsRes.add(new pQuestion(q));
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
