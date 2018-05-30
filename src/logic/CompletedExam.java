package logic;

import java.sql.Date;
import java.util.ArrayList;

public class CompletedExam extends ActiveExam {
	
	private ArrayList<SolvedExam> solvedExams = null;
	
	public CompletedExam(String code, int type, String dayActivated, Teacher activator,ArrayList<SolvedExam> solvedExams) {
		super(code, type, dayActivated, null, activator);
		this.solvedExams=solvedExams;
	}

	@SuppressWarnings("unchecked")
	public CompletedExam(CompletedExam e) {
		super(e);
		if (e.getSolvedExams()!=null)
			this.solvedExams=(ArrayList<SolvedExam>) e.getSolvedExams().clone();
	}

	@Override
	public Exam getExam() {
		if (solvedExams!=null)
			return solvedExams.get(0).getExam();
		return null;
	}

	public ArrayList<SolvedExam> getSolvedExams() {
		return solvedExams;
	}

	@Override
	public String toString() {
		String examType;
		if (getType()==1) examType = "Computerized";
		else examType = "Manual";
		if (solvedExams.size()!=0)
			return new String(String.format("Activated On: %s \nCode: %s \nID: %s \nType: %s", getDate(),getCode(),solvedExams.get(0).examIdToString() ,examType));
		else 
			return new String(String.format("Activated On: %s \nCode: %s \nID: N/A \nType: %s", getDate(),getCode(),examType));
	}

	public void setSolvedExams(ArrayList<SolvedExam> se) {
		solvedExams =  se;
	}
	
	
}
