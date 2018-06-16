package GUI;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
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
		if (selectedExam!=null) {
			selectedExam.setTeacherApproved(true);
			if (SolvedExamController.updateSolvedExam(selectedExam)>0) {
				//successfull insertion
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exam Check Is Updated Successfully");
				alert.setHeaderText(null);
				alert.setContentText("The exam was updated into the system.");
				alert.show();
				SolvedExamList.getItems().clear();
				ObservableList<SolvedExam> list = FXCollections.observableArrayList(completedExam.getSolvedExams());
				SolvedExamList.setItems(list);
			}
		}
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
