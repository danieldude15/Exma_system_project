package pGUI;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pLogic.pQuestion;
import pSQLTools.client.PrototypeClient;


public class UpdateAnswerController implements Initializable, pControlledScreen{
	pScreensController myController;
	
	ObservableList<String> list;
	private pQuestion q2push;
	private ToggleGroup group = new ToggleGroup();
	
	@FXML private Label questionID; 
	@FXML private Label questionString; 
	@FXML private Label autherName; 
	@FXML private RadioButton aRadio;
	@FXML private RadioButton bRadio;
	@FXML private RadioButton cRadio;
	@FXML private RadioButton dRadio;
	@FXML private Button backB;
	@FXML private Button updateB;

	
	
	public void excecudeUpdateCorrectAnswer(ActionEvent event) {
		PrototypeClient client = gui_globals.client;
		try {
			int index=-1;
			if(aRadio.isSelected())index=1;
			if(bRadio.isSelected())index=2;
			if(cRadio.isSelected())index=3;
			if(dRadio.isSelected())index=4;
			q2push.setCorrectAnswerIndex(index);
			client.sendToServer("UpdateQuestionAnswer "+q2push.getID()+" "+index);
			while (!client.msgSent) {
				Thread.sleep(10);
			}
			client.msgSent=false;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update Question Successfull");
			alert.setHeaderText(null);
			alert.setContentText("QuestionID:"+q2push.getID()+"\nWas updated with answer: "+index);

			alert.showAndWait();
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			e.printStackTrace();
			return;
		}
		
	}
	
	public void backToSelectingQuestionGUI(ActionEvent event) {
		myController.setScreen(pType.SelectQuestionScreenID);
		gui_globals.selectedQuestion=null;
	}
	
	@FXML 
	public void handleRadioClick(ActionEvent event) {

	}

	@Override
	public void setScreenParent(pScreensController screenParent) {
		myController = screenParent;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		aRadio.setToggleGroup(group);
		bRadio.setToggleGroup(group);
		cRadio.setToggleGroup(group);
		dRadio.setToggleGroup(group);
	}

	@Override
	public void runOnScreenChange() {
		if (gui_globals.selectedQuestion!=null) {
			pQuestion q = gui_globals.selectedQuestion;
			q2push=q;
			questionID.setText("Question ID:"+Integer.toString(q.getID()));
			autherName.setText("Auther ID:"+Integer.toString(q.getAutherID()));
			questionString.setText(q.getQuestionString());
			aRadio.setText(q.getAnswer(0));
			bRadio.setText(q.getAnswer(1));
			cRadio.setText(q.getAnswer(2));
			dRadio.setText(q.getAnswer(3));	
			int correct = q.getCorrectAnswerIndex();
			if (correct == 1) aRadio.setSelected(true);
			if (correct == 2) bRadio.setSelected(true);
			if (correct == 3) cRadio.setSelected(true);
			if (correct == 4) dRadio.setSelected(true);
		} else {
			System.out.println("selectedQuestion is null? " +gui_globals.selectedQuestion);
			this.backToSelectingQuestionGUI(null);
		}
	}
	
}
