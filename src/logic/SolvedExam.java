package logic;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SolvedExam extends Exam{
	int score;
	boolean teacherApproved;
	HashMap<Question, Integer>studentsAnswers;
	int examReportID;
	Student examSolver;
	String teachersScoreChangeNote;
	int CompletedTimeInMinutes;
	
	
	public SolvedExam(int iD, Course courseid, Field fieldid, int duration, Teacher author,
			ArrayList<Question> questionsInExam, int score, boolean teacherApproved,
			HashMap<Question, Integer> studentsAnswers, int examReportID, Student examSolver,
			String teachersScoreChangeNote, int completedTimeInMinutes) {
		super(iD, courseid, fieldid, duration, author, questionsInExam);
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
		this.studentsAnswers = (HashMap<Question, Integer>) exam.getStudentsAnswers().clone();
		this.examReportID = exam.getExamReportID();
		this.examSolver = new Student(exam.getExamSolver());
		this.teachersScoreChangeNote = new String(exam.getTeachersScoreChangeNote());
		CompletedTimeInMinutes = exam.getCompletedTimeInMinutes();
	}

	public int getScore() {
		return score;
	}

	public boolean isTeacherApproved() {
		return teacherApproved;
	}

	public HashMap<Question, Integer> getStudentsAnswers() {
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
	
	

}
