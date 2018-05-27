package GUI;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import logic.Globals;
import ocsf.client.ClientGlobals;

public class StudentStartExamFrame implements ControlledScreen{

	@FXML TextField examCode;
	@FXML TextField studentId;
	

	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		
		
	}
	@FXML
	public void StartExamButtonPressed(ActionEvent event)
	{
		boolean flag=false;
		
		int id=Integer.parseInt(studentId.getText());
		if(id==ClientGlobals.client.getUser().getID())
		{
			
		}
		
	}
	
	@FXML
	public void HomeButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
	
	
	
}
