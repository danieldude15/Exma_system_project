package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import Controllers.ControlledScreen;
import Controllers.ExamController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import logic.Course;
import logic.Exam;
import logic.Field;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

public class TeacherManageExamsFrame implements ControlledScreen {
	enum windowType {
		EDIT,Build
	}
	
	HashMap<String,Exam> exams = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Exam> dBExams;
	@FXML Button newExamB;
	@FXML Button home;
	@FXML GridPane examListGrid;
	
	@Override public void runOnScreenChange() {
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(620);			
		//loading teacher questions
		dBExams =  ExamController.getTeachersExams((Teacher)ClientGlobals.client.getUser());
		if (dBExams!=null) {
			setExamsListInVBox();
		}
	}

	
	@FXML public void newExamButtonPressed(ActionEvent event) {
		 ((TeacherBuildNewExam)Globals.mainContainer.getController(ClientGlobals.TeacherBuildNewExamID)).setType((TeacherBuildNewExam.windowType.Build));
		  Globals.mainContainer.setScreen(ClientGlobals.TeacherBuildNewExamID);
	}
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	private void setExamsListInVBox() {
		examListGrid.getChildren().clear();
		System.out.println(dBExams);
		int i=0,j=0;		
		for(Exam e:dBExams) {
			exams.put(e.examIdToString(),e);
			//examsList.getChildren().add(examAdder(e));
			if(j<9)
				examListGrid.add(examAdder(e),i,j);
			i=(i+1)%2;
			if(i==0)j++;
		}
	}

	private Node examAdder(Exam e) {
		//HBox main question container
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		//This VBox holds the question details
		HBox examInfo = new HBox();
		Label examString = new Label("Exam ID: "+e.examIdToString() + "\nCourse: " + e.getCourse().getName() + "\nField: " + e.getField().getName() + "\nQuestion Count: " + e.getQuestionsInExam().size());
		examString.setStyle("-fx-padding: 10px;");
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
        	if(exams.get(((Control)evt.getSource()).getId())!=null)
        	{
           Exam exam = exams.get(((Control)evt.getSource()).getId());
           // removing the "All" field to avoid gettin nullPointerException in next window
           teachersCourses.remove(0);
           teachersFields.remove(0);
           
           ((TeacherBuildNewExam)Globals.mainContainer.getController(ClientGlobals.TeacherBuildNewExamID)).setType((TeacherBuildNewExam.windowType.EDIT));
           ((TeacherBuildNewExam)Globals.mainContainer.getController(ClientGlobals.TeacherBuildNewExamID)).setExam(exam);
           Globals.mainContainer.setScreen(ClientGlobals.TeacherBuildNewExamID);
        	}
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
