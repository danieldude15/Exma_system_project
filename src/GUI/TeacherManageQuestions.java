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
	@FXML Button newQuestionB;
	@FXML ComboBox<String> fieldComboB;
	@FXML ComboBox<String> courseComboB;
	@FXML VBox questionsList;
	@FXML Button home;
	
	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(600);
		/**
		 * Setting comboBox of fields base on teachers assigned fields
		 */
		questions.clear();
		questionsList.getChildren().clear();
		teachersFields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		ArrayList<String> fieldStrings = new ArrayList<>();
		fieldStrings.add("All");
		for (Field f:teachersFields) {
			fieldStrings.add(f.toString());
		}
		ObservableList<String> list;
		if (fieldStrings.size()==1) {
			fieldStrings.remove(0);
			fieldStrings.add("You Have No Assigned Fields...");
		}
		list = FXCollections.observableArrayList(fieldStrings);
		fieldComboB.setItems(list);
		
		
		/**
		 * setting Course comboBox values by teachers assigned fields
		 * this comboBox is used to filter out different Questions from the ListView.
		 */
		teachersCourses = CourseFieldController.getFieldsCourses(teachersFields);;
		ArrayList<String> courseStrings = new ArrayList<>();
		courseStrings.add("All");
		for (Course c:teachersCourses) {
			courseStrings.add(c.toString());
		}
		if (courseStrings.size()==1) {
			courseStrings.remove(0);
			courseStrings.add("You Have No Assigned Courses...");
		}
		list = FXCollections.observableArrayList(courseStrings);
		courseComboB.setItems(list);
		
		
		ArrayList<Question> DBquestions =  QuestionController.getTeachersQuestions((Teacher)ClientGlobals.client.getUser());
		System.out.println(DBquestions);
		for(Question q:DBquestions) {
			questions.put(q.questionIDToString(),q);
			questionsList.getChildren().add(questionAdder(q));
		}
	}

	private Node questionAdder(Question q) {
		HBox hbox = new HBox();
		hbox.setStyle("-fx-border-color:black;"
					+ "-fx-border-radius:10px;"
					+ "-fx-padding:10px;");
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
		HBox questionEditDelete = new HBox();
		questionEditDelete.setAlignment(Pos.CENTER_RIGHT);
		questionEditDelete.setStyle("-fx-margin:20px");
		Button edit = new Button("Edit");
		edit.setId(q.questionIDToString());
		edit.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyEditHandler());
		Button delete = new Button ("Delete");
		delete.addEventHandler(MouseEvent.MOUSE_CLICKED, new MyDeleteHandler());
		delete.setId(q.questionIDToString());
		questionEditDelete.getChildren().add(edit);
		questionEditDelete.getChildren().add(delete);
		hbox.getChildren().addAll(questionInfo,questionEditDelete);
		
		
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
	@FXML
	public void handleMouseClick(MouseEvent event) {
		System.out.println("clicked");
		if (event.getButton()==MouseButton.SECONDARY) {
			final ContextMenu contextMenu = new ContextMenu();
			MenuItem edit = new MenuItem("Edit");
			MenuItem delete = new MenuItem("Delete");
			contextMenu.getItems().addAll(edit,delete);
			edit.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        System.out.println("edit...");
			    }
			});
			delete.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			        System.out.println("delete...");
			    }
			});
			final AnchorPane pane = new AnchorPane();
			contextMenu.show(pane, event.getScreenX(), event.getScreenY());
        }
	}
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

}
