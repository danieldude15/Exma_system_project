package logic;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Question implements Serializable{
	private int ID;
	private Teacher Auther;
	private String questionString;
	private String[] Answers;
	private Field field;
	private int CorrectAnswerIndex;
	
	public Question(int iD, Teacher auther, String question, String[] answers, Field field, int correctAnswerIndex) {
		super();
		ID = iD;
		Auther = auther;
		this.questionString = question;
		Answers = answers;
		this.field = field;
		CorrectAnswerIndex = correctAnswerIndex;
	}

	public Question(Question q) {
		super();
		ID = q.getID();
		Auther = new Teacher(q.getAuther());
		questionString = new String(q.getQuestionString());
		Answers = q.Answers.clone();
		CorrectAnswerIndex = q.getCorrectAnswerIndex();
		field = new Field(q.getField());
	}

	public Field getField() {
		return field;
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
		return questionString;
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

	public Teacher getAuther() {
		return Auther;
	}

	@Override
	public boolean equals(Object obj) {
		Question q=null;
		if (obj instanceof Question) {
			q = (Question)obj;
		}else {return false;}
		if (q.getID()!=ID ||
			q.getAuther().getID()!=Auther.getID() ||
			q.getCorrectAnswerIndex()!=CorrectAnswerIndex||
			q.getField().getID()!=field.getID() ||
			!q.getQuestionString().equals(questionString)) 
			return false;
		for(int i=0;i<4;i++) {
			if(!q.getAnswer(i).equals(getAnswer(i))) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return new String("QuestionID:"+getID()+"\nQuestion:" + getQuestionString() + "\nA:"+Answers[0]+"\nB:"+Answers[0]+"\nC:"+Answers[0]+"\nD:"+Answers[0]+"\nCorrectIndex:"+getCorrectAnswerIndex()+"\nField:"+getField());
	}
	
	
	
}
