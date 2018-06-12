package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import logic.*;
import ocsf.client.ClientGlobals;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;




public class InitializeExam implements Initializable, ControlledScreen {


	
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML ListView<Exam> examsList;
	@FXML Label examin;
	@FXML Button Cancel;
	@FXML Button viewid;
	@FXML Button activeid;
	Exam exam;
	ArrayList<Exam> DBexam;
	Course publicCourse;
	Field publicField;
	
	
	@Override
	public void runOnScreenChange() {
		
		Globals.primaryStage.setHeight(670);
		Globals.primaryStage.setWidth(745);
		teacherFieldsLoading();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	private void teacherFieldsLoading() {
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) 
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
	}

	@FXML 
	public void filterByField(ActionEvent event) {
			if(fieldComboB.getSelectionModel().getSelectedItem()!=null) 
			{
			courseComboB.getItems().clear();
		    publicField=fieldComboB.getSelectionModel().getSelectedItem();
			ObservableList<Course> list;
			teachersCourses = CourseFieldController.getFieldCourses(publicField);
			list = FXCollections.observableArrayList(teachersCourses);
			courseComboB.setItems(list);
		}
		System.out.println(courseComboB.getItems().toString());
		
	}
	
	@FXML 
	public void filterByCourse(ActionEvent event) 
	{
		if(courseComboB.getSelectionModel().getSelectedItem()!=null) 
		{
			examsList.getItems().clear();
			publicCourse=courseComboB.getSelectionModel().getSelectedItem();
			examin.setText(String.format("Exam in: %s in %s",publicCourse.getName(),publicField.getName()));
			 DBexam =  ExamController.getcourseExams(publicCourse);
			 ObservableList<Exam> exams = FXCollections.observableArrayList(DBexam);
				if (exams!=null) 
					examsList.setItems(exams);
		}			 
	 }
    
	@FXML
    public void MyViewHandler (ActionEvent event) {
		   ((TeacherViewExam)Globals.mainContainer.getController(ClientGlobals.TeacherViewExamID)).setExam((Exam) examsList.getSelectionModel().getSelectedItem());
           Globals.mainContainer.setScreen(ClientGlobals.TeacherViewExamID);
		}        
   
	@FXML
    public void MyActiveHandler(ActionEvent event) {
				
              ((TeacherActivateExamFrame)Globals.mainContainer.getController(ClientGlobals.ActiveExamID)).setExam((Exam) examsList.getSelectionModel().getSelectedItem());
               Globals.mainContainer.setScreen(ClientGlobals.ActiveExamID);
			 	}
		
	@FXML
    public void CancelButtonPressed(ActionEvent event)
		    {
		     Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
		    }

	}

