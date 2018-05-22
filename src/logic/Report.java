package logic;

import java.util.ArrayList;

public abstract class Report {
	
	/* Class Fields Start */
	
	private int m_ID;
	private ArrayList<Integer> m_examGrades;
	private float m_median;
	private float m_average;
	private float m_standardDiviation;
	
	/* Class Fields End */
	
	/* Constructors Start */

	public Report(int ID, ArrayList<Integer> examGrades){
		m_ID = ID;
		m_examGrades = examGrades;
		m_median = calculateMedian(examGrades);
		m_average = calculateAverage(examGrades);
		m_standardDiviation = calculateStandardDiviation(examGrades);
	}

	public Report(Report report) {
		m_ID = report.m_ID;
		m_examGrades = new ArrayList<>(report.m_examGrades);
		m_median = report.m_median;
		m_average = report.m_average;
		m_standardDiviation = report.m_standardDiviation;
	}
	
	/* Constructors End */
	
	/* Getters and Setters Start */
	
	public float get_Median() {
		return m_median;
	}

	public void set_Median(float m_median) {
		this.m_median = m_median;
	}

	public float get_Average() {
		return m_average;
	}

	public void set_Average(float m_average) {
		this.m_average = m_average;
	}

	public float get_StandardDiviation() {
		return m_standardDiviation;
	}

	public void set_StandardDiviation(float m_standardDiviation) {
		this.m_standardDiviation = m_standardDiviation;
	}
	
	/* Getters and Setters End */
	
	/* Calculators Start */ 
	
	private float calculateAverage(ArrayList<Integer> grades) {
		
		float average = 0;
		
		for (Integer grade: grades) {
			average += grade;
		}
		
		average = average / grades.size();
		
		return average;
		
	}
	
	private float calculateMedian(ArrayList<Integer> grades) {
		
		float median = 0;
		
		if((grades.size() % 2) == 0) {
			
			median = (float) grades.get(grades.size() / 2);
			median = median + (float) grades.get((grades.size() / 2) + 1);
			median = median / 2;
			
		}else {
			median = (float) grades.get(grades.size() + 1 / 2);
		}
		
		return median;
	}
	
	private float calculateStandardDiviation(ArrayList<Integer> grades) {
		
		float standardDiviation = 0;
		
		float mean = m_average;
		
		for (Integer grade: grades) {
			
			standardDiviation += Math.pow((grade - mean), 2);	
		}
		
		standardDiviation = standardDiviation / (grades.size() - 1);
		
		standardDiviation = (float) Math.sqrt(standardDiviation);
		
		return standardDiviation;
	}
	
	/* Calculators End */
}
