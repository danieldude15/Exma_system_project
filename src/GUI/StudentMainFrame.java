package GUI;


import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import logic.Field;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.ClientGlobals;

public class StudentMainFrame implements ControlledScreen {

	@FXML ListView<String> solvedExamsList;
	@FXML Label welcome;
	@FXML Label username;
	@FXML Label userid;
	@FXML Pane userImage;
	private HashMap<String,String> courseNameAndExamId=new HashMap<String,String>();
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(630);
		Globals.primaryStage.setWidth(820);	
		//courseNameAndExamId.clear();
		
		/*Get all student solved exams from database and set it to the ListView field on window/*/
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExams((Student)ClientGlobals.client.getUser());
		ArrayList<String> solveExamsFields= new ArrayList<String>();
		if(mySolvedExam!=null)
			{
			solveExamsFields.add("All");
			for (SolvedExam s:mySolvedExam) {
				
				String courseName = s.getCourse().getName();
				String solvedExamGrade=Integer.toString(s.getScore());
				courseNameAndExamId.put(courseName, Integer.toString(s.getID()));
				solveExamsFields.add(courseName+"                                                        "
						+ "                                                    "+solvedExamGrade+"\n");
				
			}
			
			ObservableList<String> list;
			if (solveExamsFields.size()==1) {
				solveExamsFields.remove(0);
				solveExamsFields.add("You Have No Assigned Solved Exams...");
			}
			list = FXCollections.observableArrayList(solveExamsFields);
			solvedExamsList.setItems(list);
		}
		/*Get student personal info from database and set it beneath the TabPane "My info" on window/*/
		Student s=(Student)ClientGlobals.client.getUser();
		welcome.setText("Welcome: "+s.getName());
		username.setText("UserName: "+s.getUserName());
		userid.setText("UserID: "+s.getID());
		userImage.setStyle("-fx-background-image: url(\"resources/profile/"+s.getID()+".PNG\");"
				+ "-fx-background-size: 150px 150px;"
				+ "-fx-background-repeat: no-repeat;");
	}
	
	/**
	 * In case that take exam button was pressed by student the start exam window opens.
	 */
	public void TakeExamButtonPressed(ActionEvent event)
	{
		Globals.mainContainer.setScreen(ClientGlobals.StudentStartExamID);
	}
	/**
	 * In case that view exam button was pressed by student the chosen solved exam opens.
	 */
	public void ViewExamButtonPressed(ActionEvent event)
	{
		Alert alert;
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExams((Student)ClientGlobals.client.getUser());
		StudentViewExamFrame studentViewExam = (StudentViewExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentViewExamID);
		//if student choose course solved exam to view from list.
		if((String)solvedExamsList.getSelectionModel().getSelectedItem()!=null &&
				!(((String)solvedExamsList.getSelectionModel().getSelectedItem()).equals("You Have No Assigned Solved Exams...")) 
				&& !((String)solvedExamsList.getSelectionModel().getSelectedItem()).equals("All"))
		{
			String[] CourseNameAndGrade=(String[])solvedExamsList.getSelectionModel().getSelectedItem().split(" ");
			
			for (SolvedExam s:mySolvedExam)
			{
				String ExamId= Integer.toString(s.getID());
				//if Student press on some course on list we check that student is actually did the exam on that course
				if(s.getCourse().getName().equals(CourseNameAndGrade[0]) && ExamId.equals(courseNameAndExamId.get(CourseNameAndGrade[0]))) 
				{
					studentViewExam.SetSolvedExam(s);
					break;
				}
				
			}
			Globals.mainContainer.setScreen(ClientGlobals.StudentViewExamID);
		}
		//if student pressed to view course solved exam and didn't choose it he gets an error.
		else
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("View exam Failed!");
			alert.setHeaderText(null);
			alert.setContentText("You have to choose exam first! ");
			alert.showAndWait();
			
		}
	}
	
	
	/**
	 * In case that Logout button was pressed by student the main window is open so the user will put his username and password again.
	 */
	public void logout(ActionEvent event)
	{
		UserController.logout();
	}

	
}
