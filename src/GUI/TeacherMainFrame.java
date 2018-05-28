package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.Globals;
import logic.iMessage;
import ocsf.client.ClientGlobals;


public class TeacherMainFrame implements Initializable,ControlledScreen {
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button generateRB;
	@FXML Button checkExamB;
	@FXML ListView<String> ActiveExamList;
	@FXML ListView<String> CompletedExamList;
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
