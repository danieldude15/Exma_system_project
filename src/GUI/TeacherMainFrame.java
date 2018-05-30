package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import logic.CompletedExam;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;


public class TeacherMainFrame implements Initializable,ControlledScreen {
	ArrayList<CompletedExam> TeacherCExams;
	
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button generateRB;
	@FXML Button checkExamB;
	@FXML ListView ActiveExamList;
	@FXML ListView CompletedExamList;
	@FXML Tab myInfoTab;
	@FXML TabPane infoTabPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(750);
		Globals.primaryStage.setWidth(820);

		TeacherCExams=SolvedExamController.getCompletedExams((Teacher) ClientGlobals.client.getUser());
		ArrayList<String> al = new ArrayList<String>();	
		for (CompletedExam ce : TeacherCExams) {
			al.add("QuestionID: "+ ce.getExam().examIdToString() + " Code:" + ce.getCode() + " Date <DATE>");
		}
		
		CompletedExamList.getItems().clear();
		ObservableList<String> list = FXCollections.observableArrayList(al);
		CompletedExamList.setItems(list);
	}
	
	@FXML
	public void gotToManageQuestions(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
	
	@FXML
	public void goToManageExams(ActionEvent event) {
		
	}
	
	@FXML
	public void goToInitiateExam(ActionEvent event) {
		
	}
	
	@FXML
	public void lockExamClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void requestTimeChangeClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void goToGenerateReportClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void goToCheckExams(ActionEvent event) {
		
	}
	
	@FXML
	public void completeExamsListViewClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void activeExamsListViewClicked(ActionEvent event) {
		
	}
	
	@FXML
	public void logout(ActionEvent event) {
		UserController.logout();
	}

}
