package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class SolvedExam extends ActiveExam{
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
	HashMap<QuestionInExam, Integer> studentsAnswers; 
	/**
	 * this HashMap holds a Key of QuestionInExam and Value of the Note string on solved question
	 */
	HashMap<QuestionInExam, String> questionNoteOnHash;
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
	public SolvedExam(int score, boolean teacherApproved, HashMap<QuestionInExam, Integer> studentsAnswers, 
			 Student examSolver, String teachersScoreChangeNote, HashMap<QuestionInExam, String> teacherNotes,
			int completedTimeInMinutes, String code, int type, Date dayActivated,Teacher activator, Exam exam) {
		super(code, type, dayActivated, exam, activator);
		this.score = score;
		this.teacherApproved = teacherApproved;
		this.studentsAnswers = studentsAnswers;
		this.examSolver = examSolver;
		this.teachersScoreChangeNote = teachersScoreChangeNote;
		if (this.teachersScoreChangeNote ==null) this.teachersScoreChangeNote ="";
		CompletedTimeInMinutes = completedTimeInMinutes;
		questionNoteOnHash = teacherNotes;
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
		if (studentsAnswers!=null)
			return new ArrayList<QuestionInExam>(studentsAnswers.keySet());
		else 
			return new ArrayList<QuestionInExam>();
	}
	
	/**
	 * overriding the toString proc to be used in listViews in GUI
	 */
	@Override public String toString() {
		if (teacherApproved)
			return "Exam Owner: " + getStudent().getName() + " ("+ getStudent().getID()+") " +"\nCourse: " + getCourse().getName() + "\nCompleted_Time: " + CompletedTimeInMinutes + "\nScore: " + score + " (APPROVED)";
		else
			return "Exam Owner: " + getStudent().getName() + " ("+ getStudent().getID()+") " +"\nCourse: " + getCourse().getName() + "\nCompleted_Time: " + CompletedTimeInMinutes + "\nScore: " + score;
	}
	public HashMap<QuestionInExam, String> getQuestionNoteOnHash() {
		return questionNoteOnHash;
	}
	public void setQuestionNoteOnHash(HashMap<QuestionInExam, String> answerNoteOnQuestion) {
		this.questionNoteOnHash = answerNoteOnQuestion;
	}
	
	
	/**
	 * This function will convert answers from database to objects by organizing them
	 * For this to work the answers must be kept in the database in a cetain way
	 * answers will appear as a long string and look like following:
	 * q<questionId>a<answerIndex>q<questionId>a<answerIndex>q<questionId>a<answerIndex>/ ... 
	 * before every answer there is the letter 'a'
	 * also before every question there is the letter 'q'
	 * @param string of answers kept in database in described matter
	 * @param questions the arraylist of questionsInExam with their points assigned to them
	 * @return an organized HashMap of questions in exam including the question and the students answer
	 */
	public static HashMap<QuestionInExam, Integer> parseStudentsAnswers(String string, ArrayList<QuestionInExam> questions) {
		if (string==null || string.equals("")) return null;
		HashMap<String, Integer> answers = new HashMap<>();
		HashMap<QuestionInExam, Integer> result = new HashMap<>();
		String[] temp = string.split("q");
		String[] splitedAnswers = Arrays.copyOfRange(temp,1,temp.length);
		for(String questionIDAndAnswer : splitedAnswers) {
			String[] arr = questionIDAndAnswer.split("a");
			answers.put(arr[0], Integer.parseInt(arr[1]));
		}
		for(QuestionInExam q: questions) {
			result.put(q,answers.get(q.questionIDToString()));
		}
		return result;
	}
	
	public static String studentAnswersToString(HashMap<QuestionInExam, Integer> studentsAnswers) {
		String answers = "";
		for (QuestionInExam qie : studentsAnswers.keySet()) {
			answers = answers.concat("q"+qie.questionIDToString()+"a"+studentsAnswers.get(qie));
		}
		return answers;
	}

	public static HashMap<QuestionInExam,String> parseTeacherNotes(String string , ArrayList<QuestionInExam> questionsInExam) {
		if (string==null || string.equals("")) return new HashMap<>();
		HashMap<QuestionInExam, String> result = new HashMap<>();
		HashMap<String, String> notes = new HashMap<>();
		String[] temp = string.split("<QID>");
		String[] splitedNotes = Arrays.copyOfRange(temp,1,temp.length);
		for(String questionNote : splitedNotes) {
			String[] arr = questionNote.split("<TEACHER-NOTE>");
			if(arr.length==2)
				notes.put(arr[0], arr[1]);
		}
		for(QuestionInExam q: questionsInExam) {
			result.put(q,notes.get(q.questionIDToString()));
		}
		return result;
	}

	public static String teachersNotesToString(HashMap<QuestionInExam, String> hashMap) {
		String allNotes = "";
		if (hashMap==null) return allNotes;
		for(QuestionInExam q: hashMap.keySet()) {
			allNotes = allNotes.concat("<QID>"+q.questionIDToString()+"<TEACHER-NOTE>"+hashMap.get(q));
		}
		return allNotes;
	}

}
