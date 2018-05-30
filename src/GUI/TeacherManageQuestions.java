/**
 * 
 */
package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.print.attribute.standard.PrinterName;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Question;
import logic.Teacher;
import ocsf.client.ClientGlobals;

/**
 * @author cky80
 *
 */
public class TeacherManageQuestions implements Initializable, ControlledScreen {
	
	HashMap<String,Question> questions = new HashMap<>();
	ArrayList<Field> teachersFields;
	ArrayList<Course> teachersCourses;
	ArrayList<Question> DBquestions;
	@FXML Button newQuestionB;
	@FXML ComboBox<String> fieldComboB;
	@FXML ComboBox<String> courseComboB;
	@FXML VBox questionsList;
	@FXML Button home;
	
	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(650);		
		
		teacherFieldsLoading();
		
		teacherCoursesLoading();		
		
		//loading teacher questions
		DBquestions =  QuestionController.getTeachersQuestions((Teacher)ClientGlobals.client.getUser());
		if (DBquestions!=null) {
			setQuestionsListInVBox();
		}
	}

	/**
	 * setting Course comboBox values by teachers assigned fields
	 * this comboBox is used to filter out different Questions from the ListView.
	 */
	private void teacherCoursesLoading() {
		ObservableList<String> list;
		teachersCourses = CourseFieldController.getFieldsCourses(teachersFields);
		ArrayList<String> courseStrings = new ArrayList<>();
		if (teachersCourses==null) {
			courseStrings.add("You Have No Assigned Courses...");
			
		} else {
			courseStrings.add("All");
			for (Course c:teachersCourses) {
				courseStrings.add(c.toString());
			}
		}
		list = FXCollections.observableArrayList(courseStrings);
		courseComboB.setItems(list);
		
	}
	
	/**
	 * Setting comboBox of fields base on teachers assigned fields
	 */
	private void teacherFieldsLoading() {
		questions.clear();
		
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		ArrayList<String> fieldStrings = new ArrayList<>();
		if(teachersFields==null) {
			fieldStrings.add("You Have No Assigned Fields...");
		} else {
			fieldStrings.add("All");
			for (Field f:teachersFields) {
				fieldStrings.add(f.toString());
			}
		}
		ObservableList<String> list;
		list = FXCollections.observableArrayList(fieldStrings);
		fieldComboB.setItems(list);
	}
	
	private Node questionAdder(Question q) {
		//HBox main question container
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
		
		//This VBox holds the question details
		VBox questionInfo = new VBox();
		Label questionString = new Label("Question: "+q.getQuestionString());
		questionString.setWrapText(true);
		questionInfo.getChildren().add(new Label("QID: "+q.questionIDToString()));
		questionInfo.getChildren().add(questionString);
		RadioButton answers[] = new RadioButton[] {new RadioButton(q.getAnswer(0)),new RadioButton(q.getAnswer(1)),new RadioButton(q.getAnswer(2)),new RadioButton(q.getAnswer(3))};
		answers[q.getCorrectAnswerIndex()].setSelected(true);
		for(RadioButton r:answers) {
			r.setDisable(true);
			r.setWrapText(true);
			questionInfo.getChildren().add(r);
		}
		
		// this HBox will hold the EditDelete buttons
		HBox questionEditDelete = new HBox();
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
		assignedCourses.getChildren().add(new Label("Courses Assigned to Question:"));
		ListView<String> courselist = new ListView<>();
		courselist.setMaxWidth(120);
		courselist.setMaxHeight(100);
		courselist.setDisable(true);
		ArrayList<String> al = new ArrayList<>();
		for(Course c : q.getCourses()) {
			al.add(c.toString());
		}
		ObservableList<String> list = FXCollections.observableArrayList(al);
		courselist.setItems(list);
		assignedCourses.getChildren().add(courselist);
		
		hbox.getChildren().addAll(questionInfo,assignedCourses);
		
		
		return hbox;
	}

	private class MyEditHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
           Question question = questions.get(((Control)evt.getSource()).getId());
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setQuestion(question);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
           ((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.EDIT);
           Globals.mainContainer.setScreen(ClientGlobals.TeacherEditAddQuestionID);
        }
    }
	
	private class MyDeleteHandler implements EventHandler<Event>{
        @Override
        public void handle(Event evt) {
           System.out.println(((Control)evt.getSource()).getId());
        }
    }
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	public void newQuestionButtonPressed(ActionEvent event) {
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setFieldsAndCourses(teachersCourses,teachersFields);
		((TeacherEditAddQuestion)Globals.mainContainer.getController(ClientGlobals.TeacherEditAddQuestionID)).setType(windowType.ADD);
		 Globals.mainContainer.setScreen(ClientGlobals.TeacherEditAddQuestionID);
	}
	
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

	@FXML void filterByField(ActionEvent event) {
		if(fieldComboB.getSelectionModel().getSelectedItem()!=null) {
			String selectedField = fieldComboB.getSelectionModel().getSelectedItem().toString().split(" ")[0];
			courseComboB.getItems().clear();
			ArrayList<String> al = new ArrayList<>();
			ObservableList<String> list;
			if(selectedField.equals("All")) {
				for(Course c : teachersCourses) {
					 al.add(c.toString());
				}
				setQuestionsListInVBox();
			} else {
				int fieldid = Integer.parseInt(selectedField);
				al.add("All");
				for(Course c: teachersCourses) {
					if(c.getId()==fieldid) {
						al.add(c.toString());
					}
				}
				questionsList.getChildren().clear();
				for(Question q:DBquestions) {
					if(q.getField().getID()==fieldid) {
						questions.put(q.questionIDToString(),q);
						questionsList.getChildren().add(questionAdder(q));
					}
				}
			}
			list = FXCollections.observableArrayList(al);
			courseComboB.setItems(list);
		}
		System.out.println(courseComboB.getItems().toString());
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@FXML public void filterByCourse(ActionEvent event) {
		if(courseComboB.getSelectionModel().getSelectedItem()!=null) {
			String selectedCourse = courseComboB.getSelectionModel().getSelectedItem().toString().split(" ")[0];
			//courseComboB.getItems().clear();
			ArrayList<String> al = new ArrayList<>();
			ObservableList<String> list;
			if(selectedCourse.equals("All")) {
				HashMap<String, String> addedQuestions = new HashMap<>();
				for(Object o : courseComboB.getItems().toArray()) {
					String c = (String)o;
					 int courseId = Course.parseID(c.split(" - ")[0])[1];
					 int courseFieldId = Course.parseID(c.split(" - ")[0])[0];
					 Course curCourse=null;
					 for(Course course:teachersCourses) {
						 if(course.getId()==courseId && course.getField().getID()==courseFieldId) {
							 curCourse=course;
							 break;
						 }
					 }
					 for (Question q: DBquestions) {
						 if(q.isInCourse(curCourse)) {
							 if(addedQuestions.get(q.questionIDToString()) == null) {
								 questionsList.getChildren().add(questionAdder(q));
								 addedQuestions.put(q.questionIDToString(), "Added");
							 }
						 }
					 }
				}
			} else {
				int courseId = Course.parseID(selectedCourse)[1];
				int courseFieldId = Course.parseID(selectedCourse)[0];
				for(Course course:teachersCourses) {
					 if(course.getId()==courseId && course.getField().getID()==courseFieldId) {
						 //curCourse=course;
						 break;
					 }
				 }
				 for (Question q: DBquestions) {
					 /*if(q.isInCourse(curCourse)) {
						 if(addedQuestions.get(q.questionIDToString()) == null) {
							 questionsList.getChildren().add(questionAdder(q));
							 addedQuestions.put(q.questionIDToString(), "Added");
						 }
					 }*/
				 }
			}
			list = FXCollections.observableArrayList(al);
			courseComboB.setItems(list);
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
}
