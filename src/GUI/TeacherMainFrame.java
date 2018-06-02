package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import logic.ActiveExam;
import logic.CompletedExam;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;


public class TeacherMainFrame implements Initializable,ControlledScreen {
	private ArrayList<CompletedExam> TeacherCExams;
	private ArrayList<ActiveExam> TeacherAExams;
	
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button generateRB;
	@FXML Button checkExamB;
	@FXML ListView<ActiveExam> ActiveExamsList;
	@FXML ListView<CompletedExam> CompletedExamList;
	@FXML Tab myInfoTab;
	@FXML TabPane infoTabPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(750);
		Globals.primaryStage.setWidth(820);

		TeacherCExams=SolvedExamController.getCompletedExams((Teacher) ClientGlobals.client.getUser());
		ArrayList<String> al = new ArrayList<String>();	
		for (CompletedExam ce : TeacherCExams) {
			al.add("ExamID: "+ ce.getExam().examIdToString() + " Code:" + ce.getCode() + " Date <DATE>");
		}
		
		CompletedExamList.getItems().clear();
		ObservableList<CompletedExam> list = FXCollections.observableArrayList(TeacherCExams);
		CompletedExamList.setItems(list);
		
		TeacherAExams=ActiveExamController.getTeachersActiveExams((Teacher) ClientGlobals.client.getUser());
		al = new ArrayList<String>();	
		for (ActiveExam ae : TeacherAExams) {
			al.add("ExamID: "+ ae.getExam().examIdToString() + " Code:" + ae.getCode() + " Date <DATE>");
		}
		
		ActiveExamsList.getItems().clear();
		ObservableList<ActiveExam> list2 = FXCollections.observableArrayList(TeacherAExams);
		ActiveExamsList.setItems(list2);
	}
	
	@FXML public void gotToManageQuestions(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
	
	@FXML
	public void goToManageExams(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherBuildNewExamID);
		
	}
	
	@FXML public void goToInitiateExam(ActionEvent event) {
		
	}
	
	@FXML public void lockExamClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void requestTimeChangeClicked(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.TeacherTimeChangeRequestID);
	}
	
	@FXML public void goToGenerateReportClicked(ActionEvent event) {
		
	}
	
	@FXML public void goToCheckExams(ActionEvent event) {
		if(CompletedExamList.getSelectionModel().getSelectedItem()==null) return;
		String selectedExam = CompletedExamList.getSelectionModel().getSelectedItem().toString();
		System.out.println(selectedExam);
		String selectedExamid = selectedExam.split(" ")[6];
		for (CompletedExam ce:TeacherCExams) {
			if(ce.getExam().examIdToString().equals(selectedExamid)) {
				TeacherCheckExams teacherCheckExams = (TeacherCheckExams) Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamsID);
				teacherCheckExams.setCompletedExams(ce);
				Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamsID);
				break;
			}
		}
	}
	
	@FXML
	public void completeExamsListViewClicked(MouseEvent event) {
		ActiveExamsList.getSelectionModel().clearSelection();
	}
	
	@FXML
	public void activeExamsListViewClicked(MouseEvent event) {
		CompletedExamList.getSelectionModel().clearSelection();
	}
	
	@FXML
	public void logout(ActionEvent event) {
		UserController.logout();
	}

}
