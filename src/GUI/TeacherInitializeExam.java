package GUI;

import java.util.ArrayList;
import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import logic.*;
import ocsf.client.ClientGlobals;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;




public class TeacherInitializeExam implements ControlledScreen {


	
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
		
		examsList.getItems().clear();
		fieldComboB.getItems().clear();
		courseComboB.getItems().clear();
		teacherFieldsLoading();
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
		if(examsList.getSelectionModel().getSelectedItem()!=null)
		{
		   ((TeacherViewExam)Globals.mainContainer.getController(ClientGlobals.TeacherViewExamID)).setExam((Exam) examsList.getSelectionModel().getSelectedItem());
           Globals.mainContainer.setScreen(ClientGlobals.TeacherViewExamID);
		}
		}        
   
	@FXML
    public void MyActiveHandler(ActionEvent event) {
		if(examsList.getSelectionModel().getSelectedItem()!=null) {
              ((TeacherActivateExamFrame)Globals.mainContainer.getController(ClientGlobals.ActiveExamID)).setExam((Exam) examsList.getSelectionModel().getSelectedItem());
               Globals.mainContainer.setScreen(ClientGlobals.ActiveExamID);
		}
			 	}
		
	@FXML
    public void CancelButtonPressed(ActionEvent event)
		    {
		     Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
		    }

	}

