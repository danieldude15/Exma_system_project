package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the Completed Exam entity that hold information on exams that already been completed 
 * by all students in course or locked by the teacher that initiated the active exam.
 * @author Group-12
 *
 */
public class ExamReport extends ActiveExam {
	
	/**
	 *  Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 4657010598883384610L;
	/**
	 * arraylist of all solved exams of this completed exam
	 */
	private ArrayList<SolvedExam> solvedExams = null;
	/**
	 * 
	 */
	private Integer participatingStudent;
	/**
	 * 
	 */
	private Integer submittedStudents;
	/**
	 * 
	 */
	private Integer notInTimeStudents;
	/**
	 * 
	 */
	private Date timeLocked;
	/**
	 * median value of all solvedExams
	 */
	int median;
	/**
	 * average of all solvedExams
	 */
	int avg;
	/**
	 * deviation value of the report for every 10 points in the solvedExams scores
	 */
	String deviation;
	/**
	 * the date the exam was initiated
	 */
	Date dateinitiated;
	/**
	 * the hashMap of all potential cheaters in this exam
	 */
	HashMap<Student,Integer> m_cheatingStudents;
	
	
	/**
	 * this constructor should be used when getting information from database and 
	 * non of the information needs to be calculated because it is all saved in the database
	 * @param code - the activeExam code (4 char)
	 * @param type - the type of exam. {computerized or manual}
	 * @param dayActivated - the date the exam was activated
	 * @param activator - the teacher who activated this exam
	 * @param solvedExams - the ArrayList of all solved exams 
	 * @param notInTimeStudents - the amount of student that did not submit their exam on time and got 0 for it
	 * @param submittedStudents - the amount of student that submitted the exam in time 
	 * @param participatingStudent - the amount of student who participated in the exam 
	 * @param e - this exam of witch this report is made for
	 * @param timeLocked -the time when the exam was locked 
	 * @param median - the median of the scores in this exam
	 * @param avg - the avg of the scores of the exam
	 * @param diviation - the diviation values of this exam separeted in 10 points 
	 * @param dateinitiated -the date that the exam was activated/initiatez 
	 * @param m_cheatingStudents - the hashmap of cheaters found in this exam.
	 */
	public ExamReport(String code, int type, Date dayActivated, Exam e, Teacher activator,
			ArrayList<SolvedExam> solvedExams, Integer participatingStudent, Integer submittedStudents,
			Integer notInTimeStudents, Date timeLocked, int median, int avg, String diviation, Date dateinitiated,
			HashMap<Student, Integer> m_cheatingStudents) {
		super(code, type, dayActivated, e, activator);
		this.solvedExams = solvedExams;
		this.participatingStudent = participatingStudent;
		this.submittedStudents = submittedStudents;
		this.notInTimeStudents = notInTimeStudents;
		this.timeLocked = timeLocked;
		this.median = median;
		this.avg = avg;
		this.deviation = diviation;
		this.dateinitiated = dateinitiated;
		this.m_cheatingStudents = m_cheatingStudents;
	}
	
	/**
	 * this constructor should be called when creating a new exam report before pushing it into the database
	 * because this constructor uses the solved exams to calculate all the average and median and deviation also 
	 * find cheating students and then the object can get pushed into the database
	 * @param code - the activeExam code (4 char)
	 * @param type - the type of exam. {computerized or manual}
	 * @param dayActivated - the date the exam was activated
	 * @param activator - the teacher who activated this exam
	 * @param solvedExams - the ArrayList of all solved exams 
	 * @param notInTimeStudents - the amount of student that did not submit their exam on time and got 0 for it
	 * @param submittedStudents - the amount of student that submitted the exam in time 
	 * @param participatingStudent - the amount of student who participated in the exam 
	 * @param e - this exam of witch this report is made for
	 * @param timeLocked -the time when the exam was locked 
	 * @param dateinitiated -the date that the exam was activated/initiatez 
	 */
	public ExamReport(String code, int type, Date dayActivated, Exam e, Teacher activator,
			ArrayList<SolvedExam> solvedExams, Integer participatingStudent, 
			Integer submittedStudents,Integer notInTimeStudents, Date timeLocked, 
			Date dateinitiated) {
		super(code, type, dayActivated, e, activator);
		this.solvedExams = solvedExams;
		this.participatingStudent = participatingStudent;
		this.submittedStudents = submittedStudents;
		this.notInTimeStudents = notInTimeStudents;
		this.timeLocked = timeLocked;
		this.median = calcMedian(solvedExams);
		this.avg = calcAvg(solvedExams);
		this.deviation = calcDeviation(solvedExams);
		this.dateinitiated = dateinitiated;
		this.m_cheatingStudents = findCheaters(solvedExams);
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

	public Integer getParticipatingStudent() {
		return participatingStudent;
	}

	public Integer getSubmittedStudents() {
		return submittedStudents;
	}
	
	/**
	 * getMedian
	 * @return the meadian of this report
	 */
	public int getMedian() {
		return median;
	}

	/**
	 * getAvg
	 * @return the avarage ofthis exam
	 */
	public int getAvg() {
		return avg;
	}
	
	/**
	 * getDateinitiated
	 * @return the date of witch this exam initiated
	 */
	public Date getDateinitiated() {
		return dateinitiated;
	}
	
	/**
	 * get all potential cheaters in this exam
	 * @return the hashmap of potential cheates
	 */
	public HashMap<Student, Integer> getM_cheatingStudents() {
		return m_cheatingStudents;
	}
	

	public Integer getNotInTimeStudents() {
		return notInTimeStudents;
	}

	public Date getTimeLocked() {
		return timeLocked;
	}

	public String getDeviation() {
		return deviation;
	}

	/**
	 * this function will be responsible for check witch students cheated and return the hashmap of these students
	 * this is a private method used in the constructor if examReport to genereate these potential cheating students 
	 * @param solvedExams2 
	 * @return hashmap of potential cheating students
	 */
	public static HashMap<Student, Integer> findCheaters(ArrayList<SolvedExam> solvedExams2) {
		return null;
	}
	

	public static int calcMedian(ArrayList<SolvedExam> solvedExams2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int calcAvg(ArrayList<SolvedExam> solvedExams2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static String calcDeviation(ArrayList<SolvedExam> solvedExams2) {
		// TODO Auto-generated method stub
		return null;
	}

}
