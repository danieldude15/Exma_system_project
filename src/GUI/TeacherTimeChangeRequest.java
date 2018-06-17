package GUI;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.TimeChangeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.ActiveExam;
import logic.Globals;
import logic.Teacher;
import logic.TimeChangeRequest;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ResourceBundle;

public class TeacherTimeChangeRequest implements Initializable, ControlledScreen{

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
	private void SubmitRequest (ActionEvent event)
	{
		//If some of the fields is empty, then the teacher get an error.
		if(SelectNewTime.getText().isEmpty()) {
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

	@FXML
	public void CancelButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	public void SetActiveExam(ActiveExam selectedItem) {
		activeexamselect=selectedItem;
		
	}



}





