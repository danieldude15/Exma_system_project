package logic;

import java.util.ArrayList;

public class QuestionInExam extends Question {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2747357847217521892L;
	int PointsValue;
	String innerNote;
	String studentNote;

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

	public int getPointsValue() {
		return PointsValue;
	}

	public String getInnerNote() {
		return innerNote;
	}


	public String getStudentNote() {
		return studentNote;
	}

	
}
