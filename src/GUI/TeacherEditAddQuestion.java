package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

public class TeacherEditAddQuestion implements ControlledScreen, Initializable {
	
	Question question=null;
	
	@FXML Label questionID;
	@FXML TextArea questionString;
	@FXML ToggleGroup answers;
	@FXML RadioButton answer1;
	@FXML RadioButton answer2;
	@FXML RadioButton answer3;
	@FXML RadioButton answer4;
	@FXML ComboBox<String> fields;
	@FXML VBox courseVbox;
	@FXML Button backB;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(656);
		Globals.primaryStage.setWidth(553);
	}

	public void setQuestion(Question q) {
		question=q;
		System.out.println("Change Question to "+ q);
	}
	
	@FXML
	public void backToMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
}
