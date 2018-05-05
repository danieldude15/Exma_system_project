package prototype;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;


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
	
	public void initialize() {
		aRadio.setToggleGroup(group);
		bRadio.setToggleGroup(group);
		cRadio.setToggleGroup(group);
		dRadio.setToggleGroup(group);
	}
	
	public void excecudeUpdateCorrectAnswer(ActionEvent event) {
		try {
			DBMain sql = new DBMain();
			int index=-1;
			if(aRadio.isSelected())index=1;
			if(bRadio.isSelected())index=2;
			if(cRadio.isSelected())index=3;
			if(dRadio.isSelected())index=4;
			if(sql.updateCorrectAnswer(q2push.getID(), index)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Update Question Successfull");
				alert.setHeaderText(null);
				alert.setContentText("QuestionID:"+q2push.getID()+"\nWas updated with answer: "+index);

				alert.showAndWait();
			}
		} catch (Exception e) {
			System.out.println("shit!");
			return;
		}
	}
	
	public void backToSelectingQuestionGUI(ActionEvent event) {
		try {
			backB.getScene().setRoot(FXMLLoader.load(getClass().getResource("/prototype/SelectQuestion.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
