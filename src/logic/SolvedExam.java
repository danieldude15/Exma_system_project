package logic;

import java.util.ArrayList;
import java.util.HashMap;


public class SolvedExam extends Exam{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6299389250694557248L;
	int score;
	boolean teacherApproved;
	HashMap<QuestionInExam, Integer> studentsAnswers;
	int examReportID;
	Student examSolver;
	String teachersScoreChangeNote;
	int CompletedTimeInMinutes;
	
	
	public SolvedExam(int iD, Course course, int duration, Teacher author,
			int score, boolean teacherApproved,	HashMap<QuestionInExam, Integer> studentsAnswers, 
			int examReportID, Student examSolver,
			String teachersScoreChangeNote, int completedTimeInMinutes) {
		super(iD, course, duration, author, null);
		this.score = score;
		this.teacherApproved = teacherApproved;
		this.studentsAnswers = studentsAnswers;
		this.examReportID = examReportID;
		this.examSolver = examSolver;
		this.teachersScoreChangeNote = teachersScoreChangeNote;
		CompletedTimeInMinutes = completedTimeInMinutes;
	}
	/**
	 * copy constructor
	 * @param solved exam
	 */
	@SuppressWarnings("unchecked")
	public SolvedExam(SolvedExam exam) {
		super(exam);
		this.score = exam.getScore();
		this.teacherApproved = exam.isTeacherApproved();
		this.studentsAnswers = (HashMap<QuestionInExam, Integer>) exam.getStudentsAnswers().clone();
		this.examReportID = exam.getExamReportID();
		this.examSolver = new Student(exam.getExamSolver());
		if(exam.getTeachersScoreChangeNote()!=null)
			this.teachersScoreChangeNote = new String(exam.getTeachersScoreChangeNote());
		CompletedTimeInMinutes = exam.getCompletedTimeInMinutes();
	}

	public int getScore() {
		return score;
	}

	public boolean isTeacherApproved() {
		return teacherApproved;
	}

	public HashMap<QuestionInExam, Integer> getStudentsAnswers() {
		return studentsAnswers;
	}

	public int getExamReportID() {
		return examReportID;
	}

	public Student getExamSolver() {
		return examSolver;
	}

	public String getTeachersScoreChangeNote() {
		return teachersScoreChangeNote;
	}

	public int getCompletedTimeInMinutes() {
		return CompletedTimeInMinutes;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setTeacherApproved(boolean teacherApproved) {
		this.teacherApproved = teacherApproved;
	}

	public void setTeachersScoreChangeNote(String teachersScoreChangeNote) {
		this.teachersScoreChangeNote = teachersScoreChangeNote;
	}
	@Override
	public ArrayList<QuestionInExam> getQuestionsInExam() {
		ArrayList<QuestionInExam> questions= new ArrayList<>();
		for(QuestionInExam q: studentsAnswers.keySet()) {
			questions.add(q);
		}
		return questions;
	}
	
	@Override
	public String toString() {
		return "SolvedExam [score=" + score + ", teacherApproved=" + teacherApproved + ", studentsAnswers="
				+ studentsAnswers + ", examReportID=" + examReportID + ", examSolver=" + examSolver
				+ ", teachersScoreChangeNote=" + teachersScoreChangeNote + ", CompletedTimeInMinutes="
				+ CompletedTimeInMinutes + "]";
	}
	
	

}
