package logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import javafx.beans.property.DoublePropertyBase;

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
	 * ArrayList of all solved exams of this completed exam
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
	HashMap<Integer, Integer> deviation;
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
			ArrayList<SolvedExam> solvedExams, Integer participatingStudent, 
			Integer submittedStudents,Integer notInTimeStudents, Date timeLocked, 
			int median, int avg, HashMap<Integer, Integer> diviation,
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
			Integer submittedStudents,Integer notInTimeStudents, Date timeLocked) {
		super(code, type, dayActivated, e, activator);
		this.solvedExams = solvedExams;
		this.participatingStudent = participatingStudent;
		this.submittedStudents = submittedStudents;
		this.notInTimeStudents = notInTimeStudents;
		this.timeLocked = timeLocked;
		this.median = calcMedian(solvedExams);
		this.avg = calcAvg(solvedExams);
		this.deviation = calcDeviation(solvedExams);

		this.m_cheatingStudents = findCheaters(solvedExams);
	}

	/**
	 * getSolvedExams
	 * @return the arraylist of solveExams
	 */
	public ArrayList<SolvedExam> getSolvedExams() {
		return solvedExams;
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

	
	public HashMap<Integer, Integer> getDeviation() {
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
		ArrayList<Integer> scores = new ArrayList<>();
		for(SolvedExam se: solvedExams2) {
			scores.add(se.getScore());
		}
		Collections.sort(scores);
		if (scores.size()>0)
			return scores.get(scores.size()/2);
		else 
			return 0;
	}

	public static int calcAvg(ArrayList<SolvedExam> solvedExams2) {
		int sum =0;
		for(SolvedExam se: solvedExams2) {
			sum+=se.getScore();
		}
		if (solvedExams2.size()==0)
			return 0;
		else 
			return sum/solvedExams2.size();
	}

	public static HashMap<Integer, Integer> calcDeviation(ArrayList<SolvedExam> solvedExams2) {
		Integer[] devValues = new Integer[] {0,0,0,0,0,0,0,0,0,0};
		for(SolvedExam se: solvedExams2) {
			int score = se.getScore();
			if (score>90)
				devValues[9]++;
			else if (score>80)
				devValues[8]++;
			else if (score>70)
				devValues[7]++;
			else if (score>60)
				devValues[6]++;
			else if (score>50)
				devValues[5]++;
			else if (score>40)
				devValues[4]++;
			else if (score>30)
				devValues[3]++;
			else if (score>20)
				devValues[2]++;
			else if (score>10)
				devValues[1]++;
			else 
				devValues[0]++;
		}
		HashMap<Integer, Integer> deviation = new HashMap<>();
		for(int i=0; i<10 ;i++) {
			deviation.put(i, devValues[i]);
		}
		return deviation;
	}

	public static HashMap<Integer, Integer> parseStringDeviation(String string) {
		String[] devs = string.split("-");
		HashMap<Integer, Integer> result = new HashMap<>();
		if (devs.length!=10) 
			return null;
		for(int i=0;i<10;i++) {
			result.put(i, Integer.parseInt(devs[i]));
		}
		return result;
	}

	public static String convertDeviationToString(HashMap<Integer, Integer> deviation2) {
		String result = "";
		if(deviation2.size()!=10)
			return "0-0-0-0-0-0-0-0-0-0";
		for(int i=0;i<10;i++) {
			result= result + "" + deviation2.get(i);
			if (i!=9)
				result+= "-";
		}
		return result;
	}
	
	/**
	 * overriding toString method
	 */
	@Override public String toString() {
		return new String(String.format("Course: %s \nActivated On: %s \nAverage: %d", getCourse().getName(),getDate(),getAvg()));
	}

	
}
