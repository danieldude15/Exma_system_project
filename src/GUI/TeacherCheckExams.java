package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.CompletedExam;
import logic.Globals;
import logic.SolvedExam;
import logic.Teacher;
import ocsf.client.ClientGlobals;

public class TeacherCheckExams implements ControlledScreen, Initializable {

	CompletedExam completedExam;
	
	@FXML Label examid;
	@FXML Label participated;
	@FXML Label submited;
	@FXML Label fialToSubmitStudents;
	@FXML Label checkOutOf;
	@FXML ListView SolvedExamList;
	@FXML Button checkB;
	@FXML Button approveB;
	@FXML Button backB;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(900);
		Globals.primaryStage.setWidth(670);
		
		ArrayList<String> al = new ArrayList<String>();	
		for (SolvedExam se : completedExam.getSolvedExams()) {
			al.add("Student ID: " + se.getStudent().getID() + " | "
					+ "Name: " + se.getStudent().getName() + " | "
					+ "Score: " + se.getScore() + " | "
					+ "Completed: " + se.getCompletedTimeInMinutes());
		}
		
		SolvedExamList.getItems().clear();
		ObservableList<String> list = FXCollections.observableArrayList(al);
		SolvedExamList.setItems(list);
		
	}
	
	@FXML 
	public void solvedExamsListViewClicked(MouseEvent event) {
		
	}
	
	@FXML void backToHome(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	public void setCompletedExams(CompletedExam ce) {
		this.completedExam = ce;
	}

}
