package GUI;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.SolvedExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import logic.Field;
import logic.Globals;
import logic.SolvedExam;
import logic.Student;
import logic.Teacher;
import logic.iMessage;
import ocsf.client.ClientGlobals;

public class StudentMainFrame implements ControlledScreen {

	@FXML ListView<String> solvedExamsList;
	@FXML Tab studentInfo;
	
	@Override
	public void runOnScreenChange() {
		// TODO Auto-generated method stub
		Globals.primaryStage.setHeight(750);
		Globals.primaryStage.setWidth(820);
		
		/*Get all student solved exams from database and set it to the ListView field on window/*/
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExams((Student)ClientGlobals.client.getUser());
		ArrayList<String> solveExamsFields= new ArrayList<String>();
		solveExamsFields.add("All");
		for (SolvedExam s:mySolvedExam) {
			String examId = Integer.toString(s.getID());
			String examGrade=Integer.toString(s.getScore());
			solveExamsFields.add(examId+"                             "+examGrade);
		}
		ObservableList<String> list;
		if (solveExamsFields.size()==1) {
			solveExamsFields.remove(0);
			solveExamsFields.add("You Have No Assigned Solved Exams...");
		}
		list = FXCollections.observableArrayList(solveExamsFields);
		solvedExamsList.setItems(list);
		
		/*Get student personal info from database and set it beneath the TabPane "My info" on window/*/
		Student s=(Student)ClientGlobals.client.getUser();
		studentInfo.setText("Hello "+s.getUserName()+"\n"+"Name: "+s.getName()+"\n"+"Id: "+s.getID());
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
	public void ViewExamButtonPressed(ActionEvent event)//Itzik.this methood is not finished yet! Do not touch!
	{
		/*
		String chosenSolvedExam;
		SolvedExam viewExamFromDatabase;
		if(solvedExamsList.getSelectionModel().getSelectedItem()!=null)
			viewExamFromDatabase=SolvedExamController.getSolvedExam((String)solvedExamsList.getSelectionModel().getSelectedItem());
		/*/
		SolvedExam chosenViewExam;
		ArrayList<SolvedExam> mySolvedExam = SolvedExamController.getSolvedExams((Student)ClientGlobals.client.getUser());
		for (SolvedExam s:mySolvedExam)
		{
			String examId = Integer.toString(s.getID());
			if(examId==(String)solvedExamsList.getSelectionModel().getSelectedItem())
			{
				chosenViewExam=new SolvedExam(s);
			}
		}
	}
	
	/**
	 * In case that Logout button was pressed by student the main window is open so the user will put his username and password again.
	 */
	public void LogoutButtonPressed(ActionEvent event)
	{
		
		try {
			if (ClientGlobals.client!=null)
				ClientGlobals.client.sendToServer(new iMessage("logout",ClientGlobals.client.getUser()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globals.mainContainer.setScreen(ClientGlobals.LogInID);
	}

	
}
