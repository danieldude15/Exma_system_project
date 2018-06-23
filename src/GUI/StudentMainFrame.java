package GUI;


import java.util.ArrayList;
import java.util.HashMap;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import logic.Course;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import ocsf.client.ClientGlobals;

public class StudentMainFrame implements ControlledScreen{

	@FXML ListView<SolvedExam> solvedExamsList;
	@FXML Label welcome;
	@FXML Label username;
	@FXML Label userid;
	@FXML Pane userImage;
	@FXML ListView<Course> courseList;
	
	//HashMap<Key(String CourseName),Value(String SolvedExam id)>
	private HashMap<String,String> courseNameAndExamId=new HashMap<String,String>();

	

	
	@Override
	public void runOnScreenChange() {
		courseNameAndExamId.clear();
		solvedExamsList.getItems().clear();
		courseList.setItems(FXCollections.observableArrayList(CourseFieldController.getStudentCourses(ClientGlobals.client.getUser())));
		//Get all student solved exams from database and set it to the ListView field on window
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExamsByUser((Student)ClientGlobals.client.getUser());
		ArrayList<SolvedExam> myAprovedSolvedExam = new ArrayList<>();
		for(SolvedExam se: mySolvedExam) {
			if(se.isTeacherApproved())
				myAprovedSolvedExam.add(se);
		}
		ObservableList<SolvedExam> solveds = FXCollections.observableArrayList(myAprovedSolvedExam);
		solvedExamsList.setItems(solveds);
		
		setStudentInfo();
		
		
		
	}
	
	
	
	
	
	
	/**
	 * Sets student's personal info on the window screen.
	 */
	private void setStudentInfo()
	{
		Student s=(Student)ClientGlobals.client.getUser();
		welcome.setText("Welcome: "+s.getName());
		username.setText("UserName: "+s.getUserName());
		userid.setText("UserID: "+s.getID());
		userImage.setStyle("-fx-background-image: url(\"resources/profile/"+s.getID()+".PNG\");"
				+ "-fx-background-size: 100px 100px;"
				+ "-fx-background-repeat: no-repeat;");
	}
	
	
	
	
	/**
	 * In case that take exam button was pressed by the student,the start exam window opens.
	 */
	public void TakeExamButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentStartExamID);
	}
	
	
	
	/**
	 * In case that view exam button was pressed by the student the chosen solved exam opens.
	 */
	public void ViewExamButtonPressed(ActionEvent event)
	{
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExamsByUser((Student)ClientGlobals.client.getUser());
		StudentViewExamFrame studentViewExam = (StudentViewExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentViewExamID);
		//if student choose course solved exam to view from list.
		if(solvedExamsList.getSelectionModel().getSelectedItem()!=null)  {
			SolvedExam se = solvedExamsList.getSelectionModel().getSelectedItem();
			studentViewExam.SetSolvedExam(se);//Send student solved exam to the studentViewExam window.
			Globals.mainContainer.setScreen(ClientGlobals.StudentViewExamID);
			return;
		}

		//if student pressed to view solved exam and didn't choose any course name, he gets an error.
		else
		{
			Globals.popUp(AlertType.INFORMATION,"View exam Failed!","You have to choose exam first! ");
		}
	}
	
	
	
	/**
	 * In case that Logout button was pressed by student the main window is open so the user will fill his username and password again.
	 */
	public void logout(ActionEvent event)
	{
		UserController.logout();
	}




	
}
