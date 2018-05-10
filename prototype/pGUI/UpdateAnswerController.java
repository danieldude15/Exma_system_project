package pGUI;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pLogic.Question;
import pSQLTools.client.PrototypeClient;


public class UpdateAnswerController {
	private Question q2push;
	
	ObservableList<String> list;
	@FXML private Label questionID; 
	@FXML private Label questionString; 
	@FXML private Label autherName; 
	@FXML private RadioButton aRadio;
	@FXML private RadioButton bRadio;
	@FXML private RadioButton cRadio;
	@FXML private RadioButton dRadio;
	@FXML private Button backB;
	@FXML private Button updateB;

	private ToggleGroup group = new ToggleGroup();
	private PrototypeClient client;
	
	public void initialize() {
		client = new PrototypeClient("localhost", 12345);
		try {
			client.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			this.backToSelectingQuestionGUI(null);
		}
		aRadio.setToggleGroup(group);
		bRadio.setToggleGroup(group);
		cRadio.setToggleGroup(group);
		dRadio.setToggleGroup(group);
	}
	
	public void excecudeUpdateCorrectAnswer(ActionEvent event) {
		try {

			int index=-1;
			if(aRadio.isSelected())index=1;
			if(bRadio.isSelected())index=2;
			if(cRadio.isSelected())index=3;
			if(dRadio.isSelected())index=4;
			client.sendToServer("UpdateQuestionAnswer "+q2push.getID()+" "+index);
			while (!client.msgSent) {
				Thread.sleep(10);
			}
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
		try {
			backB.getScene().setRoot(FXMLLoader.load(getClass().getResource("SelectQuestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadQuestion(Question q) {
		if(q==null) {
			System.out.println("No Question Selected... try again");
			return;
		}
		q2push=q;
		questionID.setText(questionID.getText()+Integer.toString(q.getID()));
		autherName.setText(autherName.getText()+Integer.toString(q.getAutherID()));
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
	}
	
	@FXML 
	public void handleRadioClick(ActionEvent event) {

	}
}
