/**
 * 
 */
package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import Controllers.CourseFieldController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Teacher;
import ocsf.client.ClientGlobals;

/**
 * @author cky80
 *
 */
public class TeacherManageQuestions implements Initializable, ControlledScreen {

	@FXML Button newQuestionB;
	@FXML ComboBox<String> fieldComboB;
	@FXML ComboBox<String> courseComboB;
	@FXML ListView<String> questionList;
	@FXML Button home;
	
	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(700);
		Globals.primaryStage.setWidth(600);
		/**
		 * Setting comboBox of fields base on teachers assigned fields
		 */
		ArrayList<Field> fields = CourseFieldController.getTeacherFields((Teacher) ClientGlobals.client.getUser());
		ArrayList<String> fieldStrings = new ArrayList<>();
		fieldStrings.add("All");
		for (Field f:fields) {
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
		ArrayList<Course> courses = CourseFieldController.getFieldsCourses(fields);;
		ArrayList<String> courseStrings = new ArrayList<>();
		courseStrings.add("All");
		for (Course c:courses) {
			courseStrings.add(c.toString());
		}
		if (courseStrings.size()==1) {
			courseStrings.remove(0);
			courseStrings.add("You Have No Assigned Courses...");
		}
		list = FXCollections.observableArrayList(courseStrings);
		courseComboB.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	@FXML
	public void newQuestionButtonPressed(ActionEvent event) {
		
	}
	@FXML
	public void activeExamsListViewClicked(ActionEvent event) {
		
	}
	
	@FXML void BackToMainMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherMainID);
	}

}
