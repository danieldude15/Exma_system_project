package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.Globals;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class StudentViewExamFrame implements ControlledScreen {

	private SolvedExam solvedExam;
	@FXML Label courseName;
	@FXML Label grade;
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		SolvedExam solved=this.GetSolvedExam();
		courseName.setText(solved.getCourse().getName());
		String solvedExamGrade=Integer.toString(solved.getScore());
		grade.setText(solvedExamGrade);
		
		
		
	}
	
	
	public void SetSolvedExam(SolvedExam s)
	{
		this.solvedExam=s;
	}
	public SolvedExam GetSolvedExam()
	{
		return this.solvedExam;
	}
	
	
	public void StudentPressedBackButton(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
}
