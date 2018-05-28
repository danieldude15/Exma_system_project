package GUI;

import java.util.ArrayList;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import logic.ActiveExam;
import logic.Globals;
import ocsf.client.ClientGlobals;

public class StudentStartExamFrame implements ControlledScreen{

	@FXML TextField examCode;
	@FXML TextField studentId;
	@FXML Label examCodeError;
	@FXML Label idError;
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		examCode.clear();
		studentId.clear();
		
	}
	/**
	 * When student pressed on start exam button and fields are filled correct the method send the active exam to StudentSolvesExamFrame class.
	 */
	@FXML
	public void StartExamButtonPressed(ActionEvent event)
	{
		Alert alert;
		ActiveExam activeE=null;
		boolean activeExamExist=false;
		String sid=Integer.toString(ClientGlobals.client.getUser().getID());
		//If some of the fields is empty, then the student get an error.
		if(studentId.getText().isEmpty() || examCode.getText().isEmpty())
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Start exam Failed!");
			alert.setHeaderText(null);
			alert.setContentText("You have missing fields!, please fill those fields and try again..");
			alert.showAndWait();
		}
		//Two fields are filled
		else
		{
			ArrayList<ActiveExam> allActiveExams=ActiveExamController.GetAllActiveExams();
			for (ActiveExam e:allActiveExams) {
				
				if(e.getCode().equals(examCode.getText()))
				{
					activeExamExist=true;
					activeE=new ActiveExam(e); 
					
				}
				
			}
			if(activeExamExist==false)
			{
				examCodeError.setText("**Invalid Exam code!");
				examCodeError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			}
			//If student entered wrong id then flag=false and student gets an error message.
			if( !(studentId.getText().equals(sid)) ) 
			{
				idError.setText("**Invalid user Id!");
				idError.setTextFill(javafx.scene.paint.Paint.valueOf("#FF0000"));
			}
			//Student filled Two correct fields 
			else if(activeExamExist==true)
			{
				//Exam is computerized, then student can start solve it.
				if(activeE.getType().equals("computerized"))
				{
					StudentSolvesExamFrame studentsolvesExam = (StudentSolvesExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentSolvesExamID);
					studentsolvesExam.SetActiveExam(activeE);
					Globals.mainContainer.setScreen(ClientGlobals.StudentSolvesExamID);
				}
				//Exam is manual, then word file is download first and then student can solve it.
				else
				{
					//download word file and solve exam from it.
				}
			}
			
				
		}
	}
	
	/**
	 *When student pressed on home button he goes back to his main window. 
	 */
	@FXML
	public void HomeButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentMainID);
	}
	
	
	
}
