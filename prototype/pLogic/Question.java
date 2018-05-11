package pLogic;

import java.io.Serializable;

public class Question implements Serializable  {
	private int ID;
	private int AutherID;
	private String question;
	private String[] Answers;
	private int CorrectAnswerIndex;
	
	public Question(int id, int autherid, String question, String[] answers,int correctindex) {
		ID=id;
		AutherID=autherid;
		this.question = new String(question);
		Answers = answers;
		setCorrectAnswerIndex(correctindex);
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
