package logic;

import java.io.Serializable;
import java.util.ArrayList;


public class Question implements Serializable{
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -406882124262979292L;
	/**
	 * question id
	 */
	private int ID;
	/**
	 * the auther of this question (Teacher)
	 */
	private Teacher Author;
	/**
	 * the Question String
	 */
	private String questionString;
	/**
	 * the Array of posible answers should be size=4 
	 */
	private String[] Answers;
	/**
	 * the field of witch this question belongs to
	 */
	private Field field;
	/**
	 * the ArrayList of courses this question belongs to
	 */
	private ArrayList<Course> courses = null;
	/**
	 * the correct answer index of this question should be in range 1-4
	 */
	private int CorrectAnswerIndex;
	
	/**
	 *  the constructor of Question Object
	 * @param iD - question id
	 * @param Author - question auther Should be a Teacher
	 * @param question - string of question
	 * @param answers - array of answers
	 * @param field - field this question belongs to
	 * @param correctAnswerIndex - the index of the correct answer
	 * @param courses - the course arraylist of this question
	 */
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

	/**
	 *  copy constructor of question
	 * @param q - the question we want to copy
	 */
	@SuppressWarnings("unchecked")
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

	/**
	 * getField
	 * @return the field of this question
	 */
	public Field getField() {
		return field;
	}

	/**
	 * get Question String
	 * @return the String of this question
	 */
	public String getQuestionString() {
		return questionString;
	}

	/**
	 * getID
	 * @return the int id of this question
	 */
	public int getID() {
		return ID;
	}

	/**
	 * range is between 1-4 and not 0-3 
	 * @param i index value of answer
	 * @return the string value of the answer
	 */
	public String getAnswer(int i) {
		if (i>4 || i<1) {return null;}
		else return Answers[i-1];
	}
	
	/**
	 * getAnswers
	 * @return the Array of answers of this question
	 */
	public String[] getAnswers() {
		return Answers;
	}

	/**
	 * will return a value between 1-4 for correct answer
	 * @return int value of correct index of answer
	 */
	public int getCorrectAnswerIndex() {
		return CorrectAnswerIndex;
	}

	/**
	 * getAuther
	 * @return the Teacher who wrote this question
	 */
	public Teacher getAuthor() {
		return Author;
	}

	/**
	 * overriding the equals method to compare different questions that do not have the same referance
	 */
	@Override public boolean equals(Object obj) {
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
		for(int i=1;i<=4;i++) {
			if(!q.getAnswer(i).equals(getAnswer(i))) return false;
		}
		return true;
	}

	/**
	 * this will return the full question ID including field id
	 * for example questionid=1 and fieldid=2 will return 02001 
	 * @return the string containg the full questionID
	 */
	public String questionIDToString() {
		return new String(String.format("%02d%03d", getField().getID(),getID()));
	}
	
	/**
	 * getCourses
	 * @return the arrayList of courses of this question
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	/**
	 * setCourses of this question
	 * @param courses - arrayList of Courses
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	/**
	 * this is a static method that can be used by anyone who need to convert question id ints to a String
	 * @param qid - question id
	 * @param fid - field id
	 * @return a String that looks like a full questionID. for example questionid=1 and fieldid=2 will return 02001 
	 */
	public static String questionIDToString(int qid,int fid) {
		return new String(String.format("%02d%03d", fid,qid));
	}
	
	/**
	 * this function is static and can be used anywhere all you need is to send a full question ID (like 02001)
	 * @param id - String of QuestionID (like 04002)
	 * @return an array of ints where index [0]=fieldid and [1]=questionid
	 */
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
	
	/**
	 * check if question is in a course
	 * @param course object
	 * @return true if this question belong to couse
	 */
	public boolean isInCourse(Course c) {
		for(Course course:courses) {
			if (course.equals(c)) return true;
		}
		return false;
	}

	/**
	 * ovverid toString of Question
	 */
	@Override public String toString() {
		return new String(getID()+" "+getQuestionString()+" "+Answers+" "+getCorrectAnswerIndex()+" "+getField());
	}
	
	/**
	 * overrideing Question hashCode to use HashMaps if needed.
	 */
	@Override public int hashCode() {
		 int result = 17;
	        result = 31 * result + ID;
	        result = 31 * result + field.hashCode();
	        result = 31 * result + CorrectAnswerIndex;
	        return result;
	}
	
}
