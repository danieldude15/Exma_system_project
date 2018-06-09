package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import Controllers.ExamController;
import Controllers.QuestionController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import logic.*;
import ocsf.client.ClientGlobals;


public class InitializeExam implements Initializable, ControlledScreen {

	HashMap<String, Button> views = new HashMap<>();
	HashMap<String, Button> actives = new HashMap<>();
	HashMap<String,Exam> exams = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML VBox examsList;
	@FXML Label examin;
	@FXML Button Cancel;
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
			Field publicField=fieldComboB.getSelectionModel().getSelectedItem();
			ObservableList<Course> list;
			courseComboB.getItems().clear();
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
			publicCourse=courseComboB.getSelectionModel().getSelectedItem();
			 DBexam =  ExamController.getcourseExams(publicCourse);
				if (DBexam!=null) 
					setExamListInVBox();
		}			 
	 }
	
	private void setExamListInVBox() {
		examsList.getChildren().clear();
		examin.setText("Exam in:" +publicCourse.getName()+ "'\'" + publicField.getName() );
		System.out.println(DBexam);
		for(Exam e:DBexam) {
			exams.put(e.examIdToString(),e);
			examsList.getChildren().add(examAdder(e));
		}
	
	}
	
	private Node examAdder(Exam e) {
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		hbox.setPrefHeight(36);
		hbox.setPrefWidth(683);
		Label eid = new Label("EID: "+e.examIdToString());
		eid.setId("blackLabel");
		Label authorString = new Label("Author: "+e.getAuthor().getName());
		authorString.setId("blackLabel");
		authorString.setWrapText(true);
		Label durationstring = new Label("Duration: "+e.getDuration());
		durationstring.setId("blackLabel");
		
		Button view = new Button("View");
		view.setId(e.examIdToString());
		view.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyViewHandler());
		views.put(e.examIdToString(),view);
		Button active = new Button ("Active");
		active.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyActiveHandler());
		active.setId(e.examIdToString());
		actives.put(e.examIdToString(),active);
		
		hbox.getChildren().addAll(eid,authorString,durationstring,view,active);
								
				return hbox;
	}
	private class MyViewHandler implements EventHandler<Event>{
        @Override public void handle(Event evt) {
          Exam exam = exams.get(((Control)evt.getSource()).getId());
           ((TeacherViewExam)Globals.mainContainer.getController(ClientGlobals.TeacherViewExamID)).setExam(exam);
           Globals.mainContainer.setScreen(ClientGlobals.TeacherViewExamID);
        }
    }
	
	private class MyActiveHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
        	   Exam exam = exams.get(((Control)evt.getSource()).getId());
              // ((ActiveExam)Globals.mainContainer.getController(ClientGlobals.ActiveExamID)).setExam(exam);
               Globals.mainContainer.setScreen(ClientGlobals.ActiveExamID);
        	}
	}
}
