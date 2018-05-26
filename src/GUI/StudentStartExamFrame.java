package GUI;

import java.awt.TextField;
import java.awt.event.ActionEvent;

import Controllers.ControlledScreen;
import javafx.fxml.FXML;
import logic.Globals;
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
		
		
	}
	
	public void BackButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
	
	
	
}
