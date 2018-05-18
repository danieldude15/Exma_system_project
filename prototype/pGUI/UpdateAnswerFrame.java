package pGUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.pControlledScreen;
import Controllers.pQuestionsController;
import Controllers.pScreensController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pClient.PrototypeClient;
import pClient.PrototypeClientApp;
import pClient.pClientGlobals;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pLogic.pQuestion;


public class UpdateAnswerFrame implements Initializable, pControlledScreen{
	pScreensController myController;
	pQuestionsController questionsController;
	
	private pQuestion q2push = null;
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
		int index=-1;
		if(aRadio.isSelected())index=1;
		if(bRadio.isSelected())index=2;
		if(cRadio.isSelected())index=3;
		if(dRadio.isSelected())index=4;
		q2push.setCorrectAnswerIndex(index);
		questionsController.updateQuestionIndex(q2push);
		
	}
	
	public void backToSelectingQuestionGUI(ActionEvent event) {
		q2push=null;
		myController.setScreen(pClientGlobals.SelectQuestionScreenID);
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
		questionsController = new pQuestionsController();
	}

	@Override
	public void runOnScreenChange() {
		if (q2push!=null) {
			questionID.setText("Question ID:"+Integer.toString(q2push.getID()));
			autherName.setText("Auther ID:"+Integer.toString(q2push.getAutherID()));
			questionString.setText(q2push.getQuestionString());
			aRadio.setText(q2push.getAnswer(0));
			bRadio.setText(q2push.getAnswer(1));
			cRadio.setText(q2push.getAnswer(2));
			dRadio.setText(q2push.getAnswer(3));	
			int correct = q2push.getCorrectAnswerIndex();
			if (correct == 1) aRadio.setSelected(true);
			if (correct == 2) bRadio.setSelected(true);
			if (correct == 3) cRadio.setSelected(true);
			if (correct == 4) dRadio.setSelected(true);
		} else {
			System.out.println("selectedQuestion is null? " + q2push);
			this.backToSelectingQuestionGUI(null);
		}
	}

	public void setQuestion(pQuestion q) {
		q2push=q;
	}
	
	public pQuestion getQuestion() {
		return q2push;
	}
	
}
