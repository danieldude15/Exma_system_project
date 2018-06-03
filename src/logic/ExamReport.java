package logic;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ExamReport entity used to create reports for the principle and Teacher when needed
 * this class will generate and hold reprot information 
 * @author Group-12
 *
 */
public class ExamReport implements Serializable{

	/**
	 * Serializable id give for client server communication
	 */
	private static final long serialVersionUID = 2557792967631535481L;
	/**
	 * report unique id
	 */
	int reportid;
	/**
	 * median value of all solvedExams
	 */
	int meadian;
	/**
	 * avg of all solvedExams
	 */
	int avg;
	/**
	 * diviation value of the report
	 */
	int diviation;
	/**
	 * the amount of students submitted this exam in time
	 */
	int submitedcount;
	/**
	 * the amount of student that started the exam
	 */
	int startedcount;
	/**
	 * the amount of student failed to submit the exam in time 
	 */
	int failedsubmitstudentcount;
	/**
	 * the actual timeduration of the exam, in most cases this should be equals to the time duration of an exam 
	 * but in case the time was changed in the active exam this should have dirrenet value
	 */
	int actualdurationofexam;
	/**
	 * the date the exam was initiated
	 */
	Date dateinitiated;
	/**
	 * arraylist of all solvedexam copies of all student
	 */
	ArrayList<SolvedExam> m_examCopies;
	/**
	 * the hashMap of all potential cheaters in this exam
	 */
	HashMap<Student,Integer> m_cheatingStudents;
	
	/**
	 * Exam Report Constructor
	 * @param ID - report id
	 * @param meadian - median value of all solvedExams
	 * @param avg - avg of all solvedExams
	 * @param diviation - the amount of students submitted this exam in time
	 * @param submitedcount - the amount of students submitted this exam in time
	 * @param startedcount - the amount of student that started the exam
	 * @param failedsubmitstudentcount - the amount of student failed to submit the exam in time 
	 * @param actualdurationofexam - the actual timeduration of the exam, in most cases this should be equals to the time duration of an exam  but in case the time was changed in the active exam this should have dirrenet value
	 * @param dateinitiated - the date the exam was initiated
	 * @param m_examCopies - arraylist of all solvedexam copies of all student
	 * @param m_cheatingStudents - the hashMap of all potential cheaters in this exam
	 */
	public ExamReport(int ID, int meadian, int avg, int diviation,
			int submitedcount, int startedcount, int failedsubmitstudentcount, int actualdurationofexam,
			Date dateinitiated, ArrayList<SolvedExam> m_examCopies) {
		this.reportid = ID;
		this.meadian = meadian;
		this.avg = avg;
		this.diviation = diviation;
		this.submitedcount = submitedcount;
		this.startedcount = startedcount;
		this.failedsubmitstudentcount = failedsubmitstudentcount;
		this.actualdurationofexam = actualdurationofexam;
		this.dateinitiated = dateinitiated;
		this.m_examCopies = m_examCopies;
		this.m_cheatingStudents = findCheaters();
	}

	/**
	 * getReportID
	 * @return the reports id
	 */
	public int getReportid() {
		return reportid;
	}

	/**
	 * getMedian
	 * @return the meadian of this report
	 */
	public int getMeadian() {
		return meadian;
	}

	/**
	 * getAvg
	 * @return the avarage ofthis exam
	 */
	public int getAvg() {
		return avg;
	}

	/**
	 * getDiviation
	 * @return the diviation of this exam
	 */
	public int getDiviation() {
		return diviation;
	}

	/**
	 * getSubmittedCount
	 * @return return the amount of studnets that submited thsi exam in time
	 */
	public int getSubmitedcount() {
		return submitedcount;
	}

	/**
	 * getStaredCount
	 * @return the amount of students who started the exam
	 */
	public int getStartedcount() {
		return startedcount;
	}

	/**
	 * getFailedsybmitstudentcount
	 * @return the amount of students who failed to submit in time
	 */
	public int getFailedsubmitstudentcount() {
		return failedsubmitstudentcount;
	}
	
	/**
	 * getActualDurationTime
	 * @return the actual duration time of this exam
	 */
	public int getActualdurationofexam() {
		return actualdurationofexam;
	}

	/**
	 * getDateinitiated
	 * @return the date of witch this exam initiated
	 */
	public Date getDateinitiated() {
		return dateinitiated;
	}

	/**
	 * get all solvedExams
	 * @return
	 */
	public ArrayList<SolvedExam> getM_examCopies() {
		return m_examCopies;
	}

	/**
	 * get all potential cheaters in this exam
	 * @return the hashmap of potential cheates
	 */
	public HashMap<Student, Integer> getM_cheatingStudents() {
		return m_cheatingStudents;
	}
	
	/**
	 * getExam
	 * @return the exam of this report
	 */
	public Exam getExam() {
		if(m_examCopies.size()==0) return null;
		return m_examCopies.get(0).getExam();
	}
	
	/**
	 * this function will be responsible for check witch students cheated and return the hashmap of these students
	 * this is a private method used in the constructor if examReport to genereate these potential cheating students 
	 * @return hashmap of potential cheating students
	 */
	private HashMap<Student, Integer> findCheaters() {
		return null;
	}
}