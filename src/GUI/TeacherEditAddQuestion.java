package GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Controllers.ControlledScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.VBox;
import logic.Course;
import logic.Field;
import logic.Globals;
import logic.Question;
import ocsf.client.ClientGlobals;

public class TeacherEditAddQuestion implements ControlledScreen, Initializable {
	enum windowType {
		EDIT,ADD
	}
	
	private windowType type = windowType.ADD;
	
	Question question=null;
	
	ArrayList<Field> teacherFields = new ArrayList<>();
	ArrayList<Course> teacherCourses = new ArrayList<>();
	HashMap<Integer, ArrayList<Course>> coursesInField = new HashMap<>();
	
	@FXML Label questionID;
	@FXML TextArea questionString;
	@FXML ToggleGroup answers;
	@FXML RadioButton answer1;
	@FXML RadioButton answer2;
	@FXML RadioButton answer3;
	@FXML RadioButton answer4;
	@FXML TextField ta1;
	@FXML TextField ta2;
	@FXML TextField ta3;
	@FXML TextField ta4;
	@FXML ComboBox<String> fields;
	@FXML VBox courseVbox;
	@FXML Button backB;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
	public void runOnScreenChange() {
		Globals.primaryStage.setHeight(656);
		Globals.primaryStage.setWidth(553);
		organizedFields();
		if (type.equals(windowType.ADD) || question==null) {
			questionID.setText("Add New Question");
			questionString.setText("");
			ta1.setText("");
			ta2.setText("");
			ta3.setText("");
			ta4.setText("");
		} else {
			questionID.setText("Edit Question: " + question.questionIDToString());
			questionString.setText(question.getQuestionString());
			ta1.setText(question.getAnswer(0));
			ta2.setText(question.getAnswer(1));
			ta3.setText(question.getAnswer(2));
			ta4.setText(question.getAnswer(3));
			int index = question.getCorrectAnswerIndex();
			switch(index) {
			case 0:
				answer1.setSelected(true);
				break;
			case 1:
				answer2.setSelected(true);
				break;
			case 2:
				answer3.setSelected(true);
				break;
			case 3:
				answer4.setSelected(true);
				break;
			}
		}
	}

	private void organizedFields() {
		coursesInField.clear();
		for(Course c:teacherCourses) {
			if (coursesInField.get(c.getField().getID()) != null){
				ArrayList<Course> arrayList = coursesInField.get(c.getField().getID());
				arrayList.add(c);
				coursesInField.put(c.getField().getID(), arrayList);
			} else {
				ArrayList<Course> arrayList = new ArrayList<>();
				arrayList.add(c);
				coursesInField.put(c.getField().getID(), arrayList);
			}
		}
		
		ArrayList<String> TF = new ArrayList<>();
		for(Field f:teacherFields) {
			TF.add(f.toString());
		}
		ObservableList<String> list = FXCollections.observableArrayList(TF);
		fields.setItems(list);
	}

	public void setQuestion(Question q) {
		question=q;
		System.out.println("Change Question to "+ q);
	}
	
	@FXML
	public void backToMenu(ActionEvent event) {
		Globals.mainContainer.setScreen(ClientGlobals.TeacherManageQuestionsID);
	}
	
	@FXML
	public void userSetField(ActionEvent event) {
		courseVbox.getChildren().clear();
		String Fieldid = fields.getSelectionModel().getSelectedItem().toString().split(" - ")[0];
		ArrayList<Course> arr = coursesInField.get(Integer.parseInt(Fieldid));
		if(arr!=null) {
			for (Course c:arr) {
				CheckBox cBox = new CheckBox(c.toString());
				courseVbox.getChildren().add(cBox);
			}
		}
	}
	
	public windowType getType() {
		return type;
	}

	public void setType(windowType type) {
		this.type = type;
	}

	public void setFieldsAndCourses(ArrayList<Course> teachersCourses, ArrayList<Field> teachersFields) {
		teacherFields=teachersFields;
		teacherCourses=teachersCourses;
	}
	
}
