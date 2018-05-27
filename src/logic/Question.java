package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import Controllers.QuestionController;

@SuppressWarnings("serial")
public class Question implements Serializable{
	private int ID;
	private Teacher Author;
	private String questionString;
	private String[] Answers;
	private Field field;
	private ArrayList<Course> courses = null;
	private int CorrectAnswerIndex;
	
	public Question(int iD, Teacher Author, String question, String[] answers, Field field, int correctAnswerIndex,ArrayList<Course> courses ) {
		super();
		ID = iD;
		this.Author = Author;
		this.questionString = question;
		Answers = answers;
		this.field = field;
		CorrectAnswerIndex = correctAnswerIndex;
		this.courses= courses;
	}

	public Question(Question q) {
		super();
		ID = q.getID();
		Author = new Teacher(q.getAuthor());
		questionString = new String(q.getQuestionString());
		Answers = q.Answers.clone();
		CorrectAnswerIndex = q.getCorrectAnswerIndex();
		field = new Field(q.getField());
		if (q.courses!=null) this.courses = (ArrayList<Course>) q.getCourses().clone();
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

	public Teacher getAuthor() {
		return Author;
	}

	@Override
	public boolean equals(Object obj) {
		Question q=null;
		if (obj instanceof Question) {
			q = (Question)obj;
		}else {return false;}
		if (q.getID()!=ID ||
			q.getAuthor().getID()!=Author.getID() ||
			q.getCorrectAnswerIndex()!=CorrectAnswerIndex||
			q.getField().getID()!=field.getID() ||
			!q.getQuestionString().equals(questionString)) 
			return false;
		for(int i=0;i<4;i++) {
			if(!q.getAnswer(i).equals(getAnswer(i))) return false;
		}
		return true;
	}

	public String questionIDToString() {
		return new String(String.format("%02d%03d", getField().getID(),getID()));
	}
	
	public String questionToString() {
		return new String("QuestionID:"+getID()+"\nQuestion:" + getQuestionString() + "\nA:"+Answers[0]+"\nB:"+Answers[1]+"\nC:"+Answers[2]+"\nD:"+Answers[3]+"\nCorrectIndex:"+getCorrectAnswerIndex()+"\nField:"+getField());
	}
	
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	public static String questionIDToString(int qid,int fid) {
		return new String(String.format("%02d%03d", fid,qid));
	}
	
	public static int[] parseID(String id) {
		try {
			int[] result = {Integer.parseInt(id.substring(0, 2)),Integer.parseInt(id.substring(2,5))};
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			int[] res = {-1,-1};
			return res;
		}
		
	}

	@Override
	public String toString() {
		return new String(getID()+" "+getQuestionString()+" "+Answers+" "+getCorrectAnswerIndex()+" "+getField());
	}
	
	
}
