package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
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
import ocsf.client.ClientGlobals;

public class TeacherCheckExams implements ControlledScreen, Initializable {

	CompletedExam completedExam;
	
	@FXML Label examid;
	@FXML Label participated;
	@FXML Label submited;
	@FXML Label fialToSubmitStudents;
	@FXML Label checkOutOf;
	@FXML ListView<SolvedExam> SolvedExamList;
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
		
		SolvedExamList.getItems().clear();
		ObservableList<SolvedExam> list = FXCollections.observableArrayList(completedExam.getSolvedExams());
		SolvedExamList.setItems(list);
		
	}
	
	@FXML public void solvedExamsListViewClicked(MouseEvent event) {
		
	}
	
	@FXML public void approvedExamClicked(ActionEvent event) {
		if (SolvedExamList.getSelectionModel().getSelectedItem()!=null) {
			((TeacherCheckExamFrame)Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamID)).setSolvedExam(SolvedExamList.getSelectionModel().getSelectedItem());
			Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamID);
		}
	}

	
	@FXML public void checkExamClicked(ActionEvent event) {
		if (SolvedExamList.getSelectionModel().getSelectedItem()!=null) {
			((TeacherCheckExamFrame)Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamID)).setSolvedExam(SolvedExamList.getSelectionModel().getSelectedItem());
			Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamID);
		}
	}
	
	@FXML void backToHome(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	public void setCompletedExams(CompletedExam ce) {
		this.completedExam = ce;
	}

}
