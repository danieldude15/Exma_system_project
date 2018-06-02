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
import GUI.TeacherEditAddQuestion.windowType;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.*;
import ocsf.client.ClientGlobals;

public class TeacherManageExamsFrame implements Initializable, ControlledScreen {
	
	HashMap<String,Exam> exams = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Exam> dBExams;
	@FXML Button newExamB;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML VBox examsList;
	@FXML Button home;
	
	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(650);		
		
		teacherFieldsLoading();
		
		teacherCoursesLoading();		
		
		//loading teacher questions
		//dBExams =  ExamController.getTeachersExams((Teacher)ClientGlobals.client.getUser());
		if (dBExams!=null) {
			setQuestionsListInVBox();
		}
	}

	@Override public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML public void newExamButtonPressed(ActionEvent event) {
		/*
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.ADD);
		*/
		Globals.mainContainer.setScreen(ClientGlobals.TeacherBuildNewExamID);
	}
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	@FXML void filterByField(ActionEvent event) {
		if(fieldComboB.getSelectionModel().getSelectedItem()!=null) {
			examsList.getChildren().clear();
			Field selectefield = fieldComboB.getSelectionModel().getSelectedItem();
			courseComboB.getItems().clear();
			ObservableList<Course> list;
			ArrayList<Course> cousesInField = new ArrayList<>();
			if(selectefield.equals(new Field(-1,"All"))) {
				for(Exam e:dBExams) {
					exams.put(e.examIdToString(),e);
					examsList.getChildren().add(examAdder(e));
				}
			} else {
				for(Course c: teachersCourses) {
					if(c.getField()==null || c.getField().equals(selectefield)) {
						cousesInField.add(c);
					}
				}
				for(Exam e:dBExams) {
					if(e.getField().equals(selectefield)) {
						exams.put(e.examIdToString(),e);
						examsList.getChildren().add(examAdder(e));
					}
				}
			}
			list = FXCollections.observableArrayList(cousesInField);
			courseComboB.setItems(list);
		}
		System.out.println(courseComboB.getItems().toString());
	}
	
	@FXML public void filterByCourse(ActionEvent event) {
		if(courseComboB.getSelectionModel().getSelectedItem()!=null) {
			examsList.getChildren().clear();
			Course selectedCourse = courseComboB.getSelectionModel().getSelectedItem();
			 for (Exam e: dBExams) {
				 if(selectedCourse.equals(new Course(0,"All",null)) || e.isInCourse(selectedCourse)) {
					 exams.put(e.examIdToString(),e);
					 examsList.getChildren().add(examAdder(e));
				 }
			 }
		 }
	}
	
	private void setQuestionsListInVBox() {
		examsList.getChildren().clear();
		System.out.println(dBExams);
		for(Exam e:dBExams) {
			exams.put(e.examIdToString(),e);
			examsList.getChildren().add(examAdder(e));
		}
	}

	/**
	 * setting Course comboBox values by teachers assigned fields
	 * this comboBox is used to filter out different Questions from the ListView.
	 */
	private void teacherCoursesLoading() {
		ObservableList<Course> list;
		teachersCourses = CourseFieldController.getFieldsCourses(teachersFields); 
		if (teachersCourses==null) {
			teachersCourses.add(new Course(0,"No Courses for teacher.",null));
		} else {
			teachersCourses.add(0,new Course(0,"All",null));
		}
		list = FXCollections.observableArrayList(teachersCourses);
		courseComboB.setItems(list);
		
	}
	
	/**
	 * Setting comboBox of fields base on teachers assigned fields
	 */
	private void teacherFieldsLoading() {
		exams.clear();
		
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) {
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		} else {
			teachersFields.add(0,new Field(-1,"All"));
		}
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
	}
	
	private Node examAdder(Exam e) {
		//HBox main question container
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		//This VBox holds the question details
		VBox examInfo = new VBox();
		Label examString = new Label("Exam ID: "+e.examIdToString() + "Course:" + e.getCourse().getName() + "Field" + e.getField().getName() + "Question Count: " + e.getQuestionsInExam().size());
		examString.setId("blackLabel");
		examString.setWrapText(true);
		examInfo.getChildren().add(examString);
		Button edit = new Button("Edit");
		edit.setId(e.examIdToString());
		edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEditHandler());
		Button delete = new Button ("Delete");
		delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyDeleteHandler());
		delete.setId(e.examIdToString());
		examInfo.getChildren().add(edit);
		examInfo.getChildren().add(delete);
		hbox.getChildren().addAll(examInfo);
		return hbox;
	}

	private class MyEditHandler implements EventHandler<Event>{
        @Override public void handle(Event evt) {
           Exam exam = exams.get(((Control)evt.getSource()).getId());
           // removing the "All" field to avoid gettin nullPointerException in next window
           teachersCourses.remove(0);
           teachersFields.remove(0);
           /*
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setQuestion(question);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.EDIT);
           */
           Globals.mainContainer.setScreen(null);
        }
    }
	
	private class MyDeleteHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Deletion Confirmation");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete this question?\nThis Operation cannot be undone!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				Exam exam = exams.get(((Control)evt.getSource()).getId());
	        	int effectedRows = ExamController.deleteExam(exam);
	        	if(effectedRows>0) {
	        		alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Exam Deleted Succesfully");
	    			alert.setHeaderText("");
	        		alert.setContentText("Exam Info:"
	        				+ "\n" + exam +""
    						+ "\n\n Was deleted Successfully");
	        		alert.show();
	        		runOnScreenChange();
	        		System.out.println("Question Deleted!");
	        	} else {
	        		alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Deletion Error");
	    			alert.setHeaderText(null);
	        		alert.setContentText("Could not delete Exam\n"
	        				+ "This Exam is already in Use in an existing Solved Exam / Completed Exam.\n"
	        				+ "you may not delete Exams that other people are relaying on. ");
	        		alert.show();
	        	}
			} else {
			    System.out.println("user chose CANCEL or closed the dialog");
			}
        }
    }
	
}
