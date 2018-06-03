package logic;

import java.util.ArrayList;

/**
 * This is the Completed Exam entity that hold information on exams that already been completed 
 * by all students in course or locked by the teacher that initiated the active exam.
 * @author Group-12
 *
 */
public class CompletedExam extends ActiveExam {
	
	/**
	 *  Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 4657010598883384610L;
	/**
	 * arraylist of all solved exams of this completed exam
	 */
	private ArrayList<SolvedExam> solvedExams = null;
	
	/**
	 * constructor of Completed Exam
	 * @param code - the activeExam code (4 char)
	 * @param type - the type of exam. {computerized or manual}
	 * @param dayActivated - the date thei exam was activated
	 * @param activator - the teacher who activated this exam
	 * @param solvedExams - the arraylist of all sovled exams 
	 */
	public CompletedExam(String code, int type, String dayActivated, Teacher activator,ArrayList<SolvedExam> solvedExams) {
		super(code, type, dayActivated, null, activator);
		this.solvedExams=solvedExams;
	}

	/**
	 * overriding getExam because we dont want to hold double information we use the 
	 * solvedexams firs element to return the exam from him
	 */
	@Override public Exam getExam() {
		if (solvedExams!=null && solvedExams.get(0)!=null)
			return solvedExams.get(0).getExam();
		return null;
	}

	/**
	 * getSolvedExams
	 * @return the arraylist of solveExams
	 */
	public ArrayList<SolvedExam> getSolvedExams() {
		return solvedExams;
	}

	/**
	 * overriding toString method
	 */
	@Override public String toString() {
		String examType;
		if (getType()==1) examType = "Computerized";
		else examType = "Manual";
		if (solvedExams.size()!=0)
			return new String(String.format("Activated On: %s \nCode: %s \nID: %s \nType: %s", getDate(),getCode(),solvedExams.get(0).examIdToString() ,examType));
		else 
			return new String(String.format("Activated On: %s \nCode: %s \nID: N/A \nType: %s", getDate(),getCode(),examType));
	}	
}
