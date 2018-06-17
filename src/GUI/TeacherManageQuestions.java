/**
 * 
 */
package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
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

public class TeacherManageQuestions implements Initializable, ControlledScreen {
	
	HashMap<String,Question> questions = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	@FXML Button newQuestionB;
	@FXML ComboBox<Field> fieldComboB;
	@FXML ComboBox<Course> courseComboB;
	@FXML VBox questionsList;
	@FXML Button home;
	
	@Override public void runOnScreenChange() {
		teacherFieldsLoading();
		
		teacherCoursesLoading();		
		
		//loading teacher questions
		DBquestions =  QuestionController.getTeachersQuestions((Teacher)ClientGlobals.client.getUser());
		if (DBquestions!=null) {
			setQuestionsListInVBox();
		}
	}

	@Override public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML public void newQuestionButtonPressed(ActionEvent event) {
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.ADD);
		 Globals.mainContainer.setScreen(ClientGlobals.TeacherEditAddQuestionID);
	}
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	@FXML void filterByField(ActionEvent event) {
		if(fieldComboB.getSelectionModel().getSelectedItem()!=null) {
			questionsList.getChildren().clear();
			Field selectefield = fieldComboB.getSelectionModel().getSelectedItem();
			courseComboB.getItems().clear();
			ObservableList<Course> list;
			ArrayList<Course> cousesInField = new ArrayList<>();
			if(selectefield.equals(new Field(-1,"All"))) {
				for(Question q:DBquestions) {
					questions.put(q.questionIDToString(),q);
					questionsList.getChildren().add(questionAdder(q));
				}
			} else {
				for(Course c: teachersCourses) {
					if(c.getField()==null || c.getField().equals(selectefield)) {
						cousesInField.add(c);
					}
				}
				for(Question q:DBquestions) {
					if(q.getField().equals(selectefield)) {
						questions.put(q.questionIDToString(),q);
						questionsList.getChildren().add(questionAdder(q));
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
			questionsList.getChildren().clear();
			Course selectedCourse = courseComboB.getSelectionModel().getSelectedItem();
			 for (Question q: DBquestions) {
				 if(selectedCourse.equals(new Course(0,"All",null)) || q.isInCourse(selectedCourse)) {
					 questions.put(q.questionIDToString(),q);
					 questionsList.getChildren().add(questionAdder(q));
				 }
			 }
			 fieldComboB.getSelectionModel().select(selectedCourse.getField());
		 }
	}
	
	private void setQuestionsListInVBox() {
		questionsList.getChildren().clear();
		System.out.println(DBquestions);
		for(Question q:DBquestions) {
			questions.put(q.questionIDToString(),q);
			questionsList.getChildren().add(questionAdder(q));
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
			teachersCourses = new ArrayList<>();
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
		questions.clear();
		
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		if(teachersFields==null) {
			teachersFields = new ArrayList<>();
			teachersFields.add(new Field(-1,"You Have No Assigned Fields..."));
		} else {
			teachersFields.add(0,new Field(-1,"All"));
		}
		ObservableList<Field> list = FXCollections.observableArrayList(teachersFields);
		fieldComboB.setItems(list);
	}
	
	private Node questionAdder(Question q) {
		//HBox main question container
		HBox hbox = new HBox();
		hbox.setStyle(""
					+ "-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		hbox.setId("transbg");
		//This VBox holds the question details
		VBox questionInfo = new VBox();
		questionInfo.setId("transbg");
		Label questionString = new Label("Question: "+q.getQuestionString());
		questionString.setId("blackLabel");
		questionString.setWrapText(true);
		questionInfo.setMinWidth(300);
		questionInfo.setMaxWidth(300);
		Label qid = new Label("QID: "+q.questionIDToString());
		qid.setId("blackLabel");
		questionInfo.getChildren().add(qid);
		questionInfo.getChildren().add(questionString);
		RadioButton answers[] = new RadioButton[] {new RadioButton(q.getAnswer(1)),new RadioButton(q.getAnswer(2)),new RadioButton(q.getAnswer(3)),new RadioButton(q.getAnswer(4))};
		answers[q.getCorrectAnswerIndex()-1].setSelected(true);
		for(RadioButton r:answers) {
			r.setDisable(true);
			r.setWrapText(true);
			r.setId("blackLabel");
			questionInfo.getChildren().add(r);
		}
		
		// this HBox will hold the EditDelete buttons
		HBox questionEditDelete = new HBox();
		questionEditDelete.setId("transbg");
		questionEditDelete.setAlignment(Pos.BOTTOM_LEFT);
		questionEditDelete.setStyle("-fx-margin:20px");
		Button edit = new Button("Edit");
		edit.setId(q.questionIDToString());
		edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEditHandler());
		Button delete = new Button ("Delete");
		delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyDeleteHandler());
		delete.setId(q.questionIDToString());
		questionEditDelete.getChildren().add(edit);
		questionEditDelete.getChildren().add(delete);
		questionInfo.getChildren().add(questionEditDelete);
		
		// this VBox holds the course list assigned to this question
		VBox assignedCourses = new VBox();
		assignedCourses.setId("transbg");
		assignedCourses.setAlignment(Pos.TOP_RIGHT);
		Label courseTitle = new Label("Courses Assigned to Question:");
		courseTitle.setId("blackLabel");
		assignedCourses.getChildren().add(courseTitle);
		ListView<Course> courselist = new ListView<>();
		courselist.setMaxWidth(120);
		courselist.setMaxHeight(100);
		courselist.setDisable(true);
		courselist.setId("blackLabel");
		ObservableList<Course> list = FXCollections.observableArrayList(q.getCourses());
		courselist.setItems(list);
		assignedCourses.getChildren().add(courselist);
		
		hbox.getChildren().addAll(questionInfo,assignedCourses);
		hbox.setId("transbg");
		
		return hbox;
	}

	private class MyEditHandler implements EventHandler<Event>{
        @Override public void handle(Event evt) {
           Question question = questions.get(((Control)evt.getSource()).getId());
           // removing the "All" field to avoid gettin nullPointerException in next window
           teachersCourses.remove(0);
           teachersFields.remove(0);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setQuestion(question);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.EDIT);
           Globals.mainContainer.setScreen(ClientGlobals.TeacherEditAddQuestionID);
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
				Question question = questions.get(((Control)evt.getSource()).getId());
	        	int effectedRows = QuestionController.deleteQuestion(question);
	        	if(effectedRows>0) {
	        		alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Question Deleted Succesfully");
	    			alert.setHeaderText("");
	        		alert.setContentText("Question Info:"
	        				+ "\n" + question +""
    						+ "\n\n Was deleted Successfully");
	        		alert.show();
	        		runOnScreenChange();
	        		System.out.println("Question Deleted!");
	        	} else {
	        		alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Deletion Error");
	    			alert.setHeaderText(null);
	        		alert.setContentText("Could not delete question\n"
	        				+ "This question is already in Use in an existing Exam / Solved Exam / Completed Exam.\n"
	        				+ "you may not delete questions that other people are relaying on. ");
	        		alert.show();
	        	}
			} else {
			    System.out.println("user chose CANCEL or closed the dialog");
			}
        }
    }
	
}
