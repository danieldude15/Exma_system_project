package GUI;

import Controllers.ActiveExamController;
import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.ActiveExam;
import logic.ExamReport;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

import java.util.ArrayList;


public class TeacherMainFrame implements ControlledScreen {
	private ArrayList<ExamReport> TeacherCExams;
	private ArrayList<ActiveExam> TeacherAExams;
	
	@FXML Button manageQuestionsB;
	@FXML Button manageExamsB;
	@FXML Button initiateExamB;
	@FXML Button lockExamB;
	@FXML Button requestTCB;
	@FXML Button checkExamB;
	@FXML ListView<ActiveExam> ActiveExamsList;
	@FXML ListView<ExamReport> CompletedExamList;
	@FXML Label welcome;
	@FXML Label username;
	@FXML Label userid;
	@FXML Pane userImage;
	@FXML Label studentsInCourse;
		

	@Override public void runOnScreenChange() {
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
		Globals.mainContainer.setScreen(ClientGlobals.TeacherInitializeExamID);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML public void lockExamClicked(ActionEvent event) {
		ActiveExam selected = ActiveExamsList.getSelectionModel().getSelectedItem();
		if (selected!=null) {
			ActiveExamController.lockExam(selected);
			Globals.popUp(AlertType.INFORMATION,"Locked Active Exam","this may take some time for all the students that \nare still in the exam to send their exam to the system.\nIn 10 seconds the list of active and completed exams will refresh and you will be able to check the active exam that you locked");
    		Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(10),
                      new EventHandler() {
                        // KeyFrame event handler
                        public void handle(Event event) {
                        	System.out.println("refreshing Teachers ListViews");
                        	updateActiveExamListView();
                    		updateCompletedExamListView();
                            timeline.stop();
                        }
                      }));
            timeline.playFromStart();
    		
		} else {
			Globals.popUp(AlertType.ERROR,"No Active Exam Selected","You must select an Active Exam from the list to lock an Active Exam");
		}
	}
	
	@FXML public void requestTimeChangeClicked(ActionEvent event) {
		if(ActiveExamsList.getSelectionModel().getSelectedItem()!=null) { 
			((TeacherTimeChangeRequest)Globals.mainContainer.getController(ClientGlobals.TeacherTimeChangeRequestID)).SetActiveExam((ActiveExam) ActiveExamsList.getSelectionModel().getSelectedItem());
			Globals.mainContainer.setScreen(ClientGlobals.TeacherTimeChangeRequestID);
		} else {
			Globals.popUp(AlertType.ERROR,"No Active Exam Selected","You must select an Active Exam from the list to make a request for it");
		}
	}
	
	@FXML public void goToCheckExams(ActionEvent event) {
		ExamReport selectedExam = CompletedExamList.getSelectionModel().getSelectedItem();
		if(CompletedExamList.getSelectionModel().getSelectedItem()==null) {
			Globals.popUp(AlertType.ERROR,"No Exam Selected","You must select an Exam from the list if you want to check its solved exams");
			return;
		}
		TeacherCheckExams teacherCheckExams = (TeacherCheckExams) Globals.mainContainer.getController(ClientGlobals.TeacherCheckExamsID);
		teacherCheckExams.setCompletedExams(selectedExam);
		Globals.mainContainer.setScreen(ClientGlobals.TeacherCheckExamsID);
	}
	
	@FXML public void completeExamsListViewClicked(MouseEvent event) {
		ActiveExamsList.getSelectionModel().clearSelection();
		studentsInCourse.setText("Students In Selected Course:");
	}
	
	@FXML public void activeExamsListViewClicked(MouseEvent event) {
		CompletedExamList.getSelectionModel().clearSelection();
		ActiveExam aExam = ActiveExamsList.getSelectionModel().getSelectedItem();
		if (aExam!=null)
			studentsInCourse.setText("Students In Selected Course:"+UserController.getStudentsInCourse(aExam.getCourse()).size());
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
