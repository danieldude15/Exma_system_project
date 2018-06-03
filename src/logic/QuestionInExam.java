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
	int PointsValue;
	/**
	 * a String of Hidden Note that is visiable only yo teachers and not visiable to students
	 */
	String innerNote;
	/**
	 * a String visiable to all teachers and student
	 */
	String studentNote;
	
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
		inNote=innerNote;
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

	/**
	 * getInnerNote
	 * @return the hidden note visiable to only TEachers And Principles
	 */
	public String getInnerNote() {
		return innerNote;
	}

	/**
	 * getStudentNote
	 * @return the String note visiable To all users
	 */
	public String getStudentNote() {
		return studentNote;
	}

	/**
	 * overriding the equals method to be able to use hashMap and check for equal objects of this type
	 * @param obj - the QuestionInExam to check equality to
	 */
	@Override public boolean equals(Object obj) {
		if (this==obj)return true;
		if(obj instanceof QuestionInExam) {
			QuestionInExam q = (QuestionInExam) obj;
			if (super.equals(q) 
					&& q.getPointsValue()==PointsValue 
					&& q.getStudentNote().equals(studentNote) 
					&& q.getInnerNote().equals(innerNote))
				return true;
		}
		return false;
	}

	/**
	 * hashCode ovverid to use in HashMaps
	 */
	@Override public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + PointsValue;
		return result;
	}

	
	
}
