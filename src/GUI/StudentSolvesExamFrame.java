package GUI;

import Controllers.ControlledScreen;
import logic.ActiveExam;
import logic.Globals;

public class StudentSolvesExamFrame implements ControlledScreen{

	private ActiveExam activeExam;
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);
		
		activeExam=this.GetActiveExam();
		
		
		
		//Active exam is manual.
		if(activeExam.getType()==0)
		{
			CreateWordFile(activeExam);
			/*Here:
			1.Create word file
			2.Download word file
			3.Initialize the timer!
			4.Submit method(check if exam is not locked yet).
			5.Remove from active exam HashMap on server.
			6.Build SolvedExam object.
			7.Upload to database SolvedExam(word file?).
			 /*/
		}
		//Active exam is computerized.
		else
		{
			StartSolveExam(activeExam);
			/*Here:
			1.Create exam on screen with the questions(Including initialize the timer).
			2.Submit method(check if exam is not locked yet).
			3.Remove from active exam HashMap on server.
			4.Build SolvedExam object.
			5.Upload to database SolvedExam.
			/*/
		}
	}

	
	private void StartSolveExam(ActiveExam active) {
		// TODO Auto-generated method stub
		
	}


	private void CreateWordFile(ActiveExam active) {
		// TODO Auto-generated method stub
		
	}


	public void SetActiveExam(ActiveExam activeE) {
		// TODO Auto-generated method stub
		this.activeExam=activeE;
		
	}

	public ActiveExam GetActiveExam()
	{
		return this.activeExam;
	}

}
