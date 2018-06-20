package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logic.ExamReport;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import ocsf.client.ClientGlobals;

public class TeacherCheckExams implements ControlledScreen {

	ExamReport completedExam;
	SolvedExam selectedExam;
	
	@FXML Label examid;
	@FXML Label participated;
	@FXML Label submited;
	@FXML Label failToSubmitStudents;
	@FXML Label checkOutOf;
	@FXML Label studentInCourse;
	@FXML ListView<SolvedExam> SolvedExamList;
	@FXML ListView<String> cheatersList;
	@FXML Button checkB;
	@FXML Button approveB;
	@FXML Button backB;
	@FXML ImageView doneImage;
	
	@Override public void runOnScreenChange() {
		doneImage.setVisible(false);
		approveB.setDisable(false);
		examid.setText(completedExam.getExam().examIdToString());
		participated.setText(Integer.toString(completedExam.getParticipatingStudent()));
		submited.setText(Integer.toString(completedExam.getSubmittedStudents()));
		failToSubmitStudents.setText(Integer.toString(completedExam.getNotInTimeStudents()));
		int studentsInCourse = UserController.getStudentsInCourse(completedExam.getCourse()).size();
		studentInCourse.setText(studentsInCourse+"");
		
		HashMap<Student, Integer> cheaters = completedExam.getM_cheatingStudents();
		ArrayList<String> listViewOfStrings = new ArrayList<>();
		for(Student cheater: cheaters.keySet()) {
			listViewOfStrings.add(cheater.getName()+" - "+cheaters.get(cheater)+"% Matched");
		}
		ObservableList<String> list = FXCollections.observableArrayList(listViewOfStrings);
		cheatersList.setItems(list);
		int counter=0;
		for(SolvedExam se : completedExam.getSolvedExams()) {
			if(se.isTeacherApproved())
				counter++;
		}
		int total = completedExam.getSolvedExams().size();
		checkOutOf.setText(counter+"/"+studentsInCourse);
		
		if(counter==total && total==studentsInCourse) {
			doneImage.setVisible(true);
			approveB.setDisable(true);
		}
		SolvedExamList.getItems().clear();
		ObservableList<SolvedExam> list2 = FXCollections.observableArrayList(completedExam.getSolvedExams());
		SolvedExamList.setItems(list2);
		
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
				Globals.popUp(AlertType.INFORMATION,"Exam Check Is Updated Successfully","The exam was updated into the system.");
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

	@FXML void viewReportButtonClicked(ActionEvent event) {
		ViewReportFrame reportFrame = ((ViewReportFrame)Globals.mainContainer.getController(ClientGlobals.ViewReportID));
		reportFrame.setExamReport(completedExam);
		reportFrame.setMe(ViewReportFrame.user.Teacher);
		reportFrame.setWindowType(ViewReportFrame.type.EXAM);
		Globals.mainContainer.setScreen(ClientGlobals.ViewReportID);
	}
	
	public void setCompletedExams(ExamReport ce) {
		this.completedExam = ce;
	}

}
