package logic;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;


public class ExamReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2557792967631535481L;
	int reportid;
	int meadian;
	int avg;
	int diviation;
	int submitedcount;
	int startedcount;
	int failedsubmitstudentcount;
	int actualdurationofexam;
	Date dateinitiated;
	
	ArrayList<SolvedExam> m_examCopies;
	HashMap<Student,Integer> m_cheatingStudents;
	
	public ExamReport(int ID, int reportid, int meadian, int avg, int diviation,
			int submitedcount, int startedcount, int failedsubmitstudentcount, int actualdurationofexam,
			Date dateinitiated, ArrayList<SolvedExam> m_examCopies, HashMap<Student, Integer> m_cheatingStudents) {
		this.reportid = ID;
		this.reportid = reportid;
		this.meadian = meadian;
		this.avg = avg;
		this.diviation = diviation;
		this.submitedcount = submitedcount;
		this.startedcount = startedcount;
		this.failedsubmitstudentcount = failedsubmitstudentcount;
		this.actualdurationofexam = actualdurationofexam;
		this.dateinitiated = dateinitiated;
		this.m_examCopies = m_examCopies;
		this.m_cheatingStudents = m_cheatingStudents;
	}
	
	public ExamReport(ExamReport report) {
		this.reportid = report.getReportid();
		this.meadian = report.getMeadian();
		this.avg = report.getAvg();
		this.diviation = report.getDiviation();
		this.submitedcount = report.getSubmitedcount();
		this.startedcount = report.getStartedcount();
		this.failedsubmitstudentcount = report.getFailedsubmitstudentcount();
		this.actualdurationofexam = report.getActualdurationofexam();
		this.dateinitiated = report.getDateinitiated();
		this.m_examCopies = report.getM_examCopies();
		this.m_cheatingStudents = report.getM_cheatingStudents();
	}

	public int getReportid() {
		return reportid;
	}

	public void setReportid(int reportid) {
		this.reportid = reportid;
	}

	public int getMeadian() {
		return meadian;
	}

	public void setMeadian(int meadian) {
		this.meadian = meadian;
	}

	public int getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public int getDiviation() {
		return diviation;
	}

	public void setDiviation(int diviation) {
		this.diviation = diviation;
	}

	public int getSubmitedcount() {
		return submitedcount;
	}

	public void setSubmitedcount(int submitedcount) {
		this.submitedcount = submitedcount;
	}

	public int getStartedcount() {
		return startedcount;
	}

	public void setStartedcount(int startedcount) {
		this.startedcount = startedcount;
	}

	public int getFailedsubmitstudentcount() {
		return failedsubmitstudentcount;
	}

	public void setFailedsubmitstudentcount(int failedsubmitstudentcount) {
		this.failedsubmitstudentcount = failedsubmitstudentcount;
	}

	public int getActualdurationofexam() {
		return actualdurationofexam;
	}

	public void setActualdurationofexam(int actualdurationofexam) {
		this.actualdurationofexam = actualdurationofexam;
	}

	public Date getDateinitiated() {
		return dateinitiated;
	}

	public void setDateinitiated(Date dateinitiated) {
		this.dateinitiated = dateinitiated;
	}

	public ArrayList<SolvedExam> getM_examCopies() {
		return m_examCopies;
	}

	public void setM_examCopies(ArrayList<SolvedExam> m_examCopies) {
		this.m_examCopies = m_examCopies;
	}

	public HashMap<Student, Integer> getM_cheatingStudents() {
		return m_cheatingStudents;
	}

	public void setM_cheatingStudents(HashMap<Student, Integer> m_cheatingStudents) {
		this.m_cheatingStudents = m_cheatingStudents;
	}
	
	public Exam getExam() {
		if(m_examCopies.size()==0) return null;
		return m_examCopies.get(0).getExam();
	}
}