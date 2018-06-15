package logic;

import java.util.ArrayList;

public class QuestionInExam extends Question {
	
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -2747357847217521892L;
	/**
	 * points value of question in an exam
	 */
	private int PointsValue;
	/**
	 * a String of Hidden Note that is visiable only yo teachers and not visiable to students
	 */
	private String innerNote;
	/**
	 * a String visiable to all teachers and student
	 */
	private String studentNote;
	
	/**
	 * Constructor to build an Object of type QuestionInExam
	 * @param iD - question id
	 * @param Author - question auther Should be a Teacher
	 * @param question - string of question
	 * @param answers - array of answers
	 * @param field - field this question belongs to
	 * @param correctAnswerIndex - the index of the correct answer
	 * @param courses - the course arraylist of this question
	 * @param points - points value of this question
	 * @param inNote - hidden note for teachers and principles only
	 * @param StdNote - visiable to all note
	 */
	public QuestionInExam(int iD, Teacher Author, String question, String[] answers, Field field,
						  int correctAnswerIndex, ArrayList<Course> courses,int points, String inNote,String StdNote) {
		super(iD, Author, question, answers, field, correctAnswerIndex, courses);
		PointsValue = points;
		innerNote = inNote;
		studentNote=StdNote;
	}
	public QuestionInExam(Question q,int points, String inNote,String StdNote) {
		super(q.getID(),q.getAuthor(),q.getQuestionString(),q.getAnswers(),q.getField(),q.getCorrectAnswerIndex(),q.getCourses());
		PointsValue = points;
		innerNote = inNote;
		studentNote=StdNote;
	}
	/**
	 * copy constructor
	 * @param q is QuestionInExam to be copied 
	 */
	public QuestionInExam(QuestionInExam q) {
		super(q);
		PointsValue = q.getPointsValue();
		innerNote = new String(q.getInnerNote());
		studentNote = new String(q.getStudentNote());
	}

	/**
	 * getPointsValue
	 * @return the int value of points given for this question
	 */
	public int getPointsValue() {
		return PointsValue;
	}
	public void setPointsValue(String Point) {
		PointsValue = Integer.parseInt(Point) ;
	}

	/**
	 * getInnerNote
	 * @return the hidden note visiable to only TEachers And Principles
	 */
	public String getInnerNote() {
		return innerNote;
	}
	public void setInnerNote(String Note) {
		innerNote = Note;
	}

	/**
	 * getStudentNote
	 * @return the String note visiable To all users
	 */
	public String getStudentNote() {
		return studentNote;
	}
	public void setStudentNote(String Note) {
		studentNote = Note;
	}

	
	/**
	 * overriding the equals method to be able to use hashMap and check for equal objects of this type
	 * @param obj - the QuestionInExam to check equality to
	 */
	@SuppressWarnings("")
	@Override public boolean equals(Object obj) {
		if (this==obj)return true;
		if(obj instanceof QuestionInExam) {
			QuestionInExam q = (QuestionInExam) obj;
			if ((super.equals(q) && q.getPointsValue()==PointsValue)) {
				return (q.getStudentNote() == studentNote || (q.getStudentNote() != null && q.getStudentNote().equals(studentNote)))
						&& (q.getInnerNote() == innerNote || (q.getInnerNote() != null && q.getInnerNote().equals(innerNote)));
			}
		}
		return false;
	}
	@Override public String toString() {
		return String.format("QuestionID: %s\nQuestion: %s\nAuthorName: %s\n ", questionIDToString(), getQuestionString(), getAuthor().getUserName());
	}

	/**
	 * hashCode ovverid to use in HashMaps
	 */
	@Override public int hashCode() {
		int result = 17;
		result = 31 * result + PointsValue;
		return result;
	}

	
	
}
