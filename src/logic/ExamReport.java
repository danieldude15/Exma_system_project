package logic;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamReport extends Report {

	ArrayList<SolvedExam> m_examCopies;
	
	HashMap<Student,Integer> m_cheatingStudents;
	
	public ExamReport(int ID, ArrayList<Integer> examGrades) {
		super(ID, examGrades);
		m_cheatingStudents = new HashMap<Student,Integer>();
	}

	public ExamReport(ExamReport report) {
		super(report);
		m_cheatingStudents = report.m_cheatingStudents;
		m_examCopies = report.m_examCopies;
	}
}