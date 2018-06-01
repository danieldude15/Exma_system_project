package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import logic.Globals;
import ocsf.client.ClientGlobals;

public class TeacherTimeChangeRequest implements Initializable, ControlledScreen{

	@FXML TextField SelectNewTime;
	@FXML TextField RequestExplenation;
	@FXML Label Errortime;
	@FXML Label Errorrequest;
	@Override
	public void runOnScreenChange() 
	{
		Globals.primaryStage.setHeight(700); 
		Globals.primaryStage.setWidth(650);	
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
	    if(RequestExplenation.getText().isEmpty())
		{
			 Errorrequest.setVisible(true);
		}
	    else
		{
			//create time change request
		}
	}

	@FXML
	public void CancelButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}



}





