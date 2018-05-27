package GUI;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.fxml.FXML;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import ocsf.client.ClientGlobals;
import Controllers.ActiveExamController;
import Controllers.UserController;

public class StudentStartExamFrame implements ControlledScreen{

	@FXML TextField examCode;
	@FXML TextField studentId;
	

	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		
		
	}

	public void StartExamButtonPressed(ActionEvent event)
	{
		boolean flag=false;
		
		int id=Integer.parseInt(studentId.getText());
		if(id==ClientGlobals.client.getUser().getID())
		{
			
		}
		
	}
	
	public void BackButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
	
	
	
}
