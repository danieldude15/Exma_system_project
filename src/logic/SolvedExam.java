package logic;

import java.util.ArrayList;
import java.util.HashMap;


public class SolvedExam extends Exam{
	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = -6299389250694557248L;
	/**
	 * this hold the score of the students solved exam. score will be final if teacherAprooved is true
	 */
	int score;
	/**
	 * This is a boolean telling us if the teacher already approved this solved exam or not
	 */
	boolean teacherApproved;
	/**
	 * this holds the students answers to each question in his solved exam
	 */
	HashMap<QuestionInExam, Integer> studentsAnswers;// 
	
	//HashMap<QuestionInExam, String> answerNoteOnQuestion;//// HashMap<(Key)QuestionInExam,(Value)Note string on solved question>
	
	/**
	 * exam report ID. in case needed can be pulled from database using this id
	 */
	int examReportID;
	/**
	 * the Student that solved this exam
	 */
	Student examSolver;
	/**
	 * the string representing the reason for the teachers changed score 
	 * (jsut in case he chagned the score and did not approve the students answer for some reason)
	 */
	String teachersScoreChangeNote;
	/**
	 * the time it took the student to complete the exam
	 */
	int CompletedTimeInMinutes;
	
	/**
	 * constructor to build this object
	 * @param iD - id of solved exam used to build the exam super class
	 * @param course - the course of this solved exam used to build the exam super class
	 * @param duration - the duration time decided by the teacher initially to solve this exam
	 * 						used to build the exam super class
	 * @param author - the creater of this exam
	 * @param score - the score given to the student for his answers on this exam
	 * @param teacherApproved - if the teacher approved this score or not
	 * @param studentsAnswers - the hasmap of all questions in exam with the students answers in them
	 * @param examReportID - the exam report id this solved exam belongs to in case we want to pull a report
	 * @param examSolver - the student that solved this exam
	 * @param teachersScoreChangeNote - the teacher score change note . in case the score was changed
	 * @param completedTimeInMinutes - the time it took the student to complete the exam
	 */
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
	 * getScore
	 * @return the score of this students solved exam
	 */
	public int getScore() {
		return score;
	}

	/**
	 * isTeacherApproved
	 * @return true if approved by the teacher or False if not yet approved
	 */
	public boolean isTeacherApproved() {
		return teacherApproved;
	}

	/**
	 * getStudentsAnswers
	 * @return will return the hashmap of the students answers
	 */
	public HashMap<QuestionInExam, Integer> getStudentsAnswers() {
		return studentsAnswers;
	}

	/**
	 * getExamReportID
	 * @return the int id of the report this solved exam belogs to
	 */
	public int getExamReportID() {
		return examReportID;
	}

	/**
	 * getStudent will get the exam solver
	 * @return will return the student witch solved this exam
	 */
	public Student getStudent() {
		return examSolver;
	}

	/**
	 * getTeachersScoreChangeNote
	 * @return the string containing the reason of the teacher that changed the students score
	 */
	public String getTeachersScoreChangeNote() {
		return teachersScoreChangeNote;
	}

	/**
	 * 
	 * @return the time in minutes of witch it took the student to complete the exam
	 */
	public int getCompletedTimeInMinutes() {
		return CompletedTimeInMinutes;
	}

	/**
	 * this will set the new score for the student. should be used by the teacher only to chagne a score of a students exam
	 * @param score - new score to change to
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * this should be updated to true after a teacher approved or changed the score of a solved exam
	 * @param teacherApproved - update to true of false
	 */
	public void setTeacherApproved(boolean teacherApproved) {
		this.teacherApproved = teacherApproved;
	}

	/**
	 * when a teacher changes the score he must add a string explaining the reason for the score change
	 * @param teachersScoreChangeNote - string
	 */
	public void setTeachersScoreChangeNote(String teachersScoreChangeNote) {
		this.teachersScoreChangeNote = teachersScoreChangeNote;
	}
	
	/**
	 * @return the arraylist of question in this exam
	 */
	@Override public ArrayList<QuestionInExam> getQuestionsInExam() {
		ArrayList<QuestionInExam> questions= new ArrayList<>();
		for(QuestionInExam q: studentsAnswers.keySet()) {
			questions.add(q);
		}
		return questions;
	}
	
	/**
	 * overriding the toString proc to be used in listViews in GUI
	 */
	@Override public String toString() {
		return "Exam Owner: " + getStudent().getName() + " ("+ getStudent().getID()+") " +"| Course: " + getCourse().getName() + " |Completed_Time: " + CompletedTimeInMinutes + " |Score: " + score;
	}
	
	

}
