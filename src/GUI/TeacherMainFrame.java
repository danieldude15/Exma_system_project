package GUI;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import logic.ActiveExam;
import logic.ExamReport;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TeacherMainFrame implements Initializable,ControlledScreen {
	private ArrayList<ExamReport> TeacherCExams;
	private ArrayList<ActiveExam> TeacherAExams;
	
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button generateRB;
	@FXML Button checkExamB;
	@FXML ListView<ActiveExam> ActiveExamsList;
	@FXML ListView<ExamReport> CompletedExamList;
	@FXML Label welcome;
	@FXML Label username;
	@FXML Label userid;
	@FXML Pane userImage;
	
	@Override public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(680);
		Globals.primaryStage.setWidth(820);

		updateCompletedExamListView();
		
		updateActiveExamListView();
		
		/*Get Teachers personal info from database and set it beneath the TabPane "My info" on window/*/
		Teacher t=(Teacher)ClientGlobals.client.getUser();
		welcome.setText("Wellcome: "+t.getName());
		username.setText("UserName: "+t.getUserName());
		userid.setText("UserID: "+t.getID());
		userImage.setStyle("-fx-background-image: url(\"resources/profile/"+t.getID()+".PNG\");"
						+ "-fx-background-size: 100px 100px;"
						+ "-fx-background-repeat: no-repeat;");

	}

	@FXML public void gotToManageQuestions(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
	
	@FXML public void goToManageExams(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageExamsID);
	}
	
	@FXML public void goToInitiateExam(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.InitializeExamID);
	}
	
	@FXML public void lockExamClicked(ActionEvent event) {
		if (ActiveExamsList.getSelectionModel().getSelectedItem()!=null) {
			int lockedUsers =  ActiveExamController.lockExam(ActiveExamsList.getSelectionModel().getSelectedItem());
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Locked Active Exam");
			alert.setHeaderText("");
    		alert.setContentText("You locked the exam while " + lockedUsers +" students where participating in it.");
    		alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("No Active Exam Selected");
			alert.setHeaderText("");
    		alert.setContentText("You must select an Active Exam from the list to lock an Active Exam");
    		alert.show();
		}
		updateActiveExamListView();
		updateCompletedExamListView();
		
	}
	
	@FXML public void requestTimeChangeClicked(ActionEvent event) {
		if(ActiveExamsList.getSelectionModel().getSelectedItem()!=null)
		{ ((TeacherTimeChangeRequest)Globals.mainContainer.getController(ClientGlobals.TeacherTimeChangeRequestID)).SetActiveExam((ActiveExam) ActiveExamsList.getSelectionModel().getSelectedItem());
		Globals.mainContainer.setScreen(ClientGlobals.TeacherTimeChangeRequestID);
		}
	}
	
	@FXML public void goToGenerateReportClicked(ActionEvent event) {
		
	}
	
	@FXML public void goToCheckExams(ActionEvent event) {
		if(CompletedExamList.getSelectionModel().getSelectedItem()==null) return;
		String selectedExam = CompletedExamList.getSelectionModel().getSelectedItem().toString();
		System.out.println(selectedExam);
		String selectedExamid = selectedExam.split(" ")[6];
		for (ExamReport ce:TeacherCExams) {
			if(ce.getExam().examIdToString().equals(selectedExamid)) {
				TeacherCheckExams teacherCheckExams = (TeacherCheckExams) Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamsID);
				teacherCheckExams.setCompletedExams(ce);
				Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamsID);
				break;
			}
		}
	}
	
	@FXML public void completeExamsListViewClicked(MouseEvent event) {
		ActiveExamsList.getSelectionModel().clearSelection();
	}
	
	@FXML public void activeExamsListViewClicked(MouseEvent event) {
		CompletedExamList.getSelectionModel().clearSelection();
	}
	
	@FXML public void logout(ActionEvent event) {
		UserController.logout();
	}

	private void updateActiveExamListView() {
		TeacherAExams=ActiveExamController.getTeachersActiveExams((Teacher) ClientGlobals.client.getUser());
		ActiveExamsList.getItems().clear();
		ObservableList<ActiveExam> list2 = FXCollections.observableArrayList(TeacherAExams);
		ActiveExamsList.setItems(list2);
	}
	
	private void updateCompletedExamListView() {
		TeacherCExams=SolvedExamController.getCompletedExams((Teacher) ClientGlobals.client.getUser());
		CompletedExamList.getItems().clear();
		ObservableList<ExamReport> list = FXCollections.observableArrayList(TeacherCExams);
		CompletedExamList.setItems(list);
		
	}
	
}
