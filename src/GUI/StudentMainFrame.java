package GUI;


import Controllers.ControlledScreen;
import Controllers.SolvedExamController;
import Controllers.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import ocsf.client.ClientGlobals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StudentMainFrame implements ControlledScreen{

	@FXML ListView<SolvedExam> solvedExamsList;
	@FXML Label welcome;
	@FXML Label username;
	@FXML Label userid;
	@FXML Pane userImage;
	
	//HashMap<Key(String CourseName),Value(String SolvedExam id)>
	private HashMap<String,String> courseNameAndExamId=new HashMap<String,String>();

	

	/*Do not delete! I keep it in case I will need it in the future.
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		courseNameAndExamId.clear();
		
		//Get all student solved exams from database and set it to the ListView field on window
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
				solveExamsFields.add("A"+"                                                        "
						+ "                                                    "+"99"+"\n");
				
			}
			Collections.sort(solveExamsFields);
			ObservableList<String> list;
			if (solveExamsFields.size()==1) //Student has not solve any exam yet.
			{
				solveExamsFields.remove(0);
				solveExamsFields.add("You Have No Assigned Solved Exams...");
			}
			list = FXCollections.observableArrayList(solveExamsFields);
			solvedExamsList.setItems(list);//Insert all student's solved exams(courseName+grade) into the ListView "solvedExamList"
			
		}
		setStudentInfo();
	}
	/*/
	

	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		courseNameAndExamId.clear();
		solvedExamsList.getItems().clear();
		
		//Get all student solved exams from database and set it to the ListView field on window
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExamsByUser((Student)ClientGlobals.client.getUser());
		ArrayList<SolvedExam> myAprovedSolvedExam = new ArrayList<>();
		for(SolvedExam se: mySolvedExam) {
			if(se.isTeacherApproved())
				myAprovedSolvedExam.add(se);
		}
		ObservableList<SolvedExam> solveds = FXCollections.observableArrayList(myAprovedSolvedExam);
		solvedExamsList.setItems(solveds);
		/*
		if(!(mySolvedExam.isEmpty()))//If student has already did at least one exam.
			{
			//solvedExamsList.getItems().add("All");
			for (SolvedExam s:mySolvedExam)//Add all student's solved exams to the ListView. 
			{	
				String courseName = s.getCourse().getName();
				String solvedExamGrade=Integer.toString(s.getScore());
				courseNameAndExamId.put(courseName, Integer.toString(s.getID()));
				if (s.isTeacherApproved())
					solvedExamsList.getItems().add(courseName+"                                                        "
						+ "                                          "+solvedExamGrade+"\n");
			}
		}
		else
			solvedExamsList.getItems().add("You Have No Assigned Solved Exams...");
		
		Collections.sort(solvedExamsList.getItems());//Sort the ListView by alphabet.
		/*/
		setStudentInfo();
		
		
		
	}
	
	
	
	
	
	
	/**
	 * Set student personal info on the window screen.
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
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExamsByUser((Student)ClientGlobals.client.getUser());
		StudentViewExamFrame studentViewExam = (StudentViewExamFrame) Globals.mainContainer.getController(ClientGlobals.StudentViewExamID);
		//if student choose course solved exam to view from list.
		if(solvedExamsList.getSelectionModel().getSelectedItem()!=null)  {
			SolvedExam se = solvedExamsList.getSelectionModel().getSelectedItem();
			studentViewExam.SetSolvedExam(se);//Send student solved exam to the studentViewExam window.
			Globals.mainContainer.setScreen(ClientGlobals.StudentViewExamID);
			return;
		}
		/*
		if((String)solvedExamsList.getSelectionModel().getSelectedItem()!=null &&
				!(((String)solvedExamsList.getSelectionModel().getSelectedItem()).equals("You Have No Assigned Solved Exams...")) 
				&& !((String)solvedExamsList.getSelectionModel().getSelectedItem()).equals("All"))
		{
			String[] CourseNameAndGrade=(String[])solvedExamsList.getSelectionModel().getSelectedItem().split(" ");
			
			for (SolvedExam s:mySolvedExam)
			{
				String ExamId= Integer.toString(s.getID());
				//if the Student pressed on some course from the list we check that the student actually did the exam on that course.
				if(s.getCourse().getName().equals(CourseNameAndGrade[0]) && ExamId.equals(courseNameAndExamId.get(CourseNameAndGrade[0])))
				{
					studentViewExam.SetSolvedExam(s);//Send student solved exam to the studentViewExam window.
					Globals.mainContainer.setScreen(ClientGlobals.StudentViewExamID);
					return;
				}

			}
			
		}
		/*/
		//if student pressed to view solved exam and didn't choose any course name, he gets an error.
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
	 * In case that Logout button was pressed by student the main window is open so the user will fill his username and password again.
	 */
	public void logout(ActionEvent event)
	{
		UserController.logout();
	}




	
}
