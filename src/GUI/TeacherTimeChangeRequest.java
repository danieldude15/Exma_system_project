package GUI;


import Controllers.ControlledScreen;
import Controllers.TimeChangeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.ActiveExam;
import logic.Globals;
import logic.Teacher;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;


public class TeacherTimeChangeRequest implements ControlledScreen{

	@FXML TextField SelectNewTime;
	@FXML TextField RequestExplenation;
	@FXML Label Errortime;
	@FXML Label Errorrequest;
	ActiveExam activeexamselect;
	
	@Override
	public void runOnScreenChange(){

		SelectNewTime.setText("");
		RequestExplenation.setText("");
		Errorrequest.setVisible(false);
		Errortime.setVisible(false);
	}
/**
 * When you click the submit button we check if the request contains an explanation and amount of time
 * @param event
 */
	@FXML
	private void SubmitRequest (ActionEvent event)
	{
		//If some of the fields is empty, then the teacher get an error.
		if(SelectNewTime.getText().isEmpty()&& SelectNewTime.getText().matches(("[0-9]+"))) {
			Errortime.setVisible(true);
		}
		else if(RequestExplenation.getText().isEmpty())
		{
			 Errorrequest.setVisible(true);
		}
	    else
	    {
	    	TimeChangeRequest tc=new TimeChangeRequest((Long.valueOf(SelectNewTime.getText())),RequestExplenation.getText(),false,activeexamselect,(Teacher) ClientGlobals.client.getUser());
	    	System.out.println(tc);
	    	TimeChangeController.requestNewTimeChangeForActiveExam(tc);
	    	Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	    }
	}
	/**
	 * Return to the previous window when you press the Back button
	 * @param event
	 */
	@FXML
	public void CancelButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}
	/**
	 * When we want to go this windows we send the specific ActiveExam
	 * @param ActiveExam
	 */
	public void SetActiveExam(ActiveExam selectedItem) {
		activeexamselect=selectedItem;
		
	}



}





