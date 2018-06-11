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
import logic.ExamReport;
import logic.Globals;
import logic.SolvedExam;
import ocsf.client.ClientGlobals;

public class TeacherCheckExams implements ControlledScreen, Initializable {

	ExamReport completedExam;
	SolvedExam selectedExam;
	
	@FXML Label examid;
	@FXML Label participated;
	@FXML Label submited;
	@FXML Label failToSubmitStudents;
	@FXML Label checkOutOf;
	@FXML ListView<SolvedExam> SolvedExamList;
	@FXML Button checkB;
	@FXML Button approveB;
	@FXML Button backB;
	
	@Override public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(500);
		Globals.primaryStage.setWidth(670);
		examid.setText(completedExam.getExam().examIdToString());
		participated.setText(Integer.toString(completedExam.getParticipatingStudent()));
		submited.setText(Integer.toString(completedExam.getSubmittedStudents()));
		failToSubmitStudents.setText(Integer.toString(completedExam.getNotInTimeStudents()));
		int counter=0;
		for(SolvedExam se : completedExam.getSolvedExams()) {
			if(se.isTeacherApproved())
				counter++;
		}
		int total = completedExam.getSolvedExams().size();
		checkOutOf.setText(counter+"/"+total);
		SolvedExamList.getItems().clear();
		ObservableList<SolvedExam> list = FXCollections.observableArrayList(completedExam.getSolvedExams());
		SolvedExamList.setItems(list);
		
	}
	
	@FXML public void solvedExamsListViewClicked(MouseEvent event) {
		if (SolvedExamList.getSelectionModel().getSelectedItem()!=null) {
			selectedExam = SolvedExamList.getSelectionModel().getSelectedItem();
		}
	}
	
	@FXML public void approvedExamClicked(ActionEvent event) {
		
	}

	
	@FXML public void checkExamClicked(ActionEvent event) {
		if (SolvedExamList.getSelectionModel().getSelectedItem()!=null) {
			((TeacherCheckSolvedExamFrame)Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamID)).setSolvedExam(selectedExam);
			Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamID);
		}
	}
	
	@FXML void backToHome(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	public void setCompletedExams(ExamReport ce) {
		this.completedExam = ce;
	}

}
