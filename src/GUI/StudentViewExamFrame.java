package GUI;

import Controllers.ControlledScreen;
import logic.SolvedExam;

public class StudentViewExamFrame implements ControlledScreen {

	private SolvedExam solvedExam;

	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void SetSolvedExam(SolvedExam s)
	{
		solvedExam=s;
	}
	public SolvedExam GetSolvedExam()
	{
		return this.solvedExam;
	}
}
